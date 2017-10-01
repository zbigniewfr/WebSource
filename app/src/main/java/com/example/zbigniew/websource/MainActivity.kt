package com.example.zbigniew.websource

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.zbigniew.websource.model.WebSource
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Presenter.View {

    @Inject
    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                },{
                    it.printStackTrace()
                })
    }

    override fun onError(error: ResponseError) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSource(source: WebSource) {
        webSource.text = source?.source
    }
}
