package com.example.ecommerceappproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class productList : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var productArrayList: ArrayList<product>
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)


        userRecyclerView = findViewById(R.id.recyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        productArrayList = arrayListOf<product>()
        getProductData()
    }
    private fun getProductData(){
        dbref = FirebaseDatabase.getInstance().getReference("Products")

        dbref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val product = userSnapshot.getValue(product::class.java)
                        productArrayList.add(product!!)
                    }
                    userRecyclerView.adapter = RecyclerAdapter(productArrayList)
                }
            }

            override fun onCancelled(error : DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}