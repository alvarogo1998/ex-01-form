package com.agalobr.androidtrainning.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import com.agalobr.androidtrainning.R
import com.agalobr.androidtrainning.data.UserDataRepository
import com.agalobr.androidtrainning.data.local.XmlLocalDataSource
import com.agalobr.androidtrainning.domain.GetUserUseCase
import com.agalobr.androidtrainning.domain.GetUsersUseCase
import com.agalobr.androidtrainning.domain.SaveUserUseCase
import com.agalobr.androidtrainning.domain.User

class MainActivity : AppCompatActivity() {

    //Para usar esta creacion se ha añadido: implementation("androidx.activity:activity-ktx:1.7.2")
    val viewModel: MainViewModel by lazy {
        MainViewModel(
            SaveUserUseCase(UserDataRepository(XmlLocalDataSource(this))),
            GetUserUseCase(UserDataRepository(XmlLocalDataSource(this))),
            GetUsersUseCase(UserDataRepository(XmlLocalDataSource(this)))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercice02)
        saveUser()
        getUser()
        getUsers()
    }

    private fun saveUser() {
        val saveButtom = findViewById<Button>(R.id.action_save)
        saveButtom.setOnClickListener {
            viewModel.saveUser(User(getNameInput(), getSurnameInput()))
        }
    }

    private fun getUser() {
        val saveButtom = findViewById<Button>(R.id.action_getUser)
        saveButtom.setOnClickListener {
            viewModel.getUser(getNameInput())
        }
    }

    private fun getUsers() {
        val saveButtom = findViewById<Button>(R.id.action_getUsers)
        saveButtom.setOnClickListener {
            viewModel.getUsers()
        }
    }

    private fun setUpObservers() {
        val observer = Observer<MainViewModel.Uistate> {
            //Codigo al notificar el observador
            it.user?.apply {
                bindData(this)
            }
        }
        viewModel.uiState.observe(this, observer)
    }

    private fun bindData(user: User) {
        setNameInput(user.name)
        setSurnameInput(user.surname)
    }

    private fun setNameInput(name: String) {
        findViewById<EditText>(R.id.input_name).setText(name)
    }

    private fun setSurnameInput(surname: String) {
        findViewById<EditText>(R.id.input_surname).setText(surname)
    }

    private fun getNameInput(): String = findViewById<EditText>(R.id.input_name).text.toString()
    private fun getSurnameInput(): String =
        findViewById<EditText>(R.id.input_surname).text.toString()
    private fun getAgeInput(): String =
        findViewById<EditText>(R.id.input_age).text.toString()
}
