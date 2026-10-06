package com.example.week5

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var chkAgree: CheckBox
    private lateinit var rgroup1: RadioGroup
    private lateinit var rdoDog: RadioButton
    private lateinit var rdoCat: RadioButton
    private lateinit var rdoRabbit: RadioButton
    private lateinit var btnOK: Button
    private lateinit var imgPet: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "애완동물 사진 보기"

        text1 = findViewById(R.id.Text1)
        chkAgree = findViewById(R.id.ChkAgree)

        text2 = findViewById(R.id.Text2)
        rgroup1 = findViewById(R.id.Rgroup1)
        rdoDog = findViewById(R.id.RdoDog)
        rdoCat = findViewById(R.id.RdoCat)
        rdoRabbit = findViewById(R.id.RdoRabbit)

        btnOK = findViewById(R.id.BtnOK)
        imgPet = findViewById(R.id.ImgPet)

        chkAgree.setOnCheckedChangeListener { compoundButton, b ->
            if (chkAgree.isChecked == true) {
                text2.visibility = android.view.View.VISIBLE
                rgroup1.visibility = android.view.View.VISIBLE
                btnOK.visibility = android.view.View.VISIBLE
                imgPet.visibility = android.view.View.VISIBLE
            } else {
                text2.visibility = android.view.View.INVISIBLE
                rgroup1.visibility = android.view.View.INVISIBLE
                btnOK.visibility = android.view.View.INVISIBLE
                imgPet.visibility = android.view.View.INVISIBLE
            }
        }

        btnOK.setOnClickListener {
            when (rgroup1.checkedRadioButtonId) {
                R.id.RdoDog -> imgPet.setImageResource(R.drawable.dog)
                R.id.RdoCat -> imgPet.setImageResource(R.drawable.cat)
                R.id.RdoRabbit -> imgPet.setImageResource(R.drawable.rabbit)
                else -> Toast.makeText(applicationContext, "동물을 먼저 선택하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
