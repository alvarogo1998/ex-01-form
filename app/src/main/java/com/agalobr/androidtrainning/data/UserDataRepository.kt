package com.agalobr.androidtrainning.data

import com.agalobr.androidtrainning.app.Either
import com.agalobr.androidtrainning.app.ErrorApp
import com.agalobr.androidtrainning.data.local.XmlLocalDataSource
import com.agalobr.androidtrainning.domain.User
import com.agalobr.androidtrainning.domain.UserRepository

class UserDataRepository(private val localDataSource: XmlLocalDataSource) : UserRepository {

    override fun save(user: User): Either<ErrorApp, User> {
        return localDataSource.saveUser(user)
    }

    override fun getUser(username: String): Either<ErrorApp, User> {
        return localDataSource.getUser(username)
    }

    override fun getUsers(): Either<ErrorApp, List<User>> {
        return localDataSource.getUsers()
    }
}