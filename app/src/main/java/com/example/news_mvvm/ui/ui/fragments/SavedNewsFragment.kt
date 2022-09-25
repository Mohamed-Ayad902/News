package com.example.news_mvvm.ui.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news_mvvm.databinding.FragmentSavedNewsBinding
import com.example.news_mvvm.ui.adapter.NewsAdapter
import com.example.news_mvvm.ui.models.Article
import com.example.news_mvvm.ui.ui.NewsActivity
import com.example.news_mvvm.ui.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

private const val TAG = "SavedNewsFragment"

class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var savedArticlesAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val itemTouchHelper =
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = true

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val article = savedArticlesAdapter.differ.currentList[position]
                    viewModel.deleteNews(article)

                    Snackbar.make(view!!, "Article deleted", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            viewModel.insertNews(article)
                        }
                        show()
                    }
                }
            }
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding.recyclerView)
        }

        savedArticlesAdapter = NewsAdapter(object : NewsAdapter.OnClickListener {
            override fun onClick(article: Article) {
                findNavController().navigate(
                    SavedNewsFragmentDirections.actionSavedNewsFragmentToArticleFragment(
                        article
                    )
                )
            }
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = savedArticlesAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        viewModel.getSavedNews().observe(viewLifecycleOwner) { savedArticles ->
            savedArticlesAdapter.differ.submitList(savedArticles)
        }

    }

}