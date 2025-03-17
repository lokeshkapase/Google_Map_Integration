package com.lokesh.google_map_integration

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapsFragment : Fragment() {
    private lateinit var mMap : GoogleMap
    private lateinit var marker : Marker
    private lateinit var bitcodeMarker : Marker
    private lateinit var puneMarker: Marker
    private lateinit var circle: Circle
    private lateinit var polygon: Polygon
    private lateinit var polyline: Polyline

    private val callback = OnMapReadyCallback { googleMap -> mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        initMapSettings()
        addShapes()
        addMarkers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback)
        }
    }

    private fun addShapes(){
        var bitcodeLatLng = LatLng(18.51021,73.83350)
        circle = mMap.addCircle(
            CircleOptions()
                .center(bitcodeLatLng)
                .radius(2000.0)
                .strokeColor(Color.CYAN)
                .strokeWidth(10.0F)
                .fillColor(Color.YELLOW)
                .zIndex(45.0F))

        polygon = mMap.addPolygon(
            PolygonOptions()
                .fillColor(Color.BLUE)
                .strokeColor(Color.BLACK)
                .strokeWidth(10.0f)
                .zIndex(30.0f)
                .add(LatLng(18.52,73.84))
                .add(LatLng(19.07,72.87))
                .add(LatLng(28.61,77.23))
                .add(LatLng(12.97,77.59))
        )
        polyline = mMap.addPolyline(
            PolylineOptions().add(LatLng(13.0674,80.2376))
                .add(LatLng(22.5726,88.3639))
                .add(LatLng(17.3871,78.4917))
                .add(LatLng(23.0339,72.5850))
                .add(LatLng(13.0674,80.2376))
                .color(Color.CYAN)
                .width(10.0f)

        )
    }

    private fun setInfoWindowAdapter(){
        //way 2 -- passing object of anonymous class by using object keyword in kotlin
//        mMap.setOnMarkerDragListener(
//            object  : GoogleMap.OnMarkerDragListener{
//                override fun onMarkerDragStart(p0: Marker) {
//                    TODO("Not yet implemented")
//                }
//                override fun onMarkerDrag(p0: Marker) {
//                    TODO("Not yet implemented")
//                }
//                override fun onMarkerDragEnd(p0: Marker) {
//                    TODO("Not yet implemented")
//                }
//            }
//        )
        //way 1
        mMap.setOnMarkerDragListener(MyMarkerDragListener())

    }
    inner class MyMarkerDragListener : GoogleMap.OnMarkerDragListener{
        override fun onMarkerDrag(p0: Marker) {
            Log.e("tag", " marker drag ${p0.position.latitude} -- ${p0.position.longitude}")
        }

        override fun onMarkerDragEnd(p0: Marker) {
            Log.e("tag", "marker drag end ${p0.position.latitude} -- ${p0.position.longitude}")
        }

        override fun onMarkerDragStart(p0: Marker) {
            Log.e("tag", "marker drag start ${p0.position.latitude} -- ${p0.position.longitude}")
        }
    }

    private fun setInfoWindowClickListener(){

        //way 2
//        mMap.setOnInfoWindowClickListener { marker->
//            Log.e("tag", marker.title + marker.id)
//
//        }

        //way 1
        mMap.setOnInfoWindowClickListener(MyInfoWindowClickListener())
    }

    inner class MyInfoWindowClickListener : GoogleMap.OnInfoWindowClickListener{
        override fun onInfoWindowClick(p0: Marker) {
            Log.e("tag", "Title : " + p0.title + "--"+ "Id : "+ p0.id)
        }
    }



    inner class MyInfoWindowAdapter : GoogleMap.InfoWindowAdapter{
        override fun getInfoContents(p0: Marker): View? {
            var infoWindowView = layoutInflater.inflate(R.layout.info_window,null)
            return  infoWindowView
        }

        override fun getInfoWindow(p0: Marker): View? {
            return null
        }
    }

    private fun addMarkers(){
        var bitcodeLatLng = LatLng(18.51021,73.83350)

        var markerOptions = MarkerOptions()

        bitcodeMarker = mMap.addMarker(
            markerOptions.position(bitcodeLatLng)
                .rotation(45.0F)
                .title("Bitcode")
        )!!

        var puneLatLng = LatLng(18.52975,73.85196)
        puneMarker = mMap.addMarker(
            MarkerOptions()
                .position(puneLatLng)
                .rotation(20.0F)
                .title("Pune Marker"))!!
    }

    @SuppressLint("MissingPermission")
    private fun initMapSettings(){
        mMap.isIndoorEnabled = true
        mMap.isTrafficEnabled = true
        mMap.isBuildingsEnabled = true
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isTiltGesturesEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isScrollGesturesEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
    }
}