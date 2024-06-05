package ru.atom.chargers.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import ru.atom.chargers.data.model.ChargerModel
import ru.atom.chargers.data.repository.ChargersRepository
import ru.atom.chargers.databinding.FragmentChargersBinding
import ru.atom.chargers.presentation.CITY_NAME_KEY
import ru.atom.chargers.presentation.adapter.ChargersRecyclerViewAdapter

class ChargersFragment : Fragment() {

    private lateinit var binding: FragmentChargersBinding

    private val chargersAdapter = ChargersRecyclerViewAdapter()
    private var cityName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentChargersBinding.inflate(layoutInflater)

        cityName = arguments?.getString(CITY_NAME_KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        chargersAdapter.applyData(getSortedChargers())
        binding.chargersRecyclerView.adapter = chargersAdapter
        binding.chargersRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun getSortedChargers(): List<ChargerModel> {
        return ChargersRepository.chargers.filter {
            it.city?.lowercase() == cityName?.lowercase()
        }.mapNotNull { it.charger }.sortedBy { it.name }
    }
}
