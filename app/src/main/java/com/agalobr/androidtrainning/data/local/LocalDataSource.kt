package com.agalobr.androidtrainning.data.local

import android.content.SharedPreferences
import com.agalobr.androidtrainning.domain.User
import com.google.gson.Gson

class LocalDataSource(val sharedPref: SharedPreferences) {

    private val gson = Gson()

    fun saveUser(user: User) {
        val editor = sharedPref.edit()
        editor.putString(user.name, gson.toJson(user)).apply()
    }

    fun getUser(userName: String): User? {
        val user = sharedPref.getString(userName, null)
        return user?.let {
            gson.fromJson(it, User::class.java)
        }
    }

    fun getUsers(): List<User> {
        val users: MutableList<User> = mutableListOf()
        sharedPref.all.forEach { map ->
            users.add(gson.fromJson(map.value as String, User::class.java))
        }
        return users
    }
}