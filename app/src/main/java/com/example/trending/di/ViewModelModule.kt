package com.example.trending.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trending.viewmodel.TrendingViewModel
import com.example.trending.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TrendingViewModel::class)
    protected abstract fun bindTrendingViewModel(trendingViewModel: TrendingViewModel): ViewModel
}