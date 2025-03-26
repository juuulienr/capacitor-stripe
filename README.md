# @swipelive/capacitor-stripe

Plugin Capacitor pour l'intégration de Stripe dans vos applications mobiles. Ce plugin permet d'implémenter facilement des paiements sécurisés via Stripe Payment Sheet dans vos applications iOS et Android.

## 📋 Prérequis

- Capacitor 5.0 ou supérieur
- Un compte Stripe avec une clé publique valide
- iOS 13.0 ou supérieur
- Android API level 21 ou supérieur

## 💻 Installation

```bash
npm install @swipelive/capacitor-stripe
npx cap sync
```

## 🔑 Configuration

### Android

Aucune configuration supplémentaire n'est nécessaire pour Android.

### iOS

Aucune configuration supplémentaire n'est nécessaire pour iOS.

## 📚 API

### Initialisation

```typescript
initialize({ publishableKey: string }): Promise<{ status: string }>
```
Initialise le SDK Stripe avec votre clé publique.

### Payment Sheet

```typescript
createPaymentSheet({
  clientSecret: string,
  merchantDisplayName: string,
  appearance?: {
    colors?: {
      primary?: string,
      background?: string,
      componentBackground?: string,
      componentBorder?: string,
      primaryText?: string,
      secondaryText?: string
    },
    shapes?: {
      cornerRadius?: number
    }
  },
  paymentMethodLayout?: 'horizontal' | 'vertical' | 'auto'
}): Promise<{ status: 'completed' | 'canceled' | 'failed' }>
```

## 🚀 Exemple d'utilisation

```typescript
import { StripePlugin } from '@swipelive/capacitor-stripe';

// Initialisation
await StripePlugin.initialize({
  publishableKey: 'VOTRE_CLE_PUBLIQUE'
});

// Présenter le Payment Sheet
const result = await StripePlugin.createPaymentSheet({
  clientSecret: 'VOTRE_CLIENT_SECRET',
  merchantDisplayName: 'Votre Boutique',
  appearance: {
    colors: {
      primary: '#007AFF',
      background: '#FFFFFF',
      componentBackground: '#F8F9FA',
      componentBorder: '#E9ECEF',
      primaryText: '#212529',
      secondaryText: '#6C757D'
    },
    shapes: {
      cornerRadius: 8
    }
  },
  paymentMethodLayout: 'auto'
});

// Gérer le résultat
switch (result.status) {
  case 'completed':
    console.log('Paiement réussi');
    break;
  case 'canceled':
    console.log('Paiement annulé');
    break;
  case 'failed':
    console.log('Échec du paiement');
    break;
}
```

## 📝 Notes

- Assurez-vous d'avoir configuré correctement votre compte Stripe
- Utilisez toujours HTTPS pour la sécurité des transactions
- Testez vos paiements en mode test avant de passer en production
- Gérez correctement les erreurs et les cas d'annulation

## 🤝 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou une pull request.

## 📄 Licence

MIT
