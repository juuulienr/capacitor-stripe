package com.swipelive.capacitor.stripe;

import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

public class Stripe {
  private PaymentSheet paymentSheet;

  public void initialize(AppCompatActivity activity, String publishableKey) {
    new Handler(Looper.getMainLooper()).post(() -> {
      PaymentConfiguration.init(activity, publishableKey);
      paymentSheet = new PaymentSheet(activity, result -> {
        // Callback will be handled in the Plugin
      });
    });
  }

  public void presentPaymentSheet(String clientSecret, PaymentSheet.Configuration configuration, PaymentSheetResultCallback callback) {
    new Handler(Looper.getMainLooper()).post(() -> {
      if (paymentSheet == null) {
        callback.onError("PaymentSheet not initialized");
        return;
      }
      paymentSheet.presentWithPaymentIntent(clientSecret, configuration);
    });
  }

  public interface PaymentSheetResultCallback {
    void onSuccess(PaymentSheetResult result);
    void onError(String error);
  }
}
