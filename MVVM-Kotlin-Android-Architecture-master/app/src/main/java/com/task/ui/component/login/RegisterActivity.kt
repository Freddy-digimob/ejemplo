package com.task.ui.component.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.task.data.Resource
import com.task.data.dto.login.LoginResponse
import com.task.databinding.RegisterusersappActivityBinding
import com.task.ui.base.BaseActivity
import com.task.ui.component.recipes.RecipesListActivity
import com.task.ui.component.splash.RegistrarActivity
import com.task.utils.*
import com.task.utils.RegexUtils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by AhmedEltaher
 */
@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: RegisterusersappActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.registrar.setOnClickListener { registrarAlUsuario() }

    }

    override fun initViewBinding() {
        binding = RegisterusersappActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun observeViewModel() {

    }

    private fun doLogin(): Boolean {

        var esValido = true
        if(binding.username.text.toString().equals(""))
        {
            esValido = false
            binding.username.error = "Requerido"
        }
        if(binding.lastname.text.toString().equals(""))
        {
            esValido = false
            binding.lastname.error = "Requerido"
        }
        if(!isValidEmail(binding.emailuser.text.trim().toString()))
        {
            esValido = false
            binding.emailuser.error = "Verifique"
        }
        if(binding.password.text.toString().equals("") || binding.confirpassword.text.toString().equals(""))
        {
            esValido = false
            binding.password.error = "Requerido"
            binding.confirpassword.error = "Requerido"
        }
        if(!binding.password.text.toString().equals(binding.confirpassword.text.toString()))
        {
            esValido = false
            binding.password.error = "Las contraseñas no coinciden"
            binding.confirpassword.error = "Las contraseñas no coinciden"
        }
        return esValido
        /*loginViewModel.doLogin(
            binding.username.text.trim().toString(),
            binding.password.text.toString()
        )*/
    }
    private fun registrarAlUsuario()
    {
        var registrar = doLogin()
        if(registrar)
        {
            Toast.makeText(this, "El usuario es valido",Toast.LENGTH_SHORT).show()
        }
    }
}
