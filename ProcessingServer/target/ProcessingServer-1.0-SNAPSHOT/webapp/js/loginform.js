let forma = document.forms["loginform"];
//
//let lastname = document.getElementById("lastname");
//let email = document.getElementById("email");
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
    forma.method = 'POST';
    forma.action="http://localhost:8080/ProcessingServer/handler"
    //alert(email.value + " " + lastname.value)
})