package com.example.trending.di

import com.example.trending.ui.TrendingActivity
import com.example.trending.ui.TrendingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeTrendingActivity(): TrendingActivity

    @ContributesAndroidInjector
    internal abstract fun contributeTrendingFragment(): TrendingFragment
}