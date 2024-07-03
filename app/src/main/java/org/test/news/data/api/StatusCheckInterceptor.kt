package org.test.news.data.api

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException

class StatusCheckInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val defaultErrorMessage = "There seems to be some error. Please try again after sometimes"
        if (response.code != 200 && response.code != 201) {
            val responseBody = response.body
            val responseString = responseBody?.string()
            val errorMessage = if (responseString != null) {
                try {
                    val jsonObject = JSONObject(responseString)
                    jsonObject.optString("message", defaultErrorMessage)
                } catch (e: Exception) {
                    defaultErrorMessage
                }
            } else {
                defaultErrorMessage
            }

            throw IOException(errorMessage)
        }

        // Recreate the response before returning it because the response body can be read only once
        return response.newBuilder()
            .body(ResponseBody.create(response.body?.contentType(), response.body?.string() ?: ""))
            .build()
    }
}
