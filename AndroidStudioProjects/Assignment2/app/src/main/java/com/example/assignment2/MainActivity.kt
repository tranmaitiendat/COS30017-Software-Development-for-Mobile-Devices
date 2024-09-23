// MainActivity.kt
package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var instruments: MutableList<Instrument>
    private var currentIndex = 0

    private lateinit var instrumentImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var nextButton: Button
    private lateinit var borrowButton: Button

    companion object {
        const val REQUEST_CODE_DETAIL = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Áapply style to application
        setTheme(R.style.Theme_Assignment2)
        setContentView(R.layout.activity_main)

        // Initialize views
        instrumentImageView = findViewById(R.id.instrumentImageView)
        nameTextView = findViewById(R.id.nameTextView)
        priceTextView = findViewById(R.id.priceTextView)
        ratingBar = findViewById(R.id.ratingBar)
        nextButton = findViewById(R.id.nextButton)
        borrowButton = findViewById(R.id.borrowButton)

        // Initialize data
        initializeInstruments()

        // Show first instrument
        displayInstrument(currentIndex)

        // "Next" button click event
        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % instruments.size
            displayInstrument(currentIndex)
        }

        // "Borrow" button click event
        borrowButton.setOnClickListener {
            // Get the current instrument based on the currentIndex in the instruments list
            val selectedInstrument = instruments[currentIndex]
            // Create an Intent to switch from MainActivity to DetailActivity
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            // Use the putExtra method to pass the instrument object to the DetailActivity
            intent.putExtra("instrument", selectedInstrument)
            // REQUEST_CODE_DETAIL is the request code to identify the result returned from DetailActivity
            startActivityForResult(intent, REQUEST_CODE_DETAIL)
        }
    }
    // Method to initialize the list of instruments
    private fun initializeInstruments() {
        // Create a list of instruments and assign it to the variable 'instruments'
        instruments = mutableListOf(
            Instrument(
                name = "Acoustic Guitar", // The name of the instrument is "Acoustic Guitar"
                rating = 4.5f, // Instrument rating is 4.5 (on a scale of 5)
                attributes = arrayListOf("Acoustic", "Available"), // Instrument properties: Acoustic and Available
                rentalPrice = 50, // The rental price of musical instruments is 50
                imageResourceId = R.drawable.acoustic_guitar  // Path to the instrument image in the drawable folder
            ),
            Instrument(
                name = "Electric Guitar",
                rating = 4.8f,
                attributes = arrayListOf("Electric", "Available"),
                rentalPrice = 70,
                imageResourceId = R.drawable.electric_guitar
            ),
            Instrument(
                name = "Violin",
                rating = 4.2f,
                attributes = arrayListOf("Classical", "Available"),
                rentalPrice = 60,
                imageResourceId = R.drawable.violin
            )
        )
    }

    private fun displayInstrument(index: Int) {
        // Get instrument based on index
        val instrument = instruments[index]
        // Display instrument information on the interface
        instrumentImageView.setImageResource(instrument.imageResourceId)
        nameTextView.text = instrument.name
        priceTextView.text = "Price: ${instrument.rentalPrice} credits"
        ratingBar.rating = instrument.rating

        // Disable borrow button if instrument is booked
        if (instrument.isBooked) {
            borrowButton.isEnabled = false
            borrowButton.text = "Booked"
            // If not set, enable the button and change the text
        } else {
            borrowButton.isEnabled = true
            borrowButton.text = "Borrow"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check request code and return result
        if (requestCode == REQUEST_CODE_DETAIL && resultCode == Activity.RESULT_OK) {
            // Get the updated instrument object from the Intent
            val updatedInstrument = data?.getParcelableExtra<Instrument>("updatedInstrument")
            if (updatedInstrument != null) {
                // Update the instrument in the list and redisplay
                instruments[currentIndex] = updatedInstrument
                displayInstrument(currentIndex)
                Toast.makeText(
                    // Display a message that the instrument has been successfully placed
                    this@MainActivity,
                    "${updatedInstrument.name} has been booked.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
