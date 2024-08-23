package com.example.lightmeup

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class MainActivity2 : AppCompatActivity() {

    // Khai báo biến isPaidIconDisplayed để theo dõi trạng thái của icon
    private var isPaidIconDisplayed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val paidIcon = findViewById<ImageView>(R.id.paidIcon)

        // Đặt sự kiện onClickListener để thay đổi hình ảnh của ImageView khi được nhấn
        paidIcon.setOnClickListener {
            if (isPaidIconDisplayed) {
                paidIcon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.savings_24dp_5f6368))
            } else {
                paidIcon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.paid_24dp_5f6368))
            }
            // Đổi trạng thái của biến isPaidIconDisplayed
            isPaidIconDisplayed = !isPaidIconDisplayed
        }
    }
}
