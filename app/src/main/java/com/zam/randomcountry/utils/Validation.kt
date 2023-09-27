package com.zam.randomcountry.utils

object Validation {
    fun validateNumber(text: String): Boolean {
        return try {
            if (text.isBlank()) {
                true
            }
            else {
                text.toInt() in AppConstants.NUMBER_RANGE
            }
        } catch (e: Exception) {
            false
        }
    }
}