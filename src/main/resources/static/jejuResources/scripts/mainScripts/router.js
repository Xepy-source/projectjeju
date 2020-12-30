Router = class {
    static resolve = function() {
        let url = new URL(window.location.href);
        let action = url.searchParams.get('action');
        if (action === null) {
            action = 'board';
        }
        let mainElement = window.document.body.querySelector('[rel="js-main"]');
        const fallback = function () {
            mainElement.innerHTML = '불러오지 못했습니다.';
        };
        let boardNameUl = window.document.body.querySelector("#board-name");
        let boardNameLi = boardNameUl.querySelectorAll("li");
        switch (action) {
            case 'create-article':
                Ajax.request('GET', 'jejuresources/parts/createArticle.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.CreateArticle.attachEvents();
                }, fallback);
                for (let i = 0; i < boardNameLi.length; i++) {
                    if (boardNameLi[i].dataset.forward !== action) {
                        boardNameLi[i].classList.remove("selected");
                    } else {
                        boardNameLi[i].classList.add("selected");
                    }
                }
                break;
            case 'modify-article':
                Ajax.request('GET', 'jejuresources/parts/modifyArticle.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.ModifyArticle.attachEvents();
                }, fallback);
                break;
            case 'all':
                Ajax.request('GET', 'jejuresources/parts/boardAll.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.BoardAll.attachEvents();
                }, fallback);
                for (let i = 0; i < boardNameLi.length; i++) {
                    if (boardNameLi[i].dataset.forward !== action) {
                        boardNameLi[i].classList.remove("selected");
                    } else {
                        boardNameLi[i].classList.add("selected");
                    }
                }
                break;
            case 'tourist-destination':
                Ajax.request('GET', 'jejuresources/parts/boardTd.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.BoardTd.attachEvents();
                }, fallback);
                for (let i = 0; i < boardNameLi.length; i++) {
                    if (boardNameLi[i].dataset.forward !== action) {
                        boardNameLi[i].classList.remove("selected");
                    } else {
                        boardNameLi[i].classList.add("selected");
                    }
                }
                break;
            case 'restaurant':
                Ajax.request('GET', 'jejuresources/parts/boardRestaurant.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.BoardRestaurant.attachEvents();
                }, fallback);
                for (let i = 0; i < boardNameLi.length; i++) {
                    if (boardNameLi[i].dataset.forward !== action) {
                        boardNameLi[i].classList.remove("selected");
                    } else {
                        boardNameLi[i].classList.add("selected");
                    }
                }
                break;
            case 'lodgment':
                Ajax.request('GET', 'jejuresources/parts/boardLodgment.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.BoardLodgment.attachEvents();
                }, fallback);
                for (let i = 0; i < boardNameLi.length; i++) {
                    if (boardNameLi[i].dataset.forward !== action) {
                        boardNameLi[i].classList.remove("selected");
                    } else {
                        boardNameLi[i].classList.add("selected");
                    }
                }
                break;
            case 'shopping':
                Ajax.request('GET', 'jejuresources/parts/boardShopping.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.BoardShopping.attachEvents();
                }, fallback);
                for (let i = 0; i < boardNameLi.length; i++) {
                    if (boardNameLi[i].dataset.forward !== action) {
                        boardNameLi[i].classList.remove("selected");
                    } else {
                        boardNameLi[i].classList.add("selected");
                    }
                }
                break;
            case 'notice':
                Ajax.request('GET', 'jejuresources/parts/boardNotice.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.BoardNotice.attachEvents();
                }, fallback);
                for (let i = 0; i < boardNameLi.length; i++) {
                    if (boardNameLi[i].dataset.forward !== action) {
                        boardNameLi[i].classList.remove("selected");
                    } else {
                        boardNameLi[i].classList.add("selected");
                    }
                }
                break;
            case 'my-info':
                Ajax.request('GET', 'jejuresources/parts/boardMyInfo.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.BoardMyInfo.attachEvents();
                }, fallback);
                for (let i = 0; i < boardNameLi.length; i++) {
                    if (boardNameLi[i].dataset.forward !== action) {
                        boardNameLi[i].classList.remove("selected");
                    } else {
                        boardNameLi[i].classList.add("selected");
                    }
                }
                break;
            default:
                Ajax.request('GET', 'resources/parts/boardAll.html', function (response) {
                    mainElement.innerHTML = response;
                    Event.BoardAll.attachEvents();
                }, fallback);
                for (let i = 0; i < boardNameLi.length; i++) {
                    if (boardNameLi[i].dataset.forward !== "all") {
                        boardNameLi[i].classList.remove("selected");
                    } else {
                        boardNameLi[i].classList.add("selected");
                    }
                }
        }
    }

    static forward = function (action) {
        let url = new URL(window.location.href);
        let actionParam = url.searchParams.get('action');
        if (actionParam !== null) {
            url.searchParams.delete('action');
        }
        url.searchParams.append('action', action);
        window.history.pushState(null, null, url.toString());
        Router.resolve();
    }
}