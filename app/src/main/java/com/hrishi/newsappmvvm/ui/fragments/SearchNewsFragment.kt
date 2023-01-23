package com.hrishi.newsappmvvm.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrishi.newsappmvvm.R
import com.hrishi.newsappmvvm.databinding.FragmentSearchNewsBinding
import com.hrishi.newsappmvvm.ui.NewsActivity
import com.hrishi.newsappmvvm.ui.adapter.NewsAdapter
import com.hrishi.newsappmvvm.ui.ui.NewsViewModel
import com.hrishi.newsappmvvm.ui.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment:Fragment(R.layout.fragment_search_news){
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        var job:Job?=null
        binding.etSearch.addTextChangedListener {editable->
            job?.cancel()
            job = MainScope().launch {
                delay(500)
                editable?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.getSearchNews(it.toString())
                    }
                }
            }
        }
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error->{
                    Toast.makeText(activity, "Failed to show search results", Toast.LENGTH_SHORT).show()
                    Log.e("search result error","${response.message}")
                    hideProgressBar()
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })
        setUpRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("article",it)
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleNewsFragment,bundle)
        }
    }
    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility=View.INVISIBLE
    }
    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvSearchNews.adapter=newsAdapter
        binding.rvSearchNews.layoutManager=LinearLayoutManager(activity)
    }

}