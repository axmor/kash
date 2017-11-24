package com.axmor.kash.toolset.safety

import com.axmor.kash.toolset.BuildConfig

/**
 * Created by akolodyazhnyy on 8/22/2017.
 */

/**
 * Just restrict for assertion only in debug mode.
 */

fun aTrue(condition: Boolean) {
    if (BuildConfig.DEBUG && !condition) {
        throw AssertionError()
    }
}

fun aFalse(condition: Boolean) {
    if (BuildConfig.DEBUG && condition) {
        throw AssertionError()
    }
}

fun aNonNull(`object`: Any?) {
    if (BuildConfig.DEBUG && null == `object`) {
        throw AssertionError()
    }
}

private fun fail() {
    fail("")
}

fun fail(message: String) {
    if (BuildConfig.DEBUG) {
        throw AssertionError(message)
    }
}