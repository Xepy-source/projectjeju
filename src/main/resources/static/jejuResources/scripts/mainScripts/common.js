window.addEventListener('DOMContentLoaded', function () {
    setTimeout(function () {
        window.document.body.classList.remove("preload");
    }, 100);

    let remote = window.document.body.querySelector("#remote");
    let top = window.document.body.querySelector("#top");
    remote.addEventListener("click", function () {
        top.scrollIntoView({'behavior': 'smooth'});
    });
    Event.Common.attachEvents();
});

window.addEventListener('load', function () {
    setTimeout(function () {
        let loading = window.document.body.querySelector("#loading");
        loading.classList.add("invisible");

        // window.document.body.classList.remove("loading");
    }, 1000);

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

    Router.resolve();
});

window.addEventListener('popstate', function () {
    Router.resolve();
});