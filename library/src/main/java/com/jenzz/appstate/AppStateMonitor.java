package com.jenzz.appstate;

import android.support.annotation.NonNull;

public interface AppStateMonitor {

  void start();

  void stop();

  void addListener(@NonNull AppStateListener appStateListener);

  void removeListener(@NonNull AppStateListener appStateListener);

  boolean isAppInForeground();

  boolean isAppInBackground();
}
