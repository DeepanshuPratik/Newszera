package com.example.newszera

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [halfSlidingNews.newInstance] factory method to
 * create an instance of this fragment.
 */
class halfSlidingNews : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_half_sliding_news, container, false)
        val imageUrl =  arguments?.getString("imageUrl")
        val url = arguments?.getString("url")
        val author = arguments?.getString("author")
        val text = arguments?.getString("text")
        val title = arguments?.getString("title")

        view.findViewById<ImageView>(R.id.image).setImageURI(Uri.parse(imageUrl))
        view.findViewById<TextView>(R.id.title).text=title
        view.findViewById<TextView>(R.id.author).text=author
        view.findViewById<TextView>(R.id.text).text=text
        view.findViewById<TextView>(R.id.link).setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()
            context?.let { it1 -> customTabsIntent.launchUrl(it1, Uri.parse(url)) }
        }

        // Inflate the layout for this fragment
        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment halfSlidingNews.
         */
    }
}