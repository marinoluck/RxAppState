package com.jenzz.appstate;

import android.support.annotation.NonNull;

import com.jenzz.appstate.fakes.FakeApplication;
import com.jenzz.appstate.stubs.StubAppStateRecognizer;

import org.junit.Test;


import io.reactivex.observers.TestObserver;

import static com.jenzz.appstate.AppState.BACKGROUND;
import static com.jenzz.appstate.AppState.FOREGROUND;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class RxAppStateMonitorTest {

  @NonNull private final StubAppStateRecognizer stubRecognizer = new StubAppStateRecognizer();
  @NonNull private final AppStateMonitor appStateMonitor = new RxAppStateMonitor(stubRecognizer);

  @Test
  public void startsAndStopsMonitoring() {
    appStateMonitor.start();

    assertThat(stubRecognizer.isStarted()).isTrue();

    appStateMonitor.stop();

    assertThat(stubRecognizer.isStarted()).isFalse();
  }

  @Test
  public void addsAndRemovesListener() {
    AppStateListener listener = AppStateListener.NO_OP;

    appStateMonitor.addListener(listener);

    assertThat(stubRecognizer.getListeners()).contains(listener);

    appStateMonitor.removeListener(listener);

    assertThat(stubRecognizer.getListeners()).doesNotContain(listener);
  }

  @Test
  public void returnsForeground() {
    stubRecognizer.setAppState(FOREGROUND);

    assertThat(appStateMonitor.isAppInForeground()).isTrue();
    assertThat(appStateMonitor.isAppInBackground()).isFalse();
  }

  @Test
  public void returnsBackground() {
    stubRecognizer.setAppState(BACKGROUND);

    assertThat(appStateMonitor.isAppInForeground()).isFalse();
    assertThat(appStateMonitor.isAppInBackground()).isTrue();
  }

  @Test
  public void emitsAppStates() {
    FakeApplication fakeApplication = new FakeApplication();
    TestObserver<AppState> subscriber = TestObserver.create();
    RxAppStateMonitor.monitor(fakeApplication).subscribe(subscriber);

    fakeApplication.goForeground();
    fakeApplication.goBackground();

    subscriber.assertValues(FOREGROUND, BACKGROUND);
    subscriber.assertNotComplete();
  }
}