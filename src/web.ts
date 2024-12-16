import { WebPlugin } from '@capacitor/core';

import type { StripePlugin } from './definitions';

export class StripeWeb extends WebPlugin implements StripePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
