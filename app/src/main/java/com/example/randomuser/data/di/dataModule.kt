package com.example.randomuser.data.di

import androidx.room.Room
import com.example.randomuser.data.local.db.RandomUserDatabase
import com.example.randomuser.data.remote.api.RandomUserApi
import com.example.randomuser.data.repository.UserRepositoryImpl
import com.example.randomuser.domain.repository.UserRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://randomuser.me/"

val dataModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor { chain->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.3"
                    ).build()
                chain.proceed(request)
            }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(RandomUserApi::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            RandomUserDatabase::class.java,
            "randomuser_db"
        ).build()
    }

    single { get<RandomUserDatabase>().userDao() }

    single<UserRepository> { UserRepositoryImpl(get(), get()) }
}
