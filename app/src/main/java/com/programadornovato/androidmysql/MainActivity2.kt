package com.programadornovato.androidmysql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.textclassifier.TextLinks
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity2 : AppCompatActivity() {
    var txtNombre: EditText?=null
    var txtEmail: EditText?=null
    var txtTelefono: EditText?=null
    var txtPass: EditText?=null
    var tvId:TextView?=null
    var id:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        txtNombre=findViewById(R.id.txtNombre)
        txtEmail=findViewById(R.id.txtEmail)
        txtTelefono=findViewById(R.id.txtTelefono)
        txtPass=findViewById(R.id.txtPass)
        id=intent.getStringExtra("id").toString()
        tvId?.setText(id)
        val queue=Volley.newRequestQueue(this)
        val url="http://192.168.8.100/android_mysql/registro.php?id=$id"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { response ->
                txtNombre?.setText(response.getString("nombre"))
                txtEmail?.setText(response.getString("email"))
                txtTelefono?.setText(response.getString("telefono"))
                txtPass?.setText(response.getString("pass"))
            },Response.ErrorListener { error ->
                Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)
    }
    fun clickRegresar(view:View){
        var intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    fun clickBorrar(view:View){
        val url="http://192.168.8.100/android_mysql/borrar.php"
        val queue=Volley.newRequestQueue(this)
        var resultadoPost = object : StringRequest(Request.Method.POST,url,
        Response.Listener { response ->
            Toast.makeText(this,"El usuario se creo de forma exitosa",Toast.LENGTH_LONG).show()
        },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Error al crear el usuario $error",Toast.LENGTH_LONG).show()
            }
            ){
            override fun getParams(): MutableMap<String, String> {
                val parametros=HashMap<String,String>()
                parametros.put("id",id!!)
                return parametros
            }
        }
        queue.add(resultadoPost)
    }
}