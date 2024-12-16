import Foundation
import StripePaymentSheet
import StripeApplePay

@objc public class Stripe: NSObject {
  @objc public static let shared = Stripe()

  /**
   * Initialize Stripe with a publishable key.
   */
  @objc public func initialize(publishableKey: String) {
    StripeAPI.defaultPublishableKey = publishableKey
  }

  /**
   * Configure and present the PaymentSheet with custom parameters.
   */
  @objc public func createPaymentSheet(
    clientSecret: String,
    merchantDisplayName: String,
    customerEphemeralKeySecret: String?,
    customerId: String?,
    countryCode: String?,
    applePayMerchantId: String?,
    appearance: [String: Any]?,
    paymentMethodLayout: String?,
    from viewController: UIViewController,
    completion: @escaping (String, String?) -> Void
  ) {
    var configuration = PaymentSheet.Configuration()
    configuration.merchantDisplayName = merchantDisplayName

    // Customer configuration (if provided)
    if let customerId = customerId, let customerEphemeralKeySecret = customerEphemeralKeySecret {
      configuration.customer = .init(id: customerId, ephemeralKeySecret: customerEphemeralKeySecret)
    }

    // Apple Pay configuration (if enabled)
    if let applePayMerchantId = applePayMerchantId {
      configuration.applePay = .init(merchantId: applePayMerchantId)
    }

    // Appearance configuration (if provided)
    if let appearanceOptions = appearance {
      configuration.appearance = createAppearance(from: appearanceOptions)
    }

    // Payment method layout (if provided)
    if let layout = paymentMethodLayout {
      switch layout {
      case "horizontal":
        configuration.paymentMethodLayout = .horizontal
      case "vertical":
        configuration.paymentMethodLayout = .vertical
      default:
        configuration.paymentMethodLayout = .automatic
      }
    }

    // Country code configuration (if provided)
    if let countryCode = countryCode {
      configuration.applePay?.merchantCountryCode = countryCode
    }

    // Create and present the PaymentSheet
    let paymentSheet = PaymentSheet(paymentIntentClientSecret: clientSecret, configuration: configuration)
    paymentSheet.present(from: viewController) { paymentResult in
      switch paymentResult {
      case .completed:
        completion("completed", nil)
      case .canceled:
        completion("canceled", nil)
      case .failed(let error):
        completion("failed", error.localizedDescription)
      }
    }
  }

  /**
   * Helper method to create a PaymentSheet.Appearance from provided options.
   */
  private func createAppearance(from options: [String: Any]) -> PaymentSheet.Appearance {
    var colors = PaymentSheet.Appearance.Colors()
    if let primary = options["colors"] as? [String: String], let primaryColor = primary["primary"] {
      colors.primary = UIColor(hex: primaryColor)
    }
    if let background = options["colors"] as? [String: String], let backgroundColor = background["background"] {
      colors.background = UIColor(hex: backgroundColor)
    }

    var shapes = PaymentSheet.Appearance.Shapes()
    if let cornerRadius = options["shapes"] as? [String: CGFloat], let radius = cornerRadius["cornerRadius"] {
      shapes.cornerRadius = radius
    }

    var fonts = PaymentSheet.Appearance.Fonts()
    if let fontBase = options["fonts"] as? [String: String], let baseFont = fontBase["base"] {
      fonts.base = UIFont(name: baseFont, size: 14)
    }
    if let fontHeading = options["fonts"] as? [String: String], let headingFont = fontHeading["heading"] {
      fonts.heading = UIFont(name: headingFont, size: 20)
    }

    return PaymentSheet.Appearance(colors: colors, shapes: shapes, fonts: fonts)
  }
}
