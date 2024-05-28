// FOR MOCK UP BOOKING

package com.example.fureverhome

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fureverhome.databinding.ActivityBookAppointmentBinding

class BookAppointmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CLICK LISTENER FOR BUTTONS
        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonSubmit.setOnClickListener {
            ConfirmDialog() // execute function
        }
    }

    //FOR ALERTDIALOG
    private fun ConfirmDialog() {
        // store info of booking
        val name = binding.editTextName.text.toString().trim()
        val phone = binding.editTextPhone.text.toString().trim()
        val date = binding.editTextDate.text.toString().trim()
        val time = binding.editTextTime.text.toString().trim()

        // implementing info and making messsage with it
        val message = "Appointment Details:\nName: $name\nPhone: $phone\nDate: $date\nTime: $time"

        //MAKING DIALOG WITH ADDING CONTENTS
        AlertDialog.Builder(this)
            .setTitle("Appointment Booked")
            .setMessage(message) // using "message" variable
            .setPositiveButton("CLOSE") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

}
