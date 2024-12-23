window.onload = function()
{
    validateForm();
}

function validateForm()
{
 // Add event listeners for input fields and form submission
 document.querySelectorAll("input, select").forEach(element => {
    element.addEventListener("input", validateForm);
});

//reading input
const uname = document.forms["myform"]["username"];
const password = document.getElementById("password");
const phone = document.getElementById("phone");
const confirmPassword = document.getElementById("confirmPassword");
const email = document.getElementById("email");
const userGroup = document.getElementById("userGroup");
const userRole = document.getElementById("userRole");
const gender = document.querySelector('input[name="genderGroup"]:checked');
const term = document.getElementById("term");
const submitBtn = document.getElementById("submit");

//declaring and initializing error variables
let unameErr="";let passwordErr="";let phoneErr="";
let confirmPasswordErr="";let emailErr = "";
let userGroupErr = "";let userRoleErr = "";let genderErr = "";let termErr = "";

//validating if all required fields are filled
/*const allFilled1 = fname.value!="" 
                    && password.value!="" 
                    && phone.value!="" 
                    && confirmPassword.value!="" 
                    && email.value!="" 
                    && country.value!="" 
                    && gender.value!=null;   */

//alternative Validating
let allFilled2 = [uname, password, phone, confirmPassword, email, userGroup,userRole, gender].every(field => field && field.value !== "");




   
   //controlling submit button
 if(term.checked && !term.disabled)
    submitBtn.disabled = false;
else
submitBtn.disabled = true;



}


function handleSubmit()
{
    //event.preventDefault();

    //reading form elements 
    const uname = document.forms["myform"]["username"];
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirmPassword");
    const phone = document.getElementById("phone");
    const email = document.getElementById("email");


    //declaring and initializing error variables
let unameErr="";
let passwordErr="";
let phoneErr="";
let confirmPasswordErr="";
let emailErr = "";
let genderErr = "";
let termErr = "";

 //validating each field seperately

//name field
if(uname.value.length >= 8 && uname.value.length <= 25)
{
    const nameRegx= /^[A-Za-z]+( [A-Za-z]+)*$/;
    if(!nameRegx.test(uname.value))
        unameErr = "Name must contain only alphabets";
    else
        unameErr = "";
}
    else
    unameErr= "Name must be min 8 and max 25 characters";

//password field 
if(password.value.length < 8 || password.value.length > 12)
{
    passwordErr = "Password must be min 8 and max 12 characters";
}
else
  passwordErr = "";

  //Confirm Password Field
  if(confirmPassword.value != password.value)
    confirmPasswordErr = "Password didn't matched ! ";
  else
   confirmPasswordErr = "";

   //phone field
   const phoneRegex1 = /^[+][0-9]+( [0-9]+)$/ ;
   const phoneRegex2 = /^[+][0-9]+( [0-9]{10})$/ ;
   if(phoneRegex1.test(phone.value))
   {
     if(phoneRegex2.test(phone.value))
           phoneErr = "";
    else
       phoneErr = "Enter a valid number";
   }
   else
    phoneErr = "Must include country code";
   
//email field
const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
if (!emailRegex.test(email.value)) {
    emailErr = "Please enter a valid email address";
} else {
    emailErr = "";
}

    document.getElementById("usernameErr").innerText = unameErr;
    document.getElementById("passwordErr").innerText = passwordErr;
    document.getElementById("confirmPasswordErr").innerText = confirmPasswordErr;
    document.getElementById("phoneErr").innerText = phoneErr;
    document.getElementById("emailErr").innerText = emailErr;
    
    uname.innerText = uname.value;
    password.innerText = password.value;
    confirmPassword.innerText = confirmPassword.value;
    phone.innerText = phone.value;
    email.innerText = email.value;


//redirecting to home page if form validates
if(unameErr=="" && passwordErr=="" && confirmPasswordErr==""&& phoneErr=="" && emailErr=="")
{
  return true; 
}
return false;

}

 