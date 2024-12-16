import Capacitor
import Stripe

@objc(StripePlugin)
public class StripePlugin: CAPPlugin {
  private var paymentSheet: PaymentSheet?

  @objc func initialize(_ call: CAPPluginCall) {
    guard let publishableKey = call.getString("publishableKey") else {
      call.reject("Publishable key is required")
      return
    }
    Stripe.shared.initialize(publishableKey: publishableKey)
    Locale.preferredLanguages = ["fr"] // Configure la langue en français
    call.resolve(["status": "initialized"])
  }

  @objc func presentPaymentSheet(_ call: CAPPluginCall) {
    guard let clientSecret = call.getString("clientSecret"),
          let merchantDisplayName = call.getString("merchantDisplayName") else {
      call.reject("Client secret and merchant display name are required")
      return
    }

    let appearance = PaymentSheet.Appearance(
      colors: PaymentSheet.Appearance.Colors(primary: UIColor.systemBlue),
      shapes: PaymentSheet.Appearance.Shapes(cornerRadius: 8),
      fonts: PaymentSheet.Appearance.Fonts(base: UIFont.systemFont(ofSize: 14), heading: UIFont.boldSystemFont(ofSize: 18))
    )

    let paymentMethodLayout: PaymentSheet.PaymentMethodLayout = .horizontal // Peut être .vertical ou .auto

    DispatchQueue.main.async {
      self.paymentSheet = Stripe.shared.createPaymentSheet(
        clientSecret: clientSecret,
        merchantDisplayName: merchantDisplayName,
        appearance: appearance,
        paymentMethodLayout: paymentMethodLayout
      )
      self.paymentSheet?.present(from: self.bridge?.viewController) { paymentResult in
        switch paymentResult {
        case .completed:
          call.resolve(["status": "completed"])
        case .canceled:
          call.resolve(["status": "canceled"])
        case .failed(let error):
          call.reject(error.localizedDescription)
        }
      }
    }
  }
}
