package com.example.gelderlandplein.dummy

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.gelderlandplein.R
import com.example.gelderlandplein.ui.GoogleMapDTO
import com.example.gelderlandplein.ui.search.BUNDLE_ICON_KEY
import com.example.gelderlandplein.ui.search.BUNDLE_SHOP_KEY
import com.example.gelderlandplein.ui.search.REQ_ICON_KEY
import com.example.gelderlandplein.ui.search.REQ_SHOP_KEY
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_detail_event.*
import kotlinx.android.synthetic.main.item_shop_detail.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class MapRouteFragment: Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var lastKnownLocation: Location? = null
    private lateinit var deviceLocation: LatLng
    private var locationPermissionGranted = false
    private var REQUEST_LOCATION = 1
    private var destinationLatLng: LatLng? = null
    private val gelderlandLatLng = LatLng(52.331164, 4.877550)
    private val mapBound = LatLngBounds(LatLng(52.330440, 4.875695), LatLng(52.332053, 4.879335))
    private val minZoom = 17f
    private val maxZoom = 20f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map_route, container, false)

        val mapViewBundle = savedInstanceState?.getBundle(MapRouteFragment.MAPVIEW_BUNDLE_KEY)
        mapView = v.findViewById(R.id.mv_route)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
        return v
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap ?: return
        map.uiSettings.isMyLocationButtonEnabled = false
        getLocationPermission()

        val gelderlandOverlay = GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.map)).anchor(0f, 1f)
                .positionFromBounds(mapBound)

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style))

        //Sets the bound on what the user can see
        map.setMinZoomPreference(minZoom)
        map.setMaxZoomPreference(maxZoom)
        map.setLatLngBoundsForCameraTarget(mapBound)
        //Sets the camera on gelderlandplein
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(gelderlandLatLng, minZoom))

        map.addGroundOverlay(gelderlandOverlay)
        observeShopResult()
    }

    @SuppressLint("MissingPermission")
    private fun startNavigation(shopLatLng: LatLng) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                activity?.let {
                    locationResult.addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            lastKnownLocation = task.result
                            deviceLocation = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                            GetDirection(getAPI(deviceLocation, shopLatLng)).execute()
                        } else {
                            Log.d("Location", "Current location is null. Using defaults.")
                            Log.e("Error", "Exception: %s", task.exception)
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
    //START
    //From here the code is copy/paste

    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e: Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            map.addPolyline(lineoption)
        }
    }

    //CODE For drawing route
    private fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }

    private fun getAPI(origin: LatLng, dest: LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&mode=walking&key=AIzaSyAhZYpkf0DvXWtlNCYFAW15KOxghYTHcUs"
    }
    //END

    private fun getLocationPermission(){
        if(context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } == PackageManager.PERMISSION_GRANTED){
            locationPermissionGranted = true
            map.isMyLocationEnabled = true
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                locationPermissionGranted = false
                Toast.makeText(requireContext(), "Location permission is needed to see your location on the map", Toast.LENGTH_SHORT).show()
            }
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION) }
        }
    }

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun observeShopResult(){
        setFragmentResultListener(REQ_SHOP_KEY) { Key, bundle ->
            bundle.getParcelable<LatLng>(BUNDLE_SHOP_KEY)?.let {
                startNavigation(it)
                destinationLatLng = it
            }
        }
        setFragmentResultListener(REQ_ICON_KEY) { Key, bundle ->
            bundle.getParcelable<Bitmap>(BUNDLE_ICON_KEY)?.let {
                map.apply {
                    addMarker(
                            destinationLatLng?.let {
                                it1 -> MarkerOptions().position(it1).icon(BitmapDescriptorFactory.fromBitmap(it)).anchor(0f, 1f)
                            }
                    )
                }
            }
        }
    }
}