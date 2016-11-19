package com.jenzz.appstate.internal.adapters;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.support.annotation.RestrictTo;

import static android.support.annotation.RestrictTo.Scope.GROUP_ID;

@RestrictTo(GROUP_ID)
public class NoOpComponentCallbacks2 implements ComponentCallbacks2 {

  //@formatter:off
  @Override public void onTrimMemory(int level) {}
  @Override public void onConfigurationChanged(Configuration newConfig) {}
  @Override public void onLowMemory() {}
  //@formatter:on
}