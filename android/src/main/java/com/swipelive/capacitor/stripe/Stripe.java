package com.swipelive.capacitor.stripe;

import android.app.Activity;
import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

public class Stripe {
  private final PaymentSheet paymentSheet;
  private PluginCall currentCall;

  public Stripe(@NonNull ComponentActivity activity, PluginCall call) {
    this.paymentSheet = new PaymentSheet(activity, this::onPaymentSheetResult);
    this.currentCall = call;
  }

  public void presentPaymentSheet(String clientSecret, String merchantDisplayName, String customerEphemeralKeySecret, String customerId, String countryCode) {
    PaymentSheet.CustomerConfiguration customerConfig =
      new PaymentSheet.CustomerConfiguration(customerId, customerEphemeralKeySecret);

    PaymentSheet.Configuration config = new PaymentSheet.Configuration(
      merchantDisplayName,
      customerConfig,
      new PaymentSheet.GooglePayConfiguration(
        PaymentSheet.GooglePayConfiguration.Environment.Test, countryCode
      )
    );

    paymentSheet.presentWithPaymentIntent(clientSecret, config);
  }

  private void onPaymentSheetResult(PaymentSheetResult paymentSheetResult) {
    if (currentCall == null) return;

    if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
      currentCall.resolve();
    } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
      currentCall.reject("Payment canceled by user");
    } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
      PaymentSheetResult.Failed failedResult = (PaymentSheetResult.Failed) paymentSheetResult;
      currentCall.reject("Payment failed", failedResult.getError().getMessage());
    }
  }
}