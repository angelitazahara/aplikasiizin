package com.example.aplikasiizin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.compareTo

class UserDataHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDB.db"
        private const val DATABASE_VERSION = 2

        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"

        private const val TABLE_CUSTOMERS = "customers"
        private const val COLUMN_CUSTOMER_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_ADDRESS = "address"
        private const val COLUMN_EMAILC = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_EMAIL TEXT, " +
                "$COLUMN_PASSWORD TEXT)")

        db.execSQL(createTable)

        val defaultUser = ContentValues().apply {
            put(COLUMN_EMAIL, "user@gmail.com")
            put(COLUMN_PASSWORD, "123456")
        }
        db.insert(TABLE_USERS, null, defaultUser)

        val createCustomerTable = ("CREATE TABLE $TABLE_CUSTOMERS (" +
                "$COLUMN_CUSTOMER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_DATE TEXT, " +
                "$COLUMN_ADDRESS TEXT, " +
                "$COLUMN_EMAILC TEXT)")

        db.execSQL(createCustomerTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CUSTOMERS")
        onCreate(db)
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun insertCustomer(username: String, etDate: String, address: String, email: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, username)
            put(COLUMN_DATE, etDate)
            put(COLUMN_ADDRESS, address)
            put(COLUMN_EMAILC, email)
        }
        val result = db.insert(TABLE_CUSTOMERS, null, values)
        db.close()
        return result != -1L
    }

    fun getAllCustomers(): List<Map<String, String>> {
        val customersList = mutableListOf<Map<String, String>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CUSTOMERS", null)

        if (cursor.moveToFirst()) {
            do {
                val customer = mapOf(
                    "name" to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    "date" to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                    "address" to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                    "email" to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAILC))
                )
                customersList.add(customer)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return customersList
    }

    fun deleteCustomer(email: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_CUSTOMERS, "$COLUMN_EMAILC = ?", arrayOf(email))
        db.close()
        return result > 0
    }

    fun updateCustomer(
        originalEmail: String,
        newName: String,
        newDate: String,
        newAddress: String,
        newEmail: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, newName)
            put(COLUMN_DATE, newDate)
            put(COLUMN_ADDRESS, newAddress)
            put(COLUMN_EMAILC, newEmail)
        }

        val result = db.update(
            TABLE_CUSTOMERS,
            values,
            "$COLUMN_EMAILC = ?",
            arrayOf(originalEmail)
        )

        db.close()
        return result > 0
    }

}



