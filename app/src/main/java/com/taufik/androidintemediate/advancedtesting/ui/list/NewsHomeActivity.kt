package com.taufik.androidintemediate.advancedtesting.ui.list

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.databinding.ActivityHomeBinding

class NewsHomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setTab()
    }

    private fun setTab() = with(binding) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this@NewsHomeActivity)
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabNews, viewPager) {tabNews: TabLayout.Tab, position: Int ->
            tabNews.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.home, R.string.bookmark)
    }
}