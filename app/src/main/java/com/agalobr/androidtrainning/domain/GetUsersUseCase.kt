package com.agalobr.androidtrainning.domain

import com.agalobr.androidtrainning.app.Either
import com.agalobr.androidtrainning.app.ErrorApp

class GetUsersUseCase(private val repository: UserRepository) {
    operator fun invoke(): Either<ErrorApp, List<User>> {
        return repository.getUsers()
    }
}