package com.chiachen.carousellnews.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chiachen.carousellnews.R
import com.chiachen.carousellnews.databinding.ActivityMainBinding
import com.chiachen.carousellnews.utils.collectWhenStarted
import com.chiachen.presentation.viewmodel.news.NewsContract
import com.chiachen.presentation.viewmodel.news.NewsContract.NewsEvent
import com.chiachen.presentation.viewmodel.news.NewsSortType
import com.chiachen.presentation.viewmodel.news.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: NewsViewModel by viewModels()
    private var newsAdapter: NewsAdapter? = null

    lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.setEvent(NewsEvent.Initialization)

        setupToolbar()
        setupRecyclerView()
        setupRefresh()
        observeViewState()
        observeViewEffect()
    }

    //region Init UI
    private fun setupRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.setEvent(NewsEvent.SwipeRefresh)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            inflateMenu(R.menu.menu_main);
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_recent -> {
                        viewModel.setEvent(NewsEvent.OnSortOptionSelected(NewsSortType.RECENT))
                        true
                    }

                    R.id.action_popular -> {
                        viewModel.setEvent(NewsEvent.OnSortOptionSelected(NewsSortType.POPULAR))
                        true
                    }

                    else -> super.onOptionsItemSelected(item)
                }
            }
        }
    }


    private fun createNewsAdapter(): NewsAdapter {
        return NewsAdapter().apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    binding.newsList.scrollToPosition(0)
                }
            })
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = createNewsAdapter()
        binding.newsList.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }
    //endregion

    //region observe data from view model
    private fun observeViewState() {
        this@MainActivity.collectWhenStarted(viewModel.viewState) { render(it) }
    }

    private fun observeViewEffect() {
        this@MainActivity.collectWhenStarted(viewModel.viewEffect) { reactTo(it) }
    }

    private fun reactTo(viewEffect: NewsContract.NewsViewEffect) {
        when (viewEffect) {
            is NewsContract.NewsViewEffect.ShowSnackBarError -> {
                Snackbar.make(
                    binding.root,
                    viewEffect.error ?: getString(R.string.default_error_msg),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun render(state: NewsContract.NewsViewState) {
        binding.swipeRefresh.isRefreshing = state.loading
        if (!state.loading) {
            if (state.data.isEmpty()) {
                setupNewsResultView(showNews = false)
            } else {
                setupNewsResultView(showNews = true)
                newsAdapter?.submitList(state.data)
            }
        }
    }

    private fun setupNewsResultView(showNews: Boolean) {
        binding.apply {
            newsList.isVisible = showNews
            errorMsg.isVisible = !showNews
        }
    }
    //endregion
}