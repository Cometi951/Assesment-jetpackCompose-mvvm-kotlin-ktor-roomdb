package com.example.task1.stateData

import com.example.task1.model.User

sealed class StateData {
    object START : StateData()
    object LOADING : StateData()
    data class SUCCESS(val users: List<User>) : StateData()
    data class FAILURE(val message: String) : StateData()
}