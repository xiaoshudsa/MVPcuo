package com.zxp.xiaomokong.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zxp.data.DemoData
import com.zxp.xiaomokong.R
import kotlinx.android.synthetic.main.demo_adapter_layout.view.*

class DemoAdapter(listData: MutableList<DemoData>, context: Context) : RecyclerView.Adapter<DemoAdapter.ViewHolder>() {
    private val listData: MutableList<DemoData>? = listData
    private val context: Context? = context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.demo_adapter_layout, parent, false))

    override fun getItemCount(): Int = this.listData!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.run {
            listData!![position].let {
                this.demoText.text = it.title
            }
        }
    }
}