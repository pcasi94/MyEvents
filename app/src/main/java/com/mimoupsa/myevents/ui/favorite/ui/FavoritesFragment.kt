package com.mimoupsa.myevents.ui.favorite.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.data.local.db.EventPOJO
import com.mimoupsa.myevents.ui.MainActivity
import com.mimoupsa.myevents.ui.extensions.gone
import com.mimoupsa.myevents.ui.extensions.visible
import com.mimoupsa.myevents.ui.favorite.presentation.FavoritesViewModel
import com.mimoupsa.myevents.ui.favorite.ui.adapter.EventPojoAdapter
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel

    private val adapter: EventPojoAdapter by lazy { EventPojoAdapter(::onDeleteFromFavoritesClicked, ::onMoreInfoClicked) }

    private lateinit var viewpager: ViewPager2
    private lateinit var indicator: IndefinitePagerIndicator
    private lateinit var textNoFavorites: TextView

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
            if (it.isEmpty()){
                textNoFavorites.visible()
            }else textNoFavorites.gone()

            adapter.onItems(it)
            adapter.notifyDataSetChanged()
        })

        favoritesViewModel.url.observe(viewLifecycleOwner,{
            openUrl(it)
        })
    }

    private fun bindView(view: View){
        viewpager = view.findViewById(R.id.vpFavorites)
        viewpager.adapter = adapter

        indicator = view.findViewById(R.id.vpIndicator)
        indicator.attachToViewPager2(viewpager)
        textNoFavorites = view.findViewById(R.id.txtNoEvents)
    }

    private fun onDeleteFromFavoritesClicked(event: EventPOJO){
        favoritesViewModel.deleteFromFavorites(event)
    }

    private fun onMoreInfoClicked(event: EventPOJO){
        favoritesViewModel.openUrl(event)
    }

    private fun openUrl(url: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}