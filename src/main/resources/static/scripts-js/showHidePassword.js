const passwordContainers = document.querySelectorAll(".password-container");

passwordContainers.forEach(container=>{
    container.onclick=(e)=>{
        if(e.target.classList[0] == "icon")
        {
            const input = container.querySelector("input");
            const icon = container.querySelector(".icon");

            if(input.type == "password")
            {
                input.type = "text"
                icon.src = "./images/eye.svg"

            }else{
                input.type = "password"
                icon.src = "./images/eye-slash.svg"
            }
        }
    }
})


