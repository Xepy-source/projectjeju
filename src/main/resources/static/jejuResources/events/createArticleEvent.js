Event.CreateArticle = class {
    static attachEvents = function () {
        function createArticle(title, image, classification, location, content, hashtags) {
            function callback(response) {
                let result = JSON.parse(response);
                if (result["result"] === "normalized_failure") {
                    alert("양식에 맞지 않는 값이 있습니다.");
                } else if (result["result"] === "not_authorized") {
                    alert("글을 작성할 권한이 없습니다. 로그인 후 진행 해 주세요");
                } else if (result["result"] === "success") {
                    alert("게시글을 작성하였습니다.");
                    window.location.reload("/main?action=all");
                } else {
                    alert("callback : 예기치 않은 오류가 발생하여 게시글을 작성하지 못했습니다. 잠시 후 다시 시도해 주세요.");
                }
            }

            function fallback() {
                alert("fallback : 예기치 않은 오류가 발생하여 게시글을 작성하지 못했습니다. 잠시 후 다시 시도해 주세요.");
            }

            let formData = new FormData();
            formData.append("title", title);
            formData.append("image", image);
            formData.append("classification", classification);
            formData.append("location", location);
            formData.append("content", content);
            formData.append("hashtags", hashtags);
            Ajax.request("POST", "/create_article", callback, fallback, formData);
        }

        let articleForm = window.document.body.querySelector("#article-form");
        articleForm.onsubmit = function () {
            if (articleForm.elements["title"].value === "") {
                alert("제목을 입력해 주세요");
                articleForm.elements["title"].focus();
            } else if (articleForm.elements["image"].value === "") {
                alert("이미지를 등록해 주세요");
                articleForm.elements["image"].focus();
            } else if (articleForm.elements["classification"].value === "") {
                alert("분류를 선택해 주세요");
                articleForm.elements["classification"].focus();
            } else if (articleForm.elements["location"].value === "") {
                alert("장소를 선택해 주세요");
                articleForm.elements["location"].focus();
            } else if (articleForm.elements["content"].value === "") {
                alert("내용을 입력해 주세요");
                articleForm.elements["content"].focus();
            } else if (articleForm.elements["hashtags"].value === "") {
                alert("해쉬태그를 입력해 주세요");
                articleForm.elements["hashtags"].focus();
            } else {
                createArticle(
                    articleForm.elements["title"].value,
                    articleForm.elements["image"].files[0],
                    articleForm.elements["classification"].value,
                    articleForm.elements["location"].value,
                    articleForm.elements["content"].value,
                    articleForm.elements["hashtags"].value);
            }
            return false;
        }
    }
}