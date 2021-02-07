package com.mimoupsa.myevents.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mimoupsa.myevents.R
import kotlinx.android.synthetic.main.view_seekbar.view.*

class SeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var mInflater: LayoutInflater
    val mProgress =  MutableLiveData<Int>()

    init {
        orientation = HORIZONTAL

        mInflater = LayoutInflater.from(context)
        mInflater.inflate(R.layout.view_seekbar, this, true)


        val attrsSet = intArrayOf(android.R.attr.text)

        context.withStyledAttributes(attrs, attrsSet) {
            customSeekBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        mProgress.value = progress
                        txtRadiusSeekBar.text = context.getString(R.string.radius_km_seekbar,progress.toString())
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }

                }
            )
        }
    }

    fun setBounds(min: Int,max:Int){
        customSeekBar.max = max
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            customSeekBar.min = min
        }
    }

    fun setProgress(progress: Int){
        customSeekBar.progress = progress
    }


}