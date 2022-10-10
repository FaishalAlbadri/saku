package com.faishal.saku.base

interface BasePresenter<T> {

    fun onAttachView(view: T)

    fun onDettachView()

}