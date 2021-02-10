package com.example.masterfan

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.masterfan.databinding.ActivityMainBinding
import com.facebook.ads.*
import com.facebook.ads.AdSize


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFacebookAds()

        binding.btnBanner.setOnClickListener {
            initFacebookBanner()
        }

        binding.btnNativeBanner.setOnClickListener {
            initFacebookNativeBanner()
        }

        binding.btnNative.setOnClickListener {
            initFacebookNative()
        }

        binding.btnInterstitial.setOnClickListener {
            showInterstitialFacebook()
        }

    }

    fun initFacebookAds(){
        binding.txtLog.append("\n Facebook Ads Init")
        AudienceNetworkAds
                .buildInitSettings(this)
                .withInitListener {
                    binding.txtLog.append("\n Facebook Ads Initialized")
                    initFacebookInterstitial()
                }.initialize()
    }

    fun initFacebookBanner(){
        binding.lyBannerAds.removeAllViews()
        binding.txtLog.append("\n Facebook banner init")
        val adView = AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50)

        val loadAdConfig = adView.buildLoadAdConfig()
                .withAdListener(object : AdListener {
                    override fun onAdClicked(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Banner onAdClicked")
                    }

                    override fun onError(p0: Ad?, p1: AdError?) {
                        binding.txtLog.append("\n Facebook Banner onError" + p1?.errorMessage)
                    }

                    override fun onAdLoaded(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Banner onAdLoaded")
                        binding.lyBannerAds.addView(adView)
                    }

                    override fun onLoggingImpression(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Banner onLoggingImpression")
                    }
                })
                .build()
        adView.loadAd(loadAdConfig)
    }


    fun initFacebookNativeBanner(){
        binding.lyBannerAds.removeAllViews()
        binding.txtLog.append("\n Facebook Native Banner init")
        val mNativeBannerAd = NativeBannerAd(applicationContext, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
        val loadAdConfig = mNativeBannerAd.buildLoadAdConfig()
                .withAdListener(object : NativeAdListener {
                    override fun onAdClicked(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Native Banner onAdClicked")
                    }

                    override fun onMediaDownloaded(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Native Banner onMediaDownloaded")
                    }

                    override fun onError(p0: Ad?, p1: AdError?) {
                        binding.txtLog.append("\n Facebook Native Banner onError" + p1?.errorMessage)
                    }

                    override fun onAdLoaded(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Native Banner onAdLoaded")
                        val adView = NativeBannerAdView.render(applicationContext, mNativeBannerAd, NativeBannerAdView.Type.HEIGHT_100)
                        binding.lyBannerAds.addView(adView)
                    }

                    override fun onLoggingImpression(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Native Banner onLoggingImpression")
                    }
                })
                .build()
        mNativeBannerAd.loadAd(loadAdConfig)
    }

    fun initFacebookNative(){
        binding.lyBannerAds.removeAllViews()
        binding.txtLog.append("\n Facebook Native medium init")
        val mAdView = layoutInflater.inflate(R.layout.native_ads_layout_facebook, binding.lyBannerAds, false)
        val nativeAd = NativeAd(applicationContext, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
        val loadAdConfig = nativeAd.buildLoadAdConfig()
                .withAdListener(object : NativeAdListener {
                    override fun onAdClicked(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Native onAdClicked")
                    }

                    override fun onMediaDownloaded(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Native onMediaDownloaded")
                    }

                    override fun onError(p0: Ad?, p1: AdError?) {
                        binding.txtLog.append("\n Facebook Native onError" + p1?.errorMessage)
                    }

                    override fun onAdLoaded(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Native onAdLoaded")
                        if (nativeAd !== p0) {
                            // Race condition, load() called again before last ad was displayed
                            return
                        }
                        if (mAdView == null) {
                            return
                        }
                        nativeAd.unregisterView()
                        populateFacebookNative(nativeAd, mAdView)
                        binding.lyBannerAds.addView(mAdView)
                    }

                    override fun onLoggingImpression(p0: Ad?) {
                        binding.txtLog.append("\n Facebook Native onLoggingImpression")
                    }
                })
                .build()
        nativeAd.loadAd(loadAdConfig)
    }

    var facebookInterstitial:InterstitialAd? =null
    fun initFacebookInterstitial(){
        binding.txtLog.append("\n Init Facebook Interstitial ")
        facebookInterstitial = InterstitialAd(applicationContext, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
        val loadAdConfig = facebookInterstitial?.buildLoadAdConfig()
                ?.withAdListener(object : com.facebook.ads.InterstitialAdListener {
                    override fun onInterstitialDisplayed(p0: Ad?) {
                        Log.d("LOG", "Facebook Interstitial onInterstitialDisplayed")
                    }

                    override fun onAdClicked(p0: Ad?) {
                        Log.d("LOG", "Facebook Interstitial onAdClicked")
                    }

                    override fun onInterstitialDismissed(p0: Ad?) {
                        Log.d("LOG", "Facebook Interstitial onInterstitialDismissed")
                        facebookInterstitial?.loadAd()
                    }

                    override fun onError(p0: Ad?, p1: AdError?) {
                        Log.d("LOG", "Facebook Interstitial onError " + p1?.errorMessage)
                    }

                    override fun onAdLoaded(p0: Ad?) {
                        Log.d("LOG", "Facebook Interstitial onAdLoaded")
                    }

                    override fun onLoggingImpression(p0: Ad?) {
                        Log.d("LOG", "Facebook Interstitial onLoggingImpression")
                    }
                })
                ?.build()
        facebookInterstitial?.loadAd(loadAdConfig)
    }



    fun showInterstitialFacebook(){
        if (facebookInterstitial != null && facebookInterstitial!!.isAdLoaded) {
            facebookInterstitial?.show()
            binding.txtLog.append("\n Interstitial Facebook Show")
        }else{
            binding.txtLog.append("\n Interstitial Facebook not loaded")
        }
    }

}