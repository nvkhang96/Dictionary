package com.nvkhang96.dictionary.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.nvkhang96.dictionary.feature_dictionary.data.local.Converters
import com.nvkhang96.dictionary.feature_dictionary.data.local.WordInfoDatabase
import com.nvkhang96.dictionary.feature_dictionary.data.remote.DictionaryApi
import com.nvkhang96.dictionary.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.nvkhang96.dictionary.feature_dictionary.data.util.GsonParser
import com.nvkhang96.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import com.nvkhang96.dictionary.feature_dictionary.domain.use_case.GetWordInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: WordInfoDatabase,
        api: DictionaryApi
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room
            .databaseBuilder(
                app, WordInfoDatabase::class.java, "word_db"
            )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}