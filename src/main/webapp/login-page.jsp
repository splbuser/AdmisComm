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
    <title><fmt:message key="label.login"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">

<%@include file="jsp/jspf/user-header.jspf" %>

<section class="position-relative py-4 py-xl-5" style="width: 1200px;height: 643px;">
    <section class="position-relative py-4 py-xl-5">
        <div class="container-fluid text-center">
            <div class="row mb-5">
                <div class="col-md-8 col-xl-6 text-center mx-auto">
                    <h2 style="color: var(--bs-light);"><fmt:message key="label.login"/></h2>
                    <%--                    <jsp:useBean id="message" scope="session" class="java.lang.String"/>--%>
                    <p class="font-monospace text-danger"
                       style="font-family: 'Noto Sans', sans-serif;">${sessionScope.message}</p>
                </div>
            </div>
            <div class="row d-flex justify-content-center" style="margin: 1px;height: 400px;width: 1200px;">
                <div class="col-md-6 col-xl-4 text-center" data-aos="fade" data-aos-duration="700" data-aos-delay="200">
                    <div class="card mb-5" style="background: rgba(255,255,255,0);">
                        <div class="card-body d-flex flex-column align-items-center justify-content-lg-center"
                             style="background: #303030;border-radius: 20px;box-shadow: 0px 0px 8px 3px var(--bs-gray);">
                            <div class="bs-icon-xl bs-icon-circle bs-icon-primary bs-icon my-4"
                                 style="background: #8dc641;">
                                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                                     viewBox="0 0 16 16" class="bi bi-person">
                                    <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"></path>
                                </svg>
                            </div>
                            <form class="text-center" method="post">
                                <div class="mb-3"><input class="form-control" type="text" name="user_name"
                                                         placeholder="User name" style="border-radius: 8px;" required=""
                                                         minlength="1"></div>
                                <div class="mb-3"><input class="form-control" type="password" name="password"
                                                         placeholder="Password" style="border-radius: 8px;" required=""
                                                         minlength="1"></div>
                                <div class="mb-3">
                                    <div class="g-recaptcha" data-theme="dark"
                                         data-sitekey="6Lc9jLUhAAAAADD3kaZmCDm2jArFWMExCcUJZoBh"></div>
                                    <button class="btn btn-primary d-block w-100" type="submit"
                                            style="background: #8dc641;border-radius: 8px;" data-bs-target="Login">Login
                                    </button>
                                </div>

                            </form>
<%--                            <p class="text-muted">Forgot your password?</p>--%>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>

<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="assets/js/Table.js"></script>
<script src="assets/js/theme.js"></script>
<script src='https://www.google.com/recaptcha/api.js?hl=${cookie['lang'].value}'></script>

</body>

</html>