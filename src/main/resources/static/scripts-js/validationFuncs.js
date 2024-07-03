
function checkEmpty(input)
{
    const inputValue = input.value.trim()
    let output;
    if(inputValue === "")
    {
        setErrorFor(input, "this value cannot be empty");
        output = true;
    }else{
        setAcceptFor(input, "");
        output = false;
    }

    return output;
}

function checkEmailFormat(input)
{
    const inputValue = input.value.trim();
    const pattern = /\w+@\w+\.\w+/
    let output;

    if(pattern.test(inputValue) === false)
    {
        setErrorFor(input, "invalid email")
        output = true;

    }else{
        setAcceptFor(input, "")
        output = false;
    }

    return output
}

function checkLength(input, min, max)
{
    const inputValue = input.value.trim();
    const valueLength = inputValue.length;
    let output = true;

    if(max != null && min != null)
    {
        if(valueLength < min || valueLength > max)
        {
            setErrorFor(input, `this value must be between ${min} to ${max} characters`);
        }else
        {
            setAcceptFor(input, "");
            output = false;
        }
    }else{

        if(max != null && valueLength > max)
        {
            setErrorFor(input, `this value must be less than ${max} characters`)
        }
        else if(min != null && valueLength < min)
        {
            setErrorFor(input, `this value must be more than ${min} characters`)
        }
        else{
            setAcceptFor(input, "")
            output = false;
        }
    }

    return output;
}

function checkIfPasswordsMatch(pwToComfirm, password)
{
    const passwordElement = document.getElementById(password);
    const passwordValue = passwordElement.value.trim();
    const pwToComfirmValue = pwToComfirm.value.trim();
    let output;

    if(pwToComfirmValue === passwordValue)
    {
        setAcceptFor(pwToComfirm, "");
        output = false;
    }else{
        setErrorFor(pwToComfirm, "the passwords are not the same");
        output = true;
    }

    return output
}





function setErrorFor(input, message)
{
    let formControl = input.parentElement;
    if(formControl.className === "password-container")
    {
        formControl = formControl.parentElement;
    }
    const small = formControl.querySelector(".error-text");

    input.classList.add("input-error-style");
    small.innerText = message;
}

function setAcceptFor(input, message)
{
    let formControl = input.parentElement;
    if(formControl.className === "password-container")
    {
        formControl = formControl.parentElement;
    }
    const small = formControl.querySelector(".error-text");

    input.classList.remove("input-error-style");
    small.innerText = message;
}