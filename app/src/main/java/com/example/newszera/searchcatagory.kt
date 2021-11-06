package com.example.newszera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_searchcatagory.*

class searchcatagory : AppCompatActivity() {
    var m=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchcatagory)
//        val topics =
//            listOf("Top Headlines", "Health", "Science", "Sports", "Business", "Entertainment", "General", "Technology")
//        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topics)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //Performs search when user hit the search button on the keyboard
                beta(p0.toString())
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                //Start filtering the list as user start entering the character
                return false
            }
        })
        findViewById<CardView>(R.id.category1).setOnClickListener {
            val name= "entertainment"
            val intent= Intent(this, MainActivity::class.java)
            intent.putExtra("name_extra",name)
            intent.putExtra("id",0)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.category2).setOnClickListener {
            val name= "business"
            val intent= Intent(this, MainActivity::class.java)
            intent.putExtra("name_extra",name)
            intent.putExtra("id",0)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.category3).setOnClickListener {
            val name= "science"
            val intent= Intent(this, MainActivity::class.java)
            intent.putExtra("name_extra",name)
            intent.putExtra("id",0)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.category4).setOnClickListener {
            val name= "health"
            val intent= Intent(this, MainActivity::class.java)
            intent.putExtra("name_extra",name)
            intent.putExtra("id",0)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.category5).setOnClickListener {
            val name= "sports"
            val intent= Intent(this, MainActivity::class.java)
            intent.putExtra("name_extra",name)
            intent.putExtra("id",0)
            startActivity(intent)
        }
        findViewById<CardView>(R.id.category6).setOnClickListener {
            val name= "technology"
            val intent= Intent(this, MainActivity::class.java)
            intent.putExtra("name_extra",name)
            intent.putExtra("id",0)
            startActivity(intent)
        }

    }
    private fun beta(string: String){
        searchView.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            intent.putExtra("name_extra",string)
            intent.putExtra("id",1)
            startActivity(intent)
        }
    }
}