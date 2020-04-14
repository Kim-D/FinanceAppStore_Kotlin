package kr.co.kimd.financeappstore.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kr.co.kimd.financeappstore.db.BookSearchTypeConverters
import java.util.*

@Entity(primaryKeys = ["id"])
@TypeConverters(BookSearchTypeConverters::class)
data class Book(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("contents")
    val contents: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("isbn")
    val isbn: String,
    @field:SerializedName("datetime")
    val datetime: Date,
    @field:SerializedName("authors")
    val authors: List<String>,
    @field:SerializedName("publisher")
    val publisher: String,
    @field:SerializedName("translators")
    val translators: List<String>,
    @field:SerializedName("price")
    val price: Int,
    @field:SerializedName("sale_price")
    val sale_price: Int,
    @field:SerializedName("thumbnail")
    val thumbnail: String,
    @field:SerializedName("status")
    val status: String
)