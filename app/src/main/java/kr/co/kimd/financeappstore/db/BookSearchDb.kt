package kr.co.kimd.financeappstore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.co.kimd.financeappstore.vo.Book
import kr.co.kimd.financeappstore.vo.BookSearchResult

@Database(
    entities = [
        Book::class,
        BookSearchResult::class
    ],
    version = 3,
    exportSchema = false
)
abstract class BookSearchDb: RoomDatabase() {
    abstract fun bookDao(): BookDao
}