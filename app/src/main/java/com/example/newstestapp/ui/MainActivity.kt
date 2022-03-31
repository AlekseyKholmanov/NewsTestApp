package com.example.newstestapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.newstestapp.R
import com.example.newstestapp.databinding.ActivityMainBinding
import com.example.newstestapp.utils.Constants
import com.example.newstestapp.utils.GoogleSignInHelper
import com.example.newstestapp.utils.SignInHost
import com.example.newstestapp.utils.ToolbarHost
import com.example.newstestapp.utils.viewbinding.viewBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class MainActivity : AppCompatActivity(), ToolbarHost, SignInHost {

    private val navHostFragment: NavHostFragment by lazy(LazyThreadSafetyMode.NONE) {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }
    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    private val googleSignInFlow: MutableStateFlow<GoogleSignInAccount?> by inject(
        qualifier = named(Constants.ChannelsName.GoogleQualifier.name)
    )

    val binding by viewBinding(ActivityMainBinding::inflate)
    private val googleSignInHelper by lazy {
        GoogleSignInHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigation()
        setUpToolbar()
        lifecycleScope.launchWhenResumed {
            val authorized = googleSignInHelper.getLastAccountSignIn()
            googleSignInFlow.value = authorized
        }
        googleSignInFlow
            .onEach {
                setUpGoogleAccountInfo(it)
            }
            .launchIn(lifecycleScope)
        binding.avatar.setOnClickListener {
            if (googleSignInHelper.getLastAccountSignIn() == null) {
                signIn()
            } else {
                signOut()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GoogleSignInHelper.requestCode) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun setTitle(title: String) {
        binding.toolbarTitle.text = title
    }

    private fun setUpToolbar() {
        NavigationUI.setupWithNavController(binding.toolbar, navController)
        binding.avatar.load(R.drawable.user)
    }

    private fun setUpGoogleAccountInfo(account: GoogleSignInAccount?) {
        binding.avatar.load(account?.photoUrl) {
            error(R.drawable.user)
            transformations(CircleCropTransformation())
        }
    }

    private fun setupNavigation() {
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnItemReselectedListener { menuItem ->
            val destinationId = when (menuItem.itemId) {
                R.id.main -> R.id.newsCategoryFragment
                R.id.favorites -> R.id.favoriteFragment
                else -> {
                    null
                }
            }

            destinationId?.let {
                navController.popBackStack(
                    destinationId = it,
                    inclusive = false
                )
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            googleSignInFlow.value = account
        } catch (e: ApiException) {
            googleSignInFlow.value = null
        }
    }

    override fun signIn() {
        googleSignInHelper.signIn()
    }

    private fun signOut() {
        googleSignInFlow.value = null
        googleSignInHelper.signOut()
    }
}