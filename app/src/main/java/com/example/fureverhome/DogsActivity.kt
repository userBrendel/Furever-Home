//SAME AS CATACTIVITY / DIFFERENT LIST FOR DOGS

package com.example.fureverhome

import PetAdapter
import android.app.Activity
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
import com.example.fureverhome.databinding.ActivityDogsBinding
import com.google.gson.Gson

class DogsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDogsBinding
    private lateinit var dogsAdapter: PetAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDogsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("DogsPrefs", MODE_PRIVATE)


        RecyclerView()


        binding.buttonBack.setOnClickListener {
            finish()
        }


        binding.fabDogs.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            startActivityForResult(intent, ADD_PET_REQUEST_CODE)
        }
    }

    private fun RecyclerView() {
        dogsAdapter = PetAdapter(this, Dogs().toMutableList(), object : PetAdapter.OnItemClickListener {
            override fun onMoreButtonClick(pet: Pet) {
                PetDetailsDialog(pet)
            }
        })

        binding.recyclerViewDogs.apply {
            adapter = dogsAdapter
            layoutManager = LinearLayoutManager(this@DogsActivity)
        }
    }

    private fun PetDetailsDialog(pet: Pet) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.popup_pet, null)


        val imageViewPet: ImageView = dialogView.findViewById(R.id.PopUpPetImage)
        val textViewPetName: TextView = dialogView.findViewById(R.id.PopUpPetName)
        val textViewPetBreed: TextView = dialogView.findViewById(R.id.PopUpPetBreed)
        val textViewPetAge: TextView = dialogView.findViewById(R.id.PopUpPetAge)
        val textViewPetDetails: TextView = dialogView.findViewById(R.id.PopUpPetDetails)
        val textViewPetLocation: TextView = dialogView.findViewById(R.id.PopUpPetLocation)
        val textViewExtraDetails: TextView = dialogView.findViewById(R.id.PopUpExtraDetails)
        val buttonBookAppointment: Button = dialogView.findViewById(R.id.buttonBookAppointment)

        Glide.with(this)
            .load(pet.imageUrl)
            .into(imageViewPet)

        textViewPetName.text = pet.name
        textViewPetBreed.text = "Breed: ${pet.breed}"
        textViewPetAge.text = "Age: ${pet.age} years"
        textViewPetDetails.text = pet.details
        textViewPetLocation.text = pet.location
        textViewExtraDetails.text = pet.extraDetails


        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()


        buttonBookAppointment.setOnClickListener {
            dialog.dismiss()
            bookAppointment(pet)
        }
    }

    private fun bookAppointment(pet: Pet) {

        val intent = Intent(this, BookAppointmentActivity::class.java)
        intent.putExtra("petName", pet.name)
        startActivity(intent)
    }

    private fun Dogs(): List<Pet> {
        return listOf(
            Pet("Buddy", 3, "San Francisco, USA", "Golden Retriever", "https://i.pinimg.com/564x/c1/88/33/c18833607b218a2d4eb68e2b3ab3db2d.jpg", "Loves running and playing fetch", "Buddy is full of energy and loves to play fetch. He is very friendly and gets along well with other dogs."),
            Pet("Max", 5, "Berlin, Germany", "German Shepherd", "https://i.pinimg.com/564x/d5/af/89/d5af8903145b5b84eea18f59c5b280c8.jpg", "Highly intelligent and protective", "Max is a highly intelligent and protective dog. He is very loyal and loves to be given tasks."),
            Pet("Charlie", 2, "Tokyo, Japan", "Mongrel", "https://i.pinimg.com/564x/8b/b4/28/8bb42878150d9ee413e4715dfb80aec7.jpg", "Independent and playful", "Charlie is independent and playful. He enjoys exploring new places and meeting new people."),
            Pet("Rocky", 4, "New York, USA", "Bulldog", "https://i.pinimg.com/564x/ab/d0/ac/abd0ac1fcea010cf889ae65d8ea95d71.jpg", "Calm and courageous", "Rocky is calm and courageous. He loves to relax at home but also enjoys his daily walks."),
            Pet("Bella", 1, "Sydney, Australia", "Labrador Retriever", "https://i.pinimg.com/564x/44/29/52/4429524c9d2cec68f82d0bc1d33cce03.jpg", "Friendly and energetic", "Bella is friendly and energetic. She loves to play and is great with kids."),
            Pet("Lucy", 6, "London, UK", "Beagle", "https://i.pinimg.com/564x/27/d3/7d/27d37de769c37b4a771a8a4ccd4558d0.jpg", "Curious and merry", "Lucy is curious and merry. She enjoys outdoor activities and has a very cheerful personality."),
            Pet("Daisy", 3, "Paris, France", "Poodle", "https://i.pinimg.com/564x/f4/18/f2/f418f20d23c55a6522a712d46f06267e.jpg", "Intelligent and active", "Daisy is intelligent and active. She enjoys learning new tricks and is very obedient."),
            Pet("Molly", 4, "Los Angeles, USA", "Mongrel", "https://i.pinimg.com/564x/2d/04/40/2d04402c84372d6963cd344d23fe0ed3.jpg", "Loyal and protective", "Molly is loyal and protective. She is very affectionate with her family and loves to be petted."),
            Pet("Bailey", 2, "Toronto, Canada", "Pug", "https://i.pinimg.com/564x/82/2c/f8/822cf8f98cff98b9ac4faec8ee41f1e2.jpg", "Charming and affectionate", "Bailey is charming and affectionate. He loves to be around people and enjoys belly rubs."),
            Pet("Coco", 5, "Dubai, UAE", "Chihuahua", "https://i.pinimg.com/564x/9b/00/76/9b0076a0796d1821655b12d47070cb33.jpg", "Bold and confident", "Coco is bold and confident. She has a big personality for a small dog and loves to be the center of attention.")
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PET_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val newPet = data?.getParcelableExtra<Pet>("newPet")
            newPet?.let {
                dogsAdapter.addPet(it)


                val pets = dogsAdapter.getPets()
                val petsJson = gson.toJson(pets)
                sharedPreferences.edit().putString("pets", petsJson).apply()
            }
        }
    }

    companion object {
        private const val ADD_PET_REQUEST_CODE = 1
    }
}
