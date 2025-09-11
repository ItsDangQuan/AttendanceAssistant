package com.kttq.attendassist.injection.modules

import com.kttq.attendassist.core.data.repositories.auth.AuthRepository
import com.kttq.attendassist.core.data.repositories.auth.AuthRepositoryImpl
import com.kttq.attendassist.core.data.repositories.student.StudentClassRepository
import com.kttq.attendassist.core.data.repositories.student.StudentClassRepositoryImpl
import com.kttq.attendassist.core.data.repositories.student.StudentProfileRepository
import com.kttq.attendassist.core.data.repositories.student.StudentProfileRepositoryImpl
import com.kttq.attendassist.core.data.repositories.student.StudentRecordRepository
import com.kttq.attendassist.core.data.repositories.student.StudentRecordRepositoryImpl
import com.kttq.attendassist.core.data.repositories.student.StudentSessionRepository
import com.kttq.attendassist.core.data.repositories.student.StudentSessionRepositoryImpl
import com.kttq.attendassist.core.data.repositories.teacher.TeacherClassRepository
import com.kttq.attendassist.core.data.repositories.teacher.TeacherClassRepositoryImpl
import com.kttq.attendassist.core.data.repositories.teacher.TeacherProfileRepository
import com.kttq.attendassist.core.data.repositories.teacher.TeacherProfileRepositoryImpl
import com.kttq.attendassist.core.data.repositories.teacher.TeacherSessionRepository
import com.kttq.attendassist.core.data.repositories.teacher.TeacherSessionRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindStudentClassRepository(
        studentClassRepositoryImpl: StudentClassRepositoryImpl
    ): StudentClassRepository

    @Binds
    @Singleton
    abstract fun bindStudentProfileRepository(
        studentProfileRepositoryImpl: StudentProfileRepositoryImpl
    ): StudentProfileRepository

    @Binds
    @Singleton
    abstract fun bindStudentRecordRepository(
        studentRecordRepositoryImpl: StudentRecordRepositoryImpl
    ): StudentRecordRepository

    @Binds
    @Singleton
    abstract fun bindStudentSessionRepository(
        studentSessionRepositoryImpl: StudentSessionRepositoryImpl
    ): StudentSessionRepository

    @Binds
    @Singleton
    abstract fun bindTeacherClassRepository(
        teacherClassRepositoryImpl: TeacherClassRepositoryImpl
    ): TeacherClassRepository

    @Binds
    @Singleton
    abstract fun bindTeacherProfileRepository(
        teacherProfileRepositoryImpl: TeacherProfileRepositoryImpl
    ): TeacherProfileRepository

    @Binds
    @Singleton
    abstract fun bindTeacherSessionRepository(
        teacherSessionRepositoryImpl: TeacherSessionRepositoryImpl
    ): TeacherSessionRepository
}