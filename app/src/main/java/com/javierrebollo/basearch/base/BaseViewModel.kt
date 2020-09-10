package com.javierrebollo.basearch.base

import android.content.Intent
import android.os.Parcelable
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import com.javierrebollo.basearch.ui.components.ToolbarDefault
import com.javierrebollo.basearch.utils.NavigationCommand
import com.javierrebollo.basearch.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    protected val TAG: String = this::class.java.simpleName
    private val _toolbarData: MutableLiveData<ToolbarDefault.ToolbarData> = MutableLiveData()
    val toolbarData
        get() = _toolbarData

    private val _navigation: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val navigation
        get() = _navigation

    private val _errorNotifier: SingleLiveEvent<ErrorType> = SingleLiveEvent()
    val errorNotifier
        get() = _errorNotifier

    private val _activityResultOk: SingleLiveEvent<Pair<Int, Intent?>> = SingleLiveEvent()
    val activityResultOk
        get() = _activityResultOk

    private val _activityResultCancel: SingleLiveEvent<Pair<Int, Intent?>> = SingleLiveEvent()
    val activityResultCancel
        get() = _activityResultCancel

    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _forceKeyboardState: SingleLiveEvent<KeyboardState> = SingleLiveEvent()
    val forceKeyboardState
        get() = _forceKeyboardState

    private val _textToShare: SingleLiveEvent<String> = SingleLiveEvent()
    val textToShare
        get() = _textToShare


    @UiThread
    fun goTo(direction: NavDirections) {
        _navigation.value = NavigationCommand.To(direction)
    }

    @UiThread
    fun goBack() {
        _navigation.value = NavigationCommand.Back
    }

    @UiThread
    fun goBackTo(directionId: Int) {
        _navigation.value = NavigationCommand.BackTo(directionId)
    }

    @UiThread
    fun goBackWithResult(resultKey: String, result: Parcelable) {
        _navigation.value = NavigationCommand.BackWithResult(resultKey, result)
    }

    @UiThread
    fun notifyError(error: ErrorType) {
        _errorNotifier.value = error
    }

    @UiThread
    fun showLoading() {
        _loadingState.value = true
    }

    @UiThread
    fun hideLoading() {
        _loadingState.value = false
    }

    @UiThread
    fun forceCloseKeyboard() {
        _forceKeyboardState.value = KeyboardState.HIDE
    }

    @UiThread
    fun forceOpenKeyboard() {
        _forceKeyboardState.value = KeyboardState.SHOW
    }

    open fun loadData() {

    }
}

enum class KeyboardState {
    HIDE, SHOW
}

sealed class ErrorType {
    data class GenericError(val message: String) : ErrorType()
    data class LoginFail(val message: String) : ErrorType()
}

abstract class BaseViewModelFactory<VM : BaseViewModel> :
    ViewModelProvider.NewInstanceFactory() {

    abstract fun buildViewModel(): VM

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return buildViewModel() as T
    }
}