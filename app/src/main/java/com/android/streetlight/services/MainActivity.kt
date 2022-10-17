package com.android.streetlight.services

import android.Manifest
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.android.streetlight.R
import com.android.streetlight.databinding.SingleMainUiBinding
import com.android.streetlight.services.assetModule.AssetDataViewModel
import com.android.streetlight.services.batteryModule.BatteryInventoryViewModel
import com.android.streetlight.services.blockModule.BlockDataModel
import com.android.streetlight.services.blockModule.BlockViewModel
import com.android.streetlight.services.districtModule.DistrictDataModel
import com.android.streetlight.services.districtModule.DistrictViewModel
import com.android.streetlight.services.panchayatModule.PanchayatDataModel
import com.android.streetlight.services.panchayatModule.PanchayatViewModel
import com.android.streetlight.services.poleModule.PoleDataViewModel
import com.android.streetlight.services.services.LocationService
import com.android.streetlight.services.subVenderModule.SubVendorDataModel
import com.android.streetlight.services.subVenderModule.SubVendorViewModel
import com.android.streetlight.services.uploadImageModule.UploadImageViewModel
import com.android.streetlight.services.utils.Constants
import com.android.streetlight.services.utils.SessionManagementActivity
import com.android.streetlight.services.vendorModule.VendorDataModel
import com.android.streetlight.services.vendorModule.VendorViewModel
import com.android.streetlight.services.wardModule.WardModel
import com.android.streetlight.services.wardModule.WardPoleViewModel
import com.android.streetlight.services.wardModule.WardViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.beneficiaryAddress
import kotlinx.android.synthetic.main.activity_main.beneficiaryname
import kotlinx.android.synthetic.main.activity_main.poleId
import kotlinx.android.synthetic.main.activity_main.spin
import kotlinx.android.synthetic.main.activity_main.spin1
import kotlinx.android.synthetic.main.activity_main.spin2
import kotlinx.android.synthetic.main.activity_main.spin3
import kotlinx.android.synthetic.main.activity_main.vendor
import kotlinx.android.synthetic.main.single_main_ui.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, SensorEventListener {
    private var broadcastReceiver: BroadcastReceiver? = null
    private var currentDegree = 0f
    var longitude: String = "28.46800804"
    var latitude: String = "77.14366618"
    var imageUrl: String = "Y"
    private var check: Int = 0;
    private var permissionCheck: Int = 0;
    lateinit var switch: Switch
    var dist: String = "Y"
    var block: String = "Y"
    var panch: String = "Y"
    var ward: String = "Y"
    var wardNew: String = "Y"
    var wardPole: String = "Y"
    var srBattryiD: String = ""
    var srPvId: String = ""
    var srLuminaryId: String = ""
    var vendorLogo: String = ""
    private var mSensorManager: SensorManager? = null
    private lateinit var sessionManagementActivity: SessionManagementActivity
    lateinit var ctx: Context
    var list: ArrayList<DistrictDataModel.Data.District> = ArrayList()
    var blocklist: ArrayList<BlockDataModel.Data.Blocks> = ArrayList()
    var panchayatlist: ArrayList<PanchayatDataModel.Data.Panchayats> = ArrayList()
    var wardlist: ArrayList<WardModel.Data.Wards> = ArrayList()
    var vendorlist: ArrayList<VendorDataModel.Data.Data.Vendor> = ArrayList()
    var subVendorlist: ArrayList<SubVendorDataModel.Data.Data.Subvendor> = ArrayList()
    lateinit var districtViewModel: DistrictViewModel
    lateinit var blockViewModel: BlockViewModel
    var progressDialog: ProgressDialog? = null
    var dataLock: Boolean = false
    lateinit var panchayatViewModel: PanchayatViewModel
    lateinit var wardViewModel: WardViewModel
    private lateinit var poleDataviewModel: PoleDataViewModel
    lateinit var uploadAttachmentViewModel: UploadImageViewModel
    lateinit var vendorViewModel: VendorViewModel
    lateinit var subVendorViewModel: SubVendorViewModel
    lateinit var wardPoleViewModel: WardPoleViewModel
    lateinit var createAssetViewModel: AssetDataViewModel
    lateinit var inventoryViewModel: InventoryViewModel
    lateinit var inventorySolarViewModel: InventorySolarViewModel
    lateinit var getBatteryInventoryViewModel: BatteryInventoryViewModel
    private lateinit var binding: SingleMainUiBinding
    protected var mLastLocation: Location? = null
    var catAdapter: ArrayAdapter<DistrictDataModel.Data.District>? = null
    var poleIdsAdapter: ArrayAdapter<String>? = null
    var serialAdapterLuminary: ArrayAdapter<String>? = null
    var serialNumberAdapterBattry: ArrayAdapter<String>? = null
    var serialNumberAdapterPv: ArrayAdapter<String>? = null
    var blockAdapter: ArrayAdapter<BlockDataModel.Data.Blocks>? = null
    var panchayatAdapter: ArrayAdapter<PanchayatDataModel.Data.Panchayats>? = null
    var wardAdapter: ArrayAdapter<WardModel.Data.Wards>? = null
    var vendorAdapter: ArrayAdapter<VendorDataModel.Data.Data.Vendor>? = null
    lateinit var moduleViewModel: ModuleViewModel
    var subVendorAdapter: ArrayAdapter<SubVendorDataModel.Data.Data.Subvendor>? = null
    var imeinum = ""
    var casing = ""
    var model_number = ""
    var height = ""
    var wattage = ""
    var leds = ""
    lateinit var ss: String
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    private var mFusedLocationClient: FusedLocationProviderClient? = null


    val day = c.get(Calendar.DAY_OF_MONTH)
    lateinit var blockid: String
    lateinit var panchayatid: String
    lateinit var wardid: String
    lateinit var districtid: String
    lateinit var vendorid: String
    var imgFile: File? = null
    lateinit var bitmap: Bitmap
    var photoURI: Uri? = null
    var listOfVehicleNames: MutableList<String> = mutableListOf()
    var listOfSerialNumberLuminary: MutableList<String> = mutableListOf()
    var listOfSerialNumberBattry: MutableList<String> = mutableListOf()
    var listOfSerialNumberPv: MutableList<String> = mutableListOf()
    var moduleList: ArrayList<ModuleDataModule.Data> = ArrayList()
    var solarList: ArrayList<ModuleDataModule.Data> = ArrayList()
    var battryList: ArrayList<ModuleDataModule.Data> = ArrayList()
    var moduleAdapter: ArrayAdapter<ModuleDataModule.Data>? = null
    var solarModuleAdapter: ArrayAdapter<ModuleDataModule.Data>? = null
    var solarModuleAdapterpv: ArrayAdapter<ModuleDataModule.Data>? = null

    var manufacturernameid: String? = null
    var manufacturernameidbattery: String? = null
    var manufacturernameidpv: String? = null
    var pvname: String? = null
    var battryname: String? = null
    var luminaryname: String? = null
    val wardIds = listOf<String>(
        "001",
        "002",
        "003",
        "004",
        "005",
        "006",
        "007",
        "008",
        "009",
        "010",
        "011",
        "012",
        "013",
        "014",
        "015"
    )


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctx = this
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // initialize your android device sensor capabilities
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager?;
        binding = DataBindingUtil.setContentView(this, R.layout.single_main_ui)
        sessionManagementActivity = SessionManagementActivity(this)
        districtViewModel = ViewModelProvider(this)[DistrictViewModel::class.java]
        blockViewModel = ViewModelProvider(this)[BlockViewModel::class.java]
        panchayatViewModel = ViewModelProvider(this)[PanchayatViewModel::class.java]
        wardViewModel = ViewModelProvider(this)[WardViewModel::class.java]
        poleDataviewModel = ViewModelProvider(this)[PoleDataViewModel::class.java]
        uploadAttachmentViewModel = ViewModelProvider(this)[UploadImageViewModel::class.java]
        vendorViewModel = ViewModelProvider(this)[VendorViewModel::class.java]
        subVendorViewModel = ViewModelProvider(this)[SubVendorViewModel::class.java]
        wardPoleViewModel = ViewModelProvider(this)[WardPoleViewModel::class.java]
        createAssetViewModel = ViewModelProvider(this)[AssetDataViewModel::class.java]
        inventoryViewModel = ViewModelProvider(this)[InventoryViewModel::class.java]
        inventorySolarViewModel = ViewModelProvider(this)[InventorySolarViewModel::class.java]
        getBatteryInventoryViewModel =
            ViewModelProvider(this)[BatteryInventoryViewModel::class.java]
        moduleViewModel = ViewModelProvider(this)[ModuleViewModel::class.java]
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayShowCustomEnabled(true)
        val actionBarLayout =
            layoutInflater.inflate(R.layout.custom_action_bar_layout, null) as ViewGroup
        //  actionBarLayout.findViewById<View>(R.id.buttonId).setOnClickListener(this)

        mSensorManager!!.registerListener(
            this, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        );
        actionBar!!.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)

        actionBar!!.setDisplayShowHomeEnabled(false);
        actionBar!!.setHomeButtonEnabled(false);
        actionBar!!.setDisplayShowTitleEnabled(false);

        actionBar!!.customView = actionBarLayout
        actionBar!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        binding.apply {
            spvType.isEnabled = false
        }
        dataLock = binding.switchUi.isChecked
        binding.switchUi.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The toggle is enabled
                binding.apply {
                    uploadImage.visibility = View.VISIBLE
                    spin.isEnabled = false
                    spin1.isEnabled = false
                    spin2.isEnabled = false
                    spin3.isEnabled = false
                    poleId.isEnabled = false
                    vendor.isClickable = false
                    beneficiaryAddress.isEnabled = false
                    beneficiaryname.isEnabled = false
                    installperson.isEnabled = false
                    vendor.isEnabled = false


                }


            } else {
                // The toggle is disabled
                binding.uploadImage.visibility = View.GONE
                spin.isEnabled = true
                spin1.isEnabled = true
                spin2.isEnabled = true
                spin3.isEnabled = true
                poleId.isEnabled = true
                vendor.isClickable = true
                beneficiaryAddress.isEnabled = true
                beneficiaryname.isEnabled = true
                installperson.isEnabled = true
                vendor.isEnabled = true
                binding.retake.visibility = View.GONE
                binding.mainImageContainer.visibility = View.GONE
                binding.details.setText("")
            }
        }

        val permissionLocation =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getLastLocation()
            val intent = Intent(this, LocationService::class.java)
            startService(intent)
        }
        getModulesLuminary()
        getModulesSolar()
        getModulesPv()
        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog!!.setTitle("Loading")
        progressDialog!!.setMessage("Sending Data Please Wait")
        binding.poleId.isEnabled = true
        binding.installationdate.setText("${month + 1}/$day/$year")
        binding.installationdateAsset.setText("${month + 1}/$day/$year")

        imeinum = intent.getStringExtra("imeinumber").toString()
        casing = intent.getStringExtra("casing").toString()
        model_number = intent.getStringExtra("model_number").toString()
        height = intent.getStringExtra("height").toString()
        wattage = intent.getStringExtra("wattage").toString()
        leds = intent.getStringExtra("leds").toString()

        binding.casingLuminaire.setText(casing)
        binding.modelNumberLuminaire.setText(model_number)
        binding.heightLuminaire.setText(height)
        binding.wattageLuminaire.setText(wattage)
        binding.numberOfLeds.setText(leds)
        binding.imeinumber.setText(imeinum)
        binding.imeinumber.isEnabled = false
        binding.retake.setOnClickListener {
            if (binding.retake.visibility == View.VISIBLE) {
                takePicture()
            }
        }
        binding.submitButtonAsset.setOnClickListener {

            if (binding.imeinumber.text.toString().isBlank()) {
                binding.imeinumber.error = "Required"
                binding.imeinumber.requestFocus()

            } else if (dist.startsWith("Y")) {
                Toast.makeText(ctx, "first select District", Toast.LENGTH_SHORT).show()
                binding.spin.requestFocus()
            } else if (block.startsWith("Name")) {
                Toast.makeText(ctx, "first select Block", Toast.LENGTH_SHORT).show()
                binding.spin1.requestFocus()
            } else if (panch.startsWith("Name")) {
                Toast.makeText(ctx, "first select Panchayat", Toast.LENGTH_SHORT).show()
                binding.poleNumberSpinner.requestFocus()
            } else if (wardPole.equals("Select")) {
                Toast.makeText(ctx, "first select pole id", Toast.LENGTH_SHORT).show()
                binding.poleNumberSpinner.requestFocus()
            } else if (binding.beneficiaryname.text.toString().isBlank()) {
                binding.beneficiaryname.error = "Required"
                binding.beneficiaryname.requestFocus()
//
            } else if (binding.beneficiaryAddress.text.toString().isBlank()) {
                binding.beneficiaryAddress.error = "Required"
                binding.beneficiaryAddress.requestFocus()
//
            } else if (binding.installationdate.text.toString().isBlank()) {
                binding.installationdate.error = "Required"
                binding.installationdate.requestFocus()

//


//

//                else if(binding.length.text.toString().isBlank()){
//                    binding.length.error = "Required"
////
//                }else if(binding.size.text.toString().isBlank()){
//                    binding.size.error = "Required"
////
//                }else if(binding.remarks.text.toString().isBlank()){
//                    binding.remarks.error = "Required"
                imageUrl = "HJ"
//
            } else if (binding.installperson.text.toString().isBlank()) {
                binding.installperson.error = "Required"
                binding.installperson.requestFocus()

            } else if (binding.installationdateAsset.text.toString().isBlank()) {
                binding.installationdate.error = "Required"
                binding.installationdateAsset.requestFocus()

            } else if (binding.modelNumberLuminaire.text.toString().isBlank()) {
                binding.modelNumberLuminaire.error = "Required"
                binding.modelNumberLuminaire.requestFocus()

            } else if (binding.manufacturingdatePvModules.text.toString().isBlank()) {
                binding.manufacturingdatePvModules.error = "Required"
                binding.manufacturingdatePvModules.requestFocus()

            } else if (binding.wattage.text.toString().isBlank()) {
                binding.wattage.error = "Required"
                binding.wattage.requestFocus()

            } else if (binding.solarCell.text.toString().isBlank()) {
                binding.solarCell.error = "Required"
                binding.solarCell.requestFocus()

            } else if (binding.casingLuminaire.text.toString().isBlank()) {
                binding.casingLuminaire.error = "Required"
                binding.casingLuminaire.requestFocus()

            } else if (binding.modelNumberLuminaire.text.toString().isBlank()) {
                binding.modelNumberLuminaire.error = "Required"

            } else if (binding.heightLuminaire.text.toString().isBlank()) {
                binding.heightLuminaire.error = "Required"

            } else if (binding.wattageLuminaire.text.toString().isBlank()) {
                binding.wattageLuminaire.error = "Required"

            } else if (binding.modelNumberBattery.text.toString().isBlank()) {
                binding.modelNumberBattery.error = "Required"

            } else if (binding.manufacturingDateBattery.text.toString().isBlank()) {
                binding.manufacturingDateBattery.error = "Required"

            } else if (wardPole.equals("Select")) {
                Toast.makeText(ctx, "first select pole id", Toast.LENGTH_SHORT).show()
                binding.poleNumberSpinner.requestFocus()
            } else if (binding.details.text.length < 7) {
                Toast.makeText(ctx, "upload image first", Toast.LENGTH_SHORT).show()

            } else {
                uploadImageFirst()

            }

        }
        binding.uploadImage.setOnClickListener {
            if (dist.startsWith("Y")) {
                Toast.makeText(ctx, "first select District", Toast.LENGTH_SHORT).show()
                binding.spin.requestFocus()
            } else if (block.startsWith("Name")) {
                Toast.makeText(ctx, "first select Block", Toast.LENGTH_SHORT).show()
                binding.spin1.requestFocus()
            } else if (panch.startsWith("Name")) {
                Toast.makeText(ctx, "first select Panchayat", Toast.LENGTH_SHORT).show()
                binding.poleNumberSpinner.requestFocus()
            } else if (wardPole.equals("Select")) {
                Toast.makeText(ctx, "first select pole id", Toast.LENGTH_SHORT).show()
                binding.poleNumberSpinner.requestFocus()
            } else if (binding.beneficiaryname.text.toString().isBlank()) {
                binding.beneficiaryname.requestFocus()
//
            } else if (binding.beneficiaryAddress.text.toString().isBlank()) {
                binding.beneficiaryAddress.error = "Required"
                binding.beneficiaryAddress.requestFocus()
//
            } else {
                takePicture()

            }
        }
        takePermissions()
        init()
        getVendor()
    }

    private fun uploadImageFirst() {
        runslowCall()
        progressDialog!!.show()
        val file = imgFile?.let { it1 -> saveBitmapToFile(it1) }
        val path = dist.lowercase()
        val fbody = RequestBody.create(
            MediaType.parse("image/jpeg"),
            imgFile
        )
        val pathId = RequestBody.create(
            MediaType.parse("text/plain"),
            path
        )

        val file_part = MultipartBody.Part.createFormData("image", file?.name, fbody)
        uploadAttachmentViewModel.uploadAttachment(file_part, pathId).observe(this) {
            if (it != null) {

                imageUrl = it!!.data!!.toString()
                Thread.sleep(2000)
                progressDialog!!.hide()
                Toast.makeText(ctx, "Image Uploaded Successfully", Toast.LENGTH_SHORT)
                    .show()
                progressDialog!!.show()
                Thread.sleep(1200)
                runslowCall()
                //Thread.sleep(1200)
            } else {
                Toast.makeText(ctx, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun runslowCall() {
        Handler(mainLooper).postDelayed({
            createPoleAndAsset(imageUrl)
        }, 3000)
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result
                    latitude = task.result.latitude.toString()
                    longitude = task.result.longitude.toString()
                } else {
                    Log.w("TAG", "getLastLocation:exception", task.exception)
                    //showMessage(getString(R.string.no_location_detected))
                }
            }
    }


    private fun getVendor() {
        val ib = VendorDataModel.Data.Data.Vendor(
            active = null,
            address = null,
            aggrementnumer = listOf(),
            countryCode = null,
            createdAt = null,
            email = null,
            id = "0",
            mobileNumber = null,
            name = "Select",
            updatedAt = null,
            v = null,
            logo = "",
            workorder = listOf()

        )
        vendorViewModel.getVendor().observe(this) {
            if (it != null && it.statusCode == 200) {
                for (i in it.data!!.data!!?.vendor!!) {
                    vendorlist.add(i!!)
                }
                vendorlist.sortBy { it.name }
                vendorlist.add(0, ib)
                vendorAdapter = ArrayAdapter<VendorDataModel.Data.Data.Vendor>(
                    this,
                    R.layout.spinner_item,
                    R.id.dist,
                    vendorlist
                )
                binding.vendor.adapter = vendorAdapter
                binding.vendor!!.onItemSelectedListener = this


            } else {
                Toast.makeText(this, "failed1", Toast.LENGTH_SHORT).show()
                binding.vendor.visibility = View.GONE
            }
        }

    }


    private fun init() {
        val ib = DistrictDataModel.Data.District(
            id = "0",
            name = "Name Of District(ज़िला)"
        )
        districtViewModel.getDistrict(Constants.STATE_ID).observe(this) {
            if (it != null && it.statusCode == 200) {

                for (i in it.data!!) {
                    list.add(i!!.district!!)
                }
                list.sortBy { it.name.toString() }
                list.add(0, ib)

                catAdapter = ArrayAdapter<DistrictDataModel.Data.District>(
                    this,
                    R.layout.spinner_item,
                    R.id.dist,
                    list
                )
                binding.spin.adapter = catAdapter
                binding.spin!!.onItemSelectedListener = this


            } else {
                binding.districtSpinner.visibility = View.GONE
            }
        }


    }

    private fun getWardPoleIds() {
        if (ward.length == 1) {
            wardNew = "W0" + ward
        } else {
            wardNew = "W" + ward
        }
        ss = dist.substring(0, 3) + "/" + block.substring(0, 3) + "/" + panch.substring(
            0,
            3
        ) + "/" + wardNew.substring(0, 3) + "/"
        if (ss.length > 6) {
            binding.poleNumberSpinner.visibility = View.VISIBLE
        } else {
            binding.poleNumberSpinner.visibility = View.GONE
        }
        val mutableList = mutableListOf<String>()
        for (i in 0 until wardIds.minus(listOfVehicleNames).size) {
            mutableList.add(0, ss + wardIds.minus(listOfVehicleNames)[i])
        }
        mutableList.sort()
        mutableList.add(0, "Select Pole ID(खंभा संख्या)")
        //mutableList.reverse()
        poleIdsAdapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            R.id.dist,
            mutableList
        )
        binding.poleId.adapter = poleIdsAdapter
        binding.poleId!!.onItemSelectedListener = this

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun takePermissions() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CAMERA,
                ), 100
            )
        } else {
            //finish()
            //Toast.makeText(this, "Please give the required permission", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionCheck == 1
                getLastLocation()
                val intent = Intent(this@MainActivity, LocationService::class.java)
                startService(intent)

            } else {
                finish()
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Please give the required permission", Toast.LENGTH_LONG)
                    .show();
                //  takePermissions();


            }

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK) {
//            val canvas = Canvas(bitmap.copy(Bitmap.Config.ARGB_8888, true))
//            binding.compassImage.animation.cancel()
//            binding.compassImage.clearAnimation()
            mSensorManager?.unregisterListener(this)


            val path = photoURI.toString().split("/").toTypedArray()
            val captureImage = path[10]
            uploadImage.visibility = View.GONE
            binding.retake.visibility = View.VISIBLE

            val addresses: List<Address>
            val geocoder: Geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
            val address =
                addresses[0].getAddressLine(0).split(",")
                    .toTypedArray() // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            val location = addresses[0].getAddressLine(0)

            /*val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode*/

            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH)
            val cDateTime = dateFormat.format(Date())
            val yourArray: List<String> = cDateTime.split("+")
            val curDate = yourArray[0].split("T")
            with(binding) {

                //details.setText("${curDate[0]} ${curDate[1]}]\n$latitude$longitude,\n${address[1]},$state,\n${dist.substring(0,3)}/" + "${block.substring(0, 3)}/" + "${panch.substring(0, 3)}/" + "${wardNew.substring(0, 3)}/" + "\n${binding.beneficiaryname.text.toString()}")
                var edit = "${curDate[0]} ${curDate[1]}\n${
                    latitude.substring(
                        0,
                        7
                    )
                }, ${
                    longitude.substring(
                        0,
                        7
                    )
                },\n${address[1]},$state,\n${wardPole}\n${binding.beneficiaryAddress.text.toString()}" + "\n${binding.beneficiaryname.text.toString()}"

                details.setText(edit)
                details.setText(edit.uppercase())
                //poleId.setText(ss.uppercase())
                //Selection.setSelection(poleId.getText(), poleId.text!!.length);
            }
            binding.logo.visibility = View.VISIBLE
            Glide
                .with(ctx)
                .load(vendorLogo)
                .fitCenter()
                .placeholder(R.drawable.logo)
                .into(binding.logo)
            showImage(captureImage)
        } else {
            binding.mainImageContainer.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

//        mSensorManager!!.registerListener(
//            this, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION),
//            SensorManager.SENSOR_DELAY_GAME
//        );

        if (broadcastReceiver == null) {
            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent) {
                    val coordinates = intent.extras!!["coordinates"].toString()
                    val latLng = coordinates.split(" ".toRegex()).toTypedArray()
                    longitude = latLng[0]
                    latitude = latLng[1]
                    val currentDate = intent.extras!!["time"]
                    var tim = getDateFromMilliseconds(currentDate as Long)
                    //Toast.makeText(this@MainActivity,"" + latitude + longitude, Toast.LENGTH_SHORT).show()
                }
            }
        }
        registerReceiver(broadcastReceiver, IntentFilter("location_update"))
    }

    private fun getDateFromMilliseconds(millis: Long): String {
        val dateFormat = "MM/dd/yyyy"
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val calendar = Calendar.getInstance()

        calendar.timeInMillis = millis
        return formatter.format(calendar.time)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        when (p0?.id) {
            R.id.spin -> {
                if (++check > 1) {
                    val selectedObject = spin.selectedItem as DistrictDataModel.Data.District
                    binding.blockSpinner.visibility = View.VISIBLE
                    districtid = selectedObject.id.toString()
                    getBlocks(selectedObject.id.toString())
                    dist = selectedObject.name.toString()
                }


            }
            R.id.spin1 -> {
                val selectedObject = spin1.selectedItem as BlockDataModel.Data.Blocks
                binding.panchayatSpinner.visibility = View.VISIBLE
                blockid = selectedObject.id.toString()
                getPanchayat(selectedObject.id.toString())
                block = selectedObject.name.toString()
            }
            R.id.spin2 -> {
                val selectedObject = spin2.selectedItem as PanchayatDataModel.Data.Panchayats
                binding.wardSpinner.visibility = View.VISIBLE
                panchayatid = selectedObject.id.toString()
                getWards(selectedObject.id.toString())
                panch = selectedObject.name.toString()
            }
            R.id.spin3 -> {
                val selectedObject = spin3.selectedItem as WardModel.Data.Wards
                ward = selectedObject.name.toString()
                wardid = selectedObject.id.toString()
                getWardPoles(wardid)
            }
            R.id.poleId -> {
                val selectedObject = poleId.selectedItem as String
                wardPole = selectedObject
                val spinnerPosition: Int = poleIdsAdapter!!.getPosition(selectedObject)
                poleId.setSelection(spinnerPosition)
                //getWardPoles()

            }

            R.id.vendor -> {
                if (++check > 1) {
                    val selectedObject = vendor.selectedItem as VendorDataModel.Data.Data.Vendor
                    binding.subVendor.visibility = View.VISIBLE
                    getSubVendor(selectedObject.id.toString())
                    vendorid = selectedObject.id.toString()
                    if (selectedObject.logo?.isEmpty() == true) {
                        //Toast.makeText(ctx, "no data found for logo image", Toast.LENGTH_SHORT).show()
                    } else {
                        vendorLogo = selectedObject.logo.toString()
                    }
                }


            }
            R.id.manufacturingNameLuminaire -> {
                if (++check > 1) {
                    val selectedObject =
                        manufacturingNameLuminaire.selectedItem as ModuleDataModule.Data
                    binding.manufacturingNameLuminaire.visibility = View.VISIBLE
                    getLuminaryInventory(selectedObject.id.toString())
                    manufacturernameid = selectedObject.id.toString()
                    luminaryname = selectedObject.name.toString()
                    getLuminarySerialNumber()

                }

            }
            R.id.manufacturingNameBattery -> {
                if (++check > 1) {
                    val selectedObject =
                        manufacturingNameBattery.selectedItem as ModuleDataModule.Data
                    binding.manufacturingNameBattery.visibility = View.VISIBLE
                    getBatteryInventory(selectedObject.id.toString())
                    manufacturernameidbattery = selectedObject.id.toString()
                    battryname = selectedObject.name.toString()
                    getBattryserialNumber()
                }


            }

            R.id.manufacturingName -> {
                if (++check > 1) {
                    val selectedObject = manufacturingName.selectedItem as ModuleDataModule.Data
                    binding.manufacturingName.visibility = View.VISIBLE
                    getSolarInventory(selectedObject.id.toString())
                    manufacturernameidpv = selectedObject.id.toString()
                    pvname = selectedObject.name.toString()
                    getSolarSerialNumber()
                }


            }
            R.id.serialNumber -> {
                //pvodule
                val selectedObject = serialNumber.selectedItem as String
                srPvId = selectedObject
                getSolarInventory(manufacturernameidpv!!)


            }
            R.id.serialNumberBattery -> {
                val selectedObject = serialNumberBattery.selectedItem as String
                srBattryiD = selectedObject
                getBatteryInventory(manufacturernameidbattery!!)

            }
            R.id.serialNumberluminary -> {
                val selectedObject = serialNumberluminary.selectedItem as String
                srLuminaryId = selectedObject
                getLuminaryInventory(manufacturernameid!!)

            }

//            R.id.sub_vendor -> {
//                val selectedObject =
//                    sub_vendor.selectedItem as SubVendorDataModel.Data.Data.Subvendor
//                sub_vendorid = selectedObject.id.toString()
//            }
        }
    }

    private fun getLuminaryInventory(manufacturernameid: String) {
        inventoryViewModel.getLuminary(manufacturernameid!!, srLuminaryId).observe(this) {
            if (it != null) {

                if (srLuminaryId.length < 1 && it!!.data!!.size > 1) {
                    listOfSerialNumberLuminary.add(0, "Luminary Serial Number(ल्यूमिनेरी क्रमिक संख्या)")
                    for (i in 0 until it!!.data!!.size) {

                        it!!.data!![i]?.serial_number?.let { it1 ->
                            listOfSerialNumberLuminary.add(
                                i + 1,
                                it1
                            )

                        }
                    }
                }


                if (srLuminaryId.length > 4 && !srLuminaryId.equals("Luminary Serial Number(ल्यूमिनेरी क्रमिक संख्या)")) {
                    binding.apply {
                        modelNumberLuminaire.setText(it.data!![0]!!.model_number)
                        heightLuminaire.setText(it.data!![0]!!.height)
                        luxMeasured.setText(it.data!![0]!!.luxMeasured)
                        casingLuminaire.setText(it.data!![0]!!.casing)
                        wattageLuminaire.setText(it.data!![0]!!.wattage)
                        numberOfLeds.setText(it!!.data!![0]!!.numberOfLed)


                        //it!!.data!![0]!!.numberOfLed?.let { it1 -> numberOfLeds.setText(it1) }
                    }
                }


            }
        }
    }

    fun getLuminarySerialNumber() {
        serialAdapterLuminary = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            R.id.dist,
            listOfSerialNumberLuminary
        )
        binding.serialNumberluminary.adapter = serialAdapterLuminary
        binding.serialNumberluminary!!.onItemSelectedListener = this
        if (listOfSerialNumberLuminary.size > 2) {
            binding.serialNumberluminarylayout.visibility = View.VISIBLE
        }

    }

    private fun getSolarInventory(manufacturernameidpv: String) {
        inventorySolarViewModel.getSolar(manufacturernameidpv, srPvId).observe(this) {
            if (it != null) {

                if (srPvId.length < 1 && it!!.data!!.size > 1) {
                    listOfSerialNumberPv.add(0, "Solar Serial Number")
                    for (i in 0 until it!!.data!!.size) {

                        it!!.data!![i]?.serial_number?.let { it1 ->
                            listOfSerialNumberPv.add(
                                i + 1,
                                it1
                            )
                        }

                    }
                }


                if (srPvId.length > 4 && !srPvId.equals("Solar Serial Number")) {

                    binding.modelNumber.setText(it.data!![0]!!.model_number)
                    var dat = it.data!![0]!!.manufacturingDate?.split("T")?.toTypedArray()
                    binding.manufacturingdatePvModules.setText(dat?.get(0))
                    binding.wattage.setText(it!!.data!![0]!!.wattage)
                    binding.cellVoltageBattery.setText(it!!.data!![0]!!.solarcell)
                    binding.solarCell.setText(it.data!![0]!!.solarcell)
                    if (it.data[0]!!.sPVType.equals("Mono")) {
                        binding.spvType.setSelection(0)
                    } else {
                        binding.spvType.setSelection(1)
                    }
                }

                // binding.spvType.setSelection(0,)
            }
        }
    }

    fun getSolarSerialNumber() {
        serialNumberAdapterPv = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            R.id.dist,
            listOfSerialNumberPv
        )
        binding.serialNumber.adapter = serialNumberAdapterPv
        binding.serialNumber!!.onItemSelectedListener = this
        if (listOfSerialNumberPv.size > 1) {
            binding.serialNumberLayout.visibility = View.VISIBLE
        }
    }

    private fun getBatteryInventory(manufacturernameidbattery: String) {
        getBatteryInventoryViewModel.getBatteryInventory(manufacturernameidbattery!!, srBattryiD)
            .observe(this) {
                if (it != null) {

                    if (srBattryiD.length < 2 && it!!.data!!.size > 1) {
                        listOfSerialNumberBattry.add(0, "Battery Serial Number(क्रमिक संख्या)")
                        for (i in 0 until it!!.data!!.size) {
                            it!!.data!![i]?.serial_number?.let { it1 ->
                                listOfSerialNumberBattry.add(
                                    i + 1,
                                    it1
                                )

                            }
                        }
                    }


                    if (srBattryiD.length > 4 && !srBattryiD.equals("Battery Serial Number(क्रमिक संख्या)")) {
                        binding.modelNumberBattery.setText(it.data!![0]!!.model_number)
                        var datt = it.data!![0]!!.manufacturingYear?.split("T")?.toTypedArray()
                        binding.manufacturingDateBattery.setText(datt?.get(0))
                        binding.voltageBattery.setText(it.data!![0]!!.voltage)
                        binding.currentBattery.setText(it.data!![0]!!.current)
                        binding.powerBattery.setText(it.data!![0]!!.power)
                        binding.typeBattery.setText(it.data!![0]!!.type)
                        binding.cellVoltageBattery.setText(it!!.data!![0]!!.cellVoltage)
                    }


                }
            }
    }

    fun getBattryserialNumber() {

        serialNumberAdapterBattry = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            R.id.dist,
            listOfSerialNumberBattry
        )
        binding.serialNumberBattery.adapter = serialNumberAdapterBattry
        binding.serialNumberBattery!!.onItemSelectedListener = this
        if (listOfSerialNumberBattry.size > 1) {
            binding.serialNumberBatteryLayout.visibility = View.VISIBLE
        }
    }

    private fun getSubVendor(toString: String) {
        subVendorViewModel.getSubVendor(toString).observe(this) {
            if (it != null && it.statusCode == 200) {
                val idb = SubVendorDataModel.Data.Data.Subvendor(id = "0", name = "Select")
                subVendorlist.add(idb)
                if (subVendorlist.size !== 0) {
                    subVendorlist.clear()
                }
                if (it.data!!.data!!.subvendor!!.isNotEmpty()) {
                    for (i in it.data!!.data!!.subvendor!!) {
                        subVendorlist.add(i!!)
                    }
                    subVendorAdapter = ArrayAdapter<SubVendorDataModel.Data.Data.Subvendor>(
                        this,
                        R.layout.spinner_item,
                        R.id.dist,
                        subVendorlist
                    )
                    binding.subVendor.adapter = subVendorAdapter
                    binding.subVendor.onItemSelectedListener = this
                } else {
//                    Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show()
                }
            } else {
//                Toast.makeText(this,"failed3",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getModulesLuminary() {
        val ib = ModuleDataModule.Data(
            createdAt = null, id = "0", name = "Select", type = null, updatedAt = null, v = null


        )
        moduleViewModel.getModule("luminaryModules").observe(this) {
            if (it != null && it.statusCode == 200) {
                for (i in it.data!!) {
                    moduleList.add(i!!)
                }
                moduleList.sortBy { it.name.toString() }
                moduleList.add(0, ib)

                moduleAdapter = ArrayAdapter<ModuleDataModule.Data>(
                    this,
                    R.layout.spinner_item,
                    R.id.dist,
                    moduleList
                )
                binding.manufacturingNameLuminaire.adapter = moduleAdapter
                binding.manufacturingNameLuminaire!!.onItemSelectedListener = this


            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                binding.manufacturingNameLuminaire.visibility = View.GONE
            }
        }
    }

    private fun getModulesSolar() {
        val ib = ModuleDataModule.Data(
            createdAt = null, id = "0", name = "Select", type = null, updatedAt = null, v = null
        )
        moduleViewModel.getModule("batteryModules").observe(this) {
            if (it != null && it.statusCode == 200) {
                for (i in it.data!!) {
                    solarList.add(i!!)
                }
                solarList.sortBy { it.name.toString() }
                solarList.add(0, ib)

                solarModuleAdapter = ArrayAdapter<ModuleDataModule.Data>(
                    this,
                    R.layout.spinner_item,
                    R.id.dist,
                    solarList
                )
                binding.manufacturingNameBattery.adapter = solarModuleAdapter
                binding.manufacturingNameBattery!!.onItemSelectedListener = this


            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                binding.manufacturingNameBattery.visibility = View.GONE
            }
        }
    }

    private fun createPoleAndAsset(img: String) {
        progressDialog!!.show()
        var dataSend = CreatePoleResponseModel(
            poleId = CreatePoleResponseModel.PoleId(
                block = blockid,
                createdBy = sessionManagementActivity.retriveData(ctx, Constants.USER_ID),
                district = districtid,
                IMEINumber = imeinum,
                installationInfo = CreatePoleResponseModel.PoleId.InstallationInfo(
                    cableProper = null,
                    fixing = null,
                    grouting = true,
                    language = true,
                    operationMaintenance = true,
                    remarks = "",
                    signBoard = true,
                    length = binding.length.text.toString(),
                    size = binding.size.text.toString(),
                    systemInstalled = true,
                    uidNumber = true

                ),
                latitude = latitude,
                longitude = longitude,
                panchayat = panchayatid,
                poleId = wardPole,
                poleImage = img,
                state = Constants.STATE_ID,
                systemDetails = CreatePoleResponseModel.PoleId.SystemDetails(
                    agencyAddress = null,
                    agencyName = null,
                    orderNumber = null,
                    orderDate = "9/21/2022",
                    details = binding.details.text.toString(),
                    agreementNumber = null,
                    agreementDate = null,
                    systemCapacity = null,
                    beneficiaryName = binding.beneficiaryname.text.toString(),
                    beneficiaryAddress = binding.beneficiaryAddress.text.toString(),
                    installationLocation = binding.installationloaction.text.toString(),
                    installationDate = binding.installationdate.text.toString(),
                    warranteeExpire = ""

                ),
                vendor = vendorid,
                ward = wardid

            ),
            asset =
            CreatePoleResponseModel.Asset(
                batteryDetails = CreatePoleResponseModel.Asset.BatteryDetails(
                    serial_number = srBattryiD,
                    model_number = binding.modelNumberBattery.text.toString(),
                    manufacturingname = battryname,
                    manufacturingDate = binding.manufacturingDateBattery.text.toString(),
                    voltage = binding.voltageBattery.text.toString(),
                    current = binding.currentBattery.text.toString(),
                    power = binding.powerBattery.text.toString(),
                    type = "LifePO4",
                    details = null,
                    cellVoltage = binding.cellVoltageBattery.text.toString(),
                    manufacturingYear = null,

                    ),
                IMEINumber = imeinum,
                installationDetails = CreatePoleResponseModel.Asset.InstallationDetails(
                    installationDate = binding.installationdateAsset.text.toString(),
                    installationPersion = binding.installperson.text.toString()

                ),
                joinigDate = null,
                luminaryDetails = CreatePoleResponseModel.Asset.LuminaiDetails(
                    manufacturingname = luminaryname,
                    serial_number = srLuminaryId,
                    casing = binding.casingLuminaire.text.toString(),
                    model_number = binding.modelNumberLuminaire.text.toString(),
                    height = binding.heightLuminaire.text.toString(),
                    wattage = binding.wattageLuminaire.text.toString(),
                    numberOfLed = binding.numberOfLeds.text.toString(),
                    lux_measured = null

                ),

                manufacturingDate = null,
                pvmoduleDetails = CreatePoleResponseModel.Asset.PvmoduleDetails(
                    manufacturingDate = binding.manufacturingdatePvModules.text.toString(),
                    manufacturingname = pvname,
                    serial_number = srPvId,
                    shadow_free = true,
                    model_number = binding.modelNumber.text.toString(),
                    SPVType = binding.spvType.selectedItem.toString(),
                    solarcell = binding.solarCell.text.toString(),
                    titl_angle = true,
                    wattage = binding.wattage.text.toString(),
                    wind_loading = "null"


                ),
                replacementDate = "8/20/2022",
                replacementReasion = " ",
                ssl = null

            )

        )

        poleDataviewModel.createPoleId(dataSend).observe(this) {
            Log.d("TAG", "createPoleAndAsset: " + it.toString())
            if (it.error!!.startsWith("This LightId already exist")) {
                progressDialog!!.hide()
                Toast.makeText(this, "Assset Created Successfully", Toast.LENGTH_SHORT).show()
                val intentfinish = Intent(this, ChooserActivity::class.java)
                startActivity(intentfinish)
                finish()
                finishAffinity()
            } else {
                progressDialog!!.hide()
                Toast.makeText(ctx, "${it.error.toString()}", Toast.LENGTH_SHORT).show()
            }
            if (it.data != null) {
                progressDialog!!.hide()
                Toast.makeText(this, "Assset Created Successfully", Toast.LENGTH_SHORT).show()
                val intentfinish = Intent(this, ChooserActivity::class.java)
                startActivity(intentfinish)
                Log.d("TAG", "createPoleAndAsset: ${dataSend.toString()}")
                finish()
                finishAffinity()

            } else {
                progressDialog!!.hide()
                // Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

            }
        }

    }

    companion object {


    }

    private fun getModulesPv() {
        val ib = ModuleDataModule.Data(
            createdAt = null, id = "0", name = "Select", type = null, updatedAt = null, v = null


        )
        moduleViewModel.getModule("pvModules").observe(this) {
            if (it != null && it.statusCode == 200) {

                for (i in it.data!!) {
                    battryList.add(i!!)
                }
                battryList.sortBy { it.name.toString() }
                battryList.add(0, ib)
                solarModuleAdapterpv = ArrayAdapter<ModuleDataModule.Data>(
                    this,
                    R.layout.spinner_item,
                    R.id.dist,
                    battryList
                )
                binding.manufacturingName.adapter = solarModuleAdapterpv
                binding.manufacturingName!!.onItemSelectedListener = this


            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                binding.manufacturingName.visibility = View.GONE
            }
        }
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    private fun getWardPoles(id: String) {
        //630110cf45e9d69f6bc221d2
        wardPoleViewModel.getWardPole(id).observe(this) {
            if (it != null) {
                for (i in 0 until it!!.data!![0]!!.poleId!!.size - 1) {
                    val poles = it!!.data!![0]!!.poleId!![i]!!.poleId?.split("/")?.toTypedArray()
                    listOfVehicleNames.add((poles?.get(4) ?: 0) as String)

                }
                Log.d("TAG", "getWardPoles: " + listOfVehicleNames)

                val finl = wardIds.minus(listOfVehicleNames).sorted()
                getWardPoleIds()


            }
        }
    }

    private fun getBlocks(id: String) {
        blockViewModel.getBlock(id).observe(this) {
            if (it != null && it.statusCode == 200) {
                if (blocklist.size !== 0) {
                    blocklist.clear()
                }
                binding.spin1.visibility = View.VISIBLE
                val idb = BlockDataModel.Data.Blocks(id = "0", "Name of Block(ब्लाक)")
                if (it.data!!.size > 1) {
                    for (i in it.data!!) {
                        blocklist.add(i!!.blocks!!)
                    }
                    blocklist.sortBy { it.name.toString() }
                    blocklist.add(0, idb)
                    blockAdapter = ArrayAdapter<BlockDataModel.Data.Blocks>(
                        this,
                        R.layout.spinner_item,
                        R.id.dist,
                        blocklist
                    )
                    binding.spin1.adapter = blockAdapter
                    binding.spin1.onItemSelectedListener = this
                } else {
                    Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show()
                }


            } else {
//                Toast.makeText(this,"failed4",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPanchayat(id: String) {
        panchayatViewModel.getPanchayat(id).observe(this) {
            if (it != null && it.statusCode == 200) {
                if (panchayatlist.size != 0) {
                    panchayatlist.clear()
                }
                binding.spin2.visibility = View.VISIBLE
                val ibb = PanchayatDataModel.Data.Panchayats(id = "0", name = "Name of Panchayat(पंचायत)")

                for (i in it.data!!) {
                    panchayatlist.add(i!!.panchayats!!)
                }
                panchayatlist.sortBy { it.name.toString() }
                panchayatlist.add(0, ibb)
                panchayatAdapter = ArrayAdapter<PanchayatDataModel.Data.Panchayats>(
                    this,
                    R.layout.spinner_item,
                    R.id.dist,
                    panchayatlist
                )
                binding.spin2.adapter = panchayatAdapter
                binding.spin2.onItemSelectedListener = this


            } else {
//                Toast.makeText(this,"failed5",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWards(id: String) {
        wardViewModel.getWards(id).observe(this) {
            if (it != null && it.statusCode == 200) {
                if (wardlist.size != 0) {
                    wardlist.clear()
                }
                binding.spin3.visibility = View.VISIBLE
                try {
                    val ib2 = WardModel.Data.Wards(
                        id = "0",
                        name = Integer.parseInt("Name Of Ward Number(वार्ड नं)")
                    )
                    // wardlist.add(ib2)
                } catch (n: NumberFormatException) {
                    n.printStackTrace()
                }

                for (i in it.data!!) {
                    wardlist.add(i!!.wards!!)
                }
                val sortedList = wardlist.sortedWith(compareBy { it.name })
                wardAdapter = ArrayAdapter<WardModel.Data.Wards>(
                    this,
                    R.layout.spinner_item,
                    R.id.dist,
                    sortedList
                )
                binding.spin3.adapter = wardAdapter
                binding.spin3.onItemSelectedListener = this


            } else {
//                Toast.makeText(this,"failed6",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveBitmapToFile(file: File): File? {
        return try {

            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 65

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: Exception) {
            null
        }
    }

    fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoURI = getOutputMediaFile()?.let {
            FileProvider.getUriForFile(
                ctx,
                ctx.getApplicationContext().getPackageName() + ".provider", it
            )
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, 200)
        binding.mainImageContainer.visibility = View.VISIBLE
    }

    private fun showImage(img: String) {
        imgFile =
            File("/storage/emulated/0/Android/data/com.android.streetlight/files/Pictures/CameraDemo/$img")

        if (imgFile!!.exists()) {
            bitmap = BitmapFactory.decodeFile(imgFile!!.absolutePath)

            val ei = ExifInterface(imgFile!!.absolutePath)
            val orientation: Int = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            var rotatedBitmap: Bitmap? = null
            rotatedBitmap =
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
                    ExifInterface.ORIENTATION_NORMAL -> bitmap
                    else -> bitmap
                }

            binding.img.setImageBitmap(rotatedBitmap)
            val dominant = getDominantColor(rotatedBitmap)

            if (dominant >= (150)) {
                binding.details.setTextColor(Color.BLACK)
            } else {
                binding.details.setTextColor(Color.WHITE)
            }


            bitmap = loadBitmapFromView(binding.mainImageContainer)
            bitmap?.let { storeImage(it) }
        }
    }

    private fun storeImage(image: Bitmap) {
        imgFile = getOutputMediaFilee()

        if (imgFile == null) {
            Log.d(
                "TAG",
                "Error creating media file, check storage permissions: "
            ) // e.getMessage());
            return
        }
        try {
            val fos = FileOutputStream(imgFile)
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.d("TAG", "File not found: " + e.message)
        } catch (e: IOException) {
            Log.d("TAG", "Error accessing file: " + e.message)
        }
    }

    private fun getOutputMediaFilee(): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        val mediaStorageDir = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/Android/data/"
                    + applicationContext.packageName
                    + "/Files"
        )

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        // Create a media file name
        val timeStamp = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
        val mediaFile: File
        val mImageName = "MI_$timeStamp.jpg"
        mediaFile = File(mediaStorageDir.path + File.separator + mImageName)
        return mediaFile
    }

    private fun loadBitmapFromView(v: View): Bitmap {
        val b = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(c)
        return b
    }

//    fun saveImage() {
//        val myDir = File("/sdcard/saved_images")
//        myDir.mkdirs()
//        val generator = Random()
//        var n = 10000
//        n = generator.nextInt(n)
//        val fname = "Image-$n.jpg"
//        val file = File(myDir, fname)
//        if (file.exists()) file.delete()
//        try {
//            val out = FileOutputStream(file)
//
//            // NEWLY ADDED CODE STARTS HERE [
//            val canvas = Canvas(originalBitmap)
//            val paint = Paint()
//            paint.setColor(Color.WHITE) // Text Color
//            paint.setTextSize(12F) // Text Size
//            paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)) // Text Overlapping Pattern
//            // some more settings...
//            canvas.drawBitmap(originalBitmap, 0, 0, paint)
//            canvas.drawText("Testing...", 10f, 10f, paint)
//            // NEWLY ADDED CODE ENDS HERE ]
//            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
//            out.flush()
//            out.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    private fun getOutputMediaFile(): File? {
        val mediaStorageDir = File(
            getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            ), "CameraDemo"
        ).absoluteFile
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return File(
            mediaStorageDir.path + File.separator +
                    "IMG_" + timeStamp + ".jpg"
        )
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return menu.let { super.onCreateOptionsMenu(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
//                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(intent, 100)
                sessionManagementActivity.logoutUser()

            }
        }
        return super.onOptionsItemSelected(item)
    }*/

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val degree = p0?.values?.let { Math.round(it.get(0)).toFloat() }
        // create a rotation animation (reverse turn degree degrees)
        // create a rotation animation (reverse turn degree degrees)
        val ra = RotateAnimation(
            currentDegree,
            -degree!!,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

        // how long the animation will take place
        // how long the animation will take place
        ra.duration = 210
        // set the animation after the end of the reservation status
        // set the animation after the end of the reservation status
        ra.fillAfter = true
        // Start the animation
        // Start the animation
        binding.compassImage.startAnimation(ra)
        currentDegree = -degree!!
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }

//    private fun initView() {
//        binding.submitButton.setOnClickListener{
//            poleDataviewModel.createPoleId()
//        }
//    }

}