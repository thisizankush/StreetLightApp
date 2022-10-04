package com.example.streetlightapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.android.streetlight.services.imeiNumberModule.ImeiViewModel
import com.example.streetlightapp.databinding.ActivityGetImeiNumberBinding
import kotlinx.android.synthetic.main.activity_get_imei_number.*


class GetImeiNumber : AppCompatActivity() {
    lateinit var binding: ActivityGetImeiNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_imei_number)
        val ImeiViewModel = ViewModelProvider(this)[ImeiViewModel::class.java]

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
                ImeiViewModel.getImeiNumber(simnumber.toString()).observe(this) {
                    if (it != null && it.statusCode == 200) {
                        progressDialog.hide()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("imeinumber", "657w9dh")
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