package com.example.newszera

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity(), NewsItemClicked,NewsItemClick {

    private var url = ""
    private lateinit var madapter: NewsListAdapter
    private var imageUrl =""
    var name=""
    private var p by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        setContentView(R.layout.activity_main)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        name= intent.getStringExtra("name_extra").toString()
        p = intent.getIntExtra("id",0)
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["apiValue"]
        val key = value.toString()
        // val category = arrayOf("&category=business", "&category=science", "&category=sports", "&category=technology","","&category=general","&category=entertainment","&category=health")
        if(p!=1)
            url = "https://newsapi.org/v2/top-headlines?country=in&category=$name&apiKey=$key"
        else url= "https://newsapi.org/v2/everything?q=$name&language=en&sortBy=publishedAt&apiKey=$key"
        fetchData()
        madapter = NewsListAdapter(this)
        var catdapter = RvAdapter( this)
        catdapter.updatecategory(fetchDatalip())
        categ.adapter = catdapter
        recyclerView.adapter = madapter
        refresh(name)
    }

    private fun fetchDatalip(): ArrayList<horizon_adap>{
        val categoryArray = ArrayList<horizon_adap>()
        var categorychose = horizon_adap("All","https://images.unsplash.com/photo-1588681664899-f142ff2dc9b1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1374&q=80")
        categoryArray.add(categorychose)
        categorychose = horizon_adap("Science","https://images.unsplash.com/photo-1567427018141-0584cfcbf1b8?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8c2NpZW5jZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60")
        categoryArray.add(categorychose)
        categorychose = horizon_adap("Technology","https://images.unsplash.com/photo-1485827404703-89b55fcc595e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=870&q=80")
        categoryArray.add(categorychose)
        categorychose = horizon_adap("Business","https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60")
        categoryArray.add(categorychose)
        categorychose = horizon_adap("Bollywood","https://images.unsplash.com/photo-1536440136628-849c177e76a1?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8bW92aWVzfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60")
        categoryArray.add(categorychose)
        categorychose = horizon_adap("Health","https://images.unsplash.com/photo-1445384763658-0400939829cd?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjh8fGhlYWx0aHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60")
        categoryArray.add(categorychose)
        categorychose = horizon_adap("Sports","https://images.unsplash.com/photo-1517649763962-0c623066013b?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c3BvcnRzfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60")
        categoryArray.add(categorychose)
        return categoryArray
    }

    private fun refresh(name: String) {
        swipeRefreshLayout.setOnRefreshListener {
            val ai: ApplicationInfo = applicationContext.packageManager
                .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
            val value = ai.metaData["apiValue"]
            val key = value.toString()
           // val category = arrayOf("&category=business", "&category=science", "&category=sports", "&category=technology","","&category=general","&category=entertainment","&category=health")
            url = when (p) {
                0 -> "https://newsapi.org/v2/top-headlines?country=in&category=$name&apiKey=$key"
                2 -> "https://newsapi.org/v2/top-headlines?country=in&apiKey=$key"
                else -> "https://newsapi.org/v2/everything?q=$name&language=en&sortBy=publishedAt&apiKey=$key"
            }
                fetchData()
                madapter = NewsListAdapter(this)
                recyclerView.adapter = madapter
                refresh(name)

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
                        newsJsonObject.getString("description")
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

    override fun onItemClicked(item: horizon_adap){
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["apiValue"]
        val key = value.toString()
        if(item.category=="All")
            url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=$key"
        else if(item.category=="Bollywood")
            url =
                "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=$key"
        else url = "https://newsapi.org/v2/top-headlines?country=in&category="+item.category+"&apiKey="+key
        fetchData()
        refresh(item.category)

    }
    override fun onItemClicked(item: News) {
//        val bundle = Bundle()
//        val fragment = BottomSheetDialogFragment()
//        bundle.putString("imageUrl",item.imageUrl)
//        bundle.putString("url",item.url)
//        bundle.putString("title",item.title)
//        bundle.putString("text",item.description)
//        bundle.putString("author",item.author)
//        fragment.arguments = bundle
//        fragment.show(supportFragmentManager,"bottomSheetFragment")
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }

    override fun onItemClickedb(item: News) {
        val imageUrl= item.url
        val image = item.imageUrl
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey! check out this latest news shared by my app Newz Today:   $imageUrl")

            // (Optional) Here we're setting the title of the content
            putExtra(Intent.EXTRA_TITLE, "Share this News using: ")

            // (Optional) Here we're passing a content URI to an image to be displayed
            data = image.toUri()
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            type = "text/html"
        }, null)
        startActivity(share)
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.type="image/jpeg"
//        intent.putExtra(Intent.EXTRA_STREAM, image)
//        //intent.putExtra(Intent.EXTRA_TEXT, "Hey! check out this latest news shared by my app Newz Today $imageUrl" )
//        val chooser = Intent.createChooser(intent, "Share this Newz using:")
//        startActivity(chooser)
    }
}