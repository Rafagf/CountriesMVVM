package com.countries.listofcountries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.countries.core.loadImage
import kotlinx.android.synthetic.main.country_card.view.*

class CountryListAdapter(
    val list: MutableList<CountryListModel> = mutableListOf(),
    val onClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_card, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val country = list[position]
        (holder as CountryViewHolder).itemView.apply {
            nameTextView.text = country.name
            continentTextView.text = country.continent
            populationTextView.text = country.population
            context.loadImage(country.flagUrl, flagImageView)
            setOnClickListener { onClick(country.name) }
        }
    }

    override fun getItemCount() = list.size

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view)
}