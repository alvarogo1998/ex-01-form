package com.agalobr.androidtrainning.data.local

import android.content.Context
import android.util.Log
import android.widget.Toast
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

        try {
            with(sharedPref.edit()) {
                putString(user.name, gson.toJson(user)).apply()
                Toast.makeText(context, "Datos Guardados Correctamente", Toast.LENGTH_LONG).show()
            }
            return user.right()
        } catch (ex: Exception) {
            return ErrorApp.UnknowError.left()
        }
    }

    fun getUser(userName: String): Either<ErrorApp, User> {
        try {
            val user = sharedPref.getString(userName, null)
            Toast.makeText(context, "$user", Toast.LENGTH_LONG).show()
            return user.let {
                gson.fromJson(it, User::class.java)
            }.right()
        } catch (io: IOException) {
            return ErrorApp.UnknowError.left()
        }
    }

    fun getUsers(): Either<ErrorApp, List<User>> {
        try {
            val users: MutableList<User> = mutableListOf()
            sharedPref.all.forEach { map ->
                users.add(gson.fromJson(map.value as String, User::class.java))
            }
            Log.d("@dev", "$users")
            return users.right()
        } catch (io: IOException) {
            return ErrorApp.UnknowError.left()
        }
    }
}