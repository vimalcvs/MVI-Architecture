package com.vimalcvs.mvi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vimalcvs.mvi.adapter.PostsAdapter
import com.vimalcvs.mvi.databinding.ActivityMainBinding
import com.vimalcvs.mvi.intent.MainIntent
import com.vimalcvs.mvi.intent.MainState
import com.vimalcvs.mvi.model.MainViewModel
import com.vimalcvs.mvi.model.ModelPost
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PostsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        adapter = PostsAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        observeViewModel()
        sendIntent(MainIntent.FetchPosts)


        binding.retryButton.setOnClickListener {
            sendIntent(MainIntent.FetchPosts)
        }

        adapter.setOnItemClickListener(object : PostsAdapter.OnItemClickListener {
            override fun onItemClick(posts: ModelPost) {
                val intent = Intent(this@MainActivity, ActivityDetail::class.java).apply {
                    putExtra("EXTRA_POST_ID", posts.id)
                    putExtra("EXTRA_POST_TITLE", posts.title)
                    putExtra("EXTRA_POST_BODY", posts.body)
                }
                startActivity(intent)
            }
        })

    }

    private fun observeViewModel() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is MainState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                    binding.messageTextView.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }

                is MainState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.messageTextView.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }

                is MainState.Posts -> {
                    binding.progressBar.visibility = View.GONE
                    binding.messageTextView.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.submitList(state.modelPosts)
                }

                is MainState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.messageTextView.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.VISIBLE
                    binding.messageTextView.text = state.error
                }

                is MainState.NoData -> {

                }
            }
        }
    }

    private fun sendIntent(intent: MainIntent) {
        lifecycleScope.launch {
            viewModel.intents.send(intent)
        }
    }
}
