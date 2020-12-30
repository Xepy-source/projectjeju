<%@ page import="com.study.projectjeju.vos.UserVo" %>
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
    <meta name="author" content="Xepy">
    <meta name="creator" content="Xepy">
    <meta name="description" content="Xepy's Introduce">
    <meta name="generator" content="IntelliJ">
    <meta name="keywords" content="portfolio-jeju">
    <meta name="referrer" content="no-referrer-when-downgrade">
    <meta name="robots" content="noindex, nofollow, nosnippet, noarchive, noimageindex, nocache">
    <meta name="viewport"
          content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>제주 여행 커뮤니티</title>

    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="jejuResources/stylesheets/indexStyleSheets/indexCommon.css">
    <link rel="shortcut icon" href="jejuResources/images/indexImages/shortcut_icon.png">
    <script defer src="jejuResources/scripts/mainScripts/ajax.js"></script>
    <script defer src="jejuResources/scripts/indexScripts/userServices.js"></script>
    <script defer src="jejuResources/scripts/indexScripts/index-events.js"></script>
</head>
<body>
<div id="top"></div>
<div id="loading">
    <img src="jejuResources/images/indexImages/loading.gif" alt="dev image">
</div>
<div id="remote" class="top-remote">
    <img src="jejuResources/images/indexImages/top_triangle.png" alt="위쪽 삼각형">
</div>
<div class="body-item background"></div>
<nav class="body-item menubox">
    <a href="/"><img src="jejuResources/images/indexImages/headerLogo.png" alt="site logo"></a>
    <ul id="board-list" class="menubox menu">
        <li data-forward="all"><a href="/main?action=all">전체사진</a></li>
        <li data-forward="tourist-destination"><a href="/main?action=tourist-destination">관광지</a></li>
        <li data-forward="restaurant"><a href="/main?action=restaurant">음식점</a></li>
        <li data-forward="lodgment"><a href="/main?action=lodgment">숙박</a></li>
        <li data-forward="shopping"><a href="/main?action=shopping">쇼핑</a></li>
        <li data-forward="notice"><a href="/main?action=notice">이벤트/공지</a></li>
        <%
            if (userVo != null) {
                out.print("<li data-forward=\"my-info\"><a href=\"/main?action=my-info\">내 정보</a></li>");
            }
        %>
    </ul>
</nav>
<nav class="body-item sub-menubox">
    <div class="sub-menubox search-bar">
        <input type="text" maxlength="50">
        <img src="jejuResources/images/indexImages/magnifier.png" alt="돋보기">
    </div>
    <div class="sub-menubox popular-search">
        <div class="popular-search title">인기</div>
        <a href="">지도</a>
        <a href="">거문 오름</a>
        <a href="">애월 카페</a>
    </div>
    <div class="sub-menubox empty">
        <div class="sub-menubox weather-seogwipo">
            <img src="http://openweathermap.org/img/wn/11d@2x.png" alt="weather thunderstorm">
            <div class="weather-item">
                <span class="weather-item city">서귀포시</span>
                <span class="weather-item temperature">11℃</span>
            </div>
        </div>
        <div class="sub-menubox weather-jeju">
            <img src="http://openweathermap.org/img/wn/11d@2x.png" alt="weather thunderstorm">
            <div class="weather-item">
                <span class="weather-item city">제주시</span>
                <span class="weather-item temperature">11℃</span>
            </div>
        </div>
        <a href="https://www.visitjeju.net/kr" target="_blank"><img src="jejuResources/images/indexImages/logo.png"
                                                                    alt="visitjeju logo"></a>
    </div>
    <%
        if (userVo == null) {
            out.print("<div id=\"login\" class=\"sub-menubox login-button\">Login</div>");
        } else {
            out.print("<div id=\"logout\" class=\"sub-menubox logout-button\">Logout</div>");
        }
    %>
</nav>
<div class="body-item main-title-container">
    <div class="main-title-container title1">함께 만들어가는 커뮤니티</div>
    <div class="main-title-container title2">제주 여행 커뮤니티</div>
</div>
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
<div class="body-item info">
    <div id="article_count" class="info-item subheadingbox">
        <div class="subheadingbox title">제주와 함께한 추억</div>
        <div class="subheadingbox value">56,913</div>
        <div class="subheadingbox unit">개</div>
    </div>
    <div id="user_count" class="info-item subheadingbox">
        <div class="subheadingbox title">제주를 빛나게 해주신 분</div>
        <div class="subheadingbox value">5,242</div>
        <div class="subheadingbox unit">명</div>
    </div>
<%--    <div id="" class="info-item subheadingbox">--%>
<%--        <div class="subheadingbox title">당신과 함께 빛나는 장소</div>--%>
<%--        <div class="subheadingbox value">2,110</div>--%>
<%--        <div class="subheadingbox unit">곳</div>--%>
<%--    </div>--%>
</div>
<div class="body-item bestArticle-container">
    <div class="article-item title">
        <div class="title title1">#</div>
        <div class="title title2">BEST</div>
        <div class="title title3">PHOTO</div>
    </div>
    <div class="article-item explanation">제주여행포럼에서 가장 인기가 많은 사진 10장을 소개합니다.</div>
    <div class="article-item first-line">
        <div class="article-item form-container">
            <div class="form-item rank">01</div>
            <div class="form-item photo"><img src="jejuResources/images/indexImages/sanbangsan.jpg" alt="산방산 사진"></div>
            <div class="form-item photo-title">산방산</div>
            <div class="form-item photo-explanation">봄의 제주 #유채꽃밭 #용암산</div>
            <div class="form-item photo-publisher">by Xepy</div>
        </div>
    </div>
    <div class="article-item second-line">
        <div class="article-item form-container">
            <div class="form-item rank">06</div>
            <div class="form-item photo"><img src="jejuResources/images/indexImages/sanbangsan.jpg" alt="산방산 사진"></div>
            <div class="form-item photo-title">산방산</div>
            <div class="form-item photo-explanation">봄의 제주 #유채꽃밭 #용암산</div>
            <div class="form-item photo-publisher">by Xepy</div>
        </div>
    </div>
</div>
<div class="body-item map-container">
    <div class="map-item title">
        <div class="title title1"># 제주</div>
        <div class="title title2">지도로 보기</div>
    </div>
    <div class="map-item explanation">원하는 지역의 사진들을 볼 수 있습니다.</div>
    <div class="map-item map">
        <img src="jejuResources/images/indexImages/Jeju.png" alt="제주도 지도">
        <a href="" target="_blank" class="chagui">차귀</a>
        <a href="" target="_blank" class="hankyeong">한경</a>
        <a href="" target="_blank" class="beyang">비양</a>
        <a href="" target="_blank" class="hanlim">한림</a>
        <a href="" target="_blank" class="aewol">애월</a>
        <a href="" target="_blank" class="jeju">제주</a>
        <a href="" target="_blank" class="jocheon">조천</a>
        <a href="" target="_blank" class="gujwa">구좌</a>
        <a href="" target="_blank" class="woodo">우도</a>
        <a href="" target="_blank" class="seongsan">성산</a>
        <a href="" target="_blank" class="pyoseon">표선</a>
        <a href="" target="_blank" class="namwon">남원</a>
        <a href="" target="_blank" class="seogwipo">서귀포</a>
        <a href="" target="_blank" class="joongmoon">중문</a>
        <a href="" target="_blank" class="andeok">안덕</a>
        <a href="" target="_blank" class="daejoeng">대정</a>
        <a href="" target="_blank" class="gapha">가파</a>
        <a href="" target="_blank" class="mara">마라</a>
    </div>
</div>
<div class="body-item hotplace-container">
    <div class="hotplace-item title">
        <div class="title title1">#</div>
        <div class="title title2">HOT</div>
        <div class="title title3">PLACE</div>
    </div>
    <div class="hotplace-item explanation">일주일간 가장 인기있는 장소 5곳을 소개합니다 .</div>
    <div class="hotplace-item list">
        <div class="hotplace-item form-container">
            <div class="form-item photo"><img src="jejuResources/images/indexImages/seopjicoji.jpg" alt="섭지코지 사진"></div>
            <div class="form-item photo-title">섭지코지</div>
            <div class="form-item photo-explanation">성산일출봉 옆 경치좋은 산책로</div>
            <div class="form-item photo-publisher">by Xepy</div>
        </div>
    </div>
</div>
<footer>
    <a href="https://ijto.or.kr/korean/" target="_blank"><img
            src="jejuResources/images/indexImages/JejuOranizationLogo.png" alt="제주관광공사 로고"></a>
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
</body>
</html>