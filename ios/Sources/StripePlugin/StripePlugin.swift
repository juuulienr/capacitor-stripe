import Capacitor
import Foundation

/// Capacitor Plugin pour gérer l'intégration de Stripe
@objc(StripePlugin)
public class StripePlugin: CAPPlugin, CAPBridgedPlugin {
  public let identifier = "StripePlugin"
  public let jsName = "Stripe"
  public let pluginMethods: [CAPPluginMethod] = [
    CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
    CAPPluginMethod(name: "createPaymentSheet", returnType: CAPPluginReturnPromise),
  ]

  private let implementation = Stripe()

  /**
   * Initialize Stripe with a publishable key.
   */
  @objc func initialize(_ call: CAPPluginCall) {
    guard let publishableKey = call.getString("publishableKey") else {
      call.reject("Missing publishable key")
      return
    }

    implementation.initialize(publishableKey: publishableKey)
    call.resolve(["status": "initialized"])
  }

  /**
   * Configure and present the Stripe Payment Sheet with custom parameters.
   */
    @objc func createPaymentSheet(_ call: CAPPluginCall) {
      guard let clientSecret = call.getString("clientSecret"),
            let merchantDisplayName = call.getString("merchantDisplayName") else {
        call.reject("Missing clientSecret or merchantDisplayName")
        return
      }

      let appearance = call.getObject("appearance")
      let paymentMethodLayout = call.getString("paymentMethodLayout")
      let customerEphemeralKeySecret = call.getString("customerEphemeralKeySecret")
      let customerId = call.getString("customerId")
      let countryCode = call.getString("countryCode")
      let applePayMerchantId = call.getString("applePayMerchantId")

      DispatchQueue.main.async {
        guard let viewController = self.bridge?.viewController else {
          call.reject("Unable to access viewController")
          return
        }

        self.implementation.createPaymentSheet(
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
