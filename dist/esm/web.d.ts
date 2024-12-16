import { WebPlugin } from '@capacitor/core';
import { StripePlugin } from './definitions';
export declare class StripeWeb extends WebPlugin implements StripePlugin {
    constructor();
    /**
     * Initialize Stripe with the publishable key.
     * @returns A rejected promise with an error message indicating the function is not supported on the web.
     */
    initialize(): Promise<{
        status: string;
    }>;
    /**
     * Present the Stripe Payment Sheet to the user.
     * @returns A rejected promise with an error message indicating the function is not supported on the web.
     */
    createPaymentSheet(): Promise<{
        status: 'completed' | 'canceled' | 'failed';
    }>;
}
