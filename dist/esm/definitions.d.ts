export interface StripePlugin {
    /**
     * Initialize the Stripe plugin with a publishable key.
     * @param options - Object containing the publishable key.
     * @returns A promise that resolves with the status of the initialization.
     */
    initialize(options: {
        publishableKey: string;
    }): Promise<{
        status: string;
    }>;
    /**
     * Present the Stripe Payment Sheet to the user.
     * @param options - Object containing the client secret and other configuration options.
     * @returns A promise that resolves with the payment status.
     */
    presentPaymentSheet(options: {
        clientSecret: string;
        merchantDisplayName: string;
        appearance?: {
            colors?: {
                primary?: string;
                background?: string;
                componentBackground?: string;
                componentBorder?: string;
                primaryText?: string;
                secondaryText?: string;
            };
            shapes?: {
                cornerRadius?: number;
            };
            fonts?: {
                base?: string;
                heading?: string;
            };
        };
        paymentMethodLayout?: 'horizontal' | 'vertical' | 'auto';
    }): Promise<{
        status: 'completed' | 'canceled' | 'failed';
    }>;
}
