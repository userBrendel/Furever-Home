//extends RecyclerView.Adapter
// this adapter is for displaying elements
//this adapts "Pet" object to be display in recyclerview / displays list effectively

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fureverhome.Pet
import com.example.fureverhome.R

class PetAdapter(
    private val context: Context,
    private val pets: MutableList<Pet>, // List of Pet objects to be displayed
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    //  for item click events
    interface OnItemClickListener {
        fun onMoreButtonClick(pet: Pet) // to handle click on "More" button
    }

    // Inflates the item layout and returns a ViewHolder object
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false)
        return PetViewHolder(view)
    }

    // Binds data to the views in each ViewHolder
    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = pets[position]
        holder.bind(pet)
    }

    //ADDING PET
    // Returns the total number of items in the dataset
    override fun getItemCount(): Int = pets.size

    // Adds a new pet to the list and notifies the adapter of the change
    fun addPet(pet: Pet) {
        pets.add(pet)
        notifyItemInserted(pets.size - 1)
    }

    // Returns the list of pets
    fun getPets(): List<Pet> {
        return pets.toList()
    }

    // ViewHolder class that holds references to the views in the item layout
    inner class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewPet: ImageView = itemView.findViewById(R.id.PetImage)
        private val textViewPetName: TextView = itemView.findViewById(R.id.PetName)
        private val textViewPetDetails: TextView = itemView.findViewById(R.id.PetDetails)
        private val textViewPetLocation: TextView = itemView.findViewById(R.id.PetLocation)
        private val buttonMore: TextView = itemView.findViewById(R.id.MoreButton)

        // Initializes the ViewHolder and sets a click listener on the "More" button
        init {
            buttonMore.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onMoreButtonClick(pets[position])
                }
            }
        }

        // Binds data to the views
        fun bind(pet: Pet) {
            textViewPetName.text = pet.name
            textViewPetDetails.text = "${pet.breed}, ${pet.age} years old\n${pet.details}"
            textViewPetLocation.text = pet.location

            // Loads the pet image into the ImageView
            Glide.with(context)
                .load(pet.imageUrl)
                .into(imageViewPet)
        }
    }
}
