package com.swipelive.capacitor.stripe;

import android.content.Context;
import android.util.Log;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.paymentsheet.model.PaymentSheetConfiguration;

public class Stripe {
  private PaymentSheet paymentSheet;
  private PaymentSheetResultCallback callback;

  public Stripe(Context context) {
    paymentSheet = new PaymentSheet((PaymentSheetResult result) -> {
      if (callback != null) {
        callback.onPaymentSheetResult(result);
      }
    });
  }

  public void initialize(String publishableKey) {
    PaymentConfiguration.init(context, publishableKey);
  }

  public void presentPaymentSheet(
    PaymentSheet.Configuration configuration,
    String clientSecret,
    PaymentSheetResultCallback callback
  ) {
    this.callback = callback;
    paymentSheet.presentWithPaymentIntent(clientSecret, configuration);
  }

  public interface PaymentSheetResultCallback {
    void onPaymentSheetResult(PaymentSheetResult result);
  }
}
