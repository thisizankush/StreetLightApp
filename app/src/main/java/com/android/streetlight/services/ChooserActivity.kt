package com.android.streetlight.services

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import com.android.streetlight.R
import com.android.streetlight.databinding.ActivityChooserBinding
import com.android.streetlight.databinding.LoginBinding
import com.android.streetlight.services.utils.SessionManagementActivity

class ChooserActivity : AppCompatActivity() {
    lateinit var binding: ActivityChooserBinding
    private lateinit var sessionManagementActivity: SessionManagementActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chooser)
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayShowCustomEnabled(true)
        val actionBarLayout = layoutInflater.inflate(R.layout.custom_action_bar_layout, null) as ViewGroup
        //  actionBarLayout.findViewById<View>(R.id.buttonId).setOnClickListener(this)
        actionBar!!.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)

        actionBar!!.setDisplayShowHomeEnabled(false);
        actionBar!!.setHomeButtonEnabled(false);
        actionBar!!.setDisplayShowTitleEnabled(false);

        actionBar!!.customView = actionBarLayout
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        sessionManagementActivity = SessionManagementActivity(this)

        binding.createNewPole.setOnClickListener{
            val intent = Intent(this,GetImeiNumber::class.java)
            startActivity(intent)
        }

        binding.logOut.setOnClickListener{
            sessionManagementActivity.logoutUser()

        }
    }
}