package com.example.zbigniew.websource.helpers

import com.example.zbigniew.websource.helpers.ResponseError

interface ErrorView {
    fun onError(error: ResponseError)
}