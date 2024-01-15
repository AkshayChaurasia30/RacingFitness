package com.example.racingfitness.adapter


import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.racingfitness.R
import com.example.racingfitness.model.MenuItemRecord
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class MenuItemAdapter(
    private var itemList: ArrayList<MenuItemRecord>, private var studentListener: ItemClickListener) : RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>() {
    private var attendanceReference = Firebase


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_menu_item, parent, false)
        return MenuItemViewHolder(view, studentListener)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val menuItem: MenuItemRecord = itemList[position]
        holder.itemIcon.setImageResource(menuItem.menuIcon!!)
        holder.itemTitle.text = menuItem.menuTitle


        holder.itemIcon.setOnClickListener {
            studentListener.onItemClick(menuItem)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size

    }

    class MenuItemViewHolder(itemView: View, studentListener: ItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        var itemIcon: ImageView = itemView.findViewById<ImageView>(R.id.menuIcon)
        var itemTitle: TextView = itemView.findViewById<TextView>(R.id.menu_Title)


    }

    // interface for itemClick of recyclerview.....
    interface ItemClickListener {
        fun onItemClick(record: MenuItemRecord)
    }
}
