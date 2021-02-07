package com.mimoupsa.myevents.ui.settings

import java.io.Serializable

interface NoticeDialogListener: Serializable {
    fun onSaveProgress(progress: Int)
}