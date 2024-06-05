package ru.atom.chargers.data.repository

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.delay
import ru.atom.chargers.data.model.CityChargerModel
import ru.atom.chargers.utils.readJSONFromAssets
import java.lang.Exception

object ChargersRepository {
    var chargers: List<CityChargerModel> = mutableListOf()

    private const val CHARGERS_JSON_NAME = "chargers.json"

    suspend fun loadChargers(context: Context): List<CityChargerModel>? {
        return try {
            delay(1000)
            getChargersFromAssets(context).also { chargers = it }
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    private fun getChargersFromAssets(context: Context): List<CityChargerModel> {
        val jsonString = readJSONFromAssets(context, CHARGERS_JSON_NAME)
        val data = Gson().fromJson(jsonString, Array<CityChargerModel>::class.java)
        return data.asList()
    }
}
