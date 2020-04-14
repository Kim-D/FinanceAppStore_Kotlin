package kr.co.kimd.financeappstore.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kr.co.kimd.financeappstore.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}