package com.swipelive.capacitor.stripe;

import androidx.appcompat.app.AppCompatActivity;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

public class Stripe {
  private PaymentSheet paymentSheet;
  private PaymentSheetResultCallback resultCallback;

  public void initialize(AppCompatActivity activity, String publishableKey, PaymentSheetResultCallback callback) {
    PaymentConfiguration.init(activity, publishableKey);
    resultCallback = callback;
    paymentSheet = new PaymentSheet(activity, this::onPaymentSheetResult);
  }

  public void presentPaymentSheet(String clientSecret, PaymentSheet.Configuration configuration) {
    if (paymentSheet != null) {
      paymentSheet.presentWithPaymentIntent(clientSecret, configuration);
    } else {
      resultCallback.onError("PaymentSheet is not initialized");
    }
  }

  private void onPaymentSheetResult(PaymentSheetResult result) {
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
