package com.example.gureevkurilenko

import android.app.Activity
import android.os.Bundle
import android.content.ContentValues.TAG
import android.util.Log

class NewActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "$TAG onCreate")

        setContentView(R.layout.activity_helloact)
    }
}