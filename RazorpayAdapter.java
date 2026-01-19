public class RazorpayAdapter implements PaymentGateway {

    private RazorPayAPI razorpay = new RazorPayAPI();

    @Override
    public void pay(int amount) {
        System.out.println("Adapter: Converting pay() -> makePayment()");
        razorpay.makePayment(amount); // adapting call
    }
}

// This clearly shows:
// Client calls pay()
// Adapter converts it to Razorpay’s makePayment()