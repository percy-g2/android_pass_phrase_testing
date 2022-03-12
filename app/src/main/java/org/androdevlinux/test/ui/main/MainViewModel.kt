package org.androdevlinux.test.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val randomListStringInternal = MutableLiveData<String>()
    val randomListString = randomListStringInternal

    private fun getNum(v: ArrayList<Int>): Int {
        val n = v.size
        val index = (Math.random() * n).toInt()
        val num = v[index]
        v[index] = v[n - 1]
        v.removeAt(n - 1)
        return num
    }

    fun generateRandom(n: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val v: ArrayList<Int> = ArrayList(n)
            val xyz = mutableListOf<String>()

            for (i in 0 until n) v.add(i.plus(1))
            while (v.size > 0) {
                xyz.add(listOfPhase[getNum(v) - 1] + "\n")
            }
            randomListString.postValue(xyz.joinToString().replace(",", ""))
        }
    }

    private val listOfPhase = listOf("impact",
            "safe",
            "include",
            "life",
            "health",
            "nature",
            "peace",
            "spirit",
            "improve",
            "equal",
            "ethics",
            "evolve")
}