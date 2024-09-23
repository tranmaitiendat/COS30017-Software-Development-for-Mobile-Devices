package com.example.assignment2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class DetailActivity : AppCompatActivity() {

    private lateinit var instrument: Instrument

    private lateinit var detailImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var attributeChipGroup: ChipGroup
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var agreeSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Assignment2)
        setContentView(R.layout.activity_detail)

        detailImageView = findViewById(R.id.detailImageView)
        nameTextView = findViewById(R.id.nameTextView)
        priceTextView = findViewById(R.id.priceTextView)
        ratingBar = findViewById(R.id.ratingBarDetail)
        attributeChipGroup = findViewById(R.id.attributeChipGroup)
        saveButton = findViewById(R.id.saveButton)
        cancelButton = findViewById(R.id.cancelButton)
        agreeSwitch = findViewById(R.id.agreeSwitch)

        // Get instrument from Intent
        instrument = intent.getParcelableExtra("instrument") ?: return

        //Display instrument information on the interface
        displayInstrumentDetails()

        // "Save" click event
        saveButton.setOnClickListener {
            if (!agreeSwitch.isChecked) {
                Toast.makeText(this@DetailActivity, "You must agree to the terms.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Check if at least one attribute is selected
            val selectedAttributes = arrayListOf<String>()
            for (i in 0 until attributeChipGroup.childCount) {
                val chip = attributeChipGroup.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedAttributes.add(chip.text.toString())
                }
            }
            if (selectedAttributes.isEmpty()) {
                Toast.makeText(this@DetailActivity, "Select at least one attribute.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update instrument information
            instrument.rating = ratingBar.rating
            instrument.attributes = selectedAttributes
            instrument.isBooked = true

            // Return result to MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("updatedInstrument", instrument)
            setResult(Activity.RESULT_OK, resultIntent)

            //Notification of success and end of activity
            Toast.makeText(this@DetailActivity, "Rental Successful!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // "Cancel" click event
        cancelButton.setOnClickListener {
            Toast.makeText(this@DetailActivity, "Rental Cancelled.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun displayInstrumentDetails() {
        // Display current instrument details on the interface
        detailImageView.setImageResource(instrument.imageResourceId)
        nameTextView.text = instrument.name // Đã thay đổi
        priceTextView.text = "Price: ${instrument.rentalPrice} credits"
        ratingBar.rating = instrument.rating

        // Add properties to ChipGroup
        val attributeOptions = listOf("Acoustic", "Electric", "Classical", "Available")
        for (attribute in attributeOptions) {
            val chip = Chip(this)
            chip.text = attribute
            chip.isCheckable = true
            if (instrument.attributes.contains(attribute)) {
                chip.isChecked = true
            }
            attributeChipGroup.addView(chip)
        }
    }
}
