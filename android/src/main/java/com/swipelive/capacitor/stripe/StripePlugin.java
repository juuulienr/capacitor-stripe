package com.swipelive.capacitor.stripe;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.stripe.android.paymentsheet.PaymentSheet;

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

    stripe.initialize(appCompatActivity, publishableKey, new Stripe.PaymentSheetResultCallback() {
      @Override
      public void onSuccess() {
        call.resolve(new JSObject().put("status", "initialized"));
      }

      @Override
      public void onCancel() {
        call.reject("Initialization canceled");
      }

      @Override
      public void onError(String error) {
        call.reject(error);
      }
    });
  }

  @PluginMethod
  public void createPaymentSheet(PluginCall call) {
    String clientSecret = call.getString("clientSecret");
    String merchantDisplayName = call.getString("merchantDisplayName");

    if (clientSecret == null || merchantDisplayName == null) {
      call.reject("Missing clientSecret or merchantDisplayName");
      return;
    }

    PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder(merchantDisplayName)
        .allowsDelayedPaymentMethods(true)
        .build();

    stripe.presentPaymentSheet(clientSecret, configuration);
    call.resolve(new JSObject().put("status", "PaymentSheet presented"));
  }
}
