package com.example.volleydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.volleydemo.Constants.BASE_URL
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject

class MainActivity : AppCompatActivity(),RcvAdapter.MyOnClickListener {


    private lateinit var fab:FloatingActionButton
    private lateinit var swipeRefreshLayout:SwipeRefreshLayout
    private lateinit var rcv:RecyclerView
    private var list:ArrayList<UserModel> = ArrayList()
    private var rcvAdapter = RcvAdapter(list,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        getData()
        fab.setOnClickListener {
            startActivity(Intent(this,SubmitActivity::class.java))
        }
        rcv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = rcvAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            getData()
            swipeRefreshLayout.isRefreshing =false
        }
    }

    override fun OnClick(position: Int) {
        Toast.makeText(this,list[position].email,Toast.LENGTH_SHORT).show()
    }

    private fun getData() {
        val url = BASE_URL + "getUsers.php"
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                list.clear()
                val jsonObject = JSONObject(response)
                if (jsonObject.get("response").equals("Success")){
                    val jsonArray = jsonObject.getJSONArray("data")
                    for (i in 0..jsonArray.length()-1){
                        val jo = jsonArray.getJSONObject(i)
                        val id = jo.get("id").toString()
                        val email = jo.get("email").toString()
                        val name = jo.get("name").toString()
                        val password = jo.get("password").toString()
                        val user = UserModel(id,name,email, password)
                        list.add(user)
                    }
                    rcvAdapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this,jsonObject.get("response").toString(),Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener { error ->

            })
        requestQueue.add(stringRequest)
    }

    private fun initView() {
        fab = findViewById(R.id.fab)
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
        rcv = findViewById(R.id.rcv)
    }
}