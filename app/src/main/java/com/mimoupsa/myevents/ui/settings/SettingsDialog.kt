package com.mimoupsa.myevents.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.ui.customviews.SeekBar

class SettingsDialog: DialogFragment() {

    private lateinit var listener: NoticeDialogListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,
            android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen)
        isCancelable = true
    }

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

        seekBar.setBounds(MINIMUM_RADIUS, MAXIMUM_RADIUS)
        seekBar.setStringResource(R.string.radius_km_seekbar)


    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host fragment implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    companion object{
        const val MAXIMUM_RADIUS = 250
        const val MINIMUM_RADIUS = 1

    }

}