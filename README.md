# @swipelive/capacitor-stripe

Capacitor plugin for integrating Stripe

## Install

```bash
npm install @swipelive/capacitor-stripe
npx cap sync
```

## API

<docgen-index>

* [`initialize(...)`](#initialize)
* [`presentPaymentSheet(...)`](#presentpaymentsheet)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options: { publishableKey: string; }) => Promise<{ status: string; }>
```

Initialize the Stripe plugin with a publishable key.

| Param         | Type                                     | Description                              |
| ------------- | ---------------------------------------- | ---------------------------------------- |
| **`options`** | <code>{ publishableKey: string; }</code> | - Object containing the publishable key. |

**Returns:** <code>Promise&lt;{ status: string; }&gt;</code>

--------------------


### presentPaymentSheet(...)

```typescript
presentPaymentSheet(options: { clientSecret: string; merchantDisplayName: string; appearance?: { colors?: { primary?: string; background?: string; componentBackground?: string; componentBorder?: string; primaryText?: string; secondaryText?: string; }; shapes?: { cornerRadius?: number; }; fonts?: { base?: string; heading?: string; }; }; paymentMethodLayout?: 'horizontal' | 'vertical' | 'auto'; }) => Promise<{ status: 'completed' | 'canceled' | 'failed'; }>
```

Present the Stripe Payment Sheet to the user.

| Param         | Type                                                                                                                                                                                                                                                                                                                                                                                            | Description                                                            |
| ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------- |
| **`options`** | <code>{ clientSecret: string; merchantDisplayName: string; appearance?: { colors?: { primary?: string; background?: string; componentBackground?: string; componentBorder?: string; primaryText?: string; secondaryText?: string; }; shapes?: { cornerRadius?: number; }; fonts?: { base?: string; heading?: string; }; }; paymentMethodLayout?: 'horizontal' \| 'vertical' \| 'auto'; }</code> | - Object containing the client secret and other configuration options. |

**Returns:** <code>Promise&lt;{ status: 'completed' | 'canceled' | 'failed'; }&gt;</code>

--------------------

</docgen-api>
