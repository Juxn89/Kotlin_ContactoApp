package com.example.contactosapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import java.util.*
import kotlin.collections.ArrayList

class Nuevo : AppCompatActivity() {

    var fotoIndex:Int = 0
    var foto:ImageView? = null
    val fotos = arrayOf(R.drawable.foto_01, R.drawable.foto_02, R.drawable.foto_03, R.drawable.foto_04, R.drawable.foto_05, R.drawable.foto_06)

    var index:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById<ImageView>(R.id.imageView)
        foto?.setOnClickListener {
            SelecionarFoto()
        }

        if (intent.hasExtra("ID")) {
            index = intent.getStringExtra("ID").toInt()
            rellenarDatos(index)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.iCrearNuevo -> {
                // Crear un nuevo elemento de tipo Contacto
                val nombre = findViewById<EditText>(R.id.tvNombre)
                val apellido = findViewById<EditText>(R.id.tvApellido)
                val empresa = findViewById<EditText>(R.id.tvEmpresa)
                val edad = findViewById<EditText>(R.id.tvEdad)
                val peso = findViewById<EditText>(R.id.tvPeso)
                val telefono = findViewById<EditText>(R.id.tvTelefono)
                val email = findViewById<EditText>(R.id.tvEmail)
                val direccion = findViewById<EditText>(R.id.tvDireccion)

                val campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellido.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(telefono.text.toString())
                campos.add(email.text.toString())
                campos.add(direccion.text.toString())

                var flag = 0
                for (campo in campos) {
                    if (campo.isNullOrEmpty()) {
                        flag++
                    }
                }

                if (flag > 0) {
                    Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_LONG).show()
                }
                else {
                    if (index > -1) {
                        MainActivity.actualizarContacto(index, Contacto(nombre.text.toString(), apellido.text.toString(), empresa.text.toString(), edad.text.toString().toInt(), peso.text.toString().toFloat(), direccion.text.toString(), telefono.text.toString(), email.text.toString(), obtenerFoto(fotoIndex)))
                        finish()
                    }
                    else {
                        MainActivity.AgregarConctacto(Contacto(nombre.text.toString(), apellido.text.toString(), empresa.text.toString(), edad.text.toString().toInt(), peso.text.toString().toFloat(), direccion.text.toString(), telefono.text.toString(), email.text.toString(), obtenerFoto(fotoIndex)))
                        finish()
                    }
                    Log.d("NO ELEMENTOS", MainActivity.contactos?.count().toString())
                }

                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun SelecionarFoto(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona imagen de perfil")

        val adaptadorDialogo = ArrayAdapter<String>(this,  android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("Foto 01")
        adaptadorDialogo.add("Foto 02")
        adaptadorDialogo.add("Foto 03")
        adaptadorDialogo.add("Foto 04")
        adaptadorDialogo.add("Foto 05")
        adaptadorDialogo.add("Foto 06")

        builder.setAdapter(adaptadorDialogo){
            dialog, which ->
            fotoIndex = which
            foto?.setImageResource(obtenerFoto(fotoIndex))
        }

        builder.setNegativeButton("Cancelar"){
            dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    fun obtenerFoto(index:Int):Int {
        return fotos.get(index)
    }

    fun rellenarDatos(index: Int) {
        val contacto = MainActivity.obtenerContacto(index)

        val tvNombre = findViewById<EditText>(R.id.tvNombre)
        val tvApellido = findViewById<EditText>(R.id.tvApellido)
        val tvEmpresa = findViewById<EditText>(R.id.tvEmpresa)
        val tvEdad = findViewById<EditText>(R.id.tvEdad)
        val tvPeso = findViewById<EditText>(R.id.tvPeso)
        val tvTelefono = findViewById<EditText>(R.id.tvTelefono)
        val tvEmail = findViewById<EditText>(R.id.tvEmail)
        val tvDireccion = findViewById<EditText>(R.id.tvDireccion)
        val ivFoto = findViewById<ImageView>(R.id.imageView)

        tvNombre.setText(contacto.nombre, TextView.BufferType.EDITABLE)
        tvApellido.setText(contacto.apellido, TextView.BufferType.EDITABLE)
        tvEmpresa.setText(contacto.empresa, TextView.BufferType.EDITABLE)
        tvEdad.setText(contacto.edad.toString(), TextView.BufferType.EDITABLE)
        tvPeso.setText(contacto.peso.toString(), TextView.BufferType.EDITABLE)
        tvTelefono.setText(contacto.telefono, TextView.BufferType.EDITABLE)
        tvEmail.setText(contacto.email, TextView.BufferType.EDITABLE)
        tvDireccion.setText(contacto.direccion, TextView.BufferType.EDITABLE)
        ivFoto.setImageResource(contacto.foto)

        var posicion = 0
        for (foto in fotos){
            if (contacto.foto == foto) {
                fotoIndex = posicion
            }
            posicion++
        }
    }
}
