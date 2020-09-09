package com.javierrebollo.basearch.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageButton
import com.javierrebollo.basearch.R
import com.javierrebollo.basearch.utils.extensions.makeGone

class ToolbarPrimaryButton : AppCompatImageButton {
    sealed class Type(val value: Int) {
        object Back : Type(0)
        object Menu : Type(1)
        object None : Type(2)

        companion object {
            fun from(value: Int): Type {
                return when (value) {
                    0 -> Back
                    1 -> Menu
                    2 -> None
                    else -> Back
                }
            }
        }
    }

    var type: Type = Type.Menu
        set(value) {
            field = value

            when (value) {
                is Type.None -> {
                    makeGone()
                }
                else -> {
                    setImageResource(imagesArray[value.value])
                }

            }
        }
    private val imagesArray: Array<Int> = arrayOf(R.drawable.ic_back, R.drawable.ic_hamburger_icon)

    constructor(context: Context) : super(context) {
        initComponent()
        setAllListeners()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        applyAttributes(context, attrs)
        initComponent()
        setAllListeners()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr) {
        applyAttributes(context, attrs)
        initComponent()
        setAllListeners()
    }

    private fun initComponent() {
    }

    private fun setAllListeners() {

    }

    private fun applyAttributes(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ToolbarDefault,
            0, 0
        ).apply {

            try {
                type = Type.from(getInt(R.styleable.ToolbarDefault_primary_button_type, 0))
            } finally {
                recycle()
            }
        }
    }
}