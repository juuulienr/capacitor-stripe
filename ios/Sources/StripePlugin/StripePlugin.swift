import Capacitor
import StripePaymentSheet
import StripeApplePay

@objc(StripePlugin)
public class StripePlugin: CAPPlugin {

  /**
   * Initialize Stripe with a publishable key.
   */
  @objc func initialize(_ call: CAPPluginCall) {
    guard let publishableKey = call.getString("publishableKey") else {
      call.reject("Publishable key is required")
      return
    }

    Stripe.shared.initialize(publishableKey: publishableKey)
    call.resolve(["status": "initialized"])
  }

  /**
   * Configure and present the Stripe Payment Sheet with custom parameters.
   */
  @objc func createPaymentSheet(_ call: CAPPluginCall) {
    guard let clientSecret = call.getString("clientSecret"),
          let merchantDisplayName = call.getString("merchantDisplayName") else {
      call.reject("Client secret and merchant display name are required")
      return
    }

    let customerEphemeralKeySecret = call.getString("customerEphemeralKeySecret")
    let customerId = call.getString("customerId")
    let countryCode = call.getString("countryCode")
    let applePayMerchantId = call.getString("applePayMerchantId")
    let appearance = call.getObject("appearance")
    let paymentMethodLayout = call.getString("paymentMethodLayout")

    DispatchQueue.main.async {
      guard let viewController = self.bridge?.viewController else {
        call.reject("Unable to find view controller")
        return
      }

      Stripe.shared.createPaymentSheet(
        clientSecret: clientSecret,
        merchantDisplayName: merchantDisplayName,
        customerEphemeralKeySecret: customerEphemeralKeySecret,
        customerId: customerId,
        countryCode: countryCode,
        applePayMerchantId: applePayMerchantId,
        appearance: appearance,
        paymentMethodLayout: paymentMethodLayout,
        from: viewController
      ) { status, errorMessage in
        if status == "completed" {
          call.resolve(["status": status])
        } else if status == "failed" {
          call.reject(errorMessage ?? "An unknown error occurred")
        } else {
          call.resolve(["status": status])
        }
      }
    }
  }
}
