package com.example.ecommerceappproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val productList: ArrayList<product>

):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.itemTitle.text= currentItem.title
        holder.itemDetail.text= currentItem.details
        holder.itemDetail.text=  currentItem.price
        holder.itemImage.text = currentItem.image



    }

    override fun getItemCount() : Int {
        return productList.size
    }
    inner class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener{
        var itemImage : TextView
        var itemTitle: TextView
        var itemDetail: TextView
        var itemPrice: TextView


        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
            itemPrice = itemView.findViewById(R.id.item_price)

            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?){
            val position:Int = adapterPosition
            val price: TextView = itemPrice
            val title: TextView = itemTitle
            val detail: TextView = itemDetail
            val name: TextView = itemDetail

            itemView.setOnClickListener{
                val position: Int = adapterPosition
                Toast.makeText(itemView.context,"you clicked on {$title[position]}", Toast.LENGTH_LONG).show()

            }


        }
    }


}