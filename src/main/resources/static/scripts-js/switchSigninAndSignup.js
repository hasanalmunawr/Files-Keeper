const switchToLogin = document.getElementById("signin-link");
const switchToRegister = document.getElementById("signup-link");

switchToRegister.addEventListener("click", (e)=>{
    e.preventDefault();

    document.getElementById("container").style.height = "485px";
    document.getElementById("register-form").style.marginLeft = "0%"
    document.getElementById("login-form").style.marginRight = "100%"
})

switchToLogin.addEventListener("click", (e)=>{
    e.preventDefault();

    document.getElementById("container").style.height = "330px";
    document.getElementById("register-form").style.marginLeft = "100%"
    document.getElementById("login-form").style.marginRight = "0%"
})