package com.catatankecilku

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

class MyListViewModel(private val sharedPreferences:SharedPreferences) : ViewModel(){
    lateinit var onListAdded:(()->Unit)

    val lists:MutableList<Daftar> by lazy {
        retriveLists()
    }

    private fun retriveLists():MutableList<Daftar>{
        val sharedPreferencesContents=sharedPreferences.all
        val daftar=ArrayList<Daftar>()

        for(daftar_list in sharedPreferencesContents){
            val itemHashSet = ArrayList(daftar_list.value as HashSet<String>)
            val daftar_list=Daftar(daftar_list.key,itemHashSet)
            daftar.add(daftar_list)

        }
        return daftar
    }

    fun saveList(list:Daftar){
        sharedPreferences.edit().putStringSet(list.name, list.tasks.toHashSet()).apply()
        lists.add(list)
        onListAdded.invoke()
    }
}
