package com.task.ui.component.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.task.data.Resource
import com.task.data.dto.login.LoginResponse
import com.task.databinding.ForgotLayoutBinding
import com.task.databinding.LoginActivityBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.recipes.RecipesListActivity
import com.task.utils.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by AhmedEltaher
 */
@AndroidEntryPoint
class ForgotActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ForgotLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.login.setOnClickListener { doLogin() }
    }

    override fun initViewBinding() {
        binding = ForgotLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun observeViewModel() {

    }

    private fun doLogin() {

    }

    private fun handleLoginResult(status: Resource<LoginResponse>) {

    }

    private fun navigateToMainScreen() {

    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {

    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
