package ir.roohi.farshid.reminderpro.views.adapter


import com.mapbox.mapboxsdk.annotations.BaseMarkerViewOptions
import com.mapbox.mapboxsdk.annotations.MarkerView

class CountryMarkerView(baseMarkerViewOptions: BaseMarkerViewOptions<*, *>, val abbrevName: String, val flagRes: Int) :
    MarkerView(baseMarkerViewOptions)