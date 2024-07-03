const loginButton = document.getElementById("login-button");
const loginInputsList = ["login-email-input", "login-password-input"];



loginButton.onclick = () => checkLoginInputs();

function checkLoginInputs()
{
    loginInputsList.forEach(input => {
        const inputElement = document.getElementById(input);

        //check if the value is empty
        if(checkEmpty(inputElement)){return}

        //check if the email is in the correct format
        else if(input === "login-email-input")
        {if(checkEmailFormat(inputElement)){return}}

        //check if the value is between the max and min pattern
        else if(input === "login-password-input")
        {
            if(checkLength(inputElement, 6, null)){return}
        }
    })
}