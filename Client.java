public class Client {
    public static void main(String[] args) {

        // Client only knows the common interface
        PaymentGateway pay1 = new RazorpayAdapter();
        PaymentGateway pay2 = new StripeAdapter();

        checkout(pay1, 500);
        checkout(pay2, 1200);
    }

    static void checkout(PaymentGateway gateway, int amount) {
        System.out.println("\nCheckout started for INR. " + amount);
        gateway.pay(amount);  // client calls SAME method always
        System.out.println("Checkout complete ");
    }
}


// Client doesn’t care if it’s Razorpay / Stripe
// Only talks to PaymentGateway