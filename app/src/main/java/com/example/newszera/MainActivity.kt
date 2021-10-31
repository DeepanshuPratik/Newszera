package com.example.newszera

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.util.Random
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.shareconnect.MySingleton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(), NewsItemClicked {
     private var url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=06e1a2895f3a4b418b274cec72972799"
     private lateinit var madapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        getSupportActionBar()?.setLogo(R.drawable.ic_baseline_arrow_back_ios_24);
        getSupportActionBar()?.setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        fetchData()
        madapter = NewsListAdapter(this)
        recyclerView.adapter = madapter
        refresh()

    }

    private fun refresh() {
        swipeRefreshLayout.setOnRefreshListener {
            val ai: ApplicationInfo = applicationContext.packageManager
                .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
            val value = ai.metaData["apiValue"]
            val key = value.toString()
            val category = arrayOf("&category=business", "&category=science", "&category=sports", "&category=technology","","&category=general","&category=entertainment","&category=health")
                url = "https://newsapi.org/v2/top-headlines?country=in"+category[Random().nextInt(8)]+"&apiKey="+key
                fetchData()
                madapter = NewsListAdapter(this)
                recyclerView.adapter = madapter
                refresh()

            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun fetchData()  {

        val queue = Volley.newRequestQueue(this)
        val getRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener{

                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject=  newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                    )
                    newsArray.add(news)
                }
                madapter.updatenews(newsArray)
            },
            Response.ErrorListener { error ->

            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }

        queue.add(getRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}