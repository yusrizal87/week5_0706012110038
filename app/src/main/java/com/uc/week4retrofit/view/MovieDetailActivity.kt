package com.uc.week4retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uc.week4retrofit.adapter.CompanyAdapter
import com.uc.week4retrofit.adapter.GenreAdapter
import com.uc.week4retrofit.adapter.LanguageAdapter
import com.uc.week4retrofit.databinding.ActivityMainBinding
import com.uc.week4retrofit.databinding.ActivityMovieDetailBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.model.Genre
import com.uc.week4retrofit.model.ProductionCompany
import com.uc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapterGenre: GenreAdapter
    private lateinit var adapterLanguage: LanguageAdapter
    private lateinit var adapterCompany: CompanyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movie_id", 0)
        Toast.makeText(applicationContext, movieId.toString(), Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.getMovieDetail(Const.API_KEY, movieId)
        viewModel.movieDetails.observe(this, Observer{
                response->
            binding.tvTitleMovieDetail.apply{
                text = response.title

            }
            Glide.with(applicationContext).load(Const.IMG_URL+response.backdrop_path).into(binding.imgPosterMovieDetail)

            binding.tvRating.text = response.vote_average.toString()
            binding.tvSynopsis.text = response.overview

            binding.rvLanguage.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
            adapterLanguage = LanguageAdapter(response.spoken_languages)
            binding.rvLanguage.adapter = adapterLanguage

            binding.rvGenre.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
            adapterGenre = GenreAdapter(response.genres as List<Genre>)
            binding.rvGenre.adapter = adapterGenre

            binding.rvLogoCompany.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            adapterCompany = CompanyAdapter(response.production_companies)
            binding.rvLogoCompany.adapter = adapterCompany
        })



    }
}