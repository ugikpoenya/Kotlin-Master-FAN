package com.example.masterfan

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.facebook.ads.MediaView
import com.facebook.ads.MediaViewListener
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdBase


fun populateFacebookNative(nativeAd: NativeAd, adView: View) {
    // Create native UI using the ad metadata.
    val nativeAdIcon = adView.findViewById<MediaView>(R.id.native_ad_icon)
    val nativeAdTitle = adView.findViewById<TextView>(R.id.native_ad_title)
    val nativeAdBody = adView.findViewById<TextView>(R.id.native_ad_body)
    val sponsoredLabel = adView.findViewById<TextView>(R.id.native_ad_sponsored_label)
    val nativeAdSocialContext = adView.findViewById<TextView>(R.id.native_ad_social_context)
    val nativeAdCallToAction = adView.findViewById<Button>(R.id.native_ad_call_to_action)
    val nativeAdMedia = adView.findViewById<MediaView>(R.id.native_ad_media)
    nativeAdMedia?.setListener(object : MediaViewListener {
        override fun onVolumeChange(mediaView: MediaView, volume: Float) {
            Log.d("LOG", "MediaViewEvent: Volume $volume")
        }

        override fun onPause(mediaView: MediaView) {
            Log.d("LOG",  "MediaViewEvent: Paused")
        }

        override fun onPlay(mediaView: MediaView) {
            Log.d("LOG",  "MediaViewEvent: Play")
        }

        override fun onFullscreenBackground(mediaView: MediaView) {
            Log.d("LOG",  "MediaViewEvent: FullscreenBackground")
        }

        override fun onFullscreenForeground(mediaView: MediaView) {
            Log.d("LOG",  "MediaViewEvent: FullscreenForeground")
        }

        override fun onExitFullscreen(mediaView: MediaView) {
            Log.d("LOG",  "MediaViewEvent: ExitFullscreen")
        }

        override fun onEnterFullscreen(mediaView: MediaView) {
            Log.d("LOG",  "MediaViewEvent: EnterFullscreen")
        }

        override fun onComplete(mediaView: MediaView) {
            Log.d("LOG",  "MediaViewEvent: Completed")
        }
    })

    // Setting the Text
    nativeAdSocialContext.text = nativeAd.adSocialContext
    nativeAdCallToAction.text = nativeAd.adCallToAction
    nativeAdCallToAction.visibility = if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
    nativeAdTitle.text = nativeAd.advertiserName
    nativeAdBody.text = nativeAd.adBodyText
    sponsoredLabel.text = "Sponsored"

    // You can use the following to specify the clickable areas.
    val clickableViews = ArrayList<View>()
    clickableViews.add(nativeAdIcon)
    clickableViews.add(nativeAdMedia!!)
    clickableViews.add(nativeAdCallToAction)
    nativeAd.registerViewForInteraction(
        adView,
        nativeAdMedia,
        nativeAdIcon,
        clickableViews
    )

    // Optional: tag views
    NativeAdBase.NativeComponentTag.tagView(nativeAdIcon, NativeAdBase.NativeComponentTag.AD_ICON)
    NativeAdBase.NativeComponentTag.tagView(nativeAdTitle, NativeAdBase.NativeComponentTag.AD_TITLE)
    NativeAdBase.NativeComponentTag.tagView(nativeAdBody, NativeAdBase.NativeComponentTag.AD_BODY)
    NativeAdBase.NativeComponentTag.tagView(
        nativeAdSocialContext,
        NativeAdBase.NativeComponentTag.AD_SOCIAL_CONTEXT
    )
    NativeAdBase.NativeComponentTag.tagView(nativeAdCallToAction, NativeAdBase.NativeComponentTag.AD_CALL_TO_ACTION)
}