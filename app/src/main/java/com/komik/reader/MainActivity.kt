package com.komik.reader

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.JavascriptInterface
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : Activity() {

    private lateinit var web: WebView

    inner class MangaApi {
        @JavascriptInterface
        fun get(path: String): String {
            if (!path.startsWith("/manga") &&
                !path.startsWith("/chapter") &&
                !path.startsWith("/at-home/server/")) {
                return JSONObject().put("error", "Endpoint tidak dibenarkan").toString()
            }

            return try {
                val connection = URL("https://api.mangadex.org$path")
                    .openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Accept", "application/json")
                connection.setRequestProperty("User-Agent", "KOMIK/1.0")
                connection.connectTimeout = 15000
                connection.readTimeout = 20000

                val code = connection.responseCode
                val stream = if (code in 200..299)
                    connection.inputStream else connection.errorStream

                val result = stream?.bufferedReader()?.use { it.readText() }
                    ?: "{}"

                connection.disconnect()

                if (code !in 200..299) {
                    JSONObject()
                        .put("error", "MangaDex HTTP $code: ${result.take(300)}")
                        .toString()
                } else {
                    result
                }
            } catch (e: Exception) {
                JSONObject()
                    .put("error", e.message ?: "Sambungan gagal")
                    .toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.addJavascriptInterface(MangaApi(), "MangaApi")
        web.webViewClient = WebViewClient()
        web.loadUrl("file:///android_asset/index.html")
        setContentView(web)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (web.canGoBack()) web.goBack()
        else super.onBackPressed()
    }
}
