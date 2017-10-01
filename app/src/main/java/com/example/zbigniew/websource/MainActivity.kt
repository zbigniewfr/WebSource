package com.example.zbigniew.websource

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.zbigniew.websource.model.WebSource
import com.example.zbigniew.websource.repository.applyTransformerSingle
import com.futuremind.omili.helpers.hide
import com.futuremind.omili.helpers.show
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Presenter.View {

    companion object {
        private const val TIMER_DELAY = 2L
        private const val PROGRESS_DEFAULT = 0
    }

    @Inject
    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        WebSourceApp.baseComponent.inject(this)
        presenter.attachView(this)
        initListener()
        presenter.loadWebPageSource("")
    }

    @SuppressLint("RxLeakedSubscription")
    private fun initListener() {
        RxView.clicks(downloadBtn)
                .subscribe({
                    progressBar.show()
                    presenter.loadWebPageSource(webAdressEt.text.toString())
                }, {
                    it.printStackTrace()
                })
    }

    override fun onError(error: ResponseError) {
        progressBar.hide()
        Toast.makeText(this, error.getErrorMessage(), Toast.LENGTH_SHORT).show()
    }

    override fun showSource(source: WebSource) {
        webSource.text = source.source
    }

    override fun updateProgress(percent: Int) {
        progressBar.progress = percent
        if (percent == 100) {
            Single.timer(TIMER_DELAY, TimeUnit.SECONDS)
                    .compose(applyTransformerSingle())
                    .subscribe({
                        progressBar.progress = PROGRESS_DEFAULT
                        progressBar.hide()
                    }, {
                        it.printStackTrace()
                    })
        }
    }
}
