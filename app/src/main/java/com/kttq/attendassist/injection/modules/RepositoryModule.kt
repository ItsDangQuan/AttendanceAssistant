package com.kttq.attendassist.injection.modules

import com.kttq.attendassist.core.data.repositories.auth.AuthRepository
import com.kttq.attendassist.core.data.repositories.auth.AuthRepositoryImpl
import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import com.kttq.attendassist.core.data.repositories.token.TokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}