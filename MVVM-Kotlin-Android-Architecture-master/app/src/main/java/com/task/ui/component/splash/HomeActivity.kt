package com.task.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.task.databinding.SplashLayoutBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.login.LoginActivity
import com.task.SPLASH_DELAY
import com.task.databinding.HomeLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by AhmedEltaher
 */
@AndroidEntryPoint
class HomeActivity : BaseActivity(){

    private lateinit var binding: HomeLayoutBinding

    override fun initViewBinding() {
        binding = HomeLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ingresar.setOnClickListener {
            val nextScreenIntent = Intent(this, IngresarActivity::class.java)
            startActivity(nextScreenIntent)

        }

        binding.registrar.setOnClickListener {
            val nextScreenIntent = Intent(this, RegistrarActivity::class.java)
            startActivity(nextScreenIntent)

        }
    }

    override fun observeViewModel() {
    }

}
