import React from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';
import { Link } from 'react-router-dom';
import { useNavigate  } from 'react-router-dom';
const convertCurrency = async (amount) => {
    const url = `https://api.apilayer.com/exchangerates_data/convert?to=USD&from=VND&amount=${amount}`;
    try {
        const response = await fetch(url, {
            headers: {
                "apikey": "CaL5Dsgtc1FeF8gQFWJMxOrPtvc0euON"
            }
        });
        const data = await response.json();
        return data.result.toFixed(2);
    } catch (error) {
        console.error('Error converting currency:', error);
        throw error;
    }
};
const PayPalButton = ({ amount, userId, serviceId }) => {
    const navigate  = useNavigate();
    const handleBuyNow = async () => {
        try {
            const convertedAmount = await convertCurrency(amount);
            const response = await axios.post('http://localhost:8080/payment/create-payment', {
                amount: convertedAmount,
                currency: 'USD',
                method: 'paypal',
                intent: 'sale',
                description: 'Payment for Service',
                userId: userId,
                serviceId: serviceId
            });

            console.log('Invoice created:', response.data);
            localStorage.setItem('userId', userId);
            localStorage.setItem('serviceId', serviceId);

            // Open PayPal in a new popup window
            const paypalWindow = window.open(`${response.data}&userId=${userId}&serviceId=${serviceId}`, 'paypal', 'width=600,height=700');

            // Listen for messages from the popup
            const handleMessage = (event) => {
                if (event.origin !== window.location.origin) return;

                if (event.data.status === 'success') {
                    Swal.fire({
                        icon: 'success',
                        title: 'Payment Successful',
                        text: 'Your payment has been successfully processed.',
                        showConfirmButton: true, // Hiển thị nút "OK"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            navigate('/')
                        }
                    });
                } else if (event.data.status === 'failure') {
                    Swal.fire({
                        icon: 'error',
                        title: 'Payment Failed',
                        text: 'Your payment has been failed .',
                        showConfirmButton: true, // Hiển thị nút "OK"
                    }).then((result) => {
                        if (result.isConfirmed) {
                            navigate('/')
                        }
                    });
                }
            };

            window.addEventListener('message', handleMessage);

            // Clean up event listener when the component unmounts or the popup is closed
            const timer = setInterval(() => {
                if (paypalWindow.closed) {
                    clearInterval(timer);
                    window.removeEventListener('message', handleMessage);
                }
            }, 500);
        } catch (error) {
            console.error('Error creating invoice:', error);
        }
    };

    return <button id={"buyMovie"} onClick={handleBuyNow}>Mua Ngay</button>;
};

export default PayPalButton;
