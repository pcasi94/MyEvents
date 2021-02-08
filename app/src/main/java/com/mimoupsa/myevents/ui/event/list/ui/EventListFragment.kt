package com.mimoupsa.myevents.ui.event.list.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.ui.MainActivity
import com.mimoupsa.myevents.ui.event.list.presentation.EventListViewModel
import com.mimoupsa.myevents.ui.common.adapter.EventListAdapter

class EventListFragment : Fragment() {

    private lateinit var eventListViewModel: EventListViewModel
    private lateinit var recyclerView: RecyclerView
    private val adapter: EventListAdapter by lazy { EventListAdapter(::onFavoritesClicked,::onMoreInfoClicked, ::onLastItemReached) }

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
        eventListViewModel.getEvents()
        eventListViewModel.eventsData().observe(viewLifecycleOwner, {events->
            adapter.onItems(events)
            adapter.notifyDataSetChanged()

        })

        eventListViewModel.getInsertResult().observe(viewLifecycleOwner,{
            if(it) Toast.makeText(requireContext(),getString(R.string.insert_success),Toast.LENGTH_SHORT).show()
            else Toast.makeText(requireContext(),R.string.insert_exists_error,Toast.LENGTH_SHORT).show()
        })

        eventListViewModel.url.observe(viewLifecycleOwner,{
            openUrl(it)
        })

        eventListViewModel.error.observe(viewLifecycleOwner,{
            showError(it.title,it.message)
        })


    }

    private fun onFavoritesClicked(event: Event){
        eventListViewModel.saveToFavorites(event)
    }

    private fun onMoreInfoClicked(event: Event){
        eventListViewModel.openUrl(event)
    }

    private fun onLastItemReached(){
        eventListViewModel.getMoreEvents()
    }

    private fun bindView(view: View){
        recyclerView = view.findViewById(R.id.rv_event_list)
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu,menu)
        val itemMenuSettings = menu.findItem(R.id.itemMenuSettings)
        itemMenuSettings.isVisible = false

        val itemMenuSearch = menu.findItem(R.id.itemMenuSearch)
        val search = itemMenuSearch?.actionView as SearchView
        search.queryHint = getString(R.string.search_hint)


        search.setOnCloseListener {
            eventListViewModel.updateKeyWord(null)
            false
        }
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                eventListViewModel.updateKeyWord(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
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

    private fun openUrl(url: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun showError(title: String,message: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext(), R.style.Theme_MaterialComponents_DayNight_Dialog_Alert)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.string_ok)) { d, _ ->
            d.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }



}