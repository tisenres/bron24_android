package com.bron24.bron24_android.domain.entity.booking

import androidx.annotation.StringRes
import com.bron24.bron24_android.R

//enum class Sector(val displayName: String) {
//    X("Full Stadium"),
//    A("Sector A"),
//    B("Sector B"),
//    C("Sector C"),
//    D("Sector D"),
//    E("Sector E"),
//    F("Sector F"),
//    G("Sector G"),
//    H("Sector H"),
//}

enum class Sector(@StringRes val displayNameRes: Int) {
    X(R.string.full_stadion),
    A(R.string.sector_a),
    B(R.string.sector_b),
    C(R.string.sector_c),
    D(R.string.sector_d),
    E(R.string.sector_e),
    F(R.string.sector_f),
    G(R.string.sector_g),
    H(R.string.sector_h);
}

