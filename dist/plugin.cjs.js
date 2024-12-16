'use strict';

var core = require('@capacitor/core');

const Stripe = core.registerPlugin('Stripe', {
    web: () => Promise.resolve().then(function () { return web; }).then((m) => new m.StripeWeb()),
});

class StripeWeb extends core.WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    StripeWeb: StripeWeb
});

exports.Stripe = Stripe;
//# sourceMappingURL=plugin.cjs.js.map
