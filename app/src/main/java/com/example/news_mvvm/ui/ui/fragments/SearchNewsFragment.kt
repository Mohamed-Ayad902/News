package com.example.news_mvvm.ui.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news_mvvm.databinding.FragmentSearchNewsBinding
import com.example.news_mvvm.ui.adapter.NewsAdapter
import com.example.news_mvvm.ui.models.Article
import com.example.news_mvvm.ui.ui.NewsActivity
import com.example.news_mvvm.ui.ui.NewsViewModel
import com.example.news_mvvm.ui.utils.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.example.news_mvvm.ui.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SearchNewsFragment"

class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var searchAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().trim().isNotEmpty())
                        viewModel.searchNews(editable.toString())
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        searchAdapter.differ.submitList(it.articles)
                    }
                    showProgressBar(false)
                }
                is Resource.Error -> {
                    showToast(response.message!!)
                    Log.e(TAG, "error while searching news: $response")
                    showProgressBar(false)
                }
                is Resource.Loading -> {
                    showProgressBar(true)
                }
            }
        }

    }

    private fun setupRecyclerView() {
        searchAdapter = NewsAdapter(object : NewsAdapter.OnClickListener {
            override fun onClick(article: Article) {
                findNavController().navigate(
                    SearchNewsFragmentDirections.actionSearchNewsFragmentToArticleFragment(
                        article
                    )
                )
            }
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar(show: Boolean) {
        if (show)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }
}
