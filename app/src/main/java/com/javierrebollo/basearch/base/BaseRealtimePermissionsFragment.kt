package com.javierrebollo.basearch.base

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding


abstract class BaseRealtimePermissionsFragment<DB : ViewDataBinding, VM : BaseViewModel, VMF : BaseViewModelFactory<VM>> :
    BaseFragment<DB, VM, VMF>() {

    private var allPermissionsSuccessCallback: (() -> Unit)? = null
    private var permissionsDeniedCallback: ((Array<String>) -> Unit)? = null
    private var neverAskAgainCallback: ((String) -> Unit)? = null

    fun requestRealtimePermissions(
        permissions: Array<String>,
        successCallback: () -> Unit,
        deniedCallback: ((Array<String>) -> Unit)? = null,
        neverAskAgainCallback: ((String) -> Unit)? = null
    ) {
        this.allPermissionsSuccessCallback = successCallback
        this.permissionsDeniedCallback = deniedCallback
        this.neverAskAgainCallback = neverAskAgainCallback

        if (areAllPermissionsGranted(permissions)) {
            allPermissionsSuccessCallback?.invoke()

        } else {
            requestPermissions(
                fetchNotGrantedPermissions(permissions),
                REQUEST_MULTIPLE_PERMISSIONS_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_MULTIPLE_PERMISSIONS_CODE) {
            if (areAllPermissionsGranted(permissions)) {
                allPermissionsSuccessCallback?.invoke()

            } else {

                val notGrantedPermissions = fetchNotGrantedPermissions(permissions)

                var permissionNeverAskAgain: String? = null
                var index: Int = 0

                while (index < notGrantedPermissions.size && permissionNeverAskAgain == null) {
                    if (!shouldShowRequestPermissionRationale(notGrantedPermissions[index])) {
                        permissionNeverAskAgain = notGrantedPermissions[index]
                    }
                    index++
                }

                if (permissionNeverAskAgain != null) {
                    neverAskAgainCallback?.invoke(permissionNeverAskAgain)

                } else {
                    permissionsDeniedCallback?.invoke(notGrantedPermissions)
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun fetchNotGrantedPermissions(permissions: Array<String>): Array<String> {
        val notGrantedPermissions = permissions.filter { !isPermissionGranted(it) }
        return notGrantedPermissions.toTypedArray()
    }

    private fun areAllPermissionsGranted(permissions: Array<String>): Boolean {
        return permissions.all { isPermissionGranted(it) }
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
    }

    fun showNeverAskAgainDialog(message: String) {
//        showAlertDialog(
//            title = R.string.permission_dialog_title.getString(),
//            message = message,
//            cancelText = R.string.permission_dialog_btn_cancel.getString(),
//            agreeText = R.string.permission_dialog_btn_accept.getString(),
//            agreeAction = {
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                val uri: Uri = Uri.fromParts("package", requireActivity().packageName, null)
//                intent.data = uri
//                startActivity(intent)
//            }
//        )
    }

    companion object {
        private const val REQUEST_MULTIPLE_PERMISSIONS_CODE = 2143
    }

}