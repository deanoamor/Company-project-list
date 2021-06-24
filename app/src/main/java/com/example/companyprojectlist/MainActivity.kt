package com.example.companyprojectlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ref = FirebaseDatabase.getInstance().getReference("PROJEKLIST")

        tombolSimpan.setOnClickListener {
            simpandata()


        }

        tombolTampil.setOnClickListener {
            val intent = Intent (this, Show::class.java)
            startActivity(intent)
        }
    }

    private fun simpandata() {
        val judul = inputJudul.text.toString()
        val desk = inputDeskripsi.text.toString()
        val tglawal = inputTglmulai.text.toString()
        val dline = inputTglakhir.text.toString()
        val ketua = inputKetua.text.toString()
        val projekId = ref.push().key.toString()

        val proj = Projek(projekId,judul,desk,tglawal,dline,ketua)


        if (judul.equals("")){
            Toast.makeText(this, "Judul tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
        else if (desk.equals("")){
            Toast.makeText(this, "deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
        else if (tglawal.equals("")){
            Toast.makeText(this, "tanggal awal tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
        else if (dline.equals("")){
            Toast.makeText(this, "deadline tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
        else if (ketua.equals("")){
            Toast.makeText(this, "ketua tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
        else{
            ref.child(projekId).setValue(proj).addOnCompleteListener {
                Toast.makeText(this, "Successs", Toast.LENGTH_SHORT).show()
                inputJudul.setText("")
                inputDeskripsi.setText("")
                inputTglmulai.setText("")
                inputTglakhir.setText("")
                inputKetua.setText("")

                val intent = Intent (this, Show::class.java)
                startActivity(intent)
            }
        }


    }
}