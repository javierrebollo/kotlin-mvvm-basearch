package com.javierrebollo.basearch.utils.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.changeFocusWhenPressDone(viewForRequest: View) {
    setOnEditorActionListener { v, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (viewForRequest !is EditText) {
                val imm: InputMethodManager =
                    v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
            v.clearFocus()
            viewForRequest.requestFocus()
        }
        return@setOnEditorActionListener false
    }
}