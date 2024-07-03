const registerButton = document.getElementById("register-button");
const registerInputsList = ["register-name-input", "register-email-input", "register-password-input", "register-comfirm-password-input"];



registerButton.onclick = () => checkRegisterInputs();

function checkRegisterInputs()
{
    registerInputsList.forEach(input => {
        const inputElement = document.getElementById(input);

        //check if the value is empty
        if(checkEmpty(inputElement)){return}

        else if(input === "register-name-input")
        {
            if(checkLength(inputElement, 3, 20)){return}
        }

        //check if the email is in the correct format
        else if(input === "register-email-input")
        {if(checkEmailFormat(inputElement)){return}}

        //check if the value is between the max and min pattern
        else if(input === "register-password-input")
        {
            if(checkLength(inputElement, 6, null)){return}
        }
        else if(input === "register-comfirm-password-input")
        {
            if(checkIfPasswordsMatch(inputElement, "register-password-input")){return}
        }
    })
}