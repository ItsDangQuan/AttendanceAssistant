package com.kttq.attendassist

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class CustomHiltTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        // This line instructs the test runner to use HiltTestApplication
        // for all Hilt-instrumented tests.
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}