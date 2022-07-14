package com.taufik.androidintemediate.advancedtesting.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.advancedtesting.data.local.entity.NewsEntity
import com.taufik.androidintemediate.advancedtesting.ui.ViewModelFactory
import com.taufik.androidintemediate.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNewsDetailBinding.inflate(layoutInflater)
    }

    private lateinit var newsDetail: NewsEntity
    private val factory = ViewModelFactory.getInstance(this)
    private val viewModel: NewsDetailViewModel by viewModels {
        factory
    }
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getParcelableData()
        setToolbar()
        showNews()
    }

    private fun getParcelableData() {
        newsDetail = intent.getParcelableExtra<NewsEntity>(NEWS_DATA) as NewsEntity
    }

    private fun setToolbar() {
        supportActionBar?.apply {
            title = newsDetail.title
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }
    }

    private fun showNews() = with(binding) {
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(newsDetail.url.toString())
        }

        viewModel.setNewsData(newsDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.bookmarkStatus.observe(this) { status ->
            setBookmarkState(status)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_bookmark -> {
                viewModel.changeBookmark(newsDetail)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_bookmark)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmarked_white)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_bookmark_white)
        }
    }

    companion object {
        const val NEWS_DATA = "com.taufik.androidintemediate.advancedtesting.ui.detail.NEWS_DATA"
    }
}