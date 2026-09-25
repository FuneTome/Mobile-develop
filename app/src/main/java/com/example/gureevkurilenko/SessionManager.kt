package com.example.gureevkurilenko

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveUser(id: Int, login: String, isAdmin: Boolean) {
        prefs.edit()
            .putInt("user_id", id)
            .putString("user_login", login)
            .putBoolean("user_is_admin", isAdmin)
            .apply()
    }

    fun getUserId(): Int = prefs.getInt("user_id", -1)
    fun getUserLogin(): String? = prefs.getString("user_login", null)
    fun isAdmin(): Boolean = prefs.getBoolean("user_is_admin", false)

    fun logout() {
        prefs.edit()
            .remove("user_id")
            .remove("user_login")
            .remove("user_is_admin")
            .apply()
    }

    fun setTheme(mode: String) = prefs.edit().putString("theme", mode).apply()
    fun getTheme(): String = prefs.getString("theme", "system") ?: "system"
}