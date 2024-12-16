export interface StripePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
