package ir.roohi.farshid.reminderpro.map


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

import com.mapbox.mapboxsdk.annotations.BaseMarkerViewOptions
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.geometry.LatLng

class PulseMarkerViewOptions : BaseMarkerViewOptions<PulseMarkerView, PulseMarkerViewOptions> {

    constructor() {}

    protected constructor(`in`: Parcel) {
        position(`in`.readParcelable<Parcelable>(LatLng::class.java.classLoader) as LatLng)
        snippet(`in`.readString())
        title(`in`.readString())
        flat(`in`.readByte().toInt() != 0)
        anchor(`in`.readFloat(), `in`.readFloat())
        selected = `in`.readByte().toInt() != 0
        rotation(`in`.readFloat())
        if (`in`.readByte().toInt() != 0) {
            // this means we have an icon
            val iconId = `in`.readString()
            val iconBitmap = `in`.readParcelable<Bitmap>(Bitmap::class.java.classLoader)
            val icon = IconFactory.recreate(iconId!!, iconBitmap!!)
            icon(icon)
        }
    }

    override fun getThis(): PulseMarkerViewOptions {
        return this
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeParcelable(getPosition(), flags)
        out.writeString(getSnippet())
        out.writeString(getTitle())
        out.writeByte((if (isFlat) 1 else 0).toByte())
        out.writeFloat(getAnchorU())
        out.writeFloat(getAnchorV())
        out.writeFloat(getInfoWindowAnchorU())
        out.writeFloat(getInfoWindowAnchorV())
        out.writeByte((if (selected) 1 else 0).toByte())
        out.writeFloat(getRotation())
        val icon = getIcon()
        out.writeByte((if (icon != null) 1 else 0).toByte())
        if (icon != null) {
            out.writeString(getIcon().id)
            out.writeParcelable(getIcon().bitmap, flags)
        }
    }

    override fun getMarker(): PulseMarkerView {
        return PulseMarkerView(this)
    }

    companion object {

        @SuppressLint("ParcelCreator")
        val CREATOR: Parcelable.Creator<CountryMarkerViewOptions> =
            object : Parcelable.Creator<CountryMarkerViewOptions> {
                override fun createFromParcel(`in`: Parcel): CountryMarkerViewOptions {
                    return CountryMarkerViewOptions(`in`)
                }

                override fun newArray(size: Int): Array<CountryMarkerViewOptions?> {
                    return arrayOfNulls<CountryMarkerViewOptions>(size)
                }
            }
    }
}