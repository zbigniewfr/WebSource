package com.example.zbigniew.websource

import com.example.zbigniew.websource.helpers.ErrorHandler
import com.example.zbigniew.websource.helpers.ErrorView
import com.example.zbigniew.websource.model.WebSource
import com.example.zbigniew.websource.repository.applyTransformerFlowable
import com.example.zbigniew.websource.repository.local.LocalDataSource
import com.futuremind.mvpbase.MvpView
import com.futuremind.mvpbase.RxBasePresenter
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import me.jessyan.progressmanager.body.ProgressInfo



class Presenter
@Inject
constructor(
        private val httpClient: OkHttpClient,
        private val db: LocalDataSource,
        private val errorHandler: ErrorHandler,
        private val progressManager: ProgressManager
) : RxBasePresenter<Presenter.View>() {

    fun loadWebPageSource(url: String) {
        Flowable.merge(getFromStorage(), getFromInternet(url))
                .compose(applyTransformerFlowable())
                .subscribe({
                    view?.showSource(it)
                }, {
                    view?.onError(errorHandler.handleError(it))
                })
                .registerInPresenter()
    }

    private fun getFromStorage(): Flowable<WebSource> {
        return db.getLastRecord()
    }

    private fun getDownloadListener(): ProgressListener {
        return object : ProgressListener {
            override fun onProgress(progressInfo: ProgressInfo) {
                view?.updateProgress(progressInfo.percent)
            }

            override fun onError(id: Long, e: Exception) {
                view?.onError(errorHandler.handleError(e))
            }
        }
    }

    private fun getFromInternet(url: String): Flowable<WebSource> {
        return if (url.isNotEmpty() && url != view?.getStringFromRes(R.string.https)) {
            view?.showLoading()
            progressManager.addResponseListener(url, getDownloadListener());
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
            view?.emptyUrl()
            Flowable.empty<WebSource>()
        }
    }

    private fun getSourceFromResponse(response: Response?): String? {
        val body = response?.body()
        return body?.string()
    }

    fun addDisposable(disposable: Disposable){
        disposable.registerInPresenter()
    }

    interface View : MvpView, ErrorView {
        fun showSource(source: WebSource)
        fun updateProgress(percent: Int)
        fun showLoading()
        fun emptyUrl()
        fun getStringFromRes(res: Int): String
    }
}