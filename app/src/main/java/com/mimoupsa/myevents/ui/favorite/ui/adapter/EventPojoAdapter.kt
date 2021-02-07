package com.mimoupsa.myevents.ui.favorite.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.data.local.db.EventPOJO
import com.mimoupsa.myevents.ui.extensions.loadImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EventPojoAdapter(
    private val onDeleteFromFavoritesClicked: (EventPOJO) -> Unit = { },
    private val onMoreInfoClicked: (EventPOJO) -> Unit = { }
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var events: MutableList<EventPOJO> = mutableListOf()

    fun onItems(e: List<EventPOJO>) {
        this.events = e.toMutableList()
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? EventsVH?)?.bindViewHolder(event = events[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventsVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_event,
                parent,
                false
            )
        )
    }

    inner class EventsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.ivEventImage)
        private val txtEventName: TextView = itemView.findViewById(R.id.txtEventName)
        private val txtEventDate: TextView = itemView.findViewById(R.id.txtEventDate)
        private val txtEventEmplacement: TextView = itemView.findViewById(R.id.txtEventEmplacement)
        private val txtEventCity: TextView = itemView.findViewById(R.id.txtEventCity)
        private val btnFavorites: Button = itemView.findViewById(R.id.btnFavorites)
        private val btnMoreInfo: Button = itemView.findViewById(R.id.btnMoreInfo)


        fun bindViewHolder(event: EventPOJO) {

            event.image.let {
                image.loadImage(itemView.context, it)
            }
            btnFavorites.text = itemView.context.getString(R.string.button_delete_from_favorites)
            txtEventName.text = event.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (event.date.isNotBlank()) {
                    txtEventDate.text =
                        LocalDate.parse(event.date, DateTimeFormatter.ISO_DATE_TIME).toString()
                }
            }
            txtEventEmplacement.text = event.emplacement
            txtEventCity.text = event.city
        }

        init {
            btnFavorites.setOnClickListener { onDeleteFromFavoritesClicked(events[adapterPosition]) }
            btnMoreInfo.setOnClickListener { onMoreInfoClicked(events[adapterPosition]) }
        }
    }
}