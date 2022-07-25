package com.taufik.androidintemediate.advancedatabase.paging.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.androidintemediate.advancedatabase.paging.ViewModelFactory
import com.taufik.androidintemediate.advancedatabase.paging.adapter.LoadingStateAdapter
import com.taufik.androidintemediate.advancedatabase.paging.adapter.QuoteListAdapter
import com.taufik.androidintemediate.databinding.ActivityMainPagingBinding

class MainPagingActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainPagingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setQuoteData()
    }

    private fun setQuoteData() {
        val quoteAdapter = QuoteListAdapter()
        val mainViewModel: MainViewModel by viewModels {
            ViewModelFactory(this)
        }

        binding.apply {
            with(rvQuote) {
                layoutManager = LinearLayoutManager(this@MainPagingActivity)
                setHasFixedSize(true)
                adapter = quoteAdapter.withLoadStateFooter(
                    footer = LoadingStateAdapter { quoteAdapter.retry() }
                )
            }
        }

        mainViewModel.quote.observe(this) {
            quoteAdapter.submitData(lifecycle, it)
        }
    }
}