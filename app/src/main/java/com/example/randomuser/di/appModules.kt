package com.example.randomuser.di

import com.example.randomuser.data.di.dataModule
import com.example.randomuser.domain.di.domainModule
import com.example.randomuser.presentation.di.presentationModule
import org.koin.dsl.module

internal val appModules = module {
    includes(
        dataModule,
        domainModule,
        presentationModule,
    )
}