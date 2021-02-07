package com.mimoupsa.myevents.ui.event.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private lateinit var recyclerView: RecyclerView
    private val adapter: EventListAdapter by lazy { EventListAdapter(::onFavoritesClicked,::onMoreInfoClicked) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_event_list,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)

        eventListViewModel = ViewModelProvider(this).get(EventListViewModel::class.java)
        eventListViewModel.getMoreEvents()
        eventListViewModel.eventsData().observe(viewLifecycleOwner, {events->
            adapter.onItems(events)
            adapter.notifyDataSetChanged()

        })

        eventListViewModel.getInsertResult().observe(viewLifecycleOwner,{
            if(it) Toast.makeText(requireContext(),"Se ha guardado correctamente!",Toast.LENGTH_SHORT).show()
            else Toast.makeText(requireContext(),"Ya tienes guardado este evento en favoritos!",Toast.LENGTH_SHORT).show()
        })
    }

    private fun onFavoritesClicked(event: Event){
        eventListViewModel.saveToFavorites(event)
    }

    private fun onMoreInfoClicked(event: Event){

    }

    private fun bindView(view: View){
        recyclerView = view.findViewById(R.id.rv_event_list)
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter
    }
}