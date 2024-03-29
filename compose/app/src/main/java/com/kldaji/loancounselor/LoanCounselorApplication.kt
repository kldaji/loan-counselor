package com.kldaji.loancounselor

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kldaji.presentation.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LoanCounselorApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.NATIVE_APP_KEY))
    }
}
