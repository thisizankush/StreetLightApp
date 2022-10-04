package com.example.streetlightapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.streetlightapp.databinding.ActivityChooserBinding
import com.example.streetlightapp.utils.SessionManagementActivity

class ChooserActivity : AppCompatActivity() {
    lateinit var binding: ActivityChooserBinding
    private lateinit var sessionManagementActivity: SessionManagementActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chooser)
        sessionManagementActivity = SessionManagementActivity(this)

        binding.createNewPole.setOnClickListener{
//            val intent = Intent(this,GetImeiNumber::class.java)
//            startActivity(intent)
        }

        binding.logOut.setOnClickListener{
            sessionManagementActivity.logoutUser()

        }
    }
}