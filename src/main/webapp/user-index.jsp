<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="text.wellcome"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">

<%@include file="jsp/jspf/user-header.jspf" %>

<div class="container py-4 py-xl-5" style="font-family: 'Noto Sans', sans-serif;margin-top: 50px;">
    <div class="row mb-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <h2 style="color: rgb(255,255,255);"><fmt:message key="text.wellcome"/> ${sessionScope.hellouser}</h2>
            <p class="w-lg-50" style="font-style: italic;color: rgb(255,255,255);"><fmt:message
                    key="text.user_step_zero"/></p>
        </div>
    </div>
    <div class="row gy-4 row-cols-1 row-cols-md-2 row-cols-xl-3">
        <div class="col">
            <div class="card" data-aos="fade-right" data-aos-duration="500" data-aos-delay="700"
                 style="background: #303030;color: rgb(255,255,255);border-radius: 32px;">
                <div class="card-body p-4">
                    <div class="bs-icon-md bs-icon-rounded bs-icon-primary d-flex justify-content-center align-items-center d-inline-block mb-3 bs-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                             viewBox="0 0 16 16" class="bi bi-house-door">
                            <path d="M8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4.5a.5.5 0 0 0 .5-.5v-4h2v4a.5.5 0 0 0 .5.5H14a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146zM2.5 14V7.707l5.5-5.5 5.5 5.5V14H10v-4a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5v4H2.5z"></path>
                        </svg>
                    </div>
                    <h4 class="card-title"><fmt:message key="text.step"/> 1</h4>
                    <p class="card-text"><fmt:message key="text.user_step_one"/></p><a
                        class="btn btn-outline-warning disabled border rounded-pill justify-content-xl-center"
                        role="button" href="#"><fmt:message key="button.done"/></a>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card" data-aos="fade-up" data-aos-duration="500" data-aos-delay="1200"
                 style="background: #303030;color: rgb(255,255,255);border-radius: 32px;">
                <div class="card-body p-4">
                    <div class="bs-icon-md bs-icon-rounded bs-icon-primary d-flex justify-content-center align-items-center d-inline-block mb-3 bs-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                             viewBox="0 0 16 16" class="bi bi-text-center">
                            <path fill-rule="evenodd"
                                  d="M4 12.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm2-3a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm-2-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"></path>
                        </svg>
                    </div>
                    <h4 class="card-title"><fmt:message key="text.step"/> 2</h4>
                    <p class="card-text"><fmt:message key="text.user_step_two"/></p>
                    <c:if test="${not resultCheck}">
                        <a class="btn btn-warning border rounded-pill" role="button" href="Submitresult"><fmt:message
                                key="button.goforit"/></a>
                    </c:if>
                    <c:if test="${resultCheck}">
                        <a class="btn btn-outline-warning disabled border rounded-pill justify-content-xl-center"
                           role="button" href="#"><fmt:message key="button.done"/></a>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card" data-aos="fade-left" data-aos-duration="500" data-aos-delay="1600"
                 style="background: #303030;color: rgb(255,255,255);border-radius: 32px;">
                <div class="card-body p-4">
                    <div class="bs-icon-md bs-icon-rounded bs-icon-primary d-flex justify-content-center align-items-center d-inline-block mb-3 bs-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                             viewBox="0 0 16 16" class="bi bi-flag-fill">
                            <path d="M14.778.085A.5.5 0 0 1 15 .5V8a.5.5 0 0 1-.314.464L14.5 8l.186.464-.003.001-.006.003-.023.009a12.435 12.435 0 0 1-.397.15c-.264.095-.631.223-1.047.35-.816.252-1.879.523-2.71.523-.847 0-1.548-.28-2.158-.525l-.028-.01C7.68 8.71 7.14 8.5 6.5 8.5c-.7 0-1.638.23-2.437.477A19.626 19.626 0 0 0 3 9.342V15.5a.5.5 0 0 1-1 0V.5a.5.5 0 0 1 1 0v.282c.226-.079.496-.17.79-.26C4.606.272 5.67 0 6.5 0c.84 0 1.524.277 2.121.519l.043.018C9.286.788 9.828 1 10.5 1c.7 0 1.638-.23 2.437-.477a19.587 19.587 0 0 0 1.349-.476l.019-.007.004-.002h.001"></path>
                        </svg>
                    </div>
                    <h4 class="card-title"><fmt:message key="text.step"/> 3</h4>
                    <p class="card-text"><fmt:message key="text.user_step_three"/><br></p>
                    <c:if test="${not step3}">
                        <a class="btn btn-warning border rounded-pill" role="button"
                           href="Reristerforfaculty"><fmt:message key="button.goforit"/></a>
                    </c:if>
                    <c:if test="${step3}">
                        <a class="btn btn-outline-warning disabled border rounded-pill justify-content-xl-center"
                           role="button" href="#"><fmt:message key="button.done"/></a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
</body>

</html>