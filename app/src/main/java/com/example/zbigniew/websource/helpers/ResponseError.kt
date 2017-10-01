package com.example.zbigniew.websource.helpers

class ResponseError(
        private val error : Error?
){

    data class Error(val code: Int,
                     val msg: String
    )

    companion object {
        private val DEFAULT_ERROR_CODE = -1

    }

    constructor(code: Int,
                msg: String) : this(Error(code, msg))

    constructor(msg: String) : this(Error(DEFAULT_ERROR_CODE, msg))

    fun getErrorMessage() = error?.msg ?: ""
    fun getErrorCode() = error?.code

}