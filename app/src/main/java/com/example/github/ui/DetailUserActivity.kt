package com.example.github.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.github.data.DetailUserViewModel
import com.example.github.data.SectionPageAdapter
import com.example.github.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        username?.let { viewModel.setUserDetail(it) }
        showLoading(true, binding.progressBar)

        viewModel.getUserDetail().observe(this, {
            if (it != null){
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)

                    showLoading(false, binding.progressBar)
                }
            }
        })

        binding.tvFollowers.setOnClickListener {
            showLoading(true, binding.progressBar)
            viewModel.getUserDetail().observe(this, { user ->
                if (user != null) {
                    binding.apply {
                        showLoading(false, binding.progressBar)
                        tvFollowers.text = "${user.followers} Followers"
                    }
                }
            })
        }

        binding.tvFollowing.setOnClickListener {
            showLoading(true, binding.progressBar)
            viewModel.getUserDetail().observe(this, { user ->
                if (user != null) {
                    binding.apply {
                        showLoading(false, binding.progressBar)
                        tvFollowing.text = "${user.following} Following"
                    }
                }
            })
        }

        val sectionPageAdapter = SectionPageAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPageAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(state: Boolean, progressBar: ProgressBar) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}