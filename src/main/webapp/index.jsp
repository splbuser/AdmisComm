<%@ page contentType="text/html;charset=UTF-8" %>
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
    <title><fmt:message key="label.homepage"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/material-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;opacity: 1;margin: 65px;">
<nav class="navbar navbar-dark navbar-expand-md fixed-top bg-dark py-3"
     style="background: var(--bs-gray-100);border-color: var(--bs-gray-100);font-family: 'Noto Sans', sans-serif;color: #0f0f0f;text-align: center;box-shadow: inset 0 0 #ffffff;--bs-dark: #0f0f0f;--bs-dark-rgb: 15,15,15;">
    <%--        <% String userName = (String) session.getAttribute("user_name"); %>--%>
    <div class="container-fluid">
        <div id="lang-bar" style="width: 100px;">
            <div class="dropdown"><a class="dropdown-toggle" aria-expanded="false" data-bs-toggle="dropdown" href="#"
                                     style="color: rgb(255,255,255);width: 100px;   text-decoration: none;"><fmt:message
                    key="label.language"/></a>
                <div class="dropdown-menu dropdown-menu-start dropdown-menu-dark" style="background: #303030;"><a
                        class="dropdown-item" href="?cookieLocale=uk" style="color: rgb(255,255,255);"><img
                        src="assets/img/uk.png" alt="UA"
                        style="width: 32px;"><fmt:message key="label.ukr"/></a><a class="dropdown-item"
                                                                                  href="?cookieLocale=en"
                                                                                  style="color: rgb(255,255,255);"><img
                        src="assets/img/en_GB.png" alt="EN" style="width: 32px;"><fmt:message key="label.eng"/></a>
                </div>
            </div>
        </div>
        <a class="navbar-brand d-flex align-items-center"
           style="margin: 0;height: 55px;padding: 0 0;"><span class="fs-2 fw-lighter" data-aos="fade"
                                                              data-aos-duration="1500" data-aos-delay="50"
                                                              style="font-family: 'Noto Sans', sans-serif;"><fmt:message
                key="label.project"/></span></a>
        <%--        <%@ page import="java.lang.String" %>--%>
        <%--        <% if (session.getAttribute("user") != null) { %>--%>
        <c:if test="${sessionScope.user != null}">
            <div class="d-none d-md-block">
                <a class="btn btn-dark border rounded-circle" role="button" href="user-index.jsp"><i
                        class="icon-home"></i></a>
                <a class="btn btn-outline-primary" role="button" data-bss-hover-animate="pulse"
                   href="logout"
                   style="transform: perspective(0px);font-family: 'Noto Sans', sans-serif;width: 100px;"><fmt:message
                        key="label.logout"/></a>
            </div>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <%--        <% } else {%>--%>
            <div class="d-none d-md-block"><a class="btn btn-light me-2" role="button" data-bss-hover-animate="pulse"
                                              href="Register"
                                              style="font-family: 'Noto Sans', sans-serif;width: 100px; text-align: center"><fmt:message
                    key="label.register"/></a><a
                    class="btn btn-primary" role="button" data-bss-hover-animate="pulse" href="Login"
                    style="transform: perspective(0px);font-family: 'Noto Sans', sans-serif;width: 100px;"><fmt:message
                    key="label.login"/></a></div>
            <%--        <% } %>--%>
        </c:if>
    </div>
</nav>
<div class="container-fluid py-4 py-xl-5"
     style="color: rgb(255,255,255);font-family: 'Noto Sans', sans-serif;margin: 0;">
    <div class="row mb-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <h2 data-aos="fade" data-aos-duration="1500" data-aos-delay="500" style="font-size: 28px;"><fmt:message
                    key="text.simple_steps"/><br></h2>
        </div>
    </div>
    <div class="row gy-4 row-cols-1 row-cols-md-2 row-cols-xl-3">
        <div class="col" style="background: rgba(255,255,255,0)">
            <div data-aos="fade-right" data-aos-duration="500" data-aos-delay="700" class="p-4"
                 style="border-radius: 20px;background: #303030;height: 253px;"><span
                    class="badge rounded-pill bg-primary mb-2">1 <fmt:message key="text.step"/></span>
                <h4><fmt:message key="text.register"/></h4>
                <p><fmt:message key="text.step_one"/></p>
            </div>
        </div>
        <div class="col" style="background: rgba(255,255,255,0)">
            <div data-aos="fade-up" data-aos-duration="500" data-aos-delay="1200"
                 style="border-radius: 20px;background: #303030;" class="p-4"><span
                    class="badge rounded-pill bg-primary mb-2">2 <fmt:message key="text.step"/></span>
                <h4><fmt:message key="text.choose"/></h4>
                <p><fmt:message key="text.step_two"/></p>
            </div>
        </div>
        <div class="col">
            <div data-aos="fade-left" data-aos-duration="500" data-aos-delay="1600" class="p-4"
                 style="border-radius: 20px;background: #303030;"><span
                    class="badge rounded-pill bg-primary mb-2">3 <fmt:message key="text.step"/></span>
                <h4><fmt:message key="text.enroll"/></h4>
                <p><fmt:message key="text.step_three"/></p>
            </div>
        </div>
    </div>
</div>

<%@include file="jsp/jspf/footer.jsp" %>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="assets/js/Table.js"></script>
</body>

</html>