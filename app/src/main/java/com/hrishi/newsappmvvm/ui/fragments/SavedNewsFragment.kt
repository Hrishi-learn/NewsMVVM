package com.hrishi.newsappmvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hrishi.newsappmvvm.R
import com.hrishi.newsappmvvm.databinding.FragmentSavedNewsBinding
import com.hrishi.newsappmvvm.ui.NewsActivity
import com.hrishi.newsappmvvm.ui.adapter.NewsAdapter
import com.hrishi.newsappmvvm.ui.ui.NewsViewModel

class SavedNewsFragment:Fragment(R.layout.fragment_saved_news){
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: FragmentSavedNewsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it)
        })
        setUpRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("article",it)
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleNewsFragment,bundle)
        }
        val itemTouchHelperCallback = object:ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view,"Article deleted",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvSavedNews)

    }
    private fun setUpRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvSavedNews.adapter=newsAdapter
        binding.rvSavedNews.layoutManager= LinearLayoutManager(activity)
    }
}