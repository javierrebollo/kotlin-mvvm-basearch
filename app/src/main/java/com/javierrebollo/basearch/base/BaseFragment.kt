package com.javierrebollo.basearch.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.javierrebollo.basearch.R
import com.javierrebollo.basearch.ui.components.ToolbarDefault
import com.javierrebollo.basearch.ui.components.ToolbarPrimaryButton
import com.javierrebollo.basearch.utils.NavigationCommand
import com.javierrebollo.basearch.utils.Tracker

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel, VMF : BaseViewModelFactory<VM>> :
    Fragment() {
    protected val TAG: String = this::class.java.simpleName
    private var originalInputMode: Int? = null
    open var softInputAdjustResize: Boolean = false
    protected lateinit var binding: DB
    abstract val viewModel: VM
    abstract fun buildViewModelFactory(): VMF
    abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): DB

    abstract fun initComponents()
    abstract fun addListeners()
    abstract fun addObservers()
    abstract fun errorHandler(errorType: ErrorType)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initComponents()
        addListeners()
        addObservers()

        viewModel.errorNotifier.observe(viewLifecycleOwner, this::errorHandler)
        viewModel.forceKeyboardState.observe(viewLifecycleOwner) { keyboardState ->
            keyboardState?.let { safeKeyboardState ->
                when (safeKeyboardState) {
                    KeyboardState.SHOW -> openKeyboard()
                    KeyboardState.HIDE -> closeKeyboard()
                }
            }
        }

        viewModel.textToShare.observe(viewLifecycleOwner) {
            shareText(it)
        }

        binding.root.findViewById<ToolbarDefault>(R.id.toolbar)?.apply {
            onPrimaryButtonPressed.observe(viewLifecycleOwner) {
                if (it is ToolbarPrimaryButton.Type.Back) {
                    requireActivity().onBackPressed()
                }
            }
            viewModel.toolbarData.observe(viewLifecycleOwner) {
                toolbarData = it
            }

        }

        modifySoftInput()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner, Observer<NavigationCommand> { command ->
            when (command) {
                is NavigationCommand.To ->
                    findNavController().navigate(command.directions)
                is NavigationCommand.BackTo ->
                    findNavController().navigate(command.destinationId)
                is NavigationCommand.Back ->
                    findNavController().popBackStack().also {
                        if (!it) {
                            requireActivity().finish()
                        }
                    }
                is NavigationCommand.ToRoot ->
                    TODO()
                is NavigationCommand.WithArgs ->
                    TODO()
                is NavigationCommand.BackWithResult -> {
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        command.resultKey,
                        command.result
                    )
                    findNavController().popBackStack().also {
                        if (!it) {
                            requireActivity().finish()
                        }
                    }
                }
            }
        })

        viewModel.loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Tracker.logDebug("TAG", "requestCode : $requestCode")
        Tracker.logDebug("TAG", "resultCode : $resultCode")
        when (resultCode) {
            Activity.RESULT_OK -> {
                Tracker.logDebug(TAG, "RESULT_OK")
                viewModel.activityResultOk.value = Pair(requestCode, data)
            }
            Activity.RESULT_CANCELED -> {
                Tracker.logDebug(TAG, "RESULT_CANCEL")
                viewModel.activityResultCancel.value = Pair(requestCode, data)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun closeKeyboard() {
        activity?.let { safeActivity ->
            val imm: InputMethodManager =
                safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

    private fun openKeyboard() {
        activity?.let { safeActivity ->
            val imm: InputMethodManager =
                safeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        restoreSoftInput()
    }

    private fun restoreSoftInput() {
        originalInputMode?.let {
            activity?.window?.setSoftInputMode(it)
        }
    }

    private fun modifySoftInput() {
        if (softInputAdjustResize) {
            originalInputMode = activity?.window?.attributes?.softInputMode

            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            Tracker.logDebug(
                TAG,
                "Setting softInputMode as adjustResize: ${WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE}"
            )
            Tracker.logDebug(
                TAG,
                "Current softInputMode as adjustResize: ${activity?.window?.attributes?.softInputMode}"
            )
        }
    }

    private fun shareText(textToShare: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToShare)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}