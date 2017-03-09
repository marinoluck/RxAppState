package com.jenzz.appstate.sample;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.jenzz.appstate.AppState;
import com.jenzz.appstate.AppStateListener;
import com.jenzz.appstate.AppStateMonitor;
import com.jenzz.appstate.RxAppStateMonitor;


import io.reactivex.functions.Consumer;

import static android.widget.Toast.LENGTH_LONG;
import static com.jenzz.appstate.AppState.BACKGROUND;
import static com.jenzz.appstate.AppState.FOREGROUND;

public class SampleApplication extends Application {

  private static final String TAG = "RxAppStateMonitor";

  private AppStateMonitor appStateMonitor;

  @Override
  public void onCreate() {
    super.onCreate();

    // RX sample
    RxAppStateMonitor.monitor(this).subscribe(new Consumer<AppState>() {
      @Override
      public void accept(AppState appState) {
        // Hocus, Pocus, Abracadabra!
      }
    });

    // Callback sample
    appStateMonitor = RxAppStateMonitor.create(this);
    appStateMonitor.addListener(new SampleAppStateListener());
    appStateMonitor.start();
  }

  public AppStateMonitor getAppStateMonitor() {
    return appStateMonitor;
  }

  private class SampleAppStateListener implements AppStateListener {

    @Override
    public void onAppDidEnterForeground() {
      logAndToast(FOREGROUND);
    }

    @Override
    public void onAppDidEnterBackground() {
      logAndToast(BACKGROUND);
    }
  }

  private void logAndToast(AppState appState) {
    Log.d(TAG, appState.toString());
    Toast.makeText(SampleApplication.this, TAG + ": " + appState, LENGTH_LONG).show();
  }
}