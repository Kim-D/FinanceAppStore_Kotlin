package kr.co.kimd.financeappstore.db

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.kimd.financeappstore.vo.Book
import kr.co.kimd.financeappstore.vo.BookSearchResult

@Dao
abstract class BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(book: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: BookSearchResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBooks(books: List<Book>)

    @Query("SELECT * FROM BookSearchResult WHERE `query` = :query")
    abstract fun search(query: String): LiveData<BookSearchResult?>

    @Query("SELECT * FROM book WHERE title = :title")
    abstract fun loadByTitle(title: String): LiveData<List<Book>>

    fun loadOrdered(repoIds: List<Int>): LiveData<List<Book>> {
        val order = SparseIntArray()
        repoIds.withIndex().forEach {
            order.put(it.value, it.index)
        }
        return loadById(repoIds).map { books ->
            books.sortedWith(compareBy { order.get(it.id) })
        }
    }

    @Query("SELECT * FROM Book WHERE id in (:bookIds)")
    protected abstract fun loadById(bookIds: List<Int>): LiveData<List<Book>>

    @Query("SELECT * FROM BookSearchResult WHERE `query` = :query")
    abstract fun findSearchResult(query: String): BookSearchResult?
}