package com.example.zbigniew.websource.repository

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


val schedulersTransformerSingle by lazy {
    SingleTransformer<Any, Any> { it
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) } }

@Suppress("UNCHECKED_CAST")
fun <T>applyTransformerSingle() = schedulersTransformerSingle as SingleTransformer<T, T>

val schedulersTransformerFlowable by lazy {
    FlowableTransformer<Any, Any> { it
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) } }

@Suppress("UNCHECKED_CAST")
fun <T>applyTransformerFlowable() = schedulersTransformerFlowable as FlowableTransformer<T, T>

val schedulersTransformerCompletable by lazy {
    CompletableTransformer { it
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) } }

fun applyTransformerCompletable() = schedulersTransformerCompletable

val schedulersTransformerObservable by lazy {
    ObservableTransformer<Any, Any> { it
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) } }

@Suppress("UNCHECKED_CAST")
fun <T>applyTransformerObservable() = schedulersTransformerObservable as ObservableTransformer<T, T>