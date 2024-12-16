import { WebPlugin } from '@capacitor/core';
export class StripeWeb extends WebPlugin {
    constructor() {
        super({
            name: 'Stripe',
            platforms: ['web'],
        });
    }
    /**
     * Initialize Stripe with the publishable key.
     * @returns A rejected promise with an error message indicating the function is not supported on the web.
     */
    async initialize() {
        throw new Error('Stripe.initialize is not available on the web.');
    }
    /**
     * Present the Stripe Payment Sheet to the user.
     * @returns A rejected promise with an error message indicating the function is not supported on the web.
     */
    async presentPaymentSheet() {
        throw new Error('Stripe.presentPaymentSheet is not available on the web.');
    }
}
//# sourceMappingURL=web.js.map