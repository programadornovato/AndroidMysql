package com.programadornovato.androidmysql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    var txtNombre:EditText?=null
    var txtEmail:EditText?=null
    var txtTelefono:EditText?=null
    var txtPass:EditText?=null
    var tbUsuarios:TableLayout?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtNombre=findViewById(R.id.txtNombre)
        txtEmail=findViewById(R.id.txtEmail)
        txtTelefono=findViewById(R.id.txtTelefono)
        txtPass=findViewById(R.id.txtPass)
        tbUsuarios=findViewById(R.id.tbUsuarios)
        tbUsuarios?.removeAllViews()
        var queue=Volley.newRequestQueue(this)
        var url="http://192.168.8.100/android_mysql/registros.php"

        var jsonObjectRequest=JsonObjectRequest(Request.Method.GET,url,null,
        Response.Listener { response ->
            try {
                var jsonArray=response.getJSONArray("data")
                for(i in 0 until jsonArray.length() ){
                    var jsonObject=jsonArray.getJSONObject(i)
                    val registro=LayoutInflater.from(this).inflate(R.layout.table_row_np,null,false)
                    val colNombre=registro.findViewById<View>(R.id.colNombre) as TextView
                    val colEmail=registro.findViewById<View>(R.id.colEmail) as TextView
                    val colEditar=registro.findViewById<View>(R.id.colEditar)
                    val colBorrar=registro.findViewById<View>(R.id.colBorrar)
                    colNombre.text=jsonObject.getString("nombre")
                    colEmail.text=jsonObject.getString("email")
                    colEditar.id=jsonObject.getString("id").toInt()
                    colBorrar.id=jsonObject.getString("id").toInt()
                    tbUsuarios?.addView(registro)
                }
            }catch (e: JSONException){
                e.printStackTrace()
            }
        },Response.ErrorListener { error ->

            }
            )
        queue.add(jsonObjectRequest)
    }
    fun clickTablaEditar(view: View){
        Toast.makeText(this,view.id.toString(),Toast.LENGTH_LONG).show()
    }
    fun clickTablaBorrar(view: View){
        Toast.makeText(this,view.id.toString(),Toast.LENGTH_LONG).show()
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