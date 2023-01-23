package com.hrishi.newsappmvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hrishi.newsappmvvm.R
import com.hrishi.newsappmvvm.databinding.FragmentArticleBinding
import com.hrishi.newsappmvvm.ui.NewsActivity
import com.hrishi.newsappmvvm.ui.ui.NewsViewModel

class ArticleNewsFragment:Fragment(R.layout.fragment_article){
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentArticleBinding
    val args:ArticleNewsFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article=args.article
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(article.url)
        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved",Snackbar.LENGTH_SHORT).show()
        }
    }
}