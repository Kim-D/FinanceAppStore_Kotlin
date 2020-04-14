package kr.co.kimd.financeappstore.api

import com.google.gson.annotations.SerializedName
import kr.co.kimd.financeappstore.vo.Book

data class BookSearchResponse(
    @SerializedName("total_count")
    val total: Int = 0,
    @SerializedName("items")
    val items: List<Book>
) {
    var nextPage: Int? = null
}
