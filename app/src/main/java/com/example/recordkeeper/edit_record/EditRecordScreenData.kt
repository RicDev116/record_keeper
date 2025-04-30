package com.example.recordkeeper.edit_record

import java.io.Serializable

data class EditRecordScreenData (
    val record: String?,
    val sharedPreferencesName: String,
    val recordFieldHint: String,
):Serializable