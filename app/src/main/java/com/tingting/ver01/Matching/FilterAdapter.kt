package com.tingting.ver01.Matching

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tingting.ver01.R

class FilterAdapter(val context: Context, var listItems:ArrayList<String>) : BaseAdapter() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var options: Array<String> ?= null

    fun FilterAdapter(context: Context, listItems: Array<String>){
        options = listItems
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view:View
        val holder: OptionHolder
        if(convertView == null){
            view = inflater.inflate(R.layout.spinner_filter_dropdown, parent, false)
            holder = OptionHolder(view)
            view?.tag = holder
        }else{
            view = convertView
            holder = view.tag as OptionHolder
        }

        /*val params = view.layoutParams
        params.height = 150
        view.layoutParams = params*/

        holder.label.text = listItems.get(position)
        holder.label.visibility = View.INVISIBLE

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view:View
        val holder: OptionHolder
        if(convertView == null){
            view = inflater.inflate(R.layout.spinner_filter_dropdown, parent, false)
            holder = OptionHolder(view)
            view?.tag = holder
        }else{
            view = convertView
            holder = view.tag as OptionHolder
        }

        val params = view.layoutParams
        params.height = 100
        view.layoutParams = params

        holder.label.text = listItems.get(position)

        return view
    }

    override fun getItem(position: Int): Any? {
        return listItems.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listItems.size
    }

    private class OptionHolder(view: View) {
        val label: TextView

        init {
            this.label = view?.findViewById(R.id.spinnerText) as TextView
        }

    }

}

