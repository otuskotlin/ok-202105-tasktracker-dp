package com.polyakovworkbox.tasktracker.backend.common.models.general

data class Principal(
    var login: String = "",
){

    companion object {
        val NONE = Principal()
    }

}