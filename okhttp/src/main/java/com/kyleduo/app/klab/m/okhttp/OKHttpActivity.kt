package com.kyleduo.app.klab.m.okhttp

import android.annotation.SuppressLint
import android.os.Bundle
import com.kyleduo.app.klab.foundation.BaseActivity
import com.kyleduo.app.klab.foundation.extensions.inflater
import com.kyleduo.app.klab.m.okhttp.databinding.ActOkhttpBinding
import kotlinx.android.synthetic.main.act_okhttp.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.InetSocketAddress
import java.net.Proxy

/**
 * @author kyleduo on 5/12/21
 */
class OKHttpActivity : BaseActivity() {

    private val binding by lazy {
        ActOkhttpBinding.inflate(inflater())
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://httpbin.org")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    private val service by lazy {
        retrofit.create(SingleHttpBinTestService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.sendGetRequest.setOnClickListener {
            sendGetRequest()
        }

        binding.applyProxy.setOnClickListener {
            applyProxy()
        }

        binding.disableProxy.setOnClickListener {
            disableProxy()
        }
    }

    private fun applyProxy() {
        val host = proxyHost.text.toString().trim()
        val port = proxyPort.text.toString().trim().toInt()

        val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(host, port))
        forceSetProxy(proxy)
    }

    private fun disableProxy() {
        forceSetProxy(null)
    }

    private fun forceSetProxy(proxy: Proxy?) {
        val proxyField = okHttpClient::class.java.getDeclaredField("proxy")
        proxyField.isAccessible = true
        proxyField.set(okHttpClient, proxy)

        GlobalScope.launch {
            okHttpClient.connectionPool().evictAll()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun sendGetRequest() {
        binding.result.text = "loading..."
        service.get("test").enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                binding.result.text = t.message
            }

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                response.body()?.let {
                    binding.result.text = it
                }
            }

        })
    }

    interface SingleHttpBinTestService {
        @GET("/get")
        fun get(@Query("param") param: String): Call<String>
    }
}