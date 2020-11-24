package com.example.gelderlandplein.ui.search

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.example.gelderlandplein.R
import com.example.gelderlandplein.models.Shop
import com.example.gelderlandplein.ui.GoogleMapDTO
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.item_shop_detail.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_detail_event.*

const val REQ_SHOP_KEY = "req_shop"
const val BUNDLE_SHOP_KEY = "bundle_shop"
const val REQ_ICON_KEY = "req_icon"
const val BUNDLE_ICON_KEY = "bundle_icon"


class ShopDetailFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var shopLogo : Bitmap? = null
    private var locationPermissionGranted = false
    private var REQUEST_LOCATION = 1
    private val gelderlandLatLng = LatLng(52.331164, 4.877550)
    private var destinationLatLng: LatLng? = null
    private val mapBound = LatLngBounds(LatLng(52.330440, 4.875695), LatLng(52.332053, 4.879335))
    private val minZoom = 17f
    private val maxZoom = 20f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeShopFragmentResult()
        bt_nav.setOnClickListener{
            Log.d("IconShop", shopLogo.toString())
            destinationLatLng?.let { it1 -> shopLogo?.let { it2 -> goToRoute(it1, it2) } }
        }
    }

    private fun observeShopFragmentResult() {
        setFragmentResultListener(REQ_INFO_SHOP_KEY) { key, bundle ->
            bundle.getParcelable<Shop>(BUNDLE_INFO_SHOP_KEY)?.let {
                if (it.image.isNullOrEmpty()) {
                    iv_detail_event.setImageResource(R.drawable.image_not_found)
                } else Picasso.get().load(it.image).into(iv_shop_detail)
                tv_shop_name_detail.text = it.name
                // Sommige openingstijden komen niet helemaal goed.
                // Denk dat het ligt aan hoe het is ingevoerd in firebase?
                tv_openingstijden.text = it.openingstijden.toString()
                tv_productenlijst.text = it.inventory.toString()

                //TODO adres van winkels in Firebase plaatsen
                destinationLatLng = LatLng(it.latitude, it.longitude)
            }
        }
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

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap ?: return
        map.uiSettings.isMyLocationButtonEnabled = false
        //asks for location permission
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

        map.apply {
            val drawable : BitmapDrawable = iv_shop_detail.drawable as BitmapDrawable
            shopLogo = drawable.bitmap
            addMarker(
                    destinationLatLng?.let {
                        MarkerOptions().position(it).icon(BitmapDescriptorFactory.fromBitmap(shopLogo)).anchor(0f, 1f)
                    }
            )
        }
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

    private fun getLocationPermission() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            map.isMyLocationEnabled = true
        } else {
            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                locationPermissionGranted = false
                Toast.makeText(
                    requireContext(),
                    "Location permission is needed to see your location on the map",
                    Toast.LENGTH_SHORT
                ).show()
            }
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION
                )
            }
        }
    }

    private fun goToRoute(shopLatLng: LatLng, shopLogo: Bitmap) {
        setFragmentResult(REQ_SHOP_KEY, bundleOf(Pair(BUNDLE_SHOP_KEY, shopLatLng)))
        setFragmentResult(REQ_ICON_KEY, bundleOf(Pair(BUNDLE_ICON_KEY, shopLogo)))
        findNavController().navigate(R.id.action_shopDetailFragment_to_mapRouteFragment)
    }

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }
}
