package com.mimoupsa.myevents.ui.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.data.local.db.EventPOJO
import com.mimoupsa.myevents.ui.MainActivity
import com.mimoupsa.myevents.ui.favorite.presentation.FavoritesViewModel
import com.mimoupsa.myevents.ui.favorite.ui.adapter.EventPojoAdapter

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel

    private val adapter: EventPojoAdapter by lazy { EventPojoAdapter(::onDeleteFromFavoritesClicked, ::onMoreInfoClicked) }

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorites, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_favorite)

        bindView(view)
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        favoritesViewModel.eventDb.observe(viewLifecycleOwner,{
            adapter.onItems(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun bindView(view: View){
        recyclerView = view.findViewById(R.id.rv_favorites)
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter
    }

    private fun onDeleteFromFavoritesClicked(event: EventPOJO){
        favoritesViewModel.deleteFromFavorites(event)
    }

    private fun onMoreInfoClicked(event: EventPOJO){

    }
}