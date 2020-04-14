package kr.co.kimd.financeappstore.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kr.co.kimd.financeappstore.api.BookSearchService
import kr.co.kimd.financeappstore.db.BookDao
import kr.co.kimd.financeappstore.db.BookSearchDb
import kr.co.kimd.financeappstore.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideBookSearchService(): BookSearchService {
        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create()) // Response Gson 형식 변환 팩토리
            .addCallAdapterFactory(LiveDataCallAdapterFactory()) // 기본 Call<T> 대신 원하는 형식의 객체 리턴
            .build()
            .create(BookSearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): BookSearchDb {
        return Room
            .databaseBuilder(app, BookSearchDb::class.java, "booksearch.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBookDao(db: BookSearchDb): BookDao {
        return db.bookDao()
    }
}