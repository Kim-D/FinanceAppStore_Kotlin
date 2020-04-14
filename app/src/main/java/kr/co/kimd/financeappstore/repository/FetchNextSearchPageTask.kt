package kr.co.kimd.financeappstore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.co.kimd.financeappstore.api.*
import kr.co.kimd.financeappstore.db.BookSearchDb
import kr.co.kimd.financeappstore.vo.BookSearchResult
import kr.co.kimd.financeappstore.vo.Resource
import java.io.IOException

class FetchNextSearchPageTask constructor(
    private val query: String,
    private val bookSearchService: BookSearchService,
    private val db: BookSearchDb
): Runnable {
    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData: LiveData<Resource<Boolean>> = _liveData

    override fun run() {
        val current = db.bookDao().findSearchResult(query)
        if(current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.next
        val size = 30
        if(nextPage == null) {
            _liveData.postValue(Resource.success(false))
            return
        }
        val newValue = try {
            val response = bookSearchService.searchBooks(query, nextPage, size).execute()
            when(val apiResponse = ApiResponse.create(response)) {
                is ApiSuccessResponse -> {
                    // merge all repo ids 1 list to make it easier to fetch
                    val ids = arrayListOf<Int>()
                    ids.addAll(current.bookIds)

                    ids.addAll(apiResponse.body.items.map { it.id })
                    val merged = BookSearchResult(
                        query, ids,
                        apiResponse.body.total, apiResponse.nextPage
                    )
                    db.runInTransaction {
                        db.bookDao().insert(merged)
                        db.bookDao().insertBooks(apiResponse.body.items)
                    }
                    Resource.success(apiResponse.nextPage != null)
                }
                is ApiEmptyResponse -> {
                    Resource.success(false)
                }
                is ApiErrorResponse -> {
                    Resource.error(apiResponse.errorMessage, true)
                }
            }

        } catch (e: IOException) {
            Resource.error(e.message!!, true)
        }
        _liveData.postValue(newValue)
    }
}