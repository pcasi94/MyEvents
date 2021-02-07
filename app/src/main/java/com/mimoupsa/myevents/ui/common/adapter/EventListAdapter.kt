package com.mimoupsa.myevents.ui.common.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.domain.model.EventList
import com.mimoupsa.myevents.ui.extensions.loadImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventListAdapter(
    private val onItemClicked: (Event) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var events:MutableList<Event> = mutableListOf()

    fun onItems(e: EventList){
        this.events = e.list.toMutableList()
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
        private val txtEventCity:TextView = itemView.findViewById(R.id.txtEventCity)


        fun bindViewHolder(event: Event){
            event.images?.first()?.url?.let {
                image.loadImage(itemView.context,it)
            }
            txtEventName.text = event.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                event.date?.let {
                    txtEventDate.text = LocalDate.parse(it, DateTimeFormatter.ISO_DATE_TIME).toString()
                }
            }
            txtEventEmplacement.text = event.emplacement
            txtEventCity.text = event.city
        }

        init {
            itemView.setOnClickListener { onItemClicked(events[adapterPosition]) }
        }
    }
}