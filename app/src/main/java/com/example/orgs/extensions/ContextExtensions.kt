package com.example.orgs.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.vaiPara(clazz: Class<*>) {
    Intent(this, clazz)
        .apply {
            startActivity(this)
        }
}

fun Context.toast(message: String){
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}