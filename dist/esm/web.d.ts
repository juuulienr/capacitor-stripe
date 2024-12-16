import { WebPlugin } from '@capacitor/core';
import type { StripePlugin } from './definitions';
export declare class StripeWeb extends WebPlugin implements StripePlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
