import Foundation
import StripePaymentSheet
import UIKit

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
    appearance: [String: Any]?,
    paymentMethodLayout: String?,
    from viewController: UIViewController,
    completion: @escaping (String, String?) -> Void
  ) {
    var configuration = PaymentSheet.Configuration()
    configuration.merchantDisplayName = merchantDisplayName

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
    var appearance = PaymentSheet.Appearance()

    // Configure colors
    if let colors = options["colors"] as? [String: String] {
      if let primaryColor = colors["primary"] {
        appearance.colors.primary = UIColor(hex: primaryColor)
      }
      if let backgroundColor = colors["background"] {
        appearance.colors.background = UIColor(hex: backgroundColor)
      }
    }

    // Configure corner radius
    if let shapes = options["shapes"] as? [String: CGFloat], let cornerRadius = shapes["cornerRadius"] {
      appearance.cornerRadius = cornerRadius
    }

    // Configure fonts
    if let fonts = options["fonts"] as? [String: String], let baseFont = fonts["base"] {
      appearance.font.base = UIFont(name: baseFont, size: 14) ?? UIFont.systemFont(ofSize: 14)
    }

    return appearance
  }
}

/**
 * Extension for UIColor to support hexadecimal strings.
 */
extension UIColor {
  convenience init(hex: String) {
    var hexSanitized = hex.trimmingCharacters(in: .whitespacesAndNewlines)
    hexSanitized = hexSanitized.replacingOccurrences(of: "#", with: "")

    var rgb: UInt64 = 0
    Scanner(string: hexSanitized).scanHexInt64(&rgb)

    let red = CGFloat((rgb & 0xFF0000) >> 16) / 255.0
    let green = CGFloat((rgb & 0x00FF00) >> 8) / 255.0
    let blue = CGFloat(rgb & 0x0000FF) / 255.0

    self.init(red: red, green: green, blue: blue, alpha: 1.0)
  }
}
