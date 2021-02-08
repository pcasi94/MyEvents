package com.mimoupsa.myevents.ui.event.detail.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.ui.MainActivity
import com.mimoupsa.myevents.ui.event.detail.presentation.EventDetailViewModel
import com.mimoupsa.myevents.ui.extensions.loadImage
import com.mimoupsa.myevents.ui.extensions.switchVisibility
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventDetailFragment: Fragment() {

    private lateinit var viewModel: EventDetailViewModel
    private val args: EventDetailFragmentArgs by navArgs()

    private lateinit var imageView: ImageView
    private lateinit var eventName: TextView
    private lateinit var eventDate: TextView
    private lateinit var eventEmplacement : TextView
    private lateinit var eventCity: TextView
    private lateinit var eventProvince: TextView
    private lateinit var eventPostalCode: TextView
    private lateinit var eventInfo: TextView
    private lateinit var prices: TextView
    private lateinit var buttonBuy: Button
    private lateinit var buttonfacebook: Button
    private lateinit var buttonWeb: Button
    private lateinit var buttonIg: Button
    private lateinit var buttonSpoty: Button
    private lateinit var buttonTwitter: Button
    private lateinit var buttonYoutube: Button



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View?  = inflater.inflate(R.layout.fragment_event_detail, container, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(EventDetailViewModel::class.java)

        bindView(view)

        viewModel.getEventDetail(args.id)

        viewModel.onEvent().observe(viewLifecycleOwner, {
            setView(it)
        })

        viewModel.openUrl.observe(viewLifecycleOwner, {
            openUrl(it)
        })
    }




    private fun bindView(view: View){
        imageView = view.findViewById(R.id.ivEventdetail)
        eventName = view.findViewById(R.id.txtEventNameD)
        eventDate = view.findViewById(R.id.txtEventDateD)
        eventEmplacement = view.findViewById(R.id.txtEventEmplacementD)
        eventCity = view.findViewById(R.id.txtEventCityD)
        eventProvince = view.findViewById(R.id.txtEventProvinceD)
        eventPostalCode = view.findViewById(R.id.txtEventPostalCodeD)
        eventInfo = view.findViewById(R.id.txtEventInfoD)
        prices = view.findViewById(R.id.txtPrices)
        buttonBuy = view.findViewById(R.id.btnBuyTickets)
        buttonfacebook = view.findViewById(R.id.btnFacebook)
        buttonWeb = view.findViewById(R.id.btnHomepage)
        buttonIg = view.findViewById(R.id.btnInstagram)
        buttonSpoty = view.findViewById(R.id.btnSpotify)
        buttonTwitter = view.findViewById(R.id.btnTwitter)
        buttonYoutube = view.findViewById(R.id.btnYoutube)
        setOnclickListeners()
    }

    private fun setOnclickListeners(){
        buttonBuy.setOnClickListener { viewModel.onBuyClicked()  }
        buttonfacebook.setOnClickListener { viewModel.onFacebookClicked() }
        buttonWeb.setOnClickListener { viewModel.onHomePageClicked() }
        buttonIg.setOnClickListener { viewModel.onInstagramClicked() }
        buttonSpoty.setOnClickListener { viewModel.onSpotifyClicked() }
        buttonTwitter.setOnClickListener { viewModel.onTwitterClicked() }
        buttonYoutube.setOnClickListener { viewModel.onYoutubeClicked() }
    }

    private fun setView(event: Event){
        setVisibility(event)
        event.images?.first()?.url?.let { imageView.loadImage(requireContext(), it) }
        eventName.text = event.name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            event.date?.let {
                eventDate.text = LocalDate.parse(it, DateTimeFormatter.ISO_DATE_TIME).toString()
            }
        }
        eventEmplacement.text = event.emplacement
        eventCity.text = event.city
        eventProvince.text = event.province
        eventCity.text = event.city
        eventPostalCode.text = event.postalCode
        eventInfo.text = event.info
        event.priceRangesData?.first()?.let {
            prices.text = getString(R.string.currency, it.min.toString(), it.max.toString(), it.currency)
        }
    }

    private fun setVisibility(event: Event){
        buttonfacebook.switchVisibility(event.externalLinks?.facebook?.first())
        buttonWeb.switchVisibility(event.externalLinks?.homepage?.first())
        buttonIg.switchVisibility(event.externalLinks?.instagram?.first())
        buttonSpoty.switchVisibility(event.externalLinks?.spotify?.first())
        buttonTwitter.switchVisibility(event.externalLinks?.twitter?.first())
        buttonYoutube.switchVisibility(event.externalLinks?.youtube?.first())
    }


    private fun openUrl(url: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}