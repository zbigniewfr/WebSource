package com.example.zbigniew.websource.helpers

import android.content.res.Resources
import com.example.zbigniew.websource.R
import com.google.gson.GsonBuilder
import retrofit2.HttpException
import java.io.IOException


class ErrorHandler(val resource : Resources){

    private val builder = GsonBuilder()

    fun handleError(exception: Throwable): ResponseError {
        exception.printStackTrace()
        return when (exception) {
            is HttpException -> parseError(exception)
            is IOException -> getNetworkError()
            else -> getUnknownError()
        }
    }

    private fun getNetworkError(): ResponseError = ResponseError(resource.getString(R.string.network_error))
    private fun getUnknownError(): ResponseError = ResponseError(resource.getString(R.string.unknown_error))

    private fun parseError(error: HttpException) : ResponseError {
        return try {
            val gson = builder.create()
            val responseError = gson.fromJson(error.response().errorBody()?.string(), ResponseError::class.java)
            responseError ?: getUnknownError()
        } catch (e: Exception) {
            Throwable("JSON response could not be converted - data malformed").printStackTrace()
            getUnknownError()
        }
    }

}