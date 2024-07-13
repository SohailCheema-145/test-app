package com.example.testapp.ui.compose

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiProvider {
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    inline fun <reified T> getAdapter() = moshi.adapter(T::class.java)
}