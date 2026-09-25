package com.example.gureevkurilenko

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Users.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val create = "CREATE TABLE ${DBContract.UserEntry.TABLE_NAME} (" +
                "${DBContract.UserEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DBContract.UserEntry.COLUMN_LOGIN} TEXT, " +
                "${DBContract.UserEntry.COLUMN_PASS} TEXT, " +
                "${DBContract.UserEntry.COLUMN_FULL_NAME} TEXT, " +
                "${DBContract.UserEntry.COLUMN_BIRTH_DATE} TEXT, " +
                "${DBContract.UserEntry.COLUMN_GENDER} TEXT, " +
                "${DBContract.UserEntry.COLUMN_AVATAR_URI} TEXT, " +
                "${DBContract.UserEntry.COLUMN_IS_ADMIN} INTEGER)"
        db.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBContract.UserEntry.TABLE_NAME}")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val values = ContentValues().apply {
            put(DBContract.UserEntry.COLUMN_LOGIN, user.login)
            put(DBContract.UserEntry.COLUMN_PASS, user.pass)
            put(DBContract.UserEntry.COLUMN_FULL_NAME, user.fullName)
            put(DBContract.UserEntry.COLUMN_BIRTH_DATE, user.birthDate)
            put(DBContract.UserEntry.COLUMN_GENDER, user.gender)
            put(DBContract.UserEntry.COLUMN_AVATAR_URI, user.avatarUri)
            put(DBContract.UserEntry.COLUMN_IS_ADMIN, if (user.isAdmin) 1 else 0)
        }
        return writableDatabase.insert(DBContract.UserEntry.TABLE_NAME, null, values)
    }

    fun getUserByLogin(login: String): User? {
        val cursor = readableDatabase.query(
            DBContract.UserEntry.TABLE_NAME, null,
            "${DBContract.UserEntry.COLUMN_LOGIN} = ?", arrayOf(login),
            null, null, null
        )
        var user: User? = null
        if (cursor.moveToFirst()) user = cursorToUser(cursor)
        cursor.close()
        return user
    }

    fun getUserById(id: Int): User? {
        val cursor = readableDatabase.query(
            DBContract.UserEntry.TABLE_NAME, null,
            "${DBContract.UserEntry.COLUMN_ID} = ?", arrayOf(id.toString()),
            null, null, null
        )
        var user: User? = null
        if (cursor.moveToFirst()) user = cursorToUser(cursor)
        cursor.close()
        return user
    }

    fun getAllUsers(): List<User> {
        val list = mutableListOf<User>()
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${DBContract.UserEntry.TABLE_NAME}", null
        )
        if (cursor.moveToFirst()) {
            do { list.add(cursorToUser(cursor)) } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun getUserCount(): Int {
        val cursor = readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM ${DBContract.UserEntry.TABLE_NAME}", null
        )
        var count = 0
        if (cursor.moveToFirst()) count = cursor.getInt(0)
        cursor.close()
        return count
    }

    fun setAdmin(id: Int, isAdmin: Boolean) {
        val values = ContentValues().apply {
            put(DBContract.UserEntry.COLUMN_IS_ADMIN, if (isAdmin) 1 else 0)
        }
        writableDatabase.update(
            DBContract.UserEntry.TABLE_NAME, values,
            "${DBContract.UserEntry.COLUMN_ID} = ?", arrayOf(id.toString())
        )
    }

    private fun cursorToUser(cursor: Cursor): User = User(
        id = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_ID)),
        login = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_LOGIN)),
        pass = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_PASS)),
        fullName = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_FULL_NAME)),
        birthDate = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_BIRTH_DATE)),
        gender = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_GENDER)),
        avatarUri = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_AVATAR_URI)),
        isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.UserEntry.COLUMN_IS_ADMIN)) == 1
    )
}