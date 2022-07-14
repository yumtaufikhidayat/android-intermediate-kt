package com.taufik.androidintemediate.advancedtesting.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.advancedtesting.data.remote.Result
import com.taufik.androidintemediate.advancedtesting.ui.ViewModelFactory
import com.taufik.androidintemediate.databinding.FragmentNewsBinding

class NewsFragment: Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter
    private var tabName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabName = arguments?.getString(ARG_TAB)

        initAdapter()
        initData()
    }

    private fun initAdapter(){
        newsAdapter = NewsAdapter()
        binding.apply {
            with(rvNews) {
                layoutManager = LinearLayoutManager(requireActivity())
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }
    }

    private fun initData() = with(binding) {
        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: NewsViewModel by viewModels {
            factory
        }

        if (tabName == TAB_NEWS) {
            viewModel.getHeadLineNews().observe(viewLifecycleOwner) { response ->
                if (response != null) {
                    when (response) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> {
                            showLoading(false)
                            newsAdapter.submitList(response.data)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            viewError.root.visibility = View.VISIBLE
                            viewError.tvError.text = getString(R.string.something_wrong)
                        }
                    }
                }
            }
        } else if (tabName == TAB_BOOKMARK) {
            viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { response ->
                newsAdapter.submitList(response)
                progressBar.visibility = View.GONE
                viewError.root.visibility = if (response.isNotEmpty()) View.GONE else View.VISIBLE
                viewError.tvError.text = getString(R.string.no_data)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isShow: Boolean) = with(binding) {
        progressBar.isVisible = isShow
    }

    companion object {
        const val ARG_TAB = "com.taufik.androidintemediate.advancedtesting.ui.list.ARG_TAB"
        const val TAB_NEWS = "com.taufik.androidintemediate.advancedtesting.ui.list.TAB_NEWS"
        const val TAB_BOOKMARK = "com.taufik.androidintemediate.advancedtesting.ui.list.TAB_BOOKMARK"
    }
}