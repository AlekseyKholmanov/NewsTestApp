package com.example.newstestapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newstestapp.R
import com.example.newstestapp.databinding.FragmentFavoriteBinding
import com.example.newstestapp.ui.adapters.FingerprintAdapter
import com.example.newstestapp.utils.ToolbarHost
import com.example.newstestapp.ui.adapters.decorators.PaddingItemDecoration
import com.example.newstestapp.ui.adapters.fingerprints.EmptyDFavoriteFingerprint
import com.example.newstestapp.ui.adapters.fingerprints.GoogleSignInFingerprintFingerprint
import com.example.newstestapp.ui.adapters.fingerprints.NewsFingerprint
import com.example.newstestapp.ui.viewModels.FavoriteViewModel
import com.example.newstestapp.utils.SignInHost
import com.example.newstestapp.utils.viewbinding.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding by viewBinding<FragmentFavoriteBinding>()

    private val viewModel: FavoriteViewModel by viewModel()


    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        FingerprintAdapter(getFingerprints())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        with(binding) {
            with(favoriteList) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = this@FavoriteFragment.adapter
                addItemDecoration(PaddingItemDecoration(20))
            }
        }
        viewModel.state
            .onEach {
                adapter.submitList(it.news)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

    }


    private fun getFingerprints() = listOf(
        NewsFingerprint(changeFavoriteState = ::removeFromFavorite, showMore = {}, showCategory = true),
        GoogleSignInFingerprintFingerprint(signIn = ::singIn),
        EmptyDFavoriteFingerprint()
    )

    private fun removeFromFavorite(id: Int) {
        viewModel.userIntent.trySend(FavoriteViewModel.Wish.RemoveFromFavorite(id))

    }

    private fun setUpToolbar() {
        (requireActivity() as ToolbarHost).setTitle("Favorites")
    }

    private fun singIn(){
        (requireActivity() as SignInHost).signIn()
    }



}