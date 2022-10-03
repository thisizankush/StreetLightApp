package com.example.streetlightapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.streetlightapp.BaseApplication


class SessionManagementActivity(  // Context
    var _context: Context
) {
    var pref: SharedPreferences

    // Editor for Shared preferences
    var editor: SharedPreferences.Editor

    // Shared pref mode
    var PRIVATE_MODE = 0

    /**
     * Create login session
     */
    fun createLoginSession(auth: String?, name: String?) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true)

        // Storing name in pref
        editor.putString(KEY_AUTH_TOKEN, auth)

        // Storing email in pref
        editor.putString(KEY_NAME, name)

        // commit changes
        editor.commit()
    }
    /**
     * Get stored session data
     */

    val userDetails: HashMap<String, String?>
        get() {
            val user = HashMap<String, String?>()
            // user name
            user[KEY_PHONE] =
                pref.getString(KEY_PHONE, null)
            user[KEY_NAME] =
                pref.getString(KEY_NAME, null)

            // return user
            return user
        }

    fun insertData(context: Context?, key: String?, value: String?) {
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun retriveData(context: Context?, key: String?): String? {
        return pref.getString(key,"")
    }

    /**
     * Clear session details
     */
//    fun logoutUser() {
//        // Clearing all data from Shared Preferences
//        editor.clear()
//        editor.commit()
////        _context.stopService(Intent(_context, LocationService::class.java))
////        // After logout redirect user to Loing Activity
////        val i = Intent(_context, LoginActivity::class.java)
//        // Closing all the Activities
//        // Staring Login Activity
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        _context.startActivity(i)
//    }

    fun gettoken(): String{
        var token =
            BaseApplication.context?.getSharedPreferences("street", Context.MODE_PRIVATE)
                ?.getString(
                    "token",
                    null
                )
        return token.toString()
    }

    /**
     * Quick check for login
     */
    val isLoggedIn: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)


    companion object {

        private const val PREF_NAME = "street"

        // All Shared Preferences Keys
        private const val IS_LOGIN = "IsLoggedIn"

        // User name (make variable public to access from outside)
        const val KEY_NAME = "name"
        // Email address (make variable public to access from outside)
        const val KEY_PHONE = "phone"
        const val KEY_AUTH_TOKEN = "auth_token"


    }


    // Constructor
    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}