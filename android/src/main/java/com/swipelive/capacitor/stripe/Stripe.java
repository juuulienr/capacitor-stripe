package com.swipelive.capacitor.stripe;

import android.content.Context;

import androidx.activity.ComponentActivity;
import androidx.lifecycle.Lifecycle;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

public class Stripe {
  private PaymentSheet paymentSheet;
  private PaymentSheetResultCallback resultCallback;
  private final Context context;

  public Stripe(Context context) {
    this.context = context;
  }

  public void initialize(String publishableKey) {
    PaymentConfiguration.init(context, publishableKey);
  }

  public void createPaymentSheet(
          ComponentActivity activity,
          String clientSecret,
          PaymentSheet.Configuration configuration,
          PaymentSheetResultCallback callback
  ) {
    resultCallback = callback;

    // Vérifie si le LifecycleOwner est dans un état correct avant d'ajouter des observateurs
    if (activity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
      paymentSheet = new PaymentSheet(activity, result -> {
        if (resultCallback != null) {
          resultCallback.onPaymentResult(result);
        }
      });

      // Appelle `presentWithPaymentIntent` dans le thread principal
      activity.runOnUiThread(() -> paymentSheet.presentWithPaymentIntent(clientSecret, configuration));
    } else {
      throw new IllegalStateException("Activity is not in a valid lifecycle state for PaymentSheet initialization.");
    }
  }

  public interface PaymentSheetResultCallback {
    void onPaymentResult(PaymentSheetResult result);
  }
}
