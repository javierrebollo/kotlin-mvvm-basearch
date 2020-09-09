package com.javierrebollo.basearch

import com.javierrebollo.basearch.data.helper.NetworkStatusHelper
import com.javierrebollo.basearch.data.helper.SharedPreferencesHelper
import com.javierrebollo.basearch.data.network.ServerClient
import com.javierrebollo.basearch.data.repository.SessionRepository
import com.javierrebollo.basearch.data.repository.UserRepository
import com.javierrebollo.basearch.domain.usecase.GetUsersUseCase
import com.javierrebollo.basearch.domain.usecase.IsLoggedUseCase
import com.javierrebollo.basearch.domain.usecase.LoginUseCase
import com.javierrebollo.basearch.ui.screens.login.LoginVMFactory
import com.javierrebollo.basearch.ui.screens.splash.SplashVMFactory
import com.javierrebollo.basearch.ui.screens.userlist.UserListVMFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object DependencyInjector {
    private val applicationContext = BaseApplication.instance.applicationContext
    //private val database = AppDatabase.getInstance(applicationContext)

    private val okHttpClient: OkHttpClient by lazy {

        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(180L, TimeUnit.SECONDS)
            .readTimeout(180L, TimeUnit.SECONDS)
            .writeTimeout(180L, TimeUnit.SECONDS)

        okHttpClientBuilder.build()
    }

    private val serverClient: ServerClient by lazy {
        ServerClient(
            okHttpClient,
            provideNetworkStatusHelper(),
            provideSharedPreferencesHelper()
        )
    }

    //*********************
    //**** REPOSITORY *****
    //*********************

    private fun provideSessionRepository(): SessionRepository {
        return SessionRepository.getInstance(
            serverClient,
            provideSharedPreferencesHelper()
        )
    }

    private fun provideUserRepository(): UserRepository {
        return UserRepository.getInstance(
            serverClient
        )
    }

    //********************
    //**** USE CASES *****
    //********************

    fun provideLoginUseCase(): LoginUseCase {
        return LoginUseCase(provideSessionRepository())
    }

    fun provideGetUsersUseCase(): GetUsersUseCase {
        return GetUsersUseCase(provideUserRepository())
    }

    fun provideIsLoggedUseCase(): IsLoggedUseCase {
        return IsLoggedUseCase(provideSessionRepository())
    }

    //*****************
    //**** HELPER *****
    //*****************

    private fun provideSharedPreferencesHelper(): SharedPreferencesHelper {
        return SharedPreferencesHelper(applicationContext)
    }

    private fun provideNetworkStatusHelper(): NetworkStatusHelper {
        return NetworkStatusHelper(applicationContext)
    }

    //**********************
    //**** VIEW MODELS *****
    //**********************

    fun provideSplashVMFactory(): SplashVMFactory {
        return SplashVMFactory(provideIsLoggedUseCase())
    }

    fun provideLoginVMFactory(): LoginVMFactory {
        return LoginVMFactory(provideLoginUseCase())
    }

    fun provideUserListVMFactory(): UserListVMFactory {
        return UserListVMFactory(provideGetUsersUseCase())
    }
}