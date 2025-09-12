package com.kttq.attendassist.injection.modules

import com.kttq.attendassist.core.data.network.AuthService
import com.kttq.attendassist.core.data.network.ClassService
import com.kttq.attendassist.core.data.network.DepartmentService
import com.kttq.attendassist.core.data.network.RecordService
import com.kttq.attendassist.core.data.network.SessionService
import com.kttq.attendassist.core.data.network.StudentService
import com.kttq.attendassist.core.data.network.TeacherService
import com.kttq.attendassist.core.data.network.UserService
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

    @Provides
    @Singleton
    fun provideAuthenticatedUserService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticatedSessionService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): SessionService {
        return retrofit.create(SessionService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticatedDepartmentService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): DepartmentService {
        return retrofit.create(DepartmentService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticatedRecordService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): RecordService {
        return retrofit.create(RecordService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticatedStudentService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): StudentService {
        return retrofit.create(StudentService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthenticatedClassService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): ClassService {
        return retrofit.create(ClassService::class.java)
    }
    @Provides
    @Singleton
    fun provideAuthenticatedTeacherService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): TeacherService {
        return retrofit.create(TeacherService::class.java)
    }

}