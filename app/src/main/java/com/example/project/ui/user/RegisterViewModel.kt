package com.example.project.ui.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.project.data.model.User
import com.example.project.data.repository.UserRepository

data class UserUiState(
    var userDetail: UserDetail = UserDetail(),
    val isEntryValid: Boolean = false
)

data class UserDetail(
    val id: Int = 0,
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false
)

fun UserDetail.toUser(): User = User(
    id = id,
    name = name,
    username = username,
    password = password
)

fun User.toUserUiState(isEntryValid: Boolean = false): UserUiState = UserUiState(
    userDetail = this.toUserDetail(),
    isEntryValid = isEntryValid
)

fun User.toUserDetail(): UserDetail = UserDetail(
    id = id,
    name = name,
    username = username,
    password = password
)

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    var userUiState by mutableStateOf(UserUiState())

    suspend fun saveItem() {
        if (validateInput()) {
            userRepository.insertUser(userUiState.userDetail.toUser())
        }
    }
    fun validateInput(uiState: UserDetail = userUiState.userDetail): Boolean {
        return with(uiState) {
            name.isNotBlank() && username.isNotBlank() && password.isNotBlank()
        }
    }
}
