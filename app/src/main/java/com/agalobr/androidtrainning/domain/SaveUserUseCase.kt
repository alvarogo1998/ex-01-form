package com.agalobr.androidtrainning.domain

import com.agalobr.androidtrainning.app.Either
import com.agalobr.androidtrainning.app.ErrorApp

class SaveUserUseCase(private val repository: UserRepository) {

    operator fun invoke(user: User): Either<ErrorApp, User> {
        return repository.save(user)
    }
}