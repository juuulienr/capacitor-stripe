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
  private ActivityResultLauncher<Intent> paymentLauncher;

  public void initialize(AppCompatActivity activity, String publishableKey, PaymentSheetResultCallback callback) {
    PaymentConfiguration.init(activity, publishableKey);

    paymentLauncher = activity.registerForActivityResult(
        new PaymentSheet.Contract(),
        result -> {
          if (result instanceof PaymentSheetResult.Completed) {
            callback.onSuccess();
          } else if (result instanceof PaymentSheetResult.Canceled) {
            callback.onCancel();
          } else if (result instanceof PaymentSheetResult.Failed) {
            callback.onError(((PaymentSheetResult.Failed) result).getError().toString());
          }
        }
    );

    paymentSheet = new PaymentSheet(activity, paymentLauncher::launch);
  }

  public void presentPaymentSheet(String clientSecret, PaymentSheet.Configuration configuration) {
    if (paymentSheet != null) {
      paymentSheet.presentWithPaymentIntent(clientSecret, configuration);
    }
  }

  public interface PaymentSheetResultCallback {
    void onSuccess();
    void onCancel();
    void onError(String error);
  }
}
