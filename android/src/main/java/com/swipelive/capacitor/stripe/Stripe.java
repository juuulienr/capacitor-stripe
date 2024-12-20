package com.swipelive.capacitor.stripe;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

public class Stripe {
  private final Context context;
  private PaymentSheet paymentSheet;

  public Stripe(Context context) {
    this.context = context;
  }

  public void initialize(String publishableKey) {
    PaymentConfiguration.init(context, publishableKey);
  }

  public void createPaymentSheet(
          Activity activity,
          String clientSecret,
          PaymentSheet.Configuration configuration
  ) {
    if (!(activity instanceof AppCompatActivity)) {
      throw new IllegalArgumentException("Activity must extend AppCompatActivity");
    }

    AppCompatActivity appCompatActivity = (AppCompatActivity) activity;

    paymentSheet = new PaymentSheet(appCompatActivity, result -> {
      if (result instanceof PaymentSheetResult.Completed) {
        Log.i("Stripe", "Payment completed");
      } else if (result instanceof PaymentSheetResult.Canceled) {
        Log.i("Stripe", "Payment canceled");
      } else if (result instanceof PaymentSheetResult.Failed) {
        Log.e("Stripe", "Payment failed", ((PaymentSheetResult.Failed) result).getError());
      }
    });

    paymentSheet.presentWithPaymentIntent(clientSecret, configuration);
  }
}
