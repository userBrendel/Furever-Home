// MAINACTIVITY TO CHOOSE BETWEEN CATS/ DOG AND MAKE SOUNDS ON CLICK

package com.example.fureverhome


import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.fureverhome.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // layout
    private lateinit var mediaPlayer: MediaPlayer // sounds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // INTRO POPUP
        showCustomPopup()

        // ON CLICK LISTENER WITH THE FUNCTION AND STARTING DIFFERENT ACTIVITY
        binding.dogsLayout.setOnClickListener {
            playSound(R.raw.dog_sound, DogsActivity::class.java)
        }

        binding.catsLayout.setOnClickListener {
            playSound(R.raw.cat_sound, CatsActivity::class.java)
        }
    }



    // FUNCTION TO MAKING MY CUSTOM POPUP
    private fun showCustomPopup() {
        val inflater = LayoutInflater.from(this)
        val popupView = inflater.inflate(R.layout.popup, null) // layout

        //VARIABLE FOR MY ANIMATION
        val scaleUpAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        //APPLYING THE ANIMATION
        val animationSet = AnimationSet(true).apply {
            addAnimation(scaleUpAnimation)
            addAnimation(fadeInAnimation)
        }

        popupView.startAnimation(animationSet) // START ANIMATION

        val dialog = AlertDialog.Builder(this)
            .setView(popupView)
            .create()

        //DISMISS WITH BUTTON
        popupView.findViewById<Button>(R.id.buttonDismiss).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    //IMPLEMENTING SOUND CUSTOMIZATION AND FUNCTION
    private fun playSound(soundResId: Int, activityClass: Class<*>) {
        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
            startActivity(Intent(this, activityClass))
        }
        mediaPlayer.start()
    }
}
