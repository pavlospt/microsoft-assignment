package com.github.pavlospt.microsoftassignment.mvp


interface Presenter<in T: MvpView> {
  fun init(view: T)
  fun destroy()
}