package org.test.news.presentation.ui

import dagger.hilt.android.AndroidEntryPoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.test.news.BaseFragment
import org.test.news.OnItemClickListener
import org.test.news.R
import org.test.news.databinding.FragmentNewsListBinding
import org.test.news.domain.model.NewsItem
import org.test.news.presentation.NewsUiState
import org.test.news.presentation.adapter.NewsAdapter
import org.test.news.presentation.viewmodel.NewsViewModel


@AndroidEntryPoint
class NewsListFragment : BaseFragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var  mSpinnerAdapter : ArrayAdapter<String>

    private val newsViewModel by viewModels<NewsViewModel>()

    private val newsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        setUpSpinnerView()
        return binding.root
    }

    private fun setUpRecyclerView(){
        binding.recyclerViewNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
        newsAdapter.setOnItemClickListener(object : OnItemClickListener<NewsItem>{
            override fun onClick(position : Int, item: NewsItem) {
                showNewsDetails(item)
            }
        })
        newsAdapter.setOnItemBookMarkClickListener(object : OnItemClickListener<NewsItem>{
            override fun onClick(position: Int, item: NewsItem) {
                newsViewModel.toggleBookMarkNewsDetails(position,item)
            }
        })
    }


    private fun setUpSpinnerView(){
        mSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,  mutableListOf())
        binding.spinnerCategory.adapter = mSpinnerAdapter
        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = binding.spinnerCategory.adapter.getItem(position) as String
                newsViewModel.fetchTopNews(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // do nothing
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.newsUiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is NewsUiState.LoadingStart -> showLoading()
                is NewsUiState.LoadingStop -> hideLoading()
                is NewsUiState.Success -> showNewsList(uiState.newsList)
                is NewsUiState.Category -> setCategories(uiState.categoryList)
                is NewsUiState.ToggleBookmarkSuccess -> toggleBookmarkSuccess(uiState.position,uiState.item)
                is NewsUiState.Error -> showError(uiState.exceptionMessage)
            }
        }
        newsViewModel.fetchTopNews()
    }

    private fun showLoading() {
        binding.progressBar.visibility = VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = GONE
    }

    private fun showNewsList(newsList: List<NewsItem>) {
        newsAdapter.setData(newsList)
    }

    private fun setCategories(list: List<String>) {
        binding.spinnerCategory.visibility = VISIBLE
        val newList = list.toMutableList()
        newList.add(0,"All")
        mSpinnerAdapter.clear()
        mSpinnerAdapter.addAll(newList)
        mSpinnerAdapter.notifyDataSetChanged()
    }

    private fun showNewsDetails(newsItem: NewsItem) {
        val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment(newsItem)
        findNavController().navigate(action)
    }

    private fun toggleBookmarkSuccess(position : Int, newsItem: NewsItem) {
       newsAdapter.updateItem(position)
    }

    private fun showError(message: String?) {
        message?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
