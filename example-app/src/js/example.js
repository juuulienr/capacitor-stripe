import { Stripe } from '@swipelive&#x2F;capacitor-stripe';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    Stripe.echo({ value: inputValue })
}
