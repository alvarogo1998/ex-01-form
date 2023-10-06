package com.agalobr.androidtrainning.domain

import com.agalobr.androidtrainning.app.Either
import com.agalobr.androidtrainning.app.ErrorApp

interface UserRepository {

    fun save(user: User): Either<ErrorApp, User>
    fun getUser(username: String): Either<ErrorApp, User>
    fun getUsers(): Either<ErrorApp, List<User>>

}