import { WebPlugin } from '@capacitor/core';
import { StripePlugin } from './definitions';

export class StripeWeb extends WebPlugin implements StripePlugin {
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
  async initialize(): Promise<{ status: string }> {
    throw new Error('Stripe.initialize is not available on the web.');
  }

  /**
   * Present the Stripe Payment Sheet to the user.
   * @returns A rejected promise with an error message indicating the function is not supported on the web.
   */
  async createPaymentSheet(): Promise<{ status: 'completed' | 'canceled' | 'failed' }> {
    throw new Error('Stripe.presentPaymentSheet is not available on the web.');
  }
}
