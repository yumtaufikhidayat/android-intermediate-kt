package com.taufik.androidintemediate.animation.activitytransition.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.animation.activitytransition.model.Hero
import com.taufik.androidintemediate.databinding.ActivityMainHeroBinding
import java.util.ArrayList

class MainHeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainHeroBinding
    private lateinit var listHeroAdapter: ListHeroAdapter
    private val list = ArrayList<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Pahlawan Indonesia"
        setupRecyclerView()
        showListHeroes()
    }

    private fun setupRecyclerView() = with(binding) {
        listHeroAdapter = ListHeroAdapter()
        list.addAll(listHeroes)
        with(rvHeroes) {
            setHasFixedSize(true)
            adapter = listHeroAdapter
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(this@MainHeroActivity, 2)
                } else {
                    LinearLayoutManager(this@MainHeroActivity)
                }
        }
    }

    private fun showListHeroes() {
        listHeroAdapter.submitList(list)
    }

    private val listHeroes: ArrayList<Hero>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataPhoto = resources.getStringArray(R.array.data_photo)
            val listHero = ArrayList<Hero>()
            for (i in dataName.indices) {
                val hero = Hero(dataName[i], dataDescription[i], dataPhoto[i])
                listHero.add(hero)
            }
            return listHero
        }
}