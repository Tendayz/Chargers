package ru.atom.chargers.presentation.viewmodel

import ru.atom.chargers.data.model.CityModel

interface SelectCityListener {

    fun selectCity(city: CityModel)
}
