package com.komik.reader

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
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

                val result = stream?.bufferedReader()?.use { it.readText() } ?: "{}"
                connection.disconnect()

                if (code !in 200..299) {
                    JSONObject().put("error", "MangaDex HTTP $code: ${result.take(300)}").toString()
                } else result
            } catch (e: Exception) {
                JSONObject().put("error", e.message ?: "Sambungan gagal").toString()
            }
        }
    }

    private fun loadImage(request: WebResourceRequest): WebResourceResponse? {
        val host = request.url.host ?: return null

        val isMangaDexImage =
            host == "uploads.mangadex.org" ||
            host == "mangadex.network" ||
            host.endsWith(".mangadex.network")

        if (!isMangaDexImage) return null

        var connection: HttpURLConnection? = null

        return try {
            connection = URL(request.url.toString()).openConnection() as HttpURLConnection
            connection.instanceFollowRedirects = true
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 KOMIK/1.0")
            connection.setRequestProperty("Referer", "https://mangadex.org/")
            connection.setRequestProperty("Accept", "image/avif,image/webp,image/apng,image/*,*/*;q=0.8")
            connection.connectTimeout = 20000
            connection.readTimeout = 30000

            val code = connection.responseCode

            if (code !in 200..299) {
                connection.disconnect()
                null
            } else {
                val mime = connection.contentType
                    ?.substringBefore(";") ?: "image/jpeg"

                WebResourceResponse(
                    mime,
                    null,
                    code,
                    "OK",
                    mapOf("Access-Control-Allow-Origin" to "*"),
                    connection.inputStream
                )
            }
        } catch (e: Exception) {
            connection?.disconnect()
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        web = WebView(this)

        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.loadsImagesAutomatically = true
        web.settings.blockNetworkImage = false
        web.settings.mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW

        web.addJavascriptInterface(MangaApi(), "MangaApi")

        web.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                if (request == null) return null
                return loadImage(request) ?: super.shouldInterceptRequest(view, request)
            }
        }

        setContentView(web)
        web.loadUrl("file:///android_asset/index.html")
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (web.canGoBack()) web.goBack()
        else super.onBackPressed()
    }
}
