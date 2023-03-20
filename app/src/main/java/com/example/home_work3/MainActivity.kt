@file:Suppress("DEPRECATION")

package com.example.home_work3

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import com.example.home_work3.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
private val  PDF:Int=0
    private lateinit var uri:Uri
    private val  storage=Firebase.storage
    private val db = Firebase.firestore
    var storageRef = storage.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { view: View? ->
            val intent = Intent()
            intent.type = "pdf/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PDF)
        }
        binding.button2.setOnClickListener {
storageRef.child("pdf/").putFile(uri).addOnSuccessListener {
    val user = hashMapOf(
        "uri" to "$uri",
    )
    db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
        }
        .addOnFailureListener {
        }
    print("yes")
}
    .addOnFailureListener{
        print("false")
    }
        }
        binding.button3.setOnClickListener {
            downloadPDF("https://www.orimi.com/pdf-test.pdf")

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode== RESULT_OK){
        uri= data!!.data!!
    }

        super.onActivityResult(requestCode, resultCode, data)
    }



fun  downloadPDF(url: String){
    val  request=DownloadManager.Request(Uri.parse(url))
    .setTitle("Download")
    .setDescription("the file is downloading")
    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setAllowedOverMetered(true)
val  manager=getSystemService(DOWNLOAD_SERVICE)as DownloadManager
    manager.enqueue(request)
}
}