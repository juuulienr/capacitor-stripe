var capacitorStripe = (function (exports, core) {
    'use strict';

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

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
