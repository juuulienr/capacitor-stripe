package com.swipelive.capacitor.stripe;

import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

public class Stripe {
  private PaymentSheet paymentSheet;
  private PaymentSheetResultCallback resultCallback;

  private ActivityResultLauncher<Intent> paymentLauncher;

  public void initialize(AppCompatActivity activity, String publishableKey, PaymentSheetResultCallback callback) {
    PaymentConfiguration.init(activity, publishableKey);
    resultCallback = callback;

    paymentLauncher = activity.registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
          if (paymentSheet != null) {
            paymentSheet.onPaymentSheetResult(result.getData());
          } else {
            resultCallback.onError("PaymentSheet not initialized");
          }
        }
    );

    paymentSheet = new PaymentSheet(activity, this::onPaymentSheetResult);
  }

  public void presentPaymentSheet(String clientSecret, PaymentSheet.Configuration configuration) {
    if (paymentLauncher == null) {
      resultCallback.onError("PaymentLauncher not initialized");
      return;
    }

    paymentSheet.presentWithPaymentIntent(clientSecret, configuration);
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
