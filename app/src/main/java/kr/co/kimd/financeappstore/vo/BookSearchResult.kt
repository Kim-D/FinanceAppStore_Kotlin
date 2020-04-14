package kr.co.kimd.financeappstore.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import kr.co.kimd.financeappstore.db.BookSearchTypeConverters

@Entity(primaryKeys = ["query"])
@TypeConverters(BookSearchTypeConverters::class)
data class BookSearchResult(
    val query: String,
    val bookIds: List<Int>,
    val totalCount: Int,
    val next: Int?
)
