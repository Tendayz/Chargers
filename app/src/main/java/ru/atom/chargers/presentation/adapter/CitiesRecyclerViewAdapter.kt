package ru.atom.chargers.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.atom.chargers.databinding.ItemCityBinding
import ru.atom.chargers.data.model.CityModel
import ru.atom.chargers.presentation.viewmodel.SelectCityListener

class CitiesRecyclerViewAdapter(
    private val listener: SelectCityListener,
) : RecyclerView.Adapter<CitiesRecyclerViewAdapter.CityHolder>() {

    val data = mutableListOf<CityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val itemBinding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        val cityModel: CityModel = data[position]
        holder.bind(cityModel)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun applyData(newData: List<CityModel>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class CityHolder(
        private val itemBinding: ItemCityBinding,
        private val listener: SelectCityListener,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: CityModel) {
            with(itemBinding) {
                radioButtonCheckbox.isChecked = item.isActive
                title.text = item.name
                root.setOnClickListener {
                    listener.selectCity(item)
                }
            }
        }
    }
}
