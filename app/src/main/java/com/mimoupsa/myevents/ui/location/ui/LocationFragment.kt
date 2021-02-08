package com.mimoupsa.myevents.ui.location.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mimoupsa.myevents.R
import com.mimoupsa.myevents.domain.model.Event
import com.mimoupsa.myevents.ui.MainActivity
import com.mimoupsa.myevents.ui.common.adapter.EventListAdapter
import com.mimoupsa.myevents.ui.location.presentation.LocationViewModel
import com.mimoupsa.myevents.ui.location.presentation.LocationViewModel.Companion.LOCATION_CODE
import com.mimoupsa.myevents.ui.settings.NoticeDialogListener
import com.mimoupsa.myevents.ui.settings.SettingsDialog


class LocationFragment : Fragment(), NoticeDialogListener{

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var client: FusedLocationProviderClient

    private val adapter: EventListAdapter by lazy { EventListAdapter(::onFavoritesClicked, ::onMoreInfoClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_location, container, false)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setTitle(R.string.title_location)

        bindView(view)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        client = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkPermissions()

        swipeRefreshLayout.setOnRefreshListener {
            locationViewModel.getMoreEvents()
        }

        locationViewModel.isRefreshing.observe(viewLifecycleOwner, {
            swipeRefreshLayout.isRefreshing = it
        })

        locationViewModel.eventsData().observe(viewLifecycleOwner, {
            adapter.onItems(it)
            adapter.notifyDataSetChanged()
        })
        locationViewModel.askPermissions.observe(viewLifecycleOwner, {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_CODE)
        })
        locationViewModel.getLocation.observe(viewLifecycleOwner, {
            this.getLocation()
        })

        locationViewModel.getInsertResult().observe(viewLifecycleOwner, {
            if (it) Toast.makeText(requireContext(), getString(R.string.insert_success), Toast.LENGTH_SHORT).show()
            else Toast.makeText(requireContext(), R.string.insert_exists_error, Toast.LENGTH_SHORT).show()
        })

        locationViewModel.radiusSettings.observe(viewLifecycleOwner, {
            openSettingsDialog(it)
        })

        locationViewModel.url.observe(viewLifecycleOwner,{
            openUrl(it)
        })

        locationViewModel.error.observe(viewLifecycleOwner,{
            showError(it.title,it.message)
        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val itemMenu = menu.findItem(R.id.itemMenuSearch)
        itemMenu.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemMenuSettings -> {
                locationViewModel.onOpenSettingsDialogClicked()
                return true
            }
        }
        return false
    }

    override fun onSaveProgress(progress: Int) {
        locationViewModel.updateRadius(progress)
    }


    private fun onFavoritesClicked(event: Event){
        locationViewModel.saveToFavorites(event)
    }

    private fun onMoreInfoClicked(event: Event){
        locationViewModel.openUrl(event)
    }

    private fun openSettingsDialog(progress: Int){
        val dialog = SettingsDialog(this, progress)
        dialog.show(requireActivity().supportFragmentManager, SettingsDialog::class.java.name)
    }

    private fun openUrl(url: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun bindView(view: View){
        recyclerView = view.findViewById(R.id.rv_location)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissions(){
        locationViewModel.checkPermissionResult(
                (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        &&
                        (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client.lastLocation.addOnCompleteListener { task ->
                val location = task.result
                location?.let {
                    locationViewModel.setLocation(it.latitude, it.longitude)
                }
            }
        }
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