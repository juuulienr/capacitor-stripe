package com.swipelive.capacitor.stripe;

import android.app.Activity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.stripe.android.paymentsheet.PaymentSheet;

import androidx.appcompat.app.AppCompatActivity;

@CapacitorPlugin(name = "Stripe")
public class StripePlugin extends Plugin {
  private Stripe stripe;

  @Override
  public void load() {
    stripe = new Stripe();
  }

  @PluginMethod
  public void initialize(PluginCall call) {
    String publishableKey = call.getString("publishableKey");
    if (publishableKey == null || publishableKey.isEmpty()) {
      call.reject("Missing publishable key");
      return;
    }

    Activity activity = getActivity();
    if (!(activity instanceof AppCompatActivity)) {
      call.reject("Activity must extend AppCompatActivity");
      return;
    }

    AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
    stripe.initialize(appCompatActivity, publishableKey);
    call.resolve(new JSObject().put("status", "initialized"));
  }

  @PluginMethod
  public void createPaymentSheet(PluginCall call) {
    String clientSecret = call.getString("clientSecret");
    String merchantDisplayName = call.getString("merchantDisplayName");

    if (clientSecret == null || merchantDisplayName == null) {
      call.reject("Missing clientSecret or merchantDisplayName");
      return;
    }

    PaymentSheet.Configuration configuration = new PaymentSheet.Configuration(merchantDisplayName);

    stripe.presentPaymentSheet(clientSecret, configuration, new Stripe.PaymentSheetResultCallback() {
      @Override
      public void onSuccess() {
        JSObject response = new JSObject();
        response.put("status", "completed");
        call.resolve(response);
      }

      @Override
      public void onCancel() {
        JSObject response = new JSObject();
        response.put("status", "canceled");
        call.resolve(response);
      }

      @Override
      public void onError(String error) {
        call.reject("Payment failed: " + error);
      }
    });
  }
}
