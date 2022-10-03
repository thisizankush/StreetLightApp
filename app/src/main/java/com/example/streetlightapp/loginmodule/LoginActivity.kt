package com.example.streetlightapp.loginmodule

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.streetlightapp.R
import com.example.streetlightapp.databinding.LoginBinding
import com.example.streetlightapp.utils.Constants
import com.example.streetlightapp.utils.SessionManagementActivity

class LoginActivity : AppCompatActivity() {
    lateinit var binding: LoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionManagementActivity: SessionManagementActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManagementActivity = SessionManagementActivity(this)
        if (!sessionManagementActivity.isLoggedIn) {
            binding = DataBindingUtil.setContentView(this, R.layout.login)
            viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
            initView()
        } else {
            Toast.makeText(this, "logged in", Toast.LENGTH_SHORT)
                .show()

        }
    }

    private fun initView() {
        binding.loginLayout.setOnClickListener {
            binding.loginLayout.isEnabled = false
            binding.progressbar.visibility = View.VISIBLE
            viewModel.getUserLogin(
                binding.edtEmail.text.toString(),
                binding.edtPass.text.toString()
            ).observe(this) {
                if (TextUtils.isEmpty(binding.edtEmail.text)) {
                    binding.apply {
                        edtEmail.error = "field required"
                        edtEmail.requestFocus()
                        loginLayout.isEnabled = true
                        progressbar.visibility = View.GONE
                    }

                } else if (TextUtils.isEmpty(binding.edtPass.text)) {
                    binding.apply {
                        edtPass.error = "field required"
                        edtPass.requestFocus()
                        loginLayout.isEnabled = true
                        progressbar.visibility = View.GONE
                    }

                } else {
                    if (it != null && it.statusCode == 200) {

                        Toast.makeText(this, "logged in", Toast.LENGTH_SHORT)
                            .show()


                        sessionManagementActivity.insertData(this,Constants.API_TOKEN, it.data?.token)
                        sessionManagementActivity.createLoginSession(
                            it.data?.token,
                            it.data?.user!!.name
                        )
                        Thread.sleep(1200)
                        Log.d("TAG", "initView: ${it.data.token}")
                        binding.progressbar.visibility = View.GONE
//                        val intent = Intent(this, ChooserActivity::class.java)
//                        startActivity(intent)
//                        finish()
                        binding.loginLayout.isEnabled = true

                    } else {
                        binding.progressbar.visibility = View.GONE
                        binding.loginLayout.isEnabled = true
                        Toast.makeText(this, "Incorrect Details", Toast.LENGTH_SHORT).show()

                    }
                }

            }
        }

    }
}