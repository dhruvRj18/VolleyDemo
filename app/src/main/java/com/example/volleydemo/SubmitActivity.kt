package com.example.volleydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.volleydemo.Constants.BASE_URL
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SubmitActivity : AppCompatActivity() {

    private lateinit var btn:MaterialButton
    private lateinit var nameEd :TextInputEditText
    private lateinit var emailEd :TextInputEditText
    private lateinit var passwordEd :TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)
        initViews()
        btn.setOnClickListener {
            val email = emailEd.text.toString()
            val name = nameEd.text.toString()
            val password = passwordEd.text.toString()
            submitData(name,email,password)
        }

    }

    private fun submitData(name: String, email: String, password: String) {

        val url = BASE_URL + "insertUser.php"
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = object : StringRequest(Request.Method.POST,url,
        Response.Listener { response ->
            Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))

        },
        Response.ErrorListener { error-> }
        ){
            override fun getParams(): HashMap<String,String>{
                val map = HashMap<String,String>()
                map["name"] = name
                map["email"] = email
                map["password"] = password
                return map
            }
        }
        requestQueue.add(stringRequest)

    }

    private fun initViews() {
        btn = findViewById(R.id.submitBtn)
        nameEd = findViewById(R.id.nameEd)
        emailEd = findViewById(R.id.emailEd)
        passwordEd = findViewById(R.id.passwordEd)
    }
}