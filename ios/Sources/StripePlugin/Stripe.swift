import Foundation
import Stripe

@objc public class Stripe: NSObject {
  @objc public static let shared = Stripe()
  
  public func initialize(publishableKey: String) {
    StripeAPI.defaultPublishableKey = publishableKey
  }
  
  public func createPaymentSheet(clientSecret: String, merchantDisplayName: String, appearance: PaymentSheet.Appearance, paymentMethodLayout: PaymentSheet.PaymentMethodLayout) -> PaymentSheet {
    let configuration = PaymentSheet.Configuration(
      merchantDisplayName: merchantDisplayName,
      appearance: appearance,
      paymentMethodLayout: paymentMethodLayout
    )
    return PaymentSheet(paymentIntentClientSecret: clientSecret, configuration: configuration)
  }
}
