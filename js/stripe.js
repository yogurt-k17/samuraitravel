const stripe = Stripe('pk_test_51Rk2TcCHS4LgyGKU0susv8qAdSP2An10k4mSF20sZGAq8ZsK4E2FPJaXZk06hJxvZWyOuHGtCqBy8hodmqX0Bnag00Z7P9sJqS');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
	stripe.redirectToCheckout({
		sessionId: sessionId
	})
});