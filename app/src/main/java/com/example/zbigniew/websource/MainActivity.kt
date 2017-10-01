package com.example.zbigniew.websource

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.WindowManager
import android.widget.Toast
import com.example.zbigniew.websource.model.WebSource
import com.example.zbigniew.websource.repository.applyTransformerSingle
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.text.Selection
import android.text.Editable
import android.text.TextWatcher
import com.example.zbigniew.websource.helpers.ResponseError
import com.example.zbigniew.websource.helpers.hide
import com.example.zbigniew.websource.helpers.show
import javax.annotation.Resource


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
                    presenter.loadWebPageSource(webAdressEt.text.toString())
                }, {
                    it.printStackTrace()
                })

        webAdressEt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!s.toString().startsWith(getString(R.string.https))) {
                    webAdressEt.setText(getString(R.string.https))
                    Selection.setSelection(webAdressEt.text, webAdressEt.text.length)

                }

            }
        })
    }

    override fun onError(error: ResponseError) {
        hideLoading()
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
                        hideLoading()
                    }, {
                        it.printStackTrace()
                    })
        }
    }

    override fun showLoading() {
        progressBar.show()
    }

    private fun hideLoading() {
        progressBar.hide()
    }

    override fun emptyUrl() {
        Toast.makeText(this, getString(R.string.empty_url_error), Toast.LENGTH_SHORT).show()
    }

    override fun getStringFromRes(@StringRes res: Int): String = getString(res)
}
