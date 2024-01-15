package com.example.racingfitness.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.racingfitness.R

class CarouselAdapter(private val imageList: ArrayList<Int>, private val   viewPager2: ViewPager2) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.carousel_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewpager_layout, parent, false)
        return CarouselViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        if (position == imageList.size - 1) {
            viewPager2.post(runnable)
        }
    }

     private val runnable =Runnable{
            imageList.addAll(imageList)
            notifyDataSetChanged()

        }
    }
