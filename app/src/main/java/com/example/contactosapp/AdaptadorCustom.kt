package com.example.contactosapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorCustom(var context: Context, items:ArrayList<Contacto>): BaseAdapter() {

    var items:ArrayList<Contacto>? = null

    init {
        this.items = items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:viewHolder? = null
        var vista:View? = convertView

        if ( vista == null) {
            vista = LayoutInflater.from(context).inflate(R.layout.template_contacto, null)
            viewHolder = viewHolder(vista)
            vista.tag = viewHolder
        } else {
            viewHolder = vista.tag as? viewHolder
        }

        val item = getItem(position) as Contacto
        viewHolder?.nombre?.text = item.nombre
        viewHolder?.foto?.setImageResource(item.foto)
        viewHolder?.empresa?.text = item.empresa

        return vista!!
    }

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.items?.count()!!
    }

    private class viewHolder(vista:View){
        var nombre:TextView? = null
        var foto:ImageView? = null
        var empresa:TextView? = null

        init {
            nombre = vista.findViewById(R.id.tvNombre)
            empresa = vista.findViewById(R.id.tvEmpresa)
            foto = vista.findViewById(R.id.ivFoto)
        }

    }
}