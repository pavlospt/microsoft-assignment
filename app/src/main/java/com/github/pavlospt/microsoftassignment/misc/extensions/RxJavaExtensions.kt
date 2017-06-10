package com.github.pavlospt.microsoftassignment.misc.extensions

import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun Subscription.disposeIn(compositeSubscription: CompositeSubscription): Unit {
  compositeSubscription.add(this)
}
