package com.mimoupsa.myevents.ui.event.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.ui.event.list.presentation.EventListViewModel

class EventListFragment : Fragment() {

    private lateinit var eventListViewModel: EventListViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_event_list,container,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventListViewModel = ViewModelProvider(this).get(EventListViewModel::class.java)
        eventListViewModel.eventsData().observe(this, Observer {

        })
    }
}