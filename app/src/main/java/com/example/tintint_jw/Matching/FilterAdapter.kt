package com.example.tintint_jw.Matching

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.tintint_jw.R
import kotlinx.android.synthetic.main.spinner_filter.view.*

class FilterAdapter(val context: Context, var listItems:Array<String>) : BaseAdapter() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var options: Array<String> ?= null

    fun FilterAdapter(context: Context, listItems: Array<String>){
        options = listItems
    }


    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return p1!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view:View = inflater.inflate(R.layout.spinner_filter_dropdown, parent, false)

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

    /*private class OptionHolder(view: View) {
        val label:TextView

        init {
            this.label = view?.findViewById(R.id.spinnerText) as TextView
        }

    }*/

}

