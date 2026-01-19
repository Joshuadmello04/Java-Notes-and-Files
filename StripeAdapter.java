public class StripeAdapter implements PaymentGateway {

    private StripeAPI stripe = new StripeAPI();

    @Override
    public void pay(int amount) {
        System.out.println("Adapter: Converting pay() -> charge()");
        stripe.charge(amount); // adapting call
    }
}

// Client calls pay()
// Adapter converts it to Stripe’s charge()