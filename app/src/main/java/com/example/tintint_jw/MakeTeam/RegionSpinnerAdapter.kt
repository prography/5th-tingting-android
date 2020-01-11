package com.example.tintint_jw.MakeTeam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.tintint_jw.R

class RegionSpinnerAdapter(val context: Context, var listItems:Array<String>) : BaseAdapter() {

    val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        var region:TextView
        if(convertView == null){
            view = inflater.inflate(R.layout.spinner_region, parent, false)
            region = view.findViewById(R.id.regionTV)
        }else{
            view = convertView
            region = view.findViewById(R.id.regionTV)

        }

        region.setText(listItems.get(position))
        region.visibility = View.INVISIBLE

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view:View
        var region:TextView
        // dropdown 시
        if(convertView == null){
            view = inflater.inflate(R.layout.spinner_region, parent, false)
            region = view.findViewById(R.id.regionTV)

        }else{
            view = convertView
            region = view.findViewById(R.id.regionTV)
        }

        region.setText(listItems.get(position))
        return view
    }

    override fun getItem(position: Int): Any? {
        return listItems.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listItems.size
    }
}