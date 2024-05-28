//MY ACTIVITY TO ADD A PET / POST

package com.example.fureverhome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fureverhome.databinding.ActivityAddPetBinding

class AddPetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting click listeners
        binding.buttonAddPet.setOnClickListener {
            // a function that check if inputs are valid or available
            if (validateInputs()) {
                // Getting values from EditText fields converting them to strings and removing trailing spaces
                val name = binding.editTextPetName.text.toString().trim()
                val age = binding.editTextPetAge.text.toString().trim().toIntOrNull() ?: 0
                val location = binding.editTextPetLocation.text.toString().trim()
                val breed = binding.editTextPetBreed.text.toString().trim()
                val imageUrl = binding.editTextPetImageUrl.text.toString().trim()
                val details = binding.editTextPetDetails.text.toString().trim()
                val extraDetails = binding.editTextPetExtraDetails.text.toString().trim()

                // Creating a new Pet object to add. Getting layout for "Pet"
                val newPet = Pet(name, age, location, breed, imageUrl, details, extraDetails)

                // handles the result - intent with the new Pet object
                val resultIntent = Intent() //The newly created Pet object is packaged into an intent (resultIntent)  as an extra with the key "newPet"
                resultIntent.putExtra("newPet", newPet)
                setResult(Activity.RESULT_OK, resultIntent)//The result code is set to Activity.RESULT_OK meaning it is successfull.
                // Finish with posting
                finish()
            } else {
                // Showing a toast message if inputs are not valid
                Toast.makeText(this, "Please fill in everything to post", Toast.LENGTH_SHORT).show()
            }
        }

        // click listener for the back button
        binding.backButtonPost.setOnClickListener {
            // to return to the previous activity
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    // Function to validate input fields if its not blank it should return the post
    private fun validateInputs(): Boolean {
        return binding.editTextPetName.text.isNotBlank() &&
                binding.editTextPetAge.text.isNotBlank() &&
                binding.editTextPetLocation.text.isNotBlank() &&
                binding.editTextPetBreed.text.isNotBlank() &&
                binding.editTextPetImageUrl.text.isNotBlank() &&
                binding.editTextPetDetails.text.isNotBlank() &&
                binding.editTextPetExtraDetails.text.isNotBlank()
    }
}
