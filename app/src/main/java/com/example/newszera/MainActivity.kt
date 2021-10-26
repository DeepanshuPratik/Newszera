package com.example.newszera

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.shareconnect.MySingleton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(), NewsItemClicked {
     private lateinit var madapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        fetchData()
        madapter = NewsListAdapter(this)
        recyclerView.adapter = madapter

    }
    private fun fetchData()  {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=06e1a2895f3a4b418b274cec72972799"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
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
            Response.ErrorListener {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
//        val openURL = Intent(Intent.ACTION_VIEW)
//        openURL.data = Uri.parse(item.url)
//        startActivity(openURL)
    }
}