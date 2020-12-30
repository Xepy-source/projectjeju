<%@ page import="com.project.projectjeju.vos.UserVo" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%
    Object userVoObject = session.getAttribute("UserVo");
    UserVo userVo = null;
    if (userVoObject instanceof UserVo) {
        userVo = (UserVo) userVoObject;
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>제주 여행 커뮤니티</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic+Coding&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="jejuResources/stylesheets/mainStyleSheets/mainCommon.css">
    <script defer src="jejuResources/scripts/mainScripts/ajax.js"></script>
    <script defer src="jejuResources/events/boardAllEvent.js"></script>
    <script defer src="jejuResources/events/boardLodgmentEvent.js"></script>
    <script defer src="jejuResources/events/boardMyInfoEvent.js"></script>
    <script defer src="jejuResources/events/boardNoticeEvent.js"></script>
    <script defer src="jejuResources/events/boardRestaurantEvent.js"></script>
    <script defer src="jejuResources/events/boardShoppingEvent.js"></script>
    <script defer src="jejuResources/events/boardTdEvent.js"></script>
    <script defer src="jejuResources/events/createArticleEvent.js"></script>
    <script defer src="jejuResources/events/ModifyArticleEvent.js"></script>
    <script defer src="jejuResources/scripts/mainScripts/event.js"></script>
    <script defer src="jejuResources/scripts/mainScripts/router.js"></script>
    <script defer src="jejuResources/scripts/mainScripts/common.js"></script>
</head>
<body>
<div id="top"></div>
<div id="loading">
    <img src="jejuResources/images/mainImages/loading.gif" alt="dev image">
</div>
<div id="remote" class="top-remote">
    <img src="jejuResources/images/mainImages/top_triangle.png" alt="위쪽 삼각형">
</div>
<div rel="js-heading" role="heading" class="heading-box">
    <nav class="body-item menubox">
        <div class="empty"></div>
        <a href="/"><img src="jejuResources/images/mainImages/headerLogo.png" alt="site logo"></a>
        <ul id="board-name" class="menubox menu">
            <li data-forward="all">전체사진</li>
            <li data-forward="tourist-destination">관광지</li>
            <li data-forward="restaurant">음식점</li>
            <li data-forward="lodgment">숙박</li>
            <li data-forward="shopping">쇼핑</li>
            <li data-forward="notice">이벤트/공지</li>
            <li data-forward="create-article">글쓰기</li>
            <%
                if (userVo != null) {
                    out.print("<li data-forward=\"my-info\">내 정보</li>");
                }
            %>
        </ul>
        <div class="empty"></div>
    </nav>
    <nav class="body-item sub-menubox">
        <div class="empty"></div>
        <div class="sub-menubox search-bar">
            <input type="text" maxlength="50">
            <img src="jejuResources/images/mainImages/magnifier.png" alt="돋보기">
        </div>
        <div class="sub-menubox popular-search">
            <div class="popular-search title">인기</div>
            <a href="">지도</a>
            <a href="">거문 오름</a>
            <a href="">애월 카페</a>
        </div>
        <div class="sub-menubox empty">
            <div class="sub-menubox weather-seogwipo">
                <img src="" alt="">
                <div class="weather-item">
                    <span class="weather-item city">서귀포시</span>
                    <span class="weather-item temperature"></span>
                </div>
            </div>
            <div class="sub-menubox weather-jeju">
                <img src="" alt="">
                <div class="weather-item">
                    <span class="weather-item city">제주시</span>
                    <span class="weather-item temperature"></span>
                </div>
            </div>
            <a href="https://www.visitjeju.net/kr" target="_blank"><img src="jejuResources/images/mainImages/logo.png"
                                                                        alt="visitjeju logo"></a>
        </div>
        <%
            if (userVo == null) {
                out.print("<div id=\"login\" class=\"sub-menubox login-button\">Login</div>");
            } else {
                out.print("<div id=\"logout\" class=\"sub-menubox logout-button\">Logout</div>");
            }
        %>
        <div class="empty"></div>
    </nav>
    <div id="login-form" class="body-item login">
        <div class="login-item top-container">
            <div class="top-container title">로그인</div>
            <div id="login-top-close" class="top-container close"></div>
        </div>
        <div class="login-item body-container">
            <div class="body-container input"><input id="login-item-email" type="email" placeholder="이메일" maxlength="100" autofocus>
            </div>
            <div class="body-container input"><input id="login-item-password" type="password" placeholder="비밀번호"
                                                     maxlength="100"></div>
            <div class="login-item check"></div> <!-- innerText -->
            <a href="">비밀번호를 잊으셨나요?</a>
            <input id="login-submit" type="submit" value="로그인">
        </div>
        <div class="login-item foot-container">
            <div class="foot-container text">제주도가 처음이신가요?</div>
            <div id="register" class="foot-container link">회원가입</div>
        </div>
    </div>
    <div id="register-form" class="body-item register">
        <div class="register-item top-container">
            <div class="top-container title">회원가입</div>
            <div id="register-top-close" class="top-container close"></div>
        </div>
        <div class="register-item body-container">
            <div class="body-container input">
                <label for="register-item-email">이메일</label>
                <input id="register-item-email" type="email" maxlength="100" autofocus>
            </div>
            <div class="email-check result"><!--innertext 처리--></div>
            <div class="body-container input">
                <label for="register-item-password">비밀번호</label>
                <input id="register-item-password" type="password" maxlength="100">
            </div>
            <div class="body-container input">
                <label for="register-item-passwordcheck">비밀번호 확인</label>
                <input id="register-item-passwordcheck" type="password" maxlength="100">
            </div>
            <div class="password-check result"><!--innertext 처리--></div>
            <div class="body-container input">
                <label for="register-item-name">이름</label>
                <input id="register-item-name" type="text" maxlength="10">
            </div>
            <div class="body-container input">
                <label for="register-item-nickname">닉네임</label>
                <input id="register-item-nickname" type="text" maxlength="50">
            </div>
            <div class="nickname-check result"><!--innertext 처리--></div>
            <div class="body-container input">
                <label for="register-item-contact">연락처</label>
                <input id="register-item-contact" type="tel" maxlength="11">
            </div>
            <div class="contact-check result"><!--innertext 처리--></div>
            <div class="body-container input">
                <label for="register-item-birth">생일</label>
                <input id="register-item-birth" type="date" maxlength="8">
            </div>
            <input id="register-submit" type="submit" value="회원가입">
        </div>
    </div>
</div>
<div rel="js-main" role="main"></div>
<div role="doc-footnote" class="footer-box">
    <footer>
        <a href="https://ijto.or.kr/korean/" target="_blank"><img
                src="jejuResources/images/mainImages/JejuOranizationLogo.png" alt="제주관광공사 로고"></a>
        <div class="footer-information">
            <div class="information-container">
                <a href="https://www.visitjeju.net/kr/common/privacy#" target="_blank">개인정보처리방침</a>
                <span> | </span>
                <a href="https://www.visitjeju.net/kr/common/terms#" target="_blank">이용약관</a>
            </div>
            <address>
                63122 제주특별자치도 제주시 선덕로 23 제주웰컴센터 | TEL 064 740 6000 | www.visitjeju.net
            </address>
            <span>COPYRIGHT ⓒ JEJU TOURISM ORGANIZATION. ALL RIGHT RESERVED.</span>
        </div>
    </footer>
</div>
</body>
</html>