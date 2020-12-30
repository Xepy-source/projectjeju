let loginSubmit = window.document.body.querySelector("#login-submit");
loginSubmit.addEventListener("click", function () {
    let loginEmailInput = window.document.body.querySelector("#login-item-email");
    let loginPasswordInput = window.document.body.querySelector("#login-item-password");

    function callback(responseText) {
        if (responseText === "SUCCESS") {
            alert("로그인 성공");
            window.location.reload();
        } else {
            let loginCheck = window.document.body.querySelector("div.login-item.check");
            loginCheck.innerText = "이메일 혹은 비밀번호가 맞지 않습니다.";
        }
    }

    function fallback() {
        alert("로그인 도중 예기치 못한 오류가 발생하였습니다. 잠시 후 다시 시도해 주시기 바랍니다.");
    }

    let formData = new FormData();
    formData.append("email", loginEmailInput.value);
    formData.append("password", loginPasswordInput.value);
    Ajax.request("POST", "/login", callback, fallback, formData);
})

let registerEmailInput = window.document.body.querySelector("#register-item-email");
let registerPasswordInput = window.document.body.querySelector("#register-item-password");
let registerPasswordCheckInput = window.document.body.querySelector("#register-item-passwordcheck");
let registerNameInput = window.document.body.querySelector("#register-item-name");
let registerNicknameInput = window.document.body.querySelector("#register-item-nickname");
let registerContactInput = window.document.body.querySelector("#register-item-contact");
let registerBirthInput = window.document.body.querySelector("#register-item-birth");

registerEmailInput.addEventListener("focusout", function () {
    let emailCheck = window.document.body.querySelector("div.email-check.result");
    let emailRegex = new RegExp("^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+(?:[a-zA-Z]{2}|aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel)$");
    if (emailRegex.test(registerEmailInput.value)) {
        function callback(responseText) {
            if (responseText === "USE_POSSIBLE") {
                if (emailCheck.classList.contains("failure")) {
                    emailCheck.classList.remove("failure");
                    emailCheck.innerText = "";
                }
                emailCheck.classList.add("success");
                emailCheck.innerText = "사용 가능한 이메일 입니다.";
            } else {
                if (emailCheck.classList.contains("success")) {
                    emailCheck.classList.remove("success");
                    emailCheck.innerText = "";
                }
                emailCheck.classList.add("failure");
                emailCheck.innerText = "사용 불가능한 이메일 입니다.";
            }
        }

        function fallback() {

        }

        let formData = new FormData();
        formData.append("email", registerEmailInput.value);
        Ajax.request("POST", "/email-check", callback, fallback, formData);
    } else {
        if (emailCheck.classList.contains("success")) {
            emailCheck.classList.remove("success");
            emailCheck.innerText = "";
        }
        emailCheck.classList.add("failure");
        emailCheck.innerText = "이메일 양식에 맞지 않습니다.";
    }

})

registerPasswordCheckInput.addEventListener("focusout", function () {
    let passwordCheck = window.document.body.querySelector("div.password-check.result");
    let passwordRegex = new RegExp("^([0-9a-zA-Z~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]{4,100})$");
    if (passwordRegex.test(registerPasswordInput.value) && passwordRegex.test(registerPasswordCheckInput.value)) {
        if (registerPasswordInput.value === registerPasswordCheckInput.value) {
            if (passwordCheck.classList.contains("failure")) {
                passwordCheck.classList.remove("failure");
                passwordCheck.innerText = "";
            }
            passwordCheck.classList.add("success");
            passwordCheck.innerText = "비밀번호가 같습니다.";
        } else {
            if (passwordCheck.classList.contains("success")) {
                passwordCheck.classList.remove("success");
                passwordCheck.innerText = "";
            }
            passwordCheck.classList.add("failure");
            passwordCheck.innerText = "비밀번호가 같지 않습니다.";
        }
    } else {
        if (passwordCheck.classList.contains("success")) {
            passwordCheck.classList.remove("success");
            passwordCheck.innerText = "";
        }
        passwordCheck.classList.add("failure");
        passwordCheck.innerText = "비밀번호 양식에 맞지 않습니다.";
    }
})

registerNicknameInput.addEventListener("focusout", function () {
    let nicknameCheck = window.document.body.querySelector("div.nickname-check.result");
    let nicknameRegex = new RegExp("^([0-9a-zA-Z가-힣]{2,10})$");
    if (nicknameRegex.test(registerNicknameInput.value)) {
        function callback(responseText) {
            if (responseText === "USE_POSSIBLE") {
                if (nicknameCheck.classList.contains("failure")) {
                    nicknameCheck.classList.remove("failure");
                    nicknameCheck.innerText = "";
                }
                nicknameCheck.classList.add("success");
                nicknameCheck.innerText = "사용 가능한 닉네임 입니다.";
            } else {
                if (nicknameCheck.classList.contains("success")) {
                    nicknameCheck.classList.remove("success");
                    nicknameCheck.innerText = "";
                }
                nicknameCheck.classList.add("failure");
                nicknameCheck.innerText = "사용 불가능한 닉네임 입니다.";
            }
        }

        function fallback() {

        }

        let formData = new FormData();
        formData.append("nickname", registerNicknameInput.value);
        Ajax.request("POST", "/nickname-check", callback, fallback, formData);
    } else {
        if (nicknameCheck.classList.contains("success")) {
            nicknameCheck.classList.remove("success");
            nicknameCheck.innerText = "";
        }
        nicknameCheck.classList.add("failure");
        nicknameCheck.innerText = "닉네임 양식에 맞지 않습니다.";
    }
})

registerContactInput.addEventListener("focusout", function () {
    let contactCheck = window.document.body.querySelector("div.contact-check.result");
    let contactRegex = new RegExp("^([0-9]{11})$");
    if (contactRegex.test(registerContactInput.value)) {
        function callback(responseText) {
            if (responseText === "USE_POSSIBLE") {
                if (contactCheck.classList.contains("failure")) {
                    contactCheck.classList.remove("failure");
                    contactCheck.innerText = "";
                }
                contactCheck.classList.add("success");
                contactCheck.innerText = "사용 가능한 연락처 입니다.";
            } else {
                if (contactCheck.classList.contains("success")) {
                    contactCheck.classList.remove("success");
                    contactCheck.innerText = "";
                }
                contactCheck.classList.add("failure");
                contactCheck.innerText = "사용 불가능한 연락처 입니다.";
            }
        }

        function fallback() {

        }

        let formData = new FormData();
        formData.append("contact", registerContactInput.value);
        Ajax.request("POST", "/contact-check", callback, fallback, formData);
    } else {
        if (contactCheck.classList.contains("success")) {
            contactCheck.classList.remove("success");
            contactCheck.innerText = "";
        }
        contactCheck.classList.add("failure");
        contactCheck.innerText = "연락처 양식에 맞지 않습니다.";
    }
})

let registerSubmitButton = window.document.body.querySelector("#register-submit");
registerSubmitButton.addEventListener("click", function () {
    let registerForm = window.document.body.querySelector("#register-form");
    let emailCheck = window.document.body.querySelector("div.email-check.result");
    let passwordCheck = window.document.body.querySelector("div.password-check.result");
    let nicknameCheck = window.document.body.querySelector("div.nickname-check.result");
    let contactCheck = window.document.body.querySelector("div.contact-check.result");

    if (emailCheck.classList.contains("success") && passwordCheck.classList.contains("success")
        && nicknameCheck.classList.contains("success") && contactCheck.classList.contains("success")) {
        function callback(responseText) {
            if (responseText === "SUCCESS") {
                alert("회원가입 성공");
                registerForm.classList.remove("visible");
                emailCheck.classList.remove("success");
                emailCheck.innerText = "";
                passwordCheck.classList.remove("success");
                passwordCheck.innerText = "";
                nicknameCheck.classList.remove("success");
                nicknameCheck.innerText = "";
                contactCheck.classList.remove("success");
                contactCheck.innerText = "";
                registerEmailInput.value = "";
                registerPasswordInput.value = "";
                registerPasswordCheckInput.value = "";
                registerNameInput.value = "";
                registerNicknameInput.value = "";
                registerContactInput.value = "";
                registerBirthInput.value = "";
            }
        }

        function fallback() {
            alert("회원가입 도중 예기치 못한 오류가 발생하였습니다. 잠시 후 다시 시도해 주시기 바랍니다.");
        }

        let formData = new FormData();
        formData.append("email", registerEmailInput.value);
        formData.append("password", registerPasswordInput.value);
        formData.append("name", registerNameInput.value);
        formData.append("nickname", registerNicknameInput.value);
        formData.append("contact", registerContactInput.value);
        formData.append("birth", registerBirthInput.value);
        Ajax.request("POST", "/register", callback, fallback, formData);
    } else if (emailCheck.classList.contains("failure")) {
        registerEmailInput.focus();
        emailCheck.classList.remove("failure");
        emailCheck.classList.add("highlight");
        setTimeout(function () {
            emailCheck.classList.remove("highlight");
        }, 1000);
    } else if (passwordCheck.classList.contains("failure")) {
        registerPasswordInput.focus();
        passwordCheck.classList.remove("failure");
        passwordCheck.classList.add("highlight");
        setTimeout(function () {
            passwordCheck.classList.remove("highlight");
        }, 1000);
    } else if (nicknameCheck.classList.contains("failure")) {
        registerNicknameInput.focus();
        nicknameCheck.classList.remove("failure");
        nicknameCheck.classList.add("highlight");
        setTimeout(function () {
            nicknameCheck.classList.remove("highlight");
        }, 1000);
    } else if (contactCheck.classList.contains("failure")) {
        registerContactInput.focus();
        contactCheck.classList.remove("failure");
        contactCheck.classList.add("highlight");
        setTimeout(function () {
            contactCheck.classList.remove("highlight");
        }, 1000);
    }
})