package com.hrishi.newsappmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrishi.newsappmvvm.R
import com.hrishi.newsappmvvm.databinding.FragmentBreakingNewsBinding
import com.hrishi.newsappmvvm.ui.NewsActivity
import com.hrishi.newsappmvvm.ui.adapter.NewsAdapter
import com.hrishi.newsappmvvm.ui.ui.NewsViewModel
import com.hrishi.newsappmvvm.ui.util.Resource

class BreakingNewsFragment:Fragment(R.layout.fragment_breaking_news){
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentBreakingNewsBinding
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentBreakingNewsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer{response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
//                        binding.sometext.text= it.articles.toString()
                        Log.e("skvsdv","${it.articles}")
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    Log.e("Breaking news error","${response.message}")
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
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articleNewsFragment,bundle)
        }
    }
    private fun hideProgressBar(){
        binding.paginationProgressBar.visibility=View.INVISIBLE
    }
    private fun showProgressBar(){
        binding.paginationProgressBar.visibility=View.VISIBLE
    }
    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvBreakingNews.adapter=newsAdapter
        binding.rvBreakingNews.layoutManager=LinearLayoutManager(activity)
    }
}