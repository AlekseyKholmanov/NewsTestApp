package com.example.newstestapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newstestapp.R
import com.example.newstestapp.databinding.FragmentNewsBinding
import com.example.newstestapp.ui.adapters.FingerprintAdapter
import com.example.newstestapp.utils.ToolbarHost
import com.example.newstestapp.ui.adapters.decorators.FeedHorizontalDividerItemDecoration
import com.example.newstestapp.ui.adapters.fingerprints.ErrorFingerprint
import com.example.newstestapp.ui.adapters.fingerprints.FullPageLoadingFingerprint
import com.example.newstestapp.ui.adapters.fingerprints.NewsFingerprint
import com.example.newstestapp.ui.adapters.fingerprints.ShortPageLoadingFingerprint
import com.example.newstestapp.ui.viewModels.NewsViewModel
import com.example.newstestapp.ui.viewModels.NewsViewModel.Wish
import com.example.newstestapp.utils.ScrollDownEndlessScrollListener
import com.example.newstestapp.utils.viewbinding.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment : Fragment(R.layout.fragment_news) {


    private val binding by viewBinding<FragmentNewsBinding>()
    val args by navArgs<NewsFragmentArgs>()
    val viewModel: NewsViewModel by viewModel()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        FingerprintAdapter(getFingerprints())
    }

    private val fetchNewNewsScrollListener by lazy(LazyThreadSafetyMode.NONE) {
        object : ScrollDownEndlessScrollListener() {
            override fun loadAfter() {

                if (adapter.itemCount >= 20) {
                    viewModel.userIntent.trySend(Wish.FetchNews(args.newsCategory))
                } else {
                    loadingAfter = false
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        with(binding) {
            with(newsAdapter) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = this@NewsFragment.adapter
                addItemDecoration(FeedHorizontalDividerItemDecoration(20))
                addOnScrollListener(fetchNewNewsScrollListener)
            }
        }

        if (savedInstanceState == null) {
            viewModel.userIntent.trySend(Wish.FetchNews(args.newsCategory))
        }

        viewModel.state.onEach {
            adapter.submitList(it.news)
            fetchNewNewsScrollListener.loadingAfter = false
        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun setUpToolbar() {
        (requireActivity() as ToolbarHost).setTitle("Category: ${args.newsCategory}")
    }

    private fun getFingerprints() = listOf(
        NewsFingerprint(changeFavoriteState = ::changeFavoriteState, showMore = ::openFullNews),
        FullPageLoadingFingerprint(),
        ShortPageLoadingFingerprint(),
        ErrorFingerprint(::fetchAgain)
    )

    private fun changeFavoriteState(id: Int) {
        viewModel.userIntent.trySend(Wish.ChangeFavoriteState(id))
    }

    private fun openFullNews(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    private fun fetchAgain(){

        viewModel.userIntent.trySend(Wish.FetchNews(category = args.newsCategory))
    }
}