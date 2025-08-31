package com.kttq.attendassist.injection.modules

import com.kttq.attendassist.core.data.network.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    @Named("PublicAuthService")
    fun providePublicAuthService(
        @Named("PublicRetrofit") retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    @Named("AuthAuthService")
    fun provideAuthenticatedAuthService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}