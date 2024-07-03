function login() {
    // Get the input values
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Create the request payload
    const payload = {
        username: username,
        password: password
    };

    // Send the POST request
    fetch('/api/v1/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
        .then(response => response.json())
        .then(data => {
            // Handle the response
            document.getElementById('responseMessage').innerText = data.message || 'Login successful!';
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('responseMessage').innerText = 'Login failed!';
        });
}