package org.androdevlinux.test.ktx

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import org.androdevlinux.test.databinding.CustomPopupViewBinding

fun Context.showCustomPopup(message: String, messageDuration: Int = Toast.LENGTH_SHORT) {
    Toast(this).apply {
        setGravity(Gravity.CENTER, 0 , 0)
        val customView  = CustomPopupViewBinding.inflate(LayoutInflater.from(this@showCustomPopup)).apply {
            tvPopupMsg.text = message
            root.background.alpha = 175
        }
        view = customView.root
        duration = messageDuration
    }.show()
}