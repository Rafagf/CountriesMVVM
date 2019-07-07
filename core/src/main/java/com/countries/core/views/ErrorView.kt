package com.countries.core.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.countries.core.R
import com.countries.core.readAttributes
import kotlinx.android.synthetic.main.error_view.view.*

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.error_view, this)
        orientation = VERTICAL
        gravity = Gravity.CENTER
        attrs.let {
            readAttributes(it, R.styleable.ErrorView, defStyleAttr) {
                CustomAttributes(
                    icon = getDrawable(R.styleable.ErrorView_icon) ?: context.getDrawable(R.drawable.ic_refresh),
                    title = getString(R.styleable.ErrorView_title) ?: context.getString(R.string.error_header),
                    message = getString(R.styleable.ErrorView_message) ?: context.getString(R.string.error_body)
                )
            }.apply {
                refreshButton.setImageDrawable(icon)
                headerTextView.text = title
                bodyTextView.text = message
            }
        }
    }

    fun onClick(action: () -> Unit) {
        refreshButton.setOnClickListener {
            action.invoke()
        }
    }
}

private data class CustomAttributes(
    val icon: Drawable,
    val title: String,
    val message: String
)