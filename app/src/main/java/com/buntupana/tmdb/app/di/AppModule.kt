package com.buntupana.tmdb.app.di

import com.buntupana.tmdb.app.presentation.MainActivityViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainActivityViewModel)
}