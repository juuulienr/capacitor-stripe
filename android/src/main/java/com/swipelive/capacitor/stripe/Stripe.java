package com.swipelive.capacitor.stripe;

import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

public class Stripe {
  private PaymentSheet paymentSheet;
  private PaymentSheetResultCallback resultCallback;

  public void initialize(AppCompatActivity activity, String publishableKey) {
    new Handler(Looper.getMainLooper()).post(() -> {
      PaymentConfiguration.init(activity, publishableKey);
      paymentSheet = new PaymentSheet(activity, this::handlePaymentResult);
    });
  }

  public void presentPaymentSheet(String clientSecret, PaymentSheet.Configuration configuration, PaymentSheetResultCallback callback) {
    new Handler(Looper.getMainLooper()).post(() -> {
      if (paymentSheet == null) {
        callback.onError("PaymentSheet not initialized");
        return;
      }
      this.resultCallback = callback;
      paymentSheet.presentWithPaymentIntent(clientSecret, configuration);
    });
  }

  private void handlePaymentResult(PaymentSheetResult result) {
    if (resultCallback == null) return;
    if (result instanceof PaymentSheetResult.Completed) {
      resultCallback.onSuccess();
    } else if (result instanceof PaymentSheetResult.Canceled) {
      resultCallback.onCancel();
    } else if (result instanceof PaymentSheetResult.Failed) {
      resultCallback.onError(((PaymentSheetResult.Failed) result).getError().toString());
    }
  }

  public interface PaymentSheetResultCallback {
    void onSuccess();
    void onCancel();
    void onError(String error);
  }
}
