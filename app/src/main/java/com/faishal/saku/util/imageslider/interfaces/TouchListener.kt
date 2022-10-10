package com.faishal.saku.util.imageslider.interfaces

import com.faishal.saku.util.imageslider.constants.ActionTypes

interface TouchListener {
    fun onTouched(touched: ActionTypes)
}