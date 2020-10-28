package com.example.gelderlandplein.ui.search

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.example.gelderlandplein.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds


class ShopDetailFragment: Fragment(), OnMapReadyCallback{
    private lateinit var mapView : MapView
    private lateinit var map: GoogleMap

    private val gelderlandLatLng = LatLng(52.331164, 4.877550)
    private val mapBound = LatLngBounds(LatLng(52.330440, 4.875695), LatLng(52.332053, 4.879335))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_shop_detail, container, false)

        val mapViewBundle = savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY)
        mapView = v.findViewById(R.id.mv_shop_detail)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
        return v
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

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap ?: return

        //Sets the bound on what the user can see
        map.setMinZoomPreference(17f)
        map.setMaxZoomPreference(20f)
        map.setLatLngBoundsForCameraTarget(mapBound)
        //Sets the camera on gelderlandplein
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(gelderlandLatLng, 17f))

        //To see the location of the user
        if(ActivityCompat.checkSelfPermission(requireContext() ,ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            map.isMyLocationEnabled = true
        } else {
            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)){
                Toast.makeText(requireContext(), "Location permission is needed to use the map", Toast.LENGTH_SHORT).show()
            }
        }

        /*val gelderlandOverlay = GroundOverlayOptions()
            .image(images[0]).anchor(0f, 1f)
            .positionFromBounds(mapBound)
        map.addGroundOverlay(gelderlandOverlay)*/
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

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }
}