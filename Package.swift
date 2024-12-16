// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "SwipeliveCapacitorStripe",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "SwipeliveCapacitorStripe",
            targets: ["StripePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "StripePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/StripePlugin"),
        .testTarget(
            name: "StripePluginTests",
            dependencies: ["StripePlugin"],
            path: "ios/Tests/StripePluginTests")
    ]
)