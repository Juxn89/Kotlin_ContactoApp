package com.example.contactosapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Switch
import android.widget.ViewSwitcher
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lista:ListView? = null
    var switcherView :ViewSwitcher? = null

    companion object {
        var contactos:ArrayList<Contacto>? = null
        var adaptador:AdaptadorCustom? = null

        fun AgregarConctacto(contacto: Contacto){
            adaptador?.addItem(contacto)
        }

        fun obtenerContacto(index:Int):Contacto{
            return adaptador?.getItem(index) as Contacto
        }

        fun eliminarContacto(index:Int){
            adaptador?.removeItem(index)
        }

        fun actualizarContacto(index:Int, nuevoContato:Contacto) {
            adaptador?.updateItem(index, nuevoContato)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Juan", "Gómez","Contollo Consulting", 30, 70.3F, "Ciudad El Doral CED F-28", "58144049", "jgomez@contolloconsulting.com", R.drawable.foto_01))
        contactos?.add(Contacto("Carlos", "Bermúdez","Contollo Consulting", 30, 70.3F, "Ciudad El Doral CED F-28", "58144049", "jgomez@contolloconsulting.com", R.drawable.foto_02))
        contactos?.add(Contacto("Giobla", "Lorente","Contollo Consulting", 30, 70.3F, "Ciudad El Doral CED F-28", "58144049", "jgomez@contolloconsulting.com", R.drawable.foto_03))
        contactos?.add(Contacto("Scarlet", "Rodríguez","Contollo Consulting", 30, 70.3F, "Ciudad El Doral CED F-28", "58144049", "jgomez@contolloconsulting.com", R.drawable.foto_04))
        contactos?.add(Contacto("Ilse", "Gómez","Contollo Consulting", 30, 70.3F, "Ciudad El Doral CED F-28", "58144049", "jgomez@contolloconsulting.com", R.drawable.foto_05))
        contactos?.add(Contacto("Michelle", "Bermúdez","Contollo Consulting", 30, 70.3F, "Ciudad El Doral CED F-28", "58144049", "jgomez@contolloconsulting.com", R.drawable.foto_06))

        lista = findViewById<ListView>(R.id.lista)
        adaptador = AdaptadorCustom(this, contactos!!)

        lista?.adapter = adaptador
        switcherView = findViewById(R.id.viewSwitcher)

        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as SearchView

        val itemSwitch = menu?.findItem(R.id.aswitch)
        itemSwitch?.setActionView(R.layout.switch_item)
        val switchView = itemSwitch?.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto"
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->

        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adaptador?.filtrar(newText!!)
                return true
            }

        })

        switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitcher?.showNext()
        }

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
