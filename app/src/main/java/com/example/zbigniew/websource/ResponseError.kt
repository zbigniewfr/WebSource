package com.example.zbigniew.websource

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
                msg: String) : this(ResponseError.Error(code,msg))

    constructor(msg: String) : this(ResponseError.Error(DEFAULT_ERROR_CODE,msg))

    fun getErrorMessage() = error?.msg ?: ""
    fun getErrorCode() = error?.code

}