package com.md.testnews

import android.app.Application
import com.md.testnews.di.koinMainModule
import com.md.testnews.di.koinNewsModule
import com.md.testnews.di.koinRestModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@NewsApp)
            modules(
                koinNewsModule,
                koinMainModule,
                koinRestModule,
            )
        }
    }

}
