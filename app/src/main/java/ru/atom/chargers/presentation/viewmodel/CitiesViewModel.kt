package ru.atom.chargers.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.atom.chargers.data.repository.ChargersRepository
import ru.atom.chargers.data.model.CityModel
import ru.atom.chargers.data.model.CityChargerModel

class CitiesViewModel(application: Application) : AndroidViewModel(application) {

    private val _chargersState = MutableStateFlow<List<CityChargerModel>?>(null)
    val chargersState: StateFlow<List<CityChargerModel>?> = _chargersState.asStateFlow()

    private val _citiesState = MutableStateFlow<List<CityModel>?>(null)
    val citiesState: StateFlow<List<CityModel>?> = _citiesState.asStateFlow()

    init {
        initCities()
        loadChargers()
    }

    fun selectCity(city: CityModel) {
        _citiesState.value = _citiesState.value?.map {
            it.copy(isActive = it.name == city.name)
        }
    }

    fun getSelectedCity(): CityModel? {
        return _citiesState.value?.find { it.isActive }
    }

    private fun initCities() {
        _citiesState.value = listOf(
            CityModel(
                name = MOSCOW,
                isActive = true,
            ),
            CityModel(
                name = SAINT_PETERSBURG,
                isActive = false,
            )
        )
    }

    private fun loadChargers() {
        viewModelScope.launch {
            _chargersState.value = ChargersRepository.loadChargers(getApplication())
        }
    }

    companion object {
        private const val MOSCOW = "Москва"
        private const val SAINT_PETERSBURG = "Санкт-Петербург"
    }
}
