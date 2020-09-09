package com.javierrebollo.basearch.utils.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

fun Spannable.makeClickable(spannable: SpannableString, start: Int, end: Int, color: Int, clickListener: () -> Unit) {
    spannable.setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            clickListener.invoke()
        }

        override fun updateDrawState(drawState: TextPaint) {
            drawState.color = color
            drawState.isUnderlineText = false
        }
    }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}