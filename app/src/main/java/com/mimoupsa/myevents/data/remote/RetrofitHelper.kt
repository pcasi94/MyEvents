package com.mimoupsa.myevents.data.remote

import com.mimoupsa.myevents.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitHelper {

    companion object {
        private const val TIMEOUT_SECONDS = 60L
        fun getRetrofit(
        ): Retrofit =
                Retrofit.Builder().apply {
                    baseUrl(BuildConfig.SERVER_BASE_URL)
                    addConverterFactory(GsonConverterFactory.create())
                    client(makeHttpClient().build())
                }.build()

        private fun makeHttpClient() = OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            addInterceptor(headerInterceptor())

        }


        private fun headerInterceptor() =
                Interceptor { chain ->
                    chain.proceed(
                            chain.request().newBuilder().apply {
                                addHeader("Accept", "application/json")
                                addHeader("Accept-Language", getLanguage())
                                addHeader("Content-Type", "application/json")
                            }.build()
                    )
                }

        private fun getLanguage(): String {
            return when (val locale = Locale.getDefault().language) {
                "es", "ca" -> locale
                else -> "en"
            }
        }
    }
}