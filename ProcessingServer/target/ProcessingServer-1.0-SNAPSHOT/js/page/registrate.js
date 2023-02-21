import RegistrationController from "../modules/controllers/RegistrationController.js";

function init() {
    let controller = new RegistrationController();
    controller.init();
}

$(document).ready(init);

/*let forma = document.forms["retistrate"];

forma.addEventListener("submit", function(e){
    e.preventDefault();
    let firstname = document.getElementById("firstname");
    let lastname = document.getElementById("lastname");
    let email = document.getElementById("email");
    console.log(firstname.value);
    let reg = /\w+@\w+\.\w{2,20}/
    if (!reg.test(email.value)){
        email.style.background = "red";
        email.focus();
        return
    }
    reg = /\w{6,16}/gi;
    if (!reg.test(password.value)){
        password.style.background = "red";
        password.focus();
        return
    }
    //forma.method = 'POST';
    //forma.action="http://localhost:8080/ProcessingServer/handler"
})*/

