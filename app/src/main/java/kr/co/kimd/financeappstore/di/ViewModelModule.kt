package kr.co.kimd.financeappstore.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

import kr.co.kimd.financeappstore.ui.search.SearchViewModel
import kr.co.kimd.financeappstore.viewmodel.BookSearchViewModelFactory

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(BookViewModel::class)
//    abstract fun bindBookViewModel(bookViewModel: BookViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: BookSearchViewModelFactory): ViewModelProvider.Factory
}