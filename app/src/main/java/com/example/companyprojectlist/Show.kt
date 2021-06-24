package com.example.companyprojectlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show.*

class Show : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Projek>
    lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        tambah.setOnClickListener {
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }

        ref = FirebaseDatabase.getInstance().getReference("PROJEKLIST")
        list = mutableListOf()
        listView = findViewById(R.id.tampil)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val projeklist = h.getValue(Projek::class.java)
                        list.add(projeklist!!)
                    }
                    val adapter = Adapter(this@Show,R.layout.projeks,list)
                    listView.adapter = adapter
                }
            }
        })
    }
}