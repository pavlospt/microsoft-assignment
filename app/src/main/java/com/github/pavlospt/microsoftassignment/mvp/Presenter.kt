package com.github.pavlospt.microsoftassignment.mvp


interface Presenter<T: View> {
  fun init(view: T)
  fun destroy()
}