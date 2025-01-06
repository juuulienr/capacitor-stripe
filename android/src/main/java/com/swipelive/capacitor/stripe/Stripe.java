package com.swipelive.capacitor.stripe;

import android.content.Context;
import android.app.Activity;
import androidx.annotation.NonNull;
import com.getcapacitor.PluginCall;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.paymentsheet.model.PaymentSheetConfiguration;

public class Stripe {
  private final PaymentSheet paymentSheet;
  private PluginCall currentCall;

  public Stripe(@NonNull Context context, PluginCall call) {
    this.paymentSheet = new PaymentSheet((Activity) context, this::onPaymentSheetResult);
    this.currentCall = call; 
  }

  public void presentPaymentSheet(String clientSecret, String merchantDisplayName, String customerEphemeralKeySecret, String customerId, String countryCode, boolean googlePayEnabled) {
    PaymentSheet.CustomerConfiguration customerConfig =
      new PaymentSheet.CustomerConfiguration(customerId, customerEphemeralKeySecret);

    PaymentSheetConfiguration.Builder configBuilder = new PaymentSheetConfiguration.Builder(merchantDisplayName)
      .customer(customerConfig);

    if (googlePayEnabled) {
      configBuilder.googlePay(new PaymentSheet.GooglePayConfiguration(countryCode));
    }

    PaymentSheetConfiguration paymentSheetConfig = configBuilder.build();

    paymentSheet.presentWithPaymentIntent(clientSecret, paymentSheetConfig);
  }

  private void onPaymentSheetResult(PaymentSheetResult paymentSheetResult) {
    if (currentCall == null) return;

    JSObject result = new JSObject();
    switch (paymentSheetResult) {
      case PaymentSheetResult.Completed:
        result.put("status", "success");
        currentCall.resolve(result);
        break;
      case PaymentSheetResult.Canceled:
        result.put("status", "canceled");
        currentCall.reject("Payment canceled by user", result);
        break;
      case PaymentSheetResult.Failed:
        result.put("status", "failed");
        result.put("error", paymentSheetResult.getError().getMessage());
        currentCall.reject("Payment failed", result);
        break;
    }
  }
}