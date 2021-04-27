package com.example.volleydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.volleydemo.Constants.BASE_URL
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class UpdateActivity : AppCompatActivity() {

    private lateinit var nameEd:TextInputEditText
    private lateinit var emailEd:TextInputEditText
    private lateinit var passwordEd:TextInputEditText
    private lateinit var updateBtn:MaterialButton
    private lateinit var delBtn:MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        initViews()

        delBtn.setOnClickListener {
            val id = intent.getStringExtra("id")
            deleteUser(id)


        }
        updateBtn.setOnClickListener {
            val id = intent.getStringExtra("id")
            val name = nameEd.text.toString()
            val email = emailEd.text.toString()
            val password = passwordEd.text.toString()
            updateUser(id,name,email,password)
        }
    }

    private fun deleteUser(id: String?) {
        val url = BASE_URL + "deleteUser.php"
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = object : StringRequest(Request.Method.POST,url,
        Response.Listener { response ->
            Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))

        },
        Response.ErrorListener { error ->  }){
            override fun getParams():HashMap<String,String>{
                val map = HashMap<String,String>()
                map["id"] =id!!
                return map
            }
        }
        requestQueue.add(stringRequest)

    }

    private fun updateUser(id: String?, name: String, email: String, password: String) {
        val url = BASE_URL + "updateUser.php"
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = object : StringRequest(Request.Method.POST,url,
        Response.Listener {response ->
            Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
            Log.d("res",response)
            startActivity(Intent(this,MainActivity::class.java))
        },Response.ErrorListener { error ->

            }){
            override fun getParams():HashMap<String,String>{
                val map = HashMap<String,String>()
                map["id"] = id!!
                map["name"] = name
                map["email"] = email
                map["password"] = password
                return map
            }
        }

        requestQueue.add(stringRequest)

    }

    private fun initViews() {
        nameEd = findViewById(R.id.udpateNameEd)
        emailEd = findViewById(R.id.updateEmailEd)
        passwordEd = findViewById(R.id.updatePasswordEd)
        updateBtn = findViewById(R.id.updateBtn)
        delBtn = findViewById(R.id.delButton)
    }
}