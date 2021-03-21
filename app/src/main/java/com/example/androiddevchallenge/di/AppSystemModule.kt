package com.example.androiddevchallenge.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 注册 App 全局相关的系统级模块
 *
 * @author 凛 (https://github.com/RinOrz)
 */
@Module @InstallIn(SingletonComponent::class)
object AppSystemModule {
  @Provides @Singleton
  fun provideAssets(
    @ApplicationContext context: Context
  ): AssetManager = context.assets

  /** TODO Migration to Jetpack-DataStore */
  @Provides @Singleton
  fun provideDataStore(
    @ApplicationContext context: Context
  ): SharedPreferences = context.getSharedPreferences("global", Context.MODE_PRIVATE)
}
