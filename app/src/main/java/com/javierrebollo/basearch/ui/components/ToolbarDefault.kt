package com.javierrebollo.basearch.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import com.javierrebollo.basearch.R
import com.javierrebollo.basearch.databinding.ToolbarDefaultBinding
import com.javierrebollo.basearch.utils.SingleLiveEvent
import com.javierrebollo.basearch.utils.extensions.getColor
import com.javierrebollo.basearch.utils.extensions.makeGone
import com.javierrebollo.basearch.utils.extensions.makeVisible

class ToolbarDefault : ConstraintLayout {
    private lateinit var binding: ToolbarDefaultBinding
    var onPrimaryButtonPressed: SingleLiveEvent<ToolbarPrimaryButton.Type> = SingleLiveEvent()
    var toolbarData: ToolbarData
        set(value) {
            binding.btnPrimary.type = value.primaryButton
            binding.tvTitle.text = value.title
        }
        get() = ToolbarData(
            primaryButton = binding.btnPrimary.type,
            title = binding.tvTitle.text.toString()
        )

    constructor(context: Context) : super(context) {
        initComponent()
        setAllListeners()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initComponent()
        setAllListeners()
        applyAttributes(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initComponent()
        setAllListeners()
        applyAttributes(context, attrs)
    }


    private fun applyAttributes(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ToolbarDefault,
            0, 0
        ).apply {

            try {
                binding.btnPrimary.type =
                    ToolbarPrimaryButton.Type.from(
                        getInt(
                            R.styleable.ToolbarDefault_primary_button_type,
                            0
                        )
                    )
            } finally {
                recycle()
            }
        }
    }

    private fun initComponent() {
        binding = ToolbarDefaultBinding.inflate(
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            this,
            true
        )
        binding.btnAction.makeGone()
    }

    private fun setAllListeners() {
        binding.btnPrimary.apply {
            setOnClickListener {
                onPrimaryButtonPressed.value = type
            }
        }
    }

    fun setAction(actionText: String, onClick: (() -> Unit)?) {
        binding.btnAction.makeVisible()
        binding.btnAction.text = actionText
        binding.btnAction.isEnabled = (onClick != null)
        binding.btnAction.setOnClickListener { onClick?.invoke() }
    }

    fun removeAction() {
        binding.btnAction.makeGone()
        binding.btnAction.text = ""
        binding.btnAction.setOnClickListener(null)
    }

    data class ToolbarData(
        var primaryButton: ToolbarPrimaryButton.Type = ToolbarPrimaryButton.Type.Back,
        var title: String = ""
    )
}