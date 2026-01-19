public class StripeAPI {
    public void charge(double rupees) {
        System.out.println("Stripe API: Charged INR" + rupees);
    }
}

// Stripe uses charge()
// Client wants: pay()