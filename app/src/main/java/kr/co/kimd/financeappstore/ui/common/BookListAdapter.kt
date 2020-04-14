package kr.co.kimd.financeappstore.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import kr.co.kimd.financeappstore.AppExecutors
import kr.co.kimd.financeappstore.R
import kr.co.kimd.financeappstore.databinding.BookItemBinding
import kr.co.kimd.financeappstore.vo.Book

class BookListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val bookClickCallback: ((Book) -> Unit)?
) : DataBoundListAdapter<Book, BookItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.authors == newItem.authors
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.contents == newItem.contents &&
                    oldItem.datetime == newItem.datetime
        }
    }
) {
    override fun createBinding(parent: ViewGroup): BookItemBinding {
        val binding = DataBindingUtil.inflate<BookItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.book_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.book?.let {
                bookClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: BookItemBinding, item: Book) {
        binding.book = item
    }
}