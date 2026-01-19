public interface PaymentGateway {
    void pay(int amount);
}


// This is the Target
// All adapters must match this format