
# Adapter Pattern (Java) – Payment Example

This minimal project shows how a client (which knows only the `PaymentProcessor` target interface) can work with multiple third-party payment providers (adaptees) via adapters.

## Files
- `PaymentProcessor.java` – Target interface expected by the client
- `Razorpay.java` – Adaptee with an incompatible method signature
- `RazorpayAdapter.java` – Adapter that implements `PaymentProcessor` and delegates to `Razorpay`
- `Stripe.java` – Another adaptee to demonstrate extensibility
- `StripeAdapter.java` – Adapter for `Stripe`
- `ClientDemo.java` – Client code demonstrating usage

## How to Compile & Run
```bash
# Compile
javac *.java

# Run
java ClientDemo
```

### Expected Output (example)
```
Initiating checkout for amount: INR499
Razorpay: Processing INR499.0
---

Initiating checkout for amount: INR1299
Stripe: Charged INR1299.0
---
```

## Notes
- The client depends **only** on `PaymentProcessor`.
- New gateways can be supported by adding new adapters without changing client code.
