package kr.co.kimd.financeappstore.api

import androidx.lifecycle.LiveData
import kr.co.kimd.financeappstore.vo.Book
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookSearchService {
    @GET("v3/search/book")
    fun searchBooksByTitle(@Query("title") title: String): LiveData<ApiResponse<List<Book>>>

    @GET("v3/search/book")
    fun searchBooks(@Query("query") query: String, @Query("page") page: Int, @Query("size") size: Int): Call<BookSearchResponse>

    @GET("search/books")
    fun searchBooks(@Query("q") query: String): LiveData<ApiResponse<BookSearchResponse>>
}