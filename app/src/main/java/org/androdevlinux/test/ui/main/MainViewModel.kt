package org.androdevlinux.test.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.androdevlinux.test.R

class MainViewModel : ViewModel() {

    private val randomListStringInternal = MutableLiveData<List<PassPhrase>>()
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
            val randomList = mutableListOf<PassPhrase>()

            for (i in 0 until n) v.add(i.plus(1))
            while (v.size > 0) {
                val position = getNum(v) - 1
                val test = listOfPhase.map { it.key }[position]
                val value = listOfPhase.map { it.value }[position]
                randomList.add(PassPhrase(test, value))
            }
            randomListString.postValue(randomList)
        }
    }

    data class PassPhrase(
        val passPhrase: String,
        val passPhraseColor: Int
    )

    private val listOfPhase = hashMapOf(
        "impact" to R.color.yellow,
        "safe" to R.color.black,
        "include" to R.color.light_brown,
        "life" to R.color.red,
        "health" to R.color.orange,
        "nature" to R.color.light_green,
        "peace" to R.color.light_blue,
        "spirit" to R.color.cyan,
        "improve" to R.color.pink,
        "equal" to R.color.dark_blue,
        "ethics" to R.color.indigio,
        "evolve" to R.color.turquoise
    )
}