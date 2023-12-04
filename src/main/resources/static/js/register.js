document.addEventListener("DOMContentLoaded",() => {

    let resetButton = document.getElementById("resetButton");
    let emailSupportingText = document.getElementById("emailSupportingText");
    let passwordSupportingText = document.getElementById("passwordSupportingText");
    let usernameSupportingText = document.getElementById("usernameSupportingText");
    let nameSupportingText = document.getElementById("nameSupportingText");
    let surnameSupportingText = document.getElementById("surnameSupportingText");

    let emailControl = document.getElementById("emailControl");
    let usernameControl = document.getElementById("usernameControl");
    let passwordControl = document.getElementById("passwordControl");
    let nameControl = document.getElementById("nameControl");
    let surnameControl = document.getElementById("surnameControl");

    let emailErrorContainer = document.getElementById("emailErrorContainer");
    let usernameErrorContainer = document.getElementById("usernameErrorContainer");
    let passwordErrorContainer = document.getElementById("passwordErrorContainer");
    let nameErrorContainer = document.getElementById("nameErrorContainer");
    let surnameErrorContainer = document.getElementById("surnameErrorContainer");

    usernameControl.addEventListener("input",(event) => {
        let usernameErrors = [];
        usernameErrorContainer.innerHTML = "";
        let value = event.target.value;
        if(value.length < 3)
            usernameErrors.push("Username must be longer than 3 characters");
        if(value.length > 20)
            usernameErrors.push("Username must be shorter than 20 characters");
        for(let i = 0;i < usernameErrors.length;i++) {
            let error = usernameErrors[i];
            usernameErrorContainer.innerHTML += "<small class = 'd-block error'>" + error + "</small>";
        }
        usernameSupportingText.style.display = usernameErrors.length == 0 ? "block" : "none";
    })
    passwordControl.addEventListener("input",(event) => {
        let passwordErrors = [];
        let value = event.target.value;
        passwordErrorContainer.innerHTML = "";
        if(value.length < 3)
            passwordErrors.push("Password must be longer than 3 characters");
        for(let i = 0;i < passwordErrors.length;i++) {
            let error = passwordErrors[i];
            passwordErrorContainer.innerHTML += "<small class = 'd-block error'>" + error + "</small>";
        }
        passwordSupportingText.style.display = passwordErrors.length == 0 ? "block" : "none";
    })
    nameControl.addEventListener("input",(event) => {
        let nameErrors = [];
        let value = event.target.value;
        nameErrorContainer.innerHTML = "";
        if(value.length < 3)
            nameErrors.push("Name must be longer than 3 characters");
        if(value.length > 10)
            nameErrors.push("Name must be shorter than 10 characters");
        for(let i = 0;i < nameErrors.length;i++) {
            let error = nameErrors[i];
            nameErrorContainer.innerHTML += "<small class = 'd-block error'>" + error + "</small>";
        }
        nameSupportingText.style.display = nameErrors.length == 0 ? "block" : "none";
    })
    surnameControl.addEventListener("input",(event) => {
        let surnameErrors = [];
        let value = event.target.value;
        surnameErrorContainer.innerHTML = "";
        if(value.length < 3)
            surnameErrors.push("Surname must be longer than 3 characters");
        if(value.length > 10)
            surnameErrors.push("Surname must be shorter than 10 characters");
        for(let i = 0;i < surnameErrors.length;i++) {
            let error = surnameErrors[i];
            surnameErrorContainer.innerHTML += "<small class = 'd-block error'>" + error + "</small>";
        }
        surnameSupportingText.style.display = surnameErrors.length == 0 ? "block" : "none";
    })
    resetButton.addEventListener("click",(event) => {
        emailControl.value = "";
        usernameControl.value = "";
        passwordControl.value = "";
        nameControl.value = "";
        surnameControl.value = "";
    })
})