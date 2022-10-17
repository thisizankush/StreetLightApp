package com.android.streetlight.services

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.android.streetlight.R
import com.android.streetlight.databinding.ActivityGetImeiNumberBinding
import com.android.streetlight.services.imeiNumberModule.ImeiViewModel
import com.android.streetlight.services.luminaryWithSerialModule.LumnaryWIthSerialViewModel
import com.android.streetlight.services.serialListModule.SerialListViewModel



class GetImeiNumber : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityGetImeiNumberBinding
    var serialList: ArrayAdapter<String>? = null
    var serialAdapter: ArrayAdapter<String>? = null
    var listOfSerialNumber: MutableList<String> = mutableListOf()
    lateinit var serialListViewModel: SerialListViewModel
    lateinit var luminaryWIthSerialViewModel: LumnaryWIthSerialViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_imei_number)
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayShowCustomEnabled(true)
        val actionBarLayout =
            layoutInflater.inflate(R.layout.custom_action_bar_layout, null) as ViewGroup
        //  actionBarLayout.findViewById<View>(R.id.buttonId).setOnClickListener(this)
        actionBar!!.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)

        actionBar!!.setDisplayShowHomeEnabled(false);
        actionBar!!.setHomeButtonEnabled(false);
        actionBar!!.setDisplayShowTitleEnabled(false);

        actionBar!!.customView = actionBarLayout
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        serialListViewModel = ViewModelProvider(this)[SerialListViewModel::class.java]
        luminaryWIthSerialViewModel = ViewModelProvider(this)[LumnaryWIthSerialViewModel::class.java]
        var ImeiViewModel = ViewModelProvider(this)[ImeiViewModel::class.java]
        getSerialList()


        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Sending Data Please Wait")

        binding.getImeiNumber.setOnClickListener{
            progressDialog.show()
            luminaryWIthSerialViewModel.getLuminaryWithSerial(binding.serialNumberList.selectedItem.toString()).observe(this){
                if ((it?.data != null) && (it.statusCode == 200)) {
                    progressDialog.hide()
                    val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("imeinumber", it.data!!.luminaryDetails!![0].IMEINumber)
                        intent.putExtra("casing", it.data!!.luminaryInventory!![0].casing)
                        intent.putExtra("model_number", it.data!!.luminaryInventory!![0].model_number)
                        intent.putExtra("height", it.data!!.luminaryInventory!![0].height)
                        intent.putExtra("wattage", it.data!!.luminaryInventory!![0].wattage)
                        intent.putExtra("leds", it.data!!.luminaryInventory!![0].numberOfLed.toString())
                        startActivity(intent)
                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                }else{
                    progressDialog.hide()
                    Toast.makeText(this, "Please Select", Toast.LENGTH_SHORT).show()

                }
            }
        }

//        binding.getImeiNumber.setOnClickListener {
//            if (binding.simnumber.text.toString().isBlank()) {
//                binding.simnumber.error = "Required"
//            } else if (binding.simnumber.length() < 19) {
//                Toast.makeText(this, "Incomplete Sim Number(19 digit)", Toast.LENGTH_SHORT).show()
//            }
//            else {
//                progressDialog.show()
//                ImeiViewModel.getImeiNumber("65873hf").observe(this) {
//                    if (it?.data != null) {
//                        progressDialog.hide()
//                        val intent = Intent(this, MainActivity::class.java)
//                        intent.putExtra("imeinumber", it!!.data!![0]!!.IMEINumber)
//                        startActivity(intent)
//                        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
//
//
//                    } else {
//                        progressDialog.hide()
//                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//
//        }


    }

    private fun getSerialList() {
        serialListViewModel.getSerialList().observe(this) {
            if (it != null) {

//                for (i in 0 until it.data.size) {
//                    Toast.makeText(this, it.data[i].serial_number.toString(), Toast.LENGTH_SHORT).show()
//                }
                if (it.data.size > 1) {
                    listOfSerialNumber.add(0, "Serial Number(क्रमिक संख्या)")
                    //Log.e("HEY", it.data.size.toString())
                    for (i in 0 until it.data.size) {


                        it.data[i].serial_number?.let { it1 ->
                            listOfSerialNumber.add(
                                 i + 1,
                                it1
                            )
                            //Log.e("HEY", it1 )
                            //Log.e("HEY", i.toString() )


                        }
                    }
                }

            }

            serialList = ArrayAdapter<String>(
                this,
                R.layout.spinner_item,
                R.id.dist,
                listOfSerialNumber
            )
            binding.serialNumberList.adapter = serialList
            binding.serialNumberList.onItemSelectedListener = this
            if (listOfSerialNumber.size > 2) {
                binding.serialNumberListLayout.visibility = View.VISIBLE
            }
        }
    }

//    fun putSerialList() {
//        serialList = ArrayAdapter<String>(
//            this,
//            R.layout.spinner_item,
//            R.id.dist,
//            listOfSerialNumber
//        )
//        binding.serialNumberList.adapter = serialList
//        binding.serialNumberList!!.onItemSelectedListener = this
//        if (listOfSerialNumber.size > 2) {
//            binding.serialNumberListLayout.visibility = View.VISIBLE
//        }
//
//    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}