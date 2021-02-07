package com.mimoupsa.myevents.ui.event.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.ui.event.list.presentation.EventListViewModel
import com.mimoupsa.myevents.ui.common.adapter.EventListAdapter

class EventListFragment : Fragment() {

    private lateinit var eventListViewModel: EventListViewModel
    private lateinit var recyclerview: RecyclerView
    private val adapter: EventListAdapter by lazy { EventListAdapter(::onEventClicked) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_event_list,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview = view.findViewById(R.id.rv_event_list)
        recyclerview.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerview.adapter = adapter

        eventListViewModel = ViewModelProvider(this).get(EventListViewModel::class.java)
        eventListViewModel.getMoreEvents()
        eventListViewModel.eventsData().observe(viewLifecycleOwner, {events->
            adapter.onItems(events)
            adapter.notifyDataSetChanged()

        })
    }

    private fun onEventClicked(event: Event){

    }
}