package com.example.gureevkurilenko

import android.provider.BaseColumns

object DBContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_PASS = "pass"
        const val COLUMN_FULL_NAME = "full_name"
        const val COLUMN_BIRTH_DATE = "birth_date"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_AVATAR_URI = "avatar_uri"
        const val COLUMN_IS_ADMIN = "is_admin"
    }
}