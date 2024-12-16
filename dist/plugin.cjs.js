'use strict';

var core = require('@capacitor/core');

const Stripe = core.registerPlugin('Stripe', {
    web: () => Promise.resolve().then(function () { return web; }).then((m) => new m.StripeWeb()),
});

class StripeWeb extends core.WebPlugin {
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
    async createPaymentSheet() {
        throw new Error('Stripe.presentPaymentSheet is not available on the web.');
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    StripeWeb: StripeWeb
});

exports.Stripe = Stripe;
//# sourceMappingURL=plugin.cjs.js.map
