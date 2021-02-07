package com.mimoupsa.myevents.ui.event.list.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.ui.MainActivity
import com.mimoupsa.myevents.ui.event.list.presentation.EventListViewModel
import com.mimoupsa.myevents.ui.common.adapter.EventListAdapter
import com.mimoupsa.myevents.ui.settings.NoticeDialogListener
import com.mimoupsa.myevents.ui.settings.SettingsDialog

class EventListFragment : Fragment() {

    private lateinit var eventListViewModel: EventListViewModel
    private lateinit var recyclerView: RecyclerView
    private val adapter: EventListAdapter by lazy { EventListAdapter(::onFavoritesClicked,::onMoreInfoClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_event_list,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)

        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_event)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu,menu)
        val itemMenu = menu.findItem(R.id.itemMenuSettings)
        itemMenu.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemMenuSearch -> {
                return true
            }
        }
        return false
    }



}