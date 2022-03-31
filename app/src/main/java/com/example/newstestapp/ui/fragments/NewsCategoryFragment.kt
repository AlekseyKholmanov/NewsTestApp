package com.example.newstestapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newstestapp.R
import com.example.newstestapp.databinding.FragmentNewsCategoryBinding
import com.example.newstestapp.utils.Constants
import com.example.newstestapp.ui.adapters.FingerprintAdapter
import com.example.newstestapp.utils.ToolbarHost
import com.example.newstestapp.ui.adapters.fingerprints.NewsCategoryFingerprint
import com.example.newstestapp.utils.viewbinding.viewBinding

class NewsCategoryFragment : Fragment(R.layout.fragment_news_category) {

    private val binding by viewBinding<FragmentNewsCategoryBinding>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        FingerprintAdapter(getFingerprints())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            CategoriesList.adapter = adapter
        }
        adapter.submitList(Constants.NewsCategory.values().toList())
        setUpToolbar()
    }

    private fun getFingerprints() = listOf(
        NewsCategoryFingerprint(::openNewsCategory)
    )

    private fun openNewsCategory(category: Constants.NewsCategory) {
        val destination = NewsCategoryFragmentDirections.openNews(category.param)
        findNavController().navigate(destination)
    }



    private fun setUpToolbar(){
        (requireActivity() as ToolbarHost).setTitle("Choose category")
    }
}