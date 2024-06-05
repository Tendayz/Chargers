package ru.atom.chargers.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.atom.chargers.R
import ru.atom.chargers.data.model.ChargerModel
import ru.atom.chargers.databinding.ItemChargerBinding

class ChargersRecyclerViewAdapter : RecyclerView.Adapter<ChargersRecyclerViewAdapter.ChargerHolder>() {

    val data = mutableListOf<ChargerModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChargerHolder {
        val itemBinding = ItemChargerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChargerHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ChargerHolder, position: Int) {
        val chargerModel: ChargerModel = data[position]
        holder.bind(chargerModel)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun applyData(newData: List<ChargerModel>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ChargerHolder(
        private val itemBinding: ItemChargerBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: ChargerModel) {
            with(itemBinding) {
                if (item.busy) {
                    root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.light_red));
                } else {
                    root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.light_green));
                }
                title.text = item.name
                address.text = item.address
            }
        }
    }
}
