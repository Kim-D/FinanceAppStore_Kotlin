package kr.co.kimd.financeappstore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import kr.co.kimd.financeappstore.AppExecutors
import kr.co.kimd.financeappstore.api.ApiResponse
import kr.co.kimd.financeappstore.api.ApiSuccessResponse
import kr.co.kimd.financeappstore.api.BookSearchResponse
import kr.co.kimd.financeappstore.api.BookSearchService
import kr.co.kimd.financeappstore.db.BookDao
import kr.co.kimd.financeappstore.db.BookSearchDb
import kr.co.kimd.financeappstore.util.AbsentLiveData
import kr.co.kimd.financeappstore.util.RateLimiter
import kr.co.kimd.financeappstore.vo.Book
import kr.co.kimd.financeappstore.vo.BookSearchResult
import kr.co.kimd.financeappstore.vo.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
//@OpenForTesting
class BookRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: BookSearchDb,
    private val bookDao: BookDao,
    private val bookSearchService: BookSearchService
) {
    private val bookListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadBooks(title: String): LiveData<Resource<List<Book>>> {
        return object : NetworkBoundResource<List<Book>, List<Book>>(appExecutors) {
            override fun saveCallResult(item: List<Book>) {
                bookDao.insertBooks(item)
            }

            override fun shouldFetch(data: List<Book>?): Boolean {
                return data == null || data.isEmpty() || bookListRateLimit.shouldFetch(title)
            }

            override fun loadFromDb(): LiveData<List<Book>> = bookDao.loadByTitle(title)

            override fun createCall(): LiveData<ApiResponse<List<Book>>> = bookSearchService.searchBooksByTitle(title)

            override fun onFetchFailed() {
                bookListRateLimit.reset(title)
            }
        }.asLiveData()
    }

    fun searchNextPage(query: String): LiveData<Resource<Boolean>> {
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
            query = query,
            bookSearchService = bookSearchService,
            db = db
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }

    fun search(query: String): LiveData<Resource<List<Book>>> {
        return object : NetworkBoundResource<List<Book>, BookSearchResponse>(appExecutors) {

            override fun saveCallResult(item: BookSearchResponse) {
                val bookIds = item.items.map { it.id }
                val bookSearchResult = BookSearchResult(
                    query = query,
                    bookIds = bookIds,
                    totalCount = item.total,
                    next = item.nextPage
                )
                db.runInTransaction {
                    bookDao.insertBooks(item.items)
                    bookDao.insert(bookSearchResult)
                }
            }

            override fun shouldFetch(data: List<Book>?) = data == null

            override fun loadFromDb(): LiveData<List<Book>> {
                return bookDao.search(query).switchMap { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        bookDao.loadOrdered(searchData.bookIds)
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<BookSearchResponse>> = bookSearchService.searchBooks(query)

            override fun processResponse(response: ApiSuccessResponse<BookSearchResponse>): BookSearchResponse {
                val body = response.body
                body.nextPage = response.nextPage
                return body
            }
        }.asLiveData()
    }
}