package com.example.contactosapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    var lista:ListView? = null
    var adaptador:AdaptadorCustom? = null

    companion object {
        var contactos:ArrayList<Contacto>? = null

        fun AgregarConctacto(contacto: Contacto){
            contactos?.add(contacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Juan", "Gómez","Contollo Consulting", 30, 70.3F, "Ciudad El Doral CED F-28", "58144049", "jgomez@contolloconsulting.com", R.drawable.foto_01))

        lista = findViewById<ListView>(R.id.lista)
        adaptador = AdaptadorCustom(this, contactos!!)

        lista?.adapter = adaptador
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.iNuevo -> {
                val intent = Intent(this, Nuevo::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        adaptador?.notifyDataSetChanged()
        super.onResume()
    }
}
