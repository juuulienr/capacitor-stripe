package com.swipelive.capacitor.stripe;

import android.app.Activity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

@CapacitorPlugin(name = "Stripe")
public class StripePlugin extends Plugin {

  private Stripe stripe;

  @Override
  public void load() {
    stripe = new Stripe(getContext());
  }

  @PluginMethod
  public void initialize(PluginCall call) {
    String publishableKey = call.getString("publishableKey");
    if (publishableKey == null || publishableKey.isEmpty()) {
      call.reject("Missing publishable key");
      return;
    }

    stripe.initialize(publishableKey);
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

    Activity activity = getActivity();

    PaymentSheet.Configuration configuration = new PaymentSheet.Configuration(merchantDisplayName);

    stripe.createPaymentSheet(activity, clientSecret, configuration);

    call.resolve(new JSObject().put("status", "payment_sheet_presented"));
  }
}
