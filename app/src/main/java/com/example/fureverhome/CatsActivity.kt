package com.example.fureverhome

import PetAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fureverhome.databinding.ActivityCatBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CatsActivity : AppCompatActivity() {
    // Layout binding
    private lateinit var binding: ActivityCatBinding
    private lateinit var catsAdapter: PetAdapter
    // Store updated data
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adding SharedPreferences to store data
        sharedPreferences = getSharedPreferences("CatsPrefs", Context.MODE_PRIVATE)

        // implementing RecyclerView and its adapter for storing the data in xml
        RecyclerView()

        // Click listener for back button
        binding.buttonBack.setOnClickListener {
            finish()
        }

        // Click listener for floating action button to add a new pet
        binding.fabCats.setOnClickListener {
            // to start AddPetActivity
            val intent = Intent(this, AddPetActivity::class.java)
            startActivityForResult(intent, ADD_PET_REQUEST_CODE) // OPENS ADD_PET AND ADDS
        }
    }

    // Function to setup RecyclerView and its adapter
    private fun RecyclerView() {
        // getting list of pets from SharedPreferences
        val petsJson = sharedPreferences.getString("pets", null)
        val pets = if (petsJson != null) {
            gson.fromJson<List<Pet>>(petsJson, object : TypeToken<List<Pet>>() {}.type)
        } else {
            Cats() // If no data found in SharedPreferences, the default list will still show
        }

        // using the adapter with the retrieved list of pets
        catsAdapter = PetAdapter(this, pets.toMutableList(), object : PetAdapter.OnItemClickListener {
            // when the more button is clicked
            override fun onMoreButtonClick(pet: Pet) {
                PetDetailsDialog(pet) //function with data executed
            }
        })

        // Set up RecyclerView with the adapter and layout manager
        binding.recyclerViewCats.apply {
            adapter = catsAdapter
            layoutManager = LinearLayoutManager(this@CatsActivity)
        }
    }

    // Function to display pet details dialog use on morebuttonclick
    private fun PetDetailsDialog(pet: Pet) {
        // From pop_up xml
        val dialogView = LayoutInflater.from(this).inflate(R.layout.popup_pet, null)

        // Populating dialog layout with pet details
        val imageViewPet: ImageView = dialogView.findViewById(R.id.PopUpPetImage)
        val textViewPetName: TextView = dialogView.findViewById(R.id.PopUpPetName)
        val textViewPetBreed: TextView = dialogView.findViewById(R.id.PopUpPetBreed)
        val textViewPetAge: TextView = dialogView.findViewById(R.id.PopUpPetAge)
        val textViewPetDetails: TextView = dialogView.findViewById(R.id.PopUpPetDetails)
        val textViewPetLocation: TextView = dialogView.findViewById(R.id.PopUpPetLocation)
        val textViewExtraDetails: TextView = dialogView.findViewById(R.id.PopUpExtraDetails)
        val buttonBookAppointment: Button = dialogView.findViewById(R.id.buttonBookAppointment)

        // Displaying the pet's image using Glide/ allows use of url
        Glide.with(this)
            .load(pet.imageUrl)
            .into(imageViewPet)

        // Setting text for various pet details for AlertDialog
        textViewPetName.text = pet.name
        textViewPetBreed.text = "Breed: ${pet.breed}"
        textViewPetAge.text = "Age: ${pet.age} years"
        textViewPetDetails.text = pet.details
        textViewPetLocation.text = pet.location
        textViewExtraDetails.text = pet.extraDetails

        // Creating and showing AlertDialog
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()

        // Click listener for the Book Appointment button
        buttonBookAppointment.setOnClickListener {
            dialog.dismiss()
            bookAppointment(pet)
        }
    }

    // Function to handle booking appointment for a pet
    private fun bookAppointment(pet: Pet) {
        // to Start the BookAppointmentActivity
        val intent = Intent(this, BookAppointmentActivity::class.java)
        startActivity(intent)
    }


    // STORING DETAILS FOR "Pet"
    private fun Cats(): List<Pet> {
        return listOf(
            Pet("Whiskers", 2, "Dubai, UAE", "Munchkin", "https://i.pinimg.com/564x/26/c7/35/26c7355fe46f62d84579857c6f8c4ea5.jpg", "Friendly and curious", "Whiskers loves to nap in the sun and enjoys chasing after toy mice."),
            Pet("Fluffy", 4, "New York, USA", "Tabby", "https://i.pinimg.com/564x/07/6e/c6/076ec65dfb0be5fa20c86fbd9c7ff493.jpg", "Loves cuddles and treats", "Fluffy has a gentle demeanor and loves to be brushed every evening."),
            Pet("Mittens", 1, "London, UK", "Ragdoll", "https://i.pinimg.com/736x/f9/a5/3d/f9a53d38ed1f9db9e2bbed472b36e682.jpg", "Enjoys exploring new places", "Mittens is very curious and enjoys exploring new places in the house."),
            Pet("Felix", 3, "Tokyo, Japan", "Persian", "https://i.pinimg.com/564x/b3/64/3b/b3643be44195838e1a77d0592834a5a2.jpg", "Playful and energetic", "Felix is very playful and gets along well with other pets."),
            Pet("Smokey", 5, "Sydney, Australia", "Moggies", "https://i.pinimg.com/564x/2f/11/45/2f1145c4daea02c225fa867ccc4562a7.jpg", "Affectionate and loyal", "Smokey is very affectionate and loves to sit on your lap while you read."),
            Pet("Simba", 2, "Paris, France", "Persian", "https://i.pinimg.com/564x/92/4a/ce/924ace2af4fc5b3ccef5f3983930a085.jpg", "Friendly and curious", "Simba is a very friendly cat and loves meeting new people."),
            Pet("Luna", 3, "Los Angeles, USA", "Persian", "https://i.pinimg.com/736x/37/16/2a/37162a664a76b93601930414940b371e.jpg", "Loves cuddles and treats", "Luna loves to play with laser pointers and is very energetic."),
            Pet("Tigger", 4, "Berlin, Germany", "Moggies", "https://i.pinimg.com/564x/d0/c6/2f/d0c62f831c9624af438615566ebec3fb.jpg", "Enjoys exploring new places", "Tigger enjoys hiding in boxes and is very playful."),
            Pet("Chloe", 1, "Toronto, Canada", "British Short Hair", "https://i.pinimg.com/736x/8f/05/ec/8f05ec4bf6a3a09836a9cb401578322a.jpg", "Playful and energetic", "Chloe is very independent but loves attention when she's in the mood."),
            Pet("Oreo", 2, "Moscow, Russia", "Siberian", "https://i.pinimg.com/564x/86/5c/b8/865cb80e243d0c6e741e9ed3438ba35c.jpg", "Affectionate and loyal", "Oreo is very loyal and enjoys spending time with his human family.")
        )
    }

    //  To get the newly added pet from the intent data.
    //  Then it adds the new pet to the adapter/updates the list of pets in SharedPreferences
    //  and saves the updated list
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the result is from adding a new pet
        if (requestCode == ADD_PET_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            // Retrieve the new pet from the result intent
            val newPet = data?.getParcelableExtra<Pet>("newPet")

            // If the new pet is not null
            newPet?.let {
                // Adds the new pet to the adapter
                catsAdapter.addPet(it)

                // to save updated list of pets to SharedPreferences
                val pets = catsAdapter.getPets()
                val petsJson = gson.toJson(pets)
                sharedPreferences.edit().putString("pets", petsJson).apply()
            }
        }
    }


    companion object {
        // Defining a constant request code for adding a new pet
        private const val ADD_PET_REQUEST_CODE = 1
    }
}