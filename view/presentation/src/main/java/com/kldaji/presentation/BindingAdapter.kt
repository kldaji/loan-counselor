package com.kldaji.presentation

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.lang.NullPointerException

@BindingAdapter("imageFromUri")
fun bindImageFromUri(view: ImageView, uriString: String) {
    try {
        val uri = Uri.parse(uriString)
        view.setImageURI(uri)
    } catch (e: NullPointerException) {

    }
}
