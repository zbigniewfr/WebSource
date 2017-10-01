package com.example.zbigniew.websource

import com.example.zbigniew.websource.model.WebSource
import com.example.zbigniew.websource.repository.applyTransformerFlowable
import com.example.zbigniew.websource.repository.local.LocalDataSource
import com.futuremind.mvpbase.MvpView
import com.futuremind.mvpbase.RxBasePresenter
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class Presenter
@Inject
constructor(
        private val httpClient: OkHttpClient,
        private val db: LocalDataSource
) : RxBasePresenter<Presenter.View>() {

    fun loadWebPageSource(url: String) {
        Flowable.merge(getFromStorage(), getFromInternet(url))
                .compose(applyTransformerFlowable())
                .subscribe({
                    view?.showSource(it)
                }, {
                    it.printStackTrace()
                })
                .registerInPresenter()
    }

    private fun getFromStorage(): Flowable<WebSource> {
        return db.getLastRecord()
    }

    private fun getFromInternet(url: String): Flowable<WebSource> {
        return if (url.isNotEmpty()) {
            Flowable.fromCallable { httpClient.newCall(Request.Builder().url(url).build()).execute() }
                    .doOnNext {
                        db.deleteWebSource()
                    }
                    .map {
                        val source = getSourceFromResponse(it)
                        val webSource = WebSource(1, source)
                        db.saveWebSource(webSource)
                        webSource
                    }
        } else {
            Flowable.empty<WebSource>()
        }
    }

    private fun getSourceFromResponse(response: Response?): String? {
        val body = response?.body()
        return body?.string()
    }


    interface View : MvpView, ErrorView {
        fun showSource(source: WebSource)
    }
}