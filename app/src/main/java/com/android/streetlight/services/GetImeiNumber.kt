package com.android.streetlight.services

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.android.streetlight.R
import com.android.streetlight.databinding.ActivityGetImeiNumberBinding
import com.android.streetlight.services.imeiNumberModule.ImeiViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_get_imei_number.*


class GetImeiNumber : AppCompatActivity() {
    lateinit var binding: ActivityGetImeiNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_imei_number)
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
        var ImeiViewModel = ViewModelProvider(this)[ImeiViewModel::class.java]

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Sending Data Please Wait")

        binding.getImeiNumber.setOnClickListener {
            if (binding.simnumber.text.toString().isBlank()) {
                binding.simnumber.error = "Required"
            }else if(binding.simnumber.length() < 19){
                Toast.makeText(this, "Incomplete Sim Number(19 digit)", Toast.LENGTH_SHORT).show()
            } else {
                progressDialog.show()
                ImeiViewModel.getImeiNumber("65873hf").observe(this) {
                    if (it?.data != null) {
                        progressDialog.hide()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("imeinumber", it!!.data!![0]!!.IMEINumber)
                        startActivity(intent)
                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()


                    } else {
                        progressDialog.hide()
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }


    }

}