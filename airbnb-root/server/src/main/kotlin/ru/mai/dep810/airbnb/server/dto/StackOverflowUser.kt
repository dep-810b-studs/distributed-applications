package ru.mai.dep810.airbnb.server.dto

import java.util.*

data class StackOverflowUser (
        val DisplayName: String,
        val CreationDate: Date)

fun StackOverflowUser.toClientDto() =
        ClientDto(this.DisplayName,this.CreationDate)