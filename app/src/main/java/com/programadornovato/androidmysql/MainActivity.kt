package com.programadornovato.androidmysql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    var txtNombre:EditText?=null
    var txtEmail:EditText?=null
    var txtTelefono:EditText?=null
    var txtPass:EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtNombre=findViewById(R.id.txtNombre)
        txtEmail=findViewById(R.id.txtEmail)
        txtTelefono=findViewById(R.id.txtTelefono)
        txtPass=findViewById(R.id.txtPass)
    }
    fun clickBtnInsertar(view:View){
        val url="http://192.168.8.100/android_mysql/insertar.php"
        val queue=Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST,url,
        Response.Listener<String> { response ->
            Toast.makeText(this,"Usuario insertado exitosamente",Toast.LENGTH_LONG).show()
        },Response.ErrorListener { error ->
                Toast.makeText(this,"Error $error ",Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val parametros=HashMap<String,String>()
                parametros.put("nombre",txtNombre?.text.toString())
                parametros.put("email",txtEmail?.text.toString())
                parametros.put("telefono",txtTelefono?.text.toString())
                parametros.put("pass",txtPass?.text.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)
    }
    fun cickVer(view: View){
        var txtId=findViewById<EditText>(R.id.txtId)
        var intent= Intent(this,MainActivity2::class.java)
        intent.putExtra("id",txtId.text.toString())
        startActivity(intent)
    }
}