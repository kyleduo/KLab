package com.kyleduo.app.klab.m.nsd

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.drakeet.multitype.MultiTypeAdapter
import com.kyleduo.app.klab.foundation.BaseActivity
import com.kyleduo.app.klab.foundation.extensions.dp2px
import kotlinx.android.synthetic.main.activity_nsd.*

/**
 * @auther kyleduo on 3/28/21
 */
class NsdActivity : BaseActivity() {

    companion object {
        private const val TAG = "NsdActivity"
    }

    val discoveryListener = object : NsdManager.DiscoveryListener {
        override fun onServiceFound(serviceInfo: NsdServiceInfo?) {
            Log.d(TAG, "onServiceFound() called with: serviceInfo = $serviceInfo")
            serviceInfo?.let {
                runOnUiThread {
                    services.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
            Log.d(TAG, "onStopDiscoveryFailed() called with: serviceType = $serviceType, errorCode = $errorCode")
        }

        override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
            Log.d(TAG, "onStartDiscoveryFailed() called with: serviceType = $serviceType, errorCode = $errorCode")
        }

        override fun onDiscoveryStarted(serviceType: String?) {
            Log.d(TAG, "onDiscoveryStarted() called with: serviceType = $serviceType")
        }

        override fun onDiscoveryStopped(serviceType: String?) {
            Log.d(TAG, "onDiscoveryStopped() called with: serviceType = $serviceType")
        }

        override fun onServiceLost(serviceInfo: NsdServiceInfo?) {
            Log.d(TAG, "onServiceLost() called with: serviceInfo = $serviceInfo")
        }
    }

    private val nsdManager by lazy {
        getSystemService(Context.NSD_SERVICE) as NsdManager
    }
    private var started = false
    private val layoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
    private val services = mutableListOf<NsdServiceInfo>()
    private val adapter = object : MultiTypeAdapter() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nsd)
        serviceList.layoutManager = layoutManager
        serviceList.adapter = adapter
        adapter.register(object : ItemViewDelegate<NsdServiceInfo, ServiceViewHolder>() {
            override fun onBindViewHolder(holder: ServiceViewHolder, item: NsdServiceInfo) {
                holder.tv.text = item.serviceName
                holder.tv.setOnClickListener {
                    resolveService(item)
                }
            }

            override fun onCreateViewHolder(context: Context, parent: ViewGroup): ServiceViewHolder {
                return ServiceViewHolder(TextView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    setPadding(16.dp2px())
                })
            }
        })
        adapter.items = services

        nsdDiscoverBt.setOnClickListener {
            if (started) {
                stopDiscover()
            } else {
                startDiscover()
            }
        }
    }

    private fun stopDiscover() {
        nsdManager.stopServiceDiscovery(discoveryListener)
        started = false
    }

    private fun startDiscover() {
        if (started) {
            return
        }
        started = true
        nsdManager.discoverServices("_http._tcp", NsdManager.PROTOCOL_DNS_SD, discoveryListener)
    }

    private fun resolveService(serviceInfo: NsdServiceInfo) {
        nsdManager.resolveService(serviceInfo, object : NsdManager.ResolveListener {
            override fun onResolveFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
                Log.d(TAG, "onResolveFailed() called with: serviceInfo = $serviceInfo, errorCode = $errorCode")
            }

            override fun onServiceResolved(serviceInfo: NsdServiceInfo?) {
                Log.d(TAG, "onServiceResolved() called with: serviceInfo = $serviceInfo")
            }

        })
    }

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView as TextView
    }
}