package kr.co.kimd.financeappstore.di

import dagger.Module
import dagger.android.ContributesAndroidInjector

import kr.co.kimd.financeappstore.ui.book.BookFragment
import kr.co.kimd.financeappstore.ui.search.SearchFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
//    @ContributesAndroidInjector
//    abstract fun contributeBookFragment(): BookFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}