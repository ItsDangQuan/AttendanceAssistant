package com.kttq.attendassist.injection.modules

import com.kttq.attendassist.core.data.network.AuthService
import com.kttq.attendassist.core.data.network.interceptor.AuthInterceptor
import com.kttq.attendassist.core.data.network.interceptor.TokenAuthenticator
import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

private val BASE_URL = "localhost:8000"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("BaseRetrofit")
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseAuthApiService(@Named("BaseRetrofit") retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Named("AuthRetrofit")
    @Singleton
    fun provideAuthRetrofit(
        @Named("BaseRetrofit") retrofit: Retrofit,
        tokenRepository: TokenRepository,
        baseAuthService: dagger.Lazy<AuthService>
    ): Retrofit {
        val authInterceptor = AuthInterceptor(tokenRepository)
        val tokenAuthenticator = TokenAuthenticator(tokenRepository, baseAuthService)

        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .build()

        return retrofit.newBuilder()
            .client(okHttpClient)
            .build()
    }

    // @Provides
    // @Singleton
    // fun provideAuthApiService(@Named("AuthRetrofit") retrofit: Retrofit): AuthService {
    //     return retrofit.create(AuthService::class.java)
    // }
}