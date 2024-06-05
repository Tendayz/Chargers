package ru.atom.chargers.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.launch
import ru.atom.chargers.R
import ru.atom.chargers.databinding.FragmentCitiesBinding
import ru.atom.chargers.presentation.adapter.CitiesRecyclerViewAdapter
import ru.atom.chargers.data.model.CityModel
import ru.atom.chargers.presentation.CITY_NAME_KEY
import ru.atom.chargers.presentation.viewmodel.CitiesViewModel
import ru.atom.chargers.presentation.viewmodel.SelectCityListener

class CitiesFragment : Fragment(), SelectCityListener {

    private lateinit var binding: FragmentCitiesBinding

    private val viewModel: CitiesViewModel by viewModels()

    private val citiesAdapter = CitiesRecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCitiesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.chargersState.collect { state ->
                if (state != null) {
                    binding.chooseButton.isEnabled = true
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.citiesState.collect { state ->
                state?.let { citiesAdapter.applyData(it) }
            }
        }
    }

    override fun selectCity(city: CityModel) {
        viewModel.selectCity(city)
    }

    private fun initViews(view: View) {
        initAdapter()
        binding.chooseButton.setOnClickListener {
            goToChargersFragment(view)
        }
    }

    private fun initAdapter() {
        binding.citiesRecyclerView.adapter = citiesAdapter
        binding.citiesRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun goToChargersFragment(view: View) {
        val bundle = Bundle()
        bundle.putString(CITY_NAME_KEY, viewModel.getSelectedCity()?.name)
        val navController: NavController = Navigation.findNavController(view)
        navController.navigate(R.id.action_citiesFragment_to_chargersFragment, bundle)
    }
}
