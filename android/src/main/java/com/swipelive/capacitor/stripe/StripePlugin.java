package com.swipelive.capacitor.stripe;

import androidx.activity.ComponentActivity;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.annotation.PluginMethod;
import com.getcapacitor.JSObject;
import com.stripe.android.PaymentConfiguration;

@CapacitorPlugin(name = "Stripe")
public class StripePlugin extends Plugin {

  private Stripe stripe;

  @Override
  public void load() {
    super.load();
    String publishableKey = getConfig().getString("publishableKey");
    if (publishableKey != null) {
      PaymentConfiguration.init(getContext(), publishableKey);
    }
    stripe = new Stripe((ComponentActivity) getActivity(), null);
  }

  @PluginMethod
  public void initialize(PluginCall call) {
    String publishableKey = call.getString("publishableKey");
    if (publishableKey == null) {
      call.reject("Missing publishableKey");
      return;
    }

    PaymentConfiguration.init(getContext(), publishableKey);
    call.resolve();
  }

  @PluginMethod
  public void createPaymentSheet(PluginCall call) {
    String clientSecret = call.getString("clientSecret");
    String merchantDisplayName = call.getString("merchantDisplayName");
    String customerEphemeralKeySecret = call.getString("customerEphemeralKeySecret");
    String customerId = call.getString("customerId");
    String countryCode = call.getString("countryCode");

    if (clientSecret == null || merchantDisplayName == null || customerEphemeralKeySecret == null || customerId == null || countryCode == null) {
      call.reject("Missing required parameters");
      return;
    }

    stripe.createPaymentSheet(clientSecret, merchantDisplayName, customerEphemeralKeySecret, customerId, countryCode);
  }
}