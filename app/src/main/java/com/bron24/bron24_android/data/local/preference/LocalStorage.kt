package com.bron24.bron24_android.data.local.preference

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalStorage @Inject constructor(@ApplicationContext context: Context) : SharedPreferencesHelper(context) {
    var isFirstRun: Boolean by booleans(true)
    var isSignIn: Boolean by booleans(false)

    var openMenu :Boolean by booleans(false)
    var openOnBoard:Boolean by booleans(false)
    var openLanguageSel by booleans(false)
    var openPhoneNumber by booleans(false)


    var token: String by strings("")
    var refreshToken: String by strings("")
    var accessToken: String by strings("")

    var inst: Int by ints()
    var parentId: Int by ints()
    var username: String by strings()
    var pin: String by strings()


    var list:String by strings("")
}