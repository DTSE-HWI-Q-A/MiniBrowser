package com.hms.demo.minibrowser

import android.app.Application
import android.content.Context
import android.util.Log
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.searchkit.SearchKitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder
import java.text.MessageFormat

class BrowserApplication : Application() {

    companion object {
        const val TOKEN_URL = "https://oauth-login.cloud.huawei.com/oauth2/v3/token"
    }

    override fun onCreate() {
        super.onCreate()
        val appID = AGConnectServicesConfig
            .fromContext(this)
            .getString("client/app_id")
        SearchKitInstance.init(this, appID)
        CoroutineScope(Dispatchers.IO).launch {
            SearchKitInstance.instance.refreshToken(this@BrowserApplication)
        }
    }

}

fun SearchKitInstance.refreshToken(context: Context) {
    val config = AGConnectServicesConfig.fromContext(context)
    val appId = config.getString("client/app_id")
    val appSecret = "0c8b14be42a0be07d74e688b5961dc124800fa01c8b273d6973f5f7506a4162f"
    val url = URL(BrowserApplication.TOKEN_URL)
    val headers = HashMap<String, String>().apply {
        put("content-type", "application/x-www-form-urlencoded")
    }
    val msgBody = MessageFormat.format(
        "grant_type={0}&client_secret={1}&client_id={2}",
        "client_credentials", URLEncoder.encode(appSecret, "UTF-8"), appId
    ).toByteArray(Charsets.UTF_8)

    val response = HTTPHelper.sendHttpRequest(url, "POST", headers, msgBody)
    Log.e("token", response)
    val accessToken = JSONObject(response).let {
        if (it.has("access_token")) {
            it.getString("access_token")
        } else ""
    }
    setInstanceCredential(accessToken)

}