package com.example.learnenglish.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnenglish.R
import com.example.learnenglish.databinding.StepsItemLayoutBinding

class StepsAdapter : RecyclerView.Adapter<StepsAdapter.StepsViewHolder>() {

    var selectedItem: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        return StepsViewHolder(StepsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun getItemCount(): Int {
        return 4
    }


    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        val currValue = holder.adapterPosition
        holder.bind(currValue)
    }


    inner class StepsViewHolder(private val binding: StepsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        val blue = binding.root.context.resources.getColor(R.color.blue)
        val gray = binding.root.context.resources.getColor(R.color.gray)


        fun bind(position: Int) {
            binding.tvPartName.text = "Part ${position + 1}"


            if (position == 3) {
                binding.viewLine.visibility = View.GONE
                binding.cvStep.visibility = View.VISIBLE
                binding.cvStep.setCardBackgroundColor(gray)
            }

            if (adapterPosition <= selectedItem) {
                binding.cvStep.setCardBackgroundColor(blue)
                binding.viewLine.setBackgroundColor(blue)
            }
            else {
                binding.cvStep.setCardBackgroundColor(gray)
                binding.viewLine.setBackgroundColor(gray)
            }


        }

    }

    fun ifButtonNextClicked() {
        selectedItem += 1
        this.notifyDataSetChanged()
    }

}