package com.agalobr.androidtrainning.domain

import com.agalobr.androidtrainning.app.Either
import com.agalobr.androidtrainning.app.ErrorApp

class GetUserUseCase(private val repository: UserRepository) {
    operator fun invoke(username: String): Either<ErrorApp, User> {
        return repository.getUser(username)
    }
}