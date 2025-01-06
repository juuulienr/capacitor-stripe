package com.swipelive.capacitor.stripe;

import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
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
    stripe = new Stripe(getContext(), null);
  }

  @PluginMethod
  public void presentPaymentSheet(PluginCall call) {
    String clientSecret = call.getString("clientSecret");
    String merchantDisplayName = call.getString("merchantDisplayName");
    String customerEphemeralKeySecret = call.getString("customerEphemeralKeySecret");
    String customerId = call.getString("customerId");
    String countryCode = call.getString("countryCode");
    boolean googlePayEnabled = call.getBoolean("googlePayEnabled", false);

    if (clientSecret == null || merchantDisplayName == null || customerEphemeralKeySecret == null || customerId == null || countryCode == null) {
      call.reject("Missing required parameters");
      return;
    }

    stripe.presentPaymentSheet(clientSecret, merchantDisplayName, customerEphemeralKeySecret, customerId, countryCode, googlePayEnabled);
  }
}
