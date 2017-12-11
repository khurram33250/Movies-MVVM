package com.example.hasham.movies_mvvm.util

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.TextView
import android.widget.Toast

/**
 * Developed by Hasham.Tahir on 1/16/2017.
 */

object HAlert {

    fun showAlertDialog(context: Activity, title: String, message: String) {

        val alert = AlertDialog.Builder(context)
        alert.setTitle(title)
        alert.setMessage(message)
        alert.setPositiveButton("OK") { dialog, which -> dialog.dismiss() }
        alert.show()
    }

    fun showAlertDialog(context: Activity, title: String, message: String, listener: DialogInterface.OnClickListener) {

        val alert = AlertDialog.Builder(context)
        alert.setTitle(title)
        alert.setMessage(message)
        alert.setPositiveButton("OK", listener)
        alert.setCancelable(false)
        alert.show()
    }

    fun showSnackBar(view: View, msg: String) {
        //        findViewById(android.R.id.content) item_spinner_hint
        try {
            val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
            val snackBarView = snackbar.view
            val tv = snackBarView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
            tv.setTextColor(Color.WHITE)
            snackbar.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showToast(context: Context, msg: String) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context, msg: String, length: Int) {

        Toast.makeText(context, msg, length).show()
    }
}
