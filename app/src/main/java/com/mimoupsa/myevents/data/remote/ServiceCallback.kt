package com.mimoupsa.myevents.data.remote

import android.text.TextUtils
import android.util.Log
import com.mimoupsa.myevents.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class ServiceCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {

        if (response.code() == HTTP_RESULT_OK) {
            Log.i(TAG, "execution OK: $response")
            onSuccess(response.body())
        } else if (!TextUtils.isEmpty(response.message())) {
            Log.e(TAG, "error. Response: ${response.message()}")
            onError(response.code(), response.message())
        } else if (response.errorBody() != null) {
            val errorString = response.errorBody()?.string()

            Log.e(TAG, "error body: $errorString")

        } else {
            Log.e(TAG, "unknown error. Error code: ${response.code()}")
            onError(response.code(), R.string.error_message_default.toString())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e(TAG, "Execution KO: ${t.localizedMessage}")
        onError(UNCONTROLLED_ERROR, R.string.error_message_default.toString())
    }

    abstract fun onSuccess(response: T?)
    abstract fun onError(error: Int, message: String?)

    companion object {
        val TAG: String = ServiceCallback::class.java.simpleName

        const val HTTP_RESULT_OK = 200
        const val UNCONTROLLED_ERROR = -1
    }
}