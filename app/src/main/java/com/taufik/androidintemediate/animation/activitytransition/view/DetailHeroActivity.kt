package com.taufik.androidintemediate.animation.activitytransition.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.taufik.androidintemediate.animation.activitytransition.model.Hero
import com.taufik.androidintemediate.databinding.ActivityDetailHeroBinding

class DetailHeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHeroBinding
    private lateinit var hero: Hero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        setToolbar()
        setData()
    }

    private fun initData() {
        hero = intent.getParcelableExtra<Hero>(EXTRA_HERO) as Hero
    }

    private fun setToolbar() {
        supportActionBar?.apply {
            title = hero.name
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setData() = with(binding) {
        Glide.with(this@DetailHeroActivity)
            .load(hero.photo)
            .circleCrop()
            .into(imgProfile)

        tvName.text = hero.name
        tvDesc.text = hero.description
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_HERO = "com.taufik.androidintemediate.animation.activitytransition.view.EXTRA_HERO"
    }
}