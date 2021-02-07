package com.mimoupsa.myevents.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.ui.customviews.SeekBar

class SettingsDialog(private val listener: NoticeDialogListener,private var progress: Int): BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_settings,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seekBar = view.findViewById<SeekBar>(R.id.customSeekBarSettings)
        val btnClose = view.findViewById<Button>(R.id.btnSettingsClose)
        val btnSave = view.findViewById<Button>(R.id.btnSettingsSave)

        seekBar.setBounds(MINIMUM_RADIUS, MAXIMUM_RADIUS)
        seekBar.setProgress(progress)

        seekBar.mProgress.observe(viewLifecycleOwner,{
            progress = it
        })

        btnClose.setOnClickListener { dismiss() }
        btnSave.setOnClickListener {
            listener.onSaveProgress(progress)
            dismiss()
        }
    }


    companion object{
        const val MAXIMUM_RADIUS = 250
        const val MINIMUM_RADIUS = 1

    }

}