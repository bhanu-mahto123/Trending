package com.example.trending.di

import android.app.Application
import com.example.trending.TrendingHubApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton



@Component(
    modules = [ApiModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: TrendingHubApplication)
}