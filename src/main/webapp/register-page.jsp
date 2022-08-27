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
    <title><fmt:message key="label.register"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<%@include file="jsp/jspf/user-header.jspf" %>


<%--<nav class="navbar navbar-dark navbar-expand-md fixed-top bg-dark py-3"--%>
<%--     style="background: var(--bs-gray-100);border-color: var(--bs-gray-100);font-family: 'Noto Sans', sans-serif;color: #0f0f0f;text-align: center;box-shadow: inset 0px 0px #ffffff;--bs-dark: #0f0f0f;--bs-dark-rgb: 15,15,15;">--%>
<%--    <div class="container-fluid">--%>
<%--        <div id="lang-bar" style="width: 100px;">--%>
<%--            <div class="dropdown"><a class="dropdown-toggle" aria-expanded="false" data-bs-toggle="dropdown" href="#"--%>
<%--                                     style="color: rgb(255,255,255);width: 100px;   text-decoration: none;">Language</a>--%>
<%--                <div class="dropdown-menu dropdown-menu-start dropdown-menu-dark" style="background: #303030;"><a--%>
<%--                        class="dropdown-item" href="#" style="color: rgb(255,255,255);"><img src="assets/img/uk.png"--%>
<%--                                                                                             style="width: 32px;">&nbsp;--%>
<%--                    Українська</a><a class="dropdown-item" href="#" style="color: rgb(255,255,255);"><img--%>
<%--                        src="assets/img/en_GB.png" style="width: 32px;">&nbsp;English</a></div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="d-none d-md-block"><a class="btn btn-success" role="button" data-bss-hover-animate="pulse"--%>
<%--                                          href="index.jsp"--%>
<%--                                          style="transform: perspective(0px);font-family: 'Noto Sans', sans-serif;width: 100px;border-radius: 12px;">Home</a>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</nav>--%>

<body style="background: #212121;">
<div class="container" style="padding-top: 50px;font-family: 'Noto Sans', sans-serif;">
    <div class="card shadow-lg o-hidden border-0 my-5" data-aos="fade" data-aos-duration="700" data-aos-delay="200"
         style="width: 934px;background: rgba(255,255,255,0);">
        <div class="card-body p-0"
             style="width: 938px;background: #303030;box-shadow: 0px 0px 8px 3px var(--bs-gray);border-radius: 20px;">
            <div class="row" style="width: 973px;">
                <div class="col-lg-7" style="width: 945.906px;">
                    <div class="p-5" style="font-family: 'Noto Sans', sans-serif;">
                        <div class="text-center" style="color: rgb(255,255,255);">
                            <h4 class="text-light mb-4" style="color: rgb(255,255,255);">Step 1. Create an Account!</h4>
                        </div>
                        <form class="user" action="Register" method="post">
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control" type="text"
                                                                          id="userNameInput" placeholder="User name*"
                                                                          name="user_name" style="border-radius: 8px;"
                                                                          required=""
                                                                          title="Alphanumeric, digits and underscore are allowed">
                                </div>
                                <div class="col-sm-6"><input class="form-control" type="email" id="EmailInput"
                                                             placeholder="e-mail*" name="email"
                                                             style="border-radius: 8px;" required="" title="local-part + @ + domain part"></div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control form-control-user"
                                                                          type="password" id="PasswordInput"
                                                                          placeholder="Password*" name="password"
                                                                          style="border-radius: 8px;" required=""
                                                                          title="4-20 characters password with digits, lowercase or uppercase letter">
                                </div>
                                <div class="col-sm-6"><input class="form-control form-control-user" type="password"
                                                             id="RepeatPasswordInput" placeholder="Repeat Password*"
                                                             name="password_repeat" style="border-radius: 8px;"
                                                             required=""
                                                             title="retype password again">
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control" type="text"
                                                                          id="firstNameInput" placeholder="First name*"
                                                                          name="first_name" style="border-radius: 8px;"
                                                                          required="">
                                </div>
                                <div class="col-sm-6"><input class="form-control" type="text" id="lastNameInput-1"
                                                             placeholder="Last name*" name="last_name"
                                                             style="border-radius: 8px;" required=""></div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control" type="text"
                                                                          id="cityInput" placeholder="City*" name="city"
                                                                          style="border-radius: 8px;" required=""></div>
                                <div class="col-sm-6"><input class="form-control" type="text" id="regionInput"
                                                             placeholder="Region*" name="region"
                                                             style="border-radius: 8px;" required=""></div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0" style="width: 100%;"><input class="form-control"
                                                                                               type="text"
                                                                                               id="EducationalinstitutionInput"
                                                                                               placeholder="Full name of the Educational Institution*"
                                                                                               name="educational_institution"
                                                                                               style="border-radius: 8px;"
                                                                                               required="">
                                </div>
                            </div>
                            <div class="mb-3">
                                <p class="text-light" style="font-family: 'Noto Sans', sans-serif;font-style: italic;">*
                                    All fields must be completed.</p>
                            </div>
                            <button class="btn btn-primary d-block btn-user w-100" type="submit"
                                    style="border-radius: 8px;" data-bs-target="Register">Register Account
                            </button>
                            <hr>
                        </form>
                        <div class="text-center"><a class="small" href="forgot-password.html"
                                                    style="color: rgba(255,255,255,0.5);text-decoration: none;">Forgot
                            Password?</a></div>
                        <div class="text-center"><a class="small" href="login"
                                                    style="color: rgba(255,255,255,0.5);text-decoration: none;">Already
                            have an account? Login!</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="assets/js/Table.js"></script>
</body>

</html>