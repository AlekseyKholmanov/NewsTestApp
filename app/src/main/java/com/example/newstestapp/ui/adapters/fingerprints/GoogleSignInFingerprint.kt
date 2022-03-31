package com.example.newstestapp.ui.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.newstestapp.R
import com.example.newstestapp.databinding.ItemGoogleSignInBinding
import com.example.newstestapp.models.ui.FullPageLoadingItem
import com.example.newstestapp.models.ui.GoogleSignInItem
import com.example.newstestapp.ui.adapters.BaseViewHolder
import com.example.newstestapp.ui.adapters.Item
import com.example.newstestapp.ui.adapters.ItemFingerprint

class GoogleSignInFingerprintFingerprint(
    val signIn: () -> Unit
) : ItemFingerprint<ItemGoogleSignInBinding, GoogleSignInItem> {
    override fun isRelativeItem(item: Item): Boolean = item is GoogleSignInItem
    override fun getLayoutId(): Int = R.layout.item_google_sign_in

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemGoogleSignInBinding, GoogleSignInItem> {
        val binding = ItemGoogleSignInBinding.inflate(layoutInflater, parent, false)
        return GoogleSignInFingerprintViewHolder(
            signIn = signIn,
            binding
        )
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<GoogleSignInItem> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<GoogleSignInItem>() {

        override fun areItemsTheSame(
            oldItem: GoogleSignInItem,
            newItem: GoogleSignInItem
        ) = true

        override fun areContentsTheSame(
            oldItem: GoogleSignInItem,
            newItem: GoogleSignInItem
        ) = true
    }
}


class GoogleSignInFingerprintViewHolder(
    signIn: () -> Unit,
    binding: ItemGoogleSignInBinding,
) : BaseViewHolder<ItemGoogleSignInBinding, GoogleSignInItem>(binding) {
    init {
        binding.signIn.setOnClickListener{
            signIn.invoke()
        }
    }
}