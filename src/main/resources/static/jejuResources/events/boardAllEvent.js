Event.BoardAll = class {
    static attachEvents = function () {
        let mapButton = window.document.body.querySelector("[rel='map-button']");
        let mapButtonImg = mapButton.querySelector("img");
        let jejuMap = window.document.body.querySelector("div.body-item.map-container");
        mapButton.addEventListener("click", function () {
            if (jejuMap.classList.contains("invisible")) {
                jejuMap.classList.remove("invisible");
                mapButtonImg.src = "jejuResources/images/mainImages/up_triangle.png";
                mapButtonImg.alt = "위쪽 화살표";
            } else {
                jejuMap.classList.add("invisible");
                mapButtonImg.src = "jejuResources/images/mainImages/down_triangle.png";
                mapButtonImg.alt = "아래쪽 화살표";
            }
        })

        function getArticles () {
            function callback (response) {
                let articles = JSON.parse(response);
                let articleBoard = window.document.body.querySelector("div.body-item.article-board");
                if (articles["result"] === "success") {
                    for (let i = 0; i < articles["articles"].length; i++) {
                        let articleContainer = document.createElement("div");
                        let articleTopItem = document.createElement("div");
                        let userImage = document.createElement("img");
                        let userNickname = document.createElement("span");
                        let articleTitle = document.createElement("div");
                        let articleImage = document.createElement("img");
                        let articleContent = document.createElement("span");
                        let articleHashTags = document.createElement("div");
                        articleContainer.classList.add("board-item");
                        articleContainer.classList.add("article-container");
                        articleTopItem.classList.add("article-container");
                        articleTopItem.classList.add("top-item");
                        userNickname.classList.add("top-item");
                        userNickname.classList.add("user-nickname");
                        articleTitle.classList.add("article-item");
                        articleTitle.classList.add("title");
                        articleContent.classList.add("article-item");
                        articleContent.classList.add("content");
                        articleHashTags.classList.add("article-item");
                        articleHashTags.classList.add("hashtag-box");

                        userImage.src = articles["articles"][i]["writer_image"];
                        userNickname.innerText = articles["articles"][i]["writer"];
                        articleTitle.innerText = articles["articles"][i]["title"];
                        articleImage.src = articles["articles"][i]["image"];
                        articleContent.innerText = articles["articles"][i]["content"];
                        let hashTags = articles["articles"][i]["hashtag"].split(",");
                        for(let j = 0; j < hashTags.length; j++) {
                            if (j < 3) {
                                let articleHashTag = document.createElement("span");
                                articleHashTag.innerText = "#" + hashTags[i];
                                articleHashTags.appendChild(articleHashTag);
                            }
                        }

                        articleTopItem.appendChild(userImage);
                        articleTopItem.appendChild(userNickname);
                        if (articles["articles"][i]["article_host"] === "true") {
                            let spring = document.createElement("div");
                            let modifyButton = document.createElement("div");
                            let modifyImage = document.createElement("img");
                            let deleteButton = document.createElement("div");
                            let deleteImage = document.createElement("img");
                            spring.classList.add("spring");
                            modifyButton.id = "modify_article";
                            modifyButton.dataset.forward = "modifyArticle";
                            modifyButton.dataset.writerId = articles["articles"][i]["writer_id"];
                            modifyButton.dataset.articleId = articles["articles"][i]["article_id"];
                            modifyButton.classList.add("top-item");
                            modifyButton.classList.add("modify-icon");
                            deleteButton.id = "delete_article";
                            deleteButton.dataset.writerId = articles["articles"][i]["writer_id"];
                            deleteButton.dataset.articleId = articles["articles"][i]["article_id"];
                            deleteButton.classList.add("top-item");
                            deleteButton.classList.add("delete-icon");
                            modifyImage.src = "JejuResources/images/mainImages/pencil.png";
                            modifyImage.alt = "수정 아이콘";
                            deleteImage.src = "JejuResources/images/mainImages/trash.png";
                            deleteImage.alt = "삭제 아이콘";
                            articleTopItem.appendChild(spring);
                            modifyButton.appendChild(modifyImage);
                            articleTopItem.appendChild(modifyButton);
                            deleteButton.appendChild(deleteImage);
                            articleTopItem.appendChild(deleteButton);
                        }
                        articleContainer.appendChild(articleTopItem);
                        articleContainer.appendChild(articleTitle);
                        articleContainer.appendChild(articleImage);
                        articleContainer.appendChild(articleContent);
                        articleContainer.appendChild(articleHashTags);
                        articleBoard.appendChild(articleContainer);
                    }
                } else {
                    alert("게시글을 불러오지 못했습니다.");
                }
            }

            function fallback () {
                alert("예상치 못한 오류가 발생하였습니다. 잠시 후 다시 시도 해 주세요.");
            }

            let formData = new FormData();
            formData.append("classification", "all");
            formData.append("page", "1");
            Ajax.request("POST", "/board", callback, fallback, formData);
        }

        function modifyArticle() {
            if(window.document.body.querySelector("#modify_article") !== null) {
                let modifyButton = window.document.body.querySelector("#modify_article");
                let modifyImg = modifyButton.querySelector("img");
                modifyImg.addEventListener("click", function () {
                    let articleId = modifyButton.dataset.articleId;
                    let writerId = modifyButton.dataset.writerId;
                    let mainElement = window.document.body.querySelector('[rel="js-main"]');
                    Ajax.request('GET', 'jejuresources/parts/modifyArticle.html', function (response) {
                        mainElement.innerHTML = response;
                        Event.ModifyArticle.attachEvents();
                    }, function() {
                        mainElement.innerHTML = '불러오지 못했습니다.';
                    });
                });
            }
        }

        function deleteArticle() {
            if(window.document.body.querySelector("#delete_article") !== null) {
                let deleteButton = window.document.body.querySelector("#delete_article");
                let deleteImg = deleteButton.querySelector("img");
                deleteImg.addEventListener("click", function () {
                    if (confirm("정말 삭제 하시겠습니까?")) {
                        let articleId = deleteButton.dataset.articleId;
                        let writerId = deleteButton.dataset.writerId;

                        function callback(response) {
                            let result = JSON.parse(response);
                            if(result["result"] === "not_authorized") {
                                alert("접근 권한이 없습니다.");
                            } else if (result["result"] === "success") {
                                alert("게시글이 삭제되었습니다.");
                                window.location.reload();
                            } else {
                                alert("예기치 못한 이유로 삭제하지 못했습니다. 잠시 후 다시 시도해 주세요");
                            }
                        }

                        function fallback() {
                            alert("실행중 오류가 발생하였습니다. 잠시 후 다시 시도해 주세요");
                        }

                        let formData = new FormData();
                        formData.append("user_id", writerId);
                        formData.append("article_id", articleId);
                        Ajax.request("POST", "delete_article", callback, fallback, formData);
                    }
                });
            }
        }
        modifyArticle();
        deleteArticle();
        getArticles();
    }
}