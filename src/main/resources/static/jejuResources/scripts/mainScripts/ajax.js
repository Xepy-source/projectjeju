const Ajax = class {
    static request = function (method, url, callback, fallback, formData) {
        let xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.onreadystatechange = function () {
            xhr.processData = false;
            xhr.contentType = false;
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    callback(xhr.responseText);
                } else {
                    fallback();
                }
            }
        };
        if (typeof (formData) === "undefined") {
            xhr.send();
        } else {
            xhr.send(formData);
        }
    }
}