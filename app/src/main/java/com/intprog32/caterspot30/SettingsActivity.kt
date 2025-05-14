package com.intprog32.caterspot30

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import com.intprog32.caterspot30.Intefaces.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsActivity : Activity() {
    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Buttons
        val back = findViewById<ImageView>(R.id.back_button)
        val home = findViewById<ImageView>(R.id.home_nav)
        val profile = findViewById<Button>(R.id.profile_button)
        val logout = findViewById<ImageView>(R.id.logout_button)
        val delAccount = findViewById<Button>(R.id.deleteAccount_button)

        back.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        home.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        profile.setOnClickListener {
            startActivity(Intent(this, NewProfileActivity::class.java))
        }

        // Switches
        val switchNotifications = findViewById<Switch>(R.id.switch_notifications)
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Notifications Enabled" else "Notifications Disabled"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        val switchNewsletter = findViewById<Switch>(R.id.switch_newsletter)
        switchNewsletter.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "Newsletter Subscribed" else "Newsletter Unsubscribed"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Delete account button
        delAccount.setOnClickListener {
            // Create a confirmation dialog
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to delete your account?")
                .setCancelable(false) // Prevent the dialog from being dismissed if clicked outside
                .setPositiveButton("Yes") { dialog, id ->
                    // If the user confirms, proceed with account deletion
                    val sharedPref = getSharedPreferences("CaterspotPrefs", MODE_PRIVATE)
                    val userId = sharedPref.getString("userId", null)

                    Log.d("DeleteAccount", "Retrieved userId: $userId")

                    if (userId != null) {
                        deleteAccount(userId) // Proceed to delete account
                    } else {
                        Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("No") { dialog, id ->
                    // If the user cancels, just dismiss the dialog
                    dialog.dismiss()
                }

            // Show the dialog
            val alert = builder.create()
            alert.show()
        }

        // Logout button
        logout.setOnClickListener {
            // Create a confirmation dialog
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to log out?")
                .setCancelable(false) // Prevent the dialog from being dismissed if clicked outside
                .setPositiveButton("Confirm") { dialog, id ->
                    // If the user confirms, clear preferences and redirect to LoginActivity
                    getSharedPreferences("CaterspotPrefs", MODE_PRIVATE).edit().clear().apply()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    // If the user cancels, just dismiss the dialog
                    dialog.dismiss()
                }

            // Show the dialog
            val alert = builder.create()
            alert.show()
        }

    }

    private fun deleteAccount(userId: String) {
        val call = RetrofitInstance.api.deleteUser(userId)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("DeleteAccount", "Response code: ${response.code()}")
                if (response.isSuccessful) {
                    Toast.makeText(this@SettingsActivity, "Account deleted successfully", Toast.LENGTH_LONG).show()
                    getSharedPreferences("CaterspotPrefs", MODE_PRIVATE).edit().clear().apply()
                    startActivity(Intent(this@SettingsActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Log.e("DeleteAccount", "Error: ${response.errorBody()?.string()}")
                    Toast.makeText(this@SettingsActivity, "Failed to delete account", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("DeleteAccount", "Network failure: ", t)
                Toast.makeText(this@SettingsActivity, "Network error: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })

    }
}
