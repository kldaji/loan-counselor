package com.kldaji.loancounselor

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class LoanCounselorApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.NATIVE_APP_KEY))
    }
}
