function domContentLoaded() {
    setTimeout(function () {
        window.document.body.classList.remove("preload");
        attachEvents();
    }, 100);

    let remote = window.document.body.querySelector("#remote");
    let top = window.document.body.querySelector("#top");
    remote.addEventListener("click", function () {
        top.scrollIntoView({'behavior': 'smooth'});
    });
}

function onLoad() {
    setTimeout(function () {
        let loading = window.document.body.querySelector("#loading");
        loading.classList.add("invisible");

        // window.document.body.classList.remove("loading");
    }, 1000);

    function getArticleCount() {
        function callback(response) {
            let articleCount = window.document.body.querySelector("#article_count");
            let articleCountValue = articleCount.querySelector("div.subheadingbox.value");
            let userCount = window.document.body.querySelector("#user_count");
            let userCountValue = userCount.querySelector("div.subheadingbox.value");
            let result = JSON.parse(response);

            articleCountValue.innerHTML = result["ArticleCount"];
            userCountValue.innerHTML = result["UserCount"];
        }
        function fallback() {

        }
        Ajax.request("POST", "/get_count", callback, fallback);
    }

    function getJejuWeather() {
        function callback(responseText) {
            let jejuWeatherInfo = window.document.body.querySelector("div.sub-menubox.weather-jeju")
            let jejuWeatherInfoImage = jejuWeatherInfo.querySelector("img");
            let jejuWeatherInfoItem = jejuWeatherInfo.querySelector("div.weather-item");
            let jejuWeatherInfoTemp = jejuWeatherInfoItem.querySelector("span.weather-item.temperature");
            let weatherJSON = JSON.parse(responseText);

            jejuWeatherInfoImage.src = "http://openweathermap.org/img/wn/" + weatherJSON['icon'] + "@2x.png";
            jejuWeatherInfoImage.alt = weatherJSON['alt'];
            jejuWeatherInfoTemp.innerHTML = weatherJSON['temp'] + "℃";

        }

        function fallback() {

        }

        let formData = new FormData();
        formData.append("city", "jeju");
        Ajax.request("POST", "/apis/weather-info", callback, fallback, formData);
    }

    function getSeogwipoWeather() {
        function callback(responseText) {
            let seogwipoWeatherInfo = window.document.body.querySelector("div.sub-menubox.weather-seogwipo")
            let seogwipoWeatherInfoImage = seogwipoWeatherInfo.querySelector("img");
            let seogwipoWeatherInfoItem = seogwipoWeatherInfo.querySelector("div.weather-item");
            let seogwipoWeatherInfoTemp = seogwipoWeatherInfoItem.querySelector("span.weather-item.temperature");
            let weatherJSON = JSON.parse(responseText);

            seogwipoWeatherInfoImage.src = "http://openweathermap.org/img/wn/" + weatherJSON['icon'] + "@2x.png";
            seogwipoWeatherInfoImage.alt = weatherJSON['alt'];
            seogwipoWeatherInfoTemp.innerHTML = weatherJSON['temp'] + "℃";
        }

        function fallback() {

        }

        let formData = new FormData();
        formData.append("city", "seogwipo");
        Ajax.request("POST", "/apis/weather-info", callback, fallback, formData);
    }

    getJejuWeather();
    getSeogwipoWeather();
    getArticleCount();
}

function attachEvents() {
    let loginButton = window.document.body.querySelector("#login");
    let logoutButton = window.document.body.querySelector("#logout");
    let registerButton = window.document.body.querySelector("#register");

    function login() {
        let loginForm = window.document.body.querySelector("#login-form");
        loginButton.addEventListener("click", function () {
            loginForm.classList.add("visible");
        });
        let loginClose = window.document.body.querySelector("#login-top-close");
        loginClose.addEventListener("click", function () {
            loginForm.classList.remove("visible");
        });
    }

    function logout() {
        logoutButton.addEventListener("click", function () {
            if (confirm("로그아웃 하시겠습니까?") === true) {
                Ajax.request("POST", "/logout");
                window.location.reload();
            }
        });
    }

    function register() {
        let registerForm = window.document.body.querySelector("#register-form");
        registerButton.addEventListener("click", function () {
            registerForm.classList.add("visible");
        });
        let registerClose = window.document.body.querySelector("#register-top-close");
        registerClose.addEventListener("click", function () {
            registerForm.classList.remove("visible");
        });
    }

    let weatherJeju = window.document.body.querySelector("div.sub-menubox.weather-jeju");
    let weatherSeogwipo = window.document.body.querySelector("div.sub-menubox.weather-seogwipo");

    weatherJeju.addEventListener("click", function () {
        window.open("https://weather.com/ko-KR/weather/today/l/546ce05ba2ceb437acf3b42a04da1fe7f2de749171ca39839a75b29436747313");
    })

    weatherSeogwipo.addEventListener("click", function () {
        window.open("https://weather.com/ko-KR/weather/today/l/93f2d0c72772b793464e33e1e161d33d6fac6d758c4115879e1fc576352ddd35");
    })

    if (loginButton !== null) {
        login();
    }
    if (logoutButton !== null) {
        logout();
    }
    register();
}

window.document.addEventListener("DOMContentLoaded", domContentLoaded);
window.addEventListener("load", onLoad);

// function xhr(method, url, callback, fallback, formData) {
//     let xhr = new XMLHttpRequest();
//     xhr.open(method, url);
//     xhr.onreadystatechange = function () {
//         if (xhr.readyState === XMLHttpRequest.DONE) {
//             if (xhr.status >= 200 && xhr.status < 300) {
//                 callback(xhr.responseText);
//             } else {
//                 fallback();
//             }
//         }
//     };
//     if (typeof (formData) === "undefined") {
//         xhr.send();
//     } else {
//         xhr.send(formData);
//     }
// }
