package com.mimoupsa.myevents.ui.customviews

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.mimoupsa.myevents.R

class ExpandableTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    AppCompatTextView(context!!, attrs, defStyle) {
    var isCollapsed = true // Show short version as default.
        private set
    private var mCollapsedHeight = 0
    private var mTextHeightWithMaxLines = 0
    private val mMaxCollapsedLines: Int
    private val mAnimationDuration: Int
    private var originalText: CharSequence? = null
    private var ellipsizedText: CharSequence? = null
    private var originalTextFetched = false

    /* Listener for callback */
    private var mListener: OnExpandStateChangeListener? =
        null
    private var animator: Animator? = null
    private val animatorListener: Animator.AnimatorListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator, isReverse: Boolean) {
            mListener?.onExpandStateChanged(this@ExpandableTextView, isCollapsed)
        }
    }

    fun toggle() {
        if (isCollapsed) {
            expand()
        } else {
            collapse()
        }
    }

    fun expand() {
        if (animator != null && animator!!.isRunning) {
            return
        }
        toOriginalText()
        isCollapsed = false
        runAnimation()
    }

    fun collapse() {
        if (animator != null && animator!!.isRunning) {
            return
        }
        toElipsize()
        isCollapsed = true
        runAnimation()
    }

    private fun runAnimation() {
        animator = createAnimation(isCollapsed)
        animator!!.duration = mAnimationDuration.toLong()
        animator!!.addListener(animatorListener)
        animator!!.interpolator =
            if (isCollapsed) DecelerateInterpolator() else AccelerateInterpolator()
        animator!!.start()
    }

    private fun createAnimation(collapsed: Boolean): Animator {
        return if (collapsed) {
            ObjectAnimator.ofInt(this, "height", height, mCollapsedHeight)
        } else {
            ObjectAnimator.ofInt(
                this, "height", height,
                height + mTextHeightWithMaxLines - height
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        maxLines = Int.MAX_VALUE
        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // If the text fits in collapsed mode, we are done.
        if (lineCount <= mMaxCollapsedLines) {
            return
        }
        // Saves the text height w/ max lines
        mTextHeightWithMaxLines = realTextViewHeight

        // Doesn't fit in collapsed mode. Collapse text view as needed.
        if (isCollapsed) {
            maxLines = mMaxCollapsedLines
        }

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (isCollapsed) {
            // Saves the collapsed height of this ViewGroup
            mCollapsedHeight = measuredHeight
        }
    }

    fun setOnExpandStateChangeListener(listener: OnExpandStateChangeListener?) {
        mListener = listener
    }

    private val realTextViewHeight: Int
        private get() {
            val textHeight = layout.getLineTop(lineCount)
            val padding = compoundPaddingTop + compoundPaddingBottom
            return textHeight + padding
        }

    private fun saveElipsedText() {
        val startPos = this.layout.getLineStart(0)
        val endPos = this.layout.getLineEnd(mMaxCollapsedLines - 1)
        ellipsizedText = originalText!!.subSequence(startPos, endPos)
        ellipsizedText =
            ellipsizedText!!.subSequence(0, ellipsizedText!!.length - ELIPSIZE_POINTS.length)
                .toString() + ELIPSIZE_POINTS
    }

    private fun toOriginalText() {
        this.text = originalText
    }

    private fun toElipsize() {
        this.text = ellipsizedText
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (!text.toString().isEmpty()) {
            if (!originalTextFetched) {
                originalTextFetched = true
                originalText = text
                if (lineCount > mMaxCollapsedLines) {
                    saveElipsedText()
                    toElipsize()
                }
            }
        }
    }

    interface OnExpandStateChangeListener {
        /**
         * Called when the expand/collapse animation has been finished
         *
         * @param textView   - TextView being expanded/collapsed
         * @param isExpanded - true if the TextView has been expanded
         */
        fun onExpandStateChanged(textView: TextView?, isExpanded: Boolean)
    }

    companion object {
        /* The default number of lines */
        private const val MAX_COLLAPSED_LINES = 3

        /* The default animation duration */
        private const val DEFAULT_ANIM_DURATION = 300
        private const val ELIPSIZE_POINTS = "..."
    }

    init {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        mMaxCollapsedLines =
            typedArray.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES)
        mAnimationDuration =
            typedArray.getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION)
        typedArray.recycle()
    }
}