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
import com.task.databinding.IngresarLayoutBinding
import com.task.databinding.RegistrarLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by AhmedEltaher
 */
@AndroidEntryPoint
class RegistrarActivity : BaseActivity(){

    private lateinit var binding: RegistrarLayoutBinding

    override fun initViewBinding() {
        binding = RegistrarLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ingresar.setOnClickListener {
            val nextScreenIntent = Intent(this, LoginActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }

    }

    override fun observeViewModel() {
    }


}
