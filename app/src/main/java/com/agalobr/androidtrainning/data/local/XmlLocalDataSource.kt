package com.agalobr.androidtrainning.data.local

import android.content.Context
import android.util.Log
import com.agalobr.androidtrainning.app.Either
import com.agalobr.androidtrainning.app.ErrorApp
import com.agalobr.androidtrainning.app.left
import com.agalobr.androidtrainning.app.right
import com.agalobr.androidtrainning.domain.User
import com.google.gson.Gson
import java.io.IOException
import java.lang.Exception


class XmlLocalDataSource(private val context: Context) {

    private val gson = Gson()

    private val sharedPref = context.getSharedPreferences("users", Context.MODE_PRIVATE)

    fun saveUser(user: User): Either<ErrorApp, User> {

        /* val editor = sharedPref.edit()
         editor.apply {
             putString("username", username)
             putString("surname", surname)
             apply()
         }*/

        return try {
            with(sharedPref.edit()) {
                putString(user.name, gson.toJson(user)).apply()
            }
            user.right()
        } catch (ex: Exception) {
            ErrorApp.UnknowError.left()
        }
    }

    fun getUser(username: String): Either<ErrorApp, User> {
        return try {
            val user = sharedPref.getString(username, null)
            user.let {
                gson.fromJson(it, User::class.java)
            }.right()
        } catch (io: IOException) {
            ErrorApp.UnknowError.left()
        }
    }

    fun getUsers(): Either<ErrorApp, List<User>> {
        return try {
            val users: MutableList<User> = mutableListOf()
            sharedPref.all.forEach { map ->
                users.add(gson.fromJson(map.value as String, User::class.java))
            }
            Log.d("@dev", "$users")
            users.right()
        } catch (io: IOException) {
            ErrorApp.UnknowError.left()
        }
    }
}