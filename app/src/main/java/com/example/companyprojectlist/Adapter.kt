package com.example.companyprojectlist

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Projek> )
    : ArrayAdapter<Projek>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val judul = view.findViewById<TextView>(R.id.textjudul)
        val deskripsi = view.findViewById<TextView>(R.id. textdeskripsi)
        val tglawal = view.findViewById<TextView>(R.id.textawal)
        val deadline = view.findViewById<TextView>(R.id.textdline)
        val ketua = view.findViewById<TextView>(R.id.textketua)

        val btnUpdate = view.findViewById<Button>(R.id.tombolupdate)
        val btnDelete = view.findViewById<Button>(R.id.tomboldelete)

        val projeks = list[position]

        judul.text = projeks.judul
        deskripsi.text = projeks.desk
        tglawal.text = projeks.tglawal
        deadline.text = projeks.dline
        ketua.text = projeks.ketua

        btnUpdate.setOnClickListener {
            Update(projeks)
        }
        btnDelete.setOnClickListener {
            Delete(projeks)
        }


        return view

    }

    private fun Delete(projeks: Projek) {



        val mydatabase = FirebaseDatabase.getInstance().getReference("PROJEKLIST")
        
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle(context.getString(R.string.confirm_delete))
        builder.setMessage(context.getString(R.string.delete_confirmation_message))
        builder.setPositiveButton(context.getString(R.string.yes), DialogInterface.OnClickListener{ dialog, id ->

            mydatabase.child(projeks.id).removeValue()
            Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
            val intent = Intent(context, Show::class.java)
            context.startActivity(intent)



        })
        builder.setNegativeButton(context.getString(R.string.no) , DialogInterface.OnClickListener{ dialog, id ->
            dialog.cancel()
        })

        var alert= builder.create()
        alert.show()


    }

    private fun Update(projeks: Projek) {
        val builder = AlertDialog.Builder(mCtx)

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val judulup = view.findViewById<TextView>(R.id.inputJudul)
        val deskripsiup = view.findViewById<TextView>(R.id. inputDeskripsi)
        val tglawalup = view.findViewById<TextView>(R.id.inputTglmulai)
        val deadlineup = view.findViewById<TextView>(R.id.inputTglakhir)
        val ketuaup = view.findViewById<TextView>(R.id.inputKetua)

        judulup.setText(projeks.judul)
        deskripsiup.setText(projeks.desk)
        tglawalup.setText(projeks.tglawal)
        deadlineup.setText(projeks.dline)
        ketuaup.setText(projeks.ketua)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbProjek = FirebaseDatabase.getInstance().getReference("PROJEKLIST")

            val title = judulup.text.toString().trim()
            val desc = deskripsiup.text.toString().trim()
            val date = tglawalup.text.toString().trim()
            val last = deadlineup.text.toString().trim()
            val lead = ketuaup.text.toString().trim()

//            if (title.equals("")){
//                judulup.error = "please enter title"
//                judulup.requestFocus()
//                return@setPositiveButton
//            }
//
//            else if (desc.equals("")){
//                deskripsiup.error = "please enter desc"
//                deskripsiup.requestFocus()
//                return@setPositiveButton
//            }
//
//            else if (date.equals("")){
//                tglawalup.error = "please enter first date"
//                tglawalup.requestFocus()
//                return@setPositiveButton
//            }
//
//            else if (last.equals("")){
//                deadlineup.error = "please enter deadline"
//                deadlineup.requestFocus()
//                return@setPositiveButton
//            }
//
//            else if (lead.equals("")){
//                ketuaup.error = "please enter leader"
//                ketuaup.requestFocus()
//                return@setPositiveButton
//            }
//            else{
//
//            }

            val projeks = Projek(projeks.id,title,desc,date,last,lead)

            dbProjek.child(projeks.id).setValue(projeks).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()

    }

}