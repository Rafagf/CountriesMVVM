package com.countries.detailedcountry

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.countries.ui.gone
import com.countries.ui.visible
import kotlinx.android.synthetic.main.border_view.view.*

class BorderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.border_view, this)
    }

    fun bind(border: BorderModel, action: () -> Unit) {
        countryTextView.text = border.name
        when (border.capital == null) {
            true -> capitalTextView.gone()
            false -> {
                capitalTextView.text = border.capital
                capitalTextView.visible()
            }
        }

        setOnClickListener { action.invoke() }
    }
}