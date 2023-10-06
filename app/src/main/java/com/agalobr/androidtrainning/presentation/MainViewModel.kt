package com.agalobr.androidtrainning.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agalobr.androidtrainning.app.ErrorApp
import com.agalobr.androidtrainning.domain.GetUserUseCase
import com.agalobr.androidtrainning.domain.GetUsersUseCase
import com.agalobr.androidtrainning.domain.SaveUserUseCase
import com.agalobr.androidtrainning.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase, private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    /*Utilizaremos un Observer*/
    private val _uiState = MutableLiveData<Uistate>()
    val uiState: LiveData<Uistate> = _uiState

    fun saveUser(user: User) {
        saveUserUseCase(user).fold(
            { responseError(it) },
            { responseSuccess(it) }
        )
    }

    /*La corrutina por defecto nos coje un hilo, si queremos asegurarnos de usar un hilo secundarion pasaremos
    * el Dispatchers.IO el cual es un hilo secundario, OJO cuidado con Dispatchers.Main que se ejecutaria en el hilo principal*/
    fun getUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase(user).fold(
                { responseError(it) },
                { responseSuccessUser(it) }
            )
        }
    }

    fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersUseCase().fold(
                { responseError(it) },
                { responseSuccessUsers(it) }
            )
        }
    }

    private fun responseError(errorApp: ErrorApp) {

    }

    private fun responseSuccess(isOk: User) {
        saveUserUseCase.invoke(isOk)
    }

    private fun responseSuccessUser(user: User) {
        //Dispatchers.IO
        _uiState.postValue(Uistate(user = user))
    }

    private fun responseSuccessUsers(isOK: List<User>) {
        getUsersUseCase.invoke()
    }

    data class Uistate(
        val errorApp: ErrorApp? = null,
        val isLoading: Boolean = false,
        val user : User? = null
    )
}