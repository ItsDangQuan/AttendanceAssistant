package com.kttq.attendassist

import androidx.test.espresso.core.internal.deps.dagger.Binds
import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import com.kttq.attendassist.injection.modules.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object FakeRepositoryModule { // Can be an 'object' now

    @Provides
    @Singleton
    fun provideFakeTokenRepository(): TokenRepository {
        // This explicitly tells Hilt how to get a FakeTokenRepository
        return FakeTokenRepository()
    }
}

