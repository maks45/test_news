package com.md.testnews.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.md.testnews.core.biometric.BiometricHelper
import com.md.testnews.core.network.NetworkStateProvider
import com.md.testnews.core.network.NetworkStateProviderImpl
import com.md.testnews.db.NewsDao
import com.md.testnews.db.NewsDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val koinMainModule = module {

    single<NewsDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            NewsDatabase::class.java,
            NewsDatabase.NAME
        ).build()
    }

    single<BiometricHelper> {
        BiometricHelper()
    }

    factory<NewsDao> {
        val newsDatabase: NewsDatabase = get()
        newsDatabase.getNewsDao()
    }

    single<ConnectivityManager> {
        androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single<NetworkStateProvider> {
        NetworkStateProviderImpl(connectivityManager = get())
    }

}