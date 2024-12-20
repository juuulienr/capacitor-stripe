package com.swipelive.capacitor.stripe;

import android.app.Activity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.model.PaymentSheetConfiguration;

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

    PaymentSheet.Configuration configuration = new PaymentSheet.Configuration(
      merchantDisplayName
    );

    stripe.presentPaymentSheet(configuration, clientSecret, result -> {
      JSObject response = new JSObject();
      if (result instanceof PaymentSheetResult.Completed) {
        response.put("status", "completed");
        call.resolve(response);
      } else if (result instanceof PaymentSheetResult.Canceled) {
        response.put("status", "canceled");
        call.resolve(response);
      } else if (result instanceof PaymentSheetResult.Failed) {
        response.put("status", "failed");
        call.reject(((PaymentSheetResult.Failed) result).getError().getMessage());
      }
    });
  }
}
