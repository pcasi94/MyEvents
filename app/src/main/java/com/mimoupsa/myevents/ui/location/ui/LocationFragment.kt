package com.mimoupsa.myevents.ui.location.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
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

    private val adapter: EventListAdapter by lazy { EventListAdapter(::onFavoritesClicked,::onMoreInfoClicked) }

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

        locationViewModel.isRefreshing.observe(viewLifecycleOwner,{
            swipeRefreshLayout.isRefreshing = it
        })

        locationViewModel.eventsData().observe(viewLifecycleOwner,{
            adapter.onItems(it)
            adapter.notifyDataSetChanged()
        })
        locationViewModel.askPermissions.observe(viewLifecycleOwner,{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_CODE)
        })
        locationViewModel.getLocation.observe(viewLifecycleOwner,{
            this.getLocation()
        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu,menu)
        val itemMenu = menu.findItem(R.id.itemMenuSearch)
        itemMenu.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemMenuSettings -> {
                val dialog = SettingsDialog(this,12)
                dialog.show(requireActivity().supportFragmentManager, SettingsDialog::class.java.name)
                return true
            }
        }
        return false
    }

    override fun onSaveProgress(progress: Int) {

    }


    private fun onFavoritesClicked(event: Event){

    }

    private fun onMoreInfoClicked(event: Event){
        val dialog = SettingsDialog(this,12)
        dialog.show(requireActivity().supportFragmentManager, SettingsDialog::class.java.name)
    }

    private fun navigateToDetail(eventId: Event){

    }

    private fun bindView(view: View){
        recyclerView = view.findViewById(R.id.rv_location)
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissions(){
        locationViewModel.checkPermissionResult(
            (ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    &&
                    (ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
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




}