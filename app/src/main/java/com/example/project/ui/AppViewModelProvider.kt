package com.example.project.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.project.NobarApplication
import com.example.project.ui.user.RegisterViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Other Initializers
        // Initializer for ItemEntryViewModel
        initializer {
            RegisterViewModel(NobarApplication().container.userRepository)
        }
        //...
    }
}