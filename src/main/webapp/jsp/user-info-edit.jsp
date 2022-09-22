<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<jsp:useBean id="userinfo" scope="request" type="com.splb.model.entity.Applicant"/>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="label.profile_edit"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">

    <style>
        input:invalid {
            border: red solid 3px;
        }
    </style>
</head>

<body style="background: #212121;">
<%@include file="jspf/user-header.jspf" %>

<div class="container py-4 py-xl-5" style="font-family: 'Noto Sans', sans-serif; margin-top: 80px;">
    <div class="row mb-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <h2 style="color: rgb(255,255,255);"><fmt:message key="label.profile_edit"/></h2>
            <p class="w-lg-50" style="font-style: italic;color: rgb(255,255,255);">${currentUser.email}</p>
        </div>
    </div>
    <div class="row mb-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <div class="card" data-aos="fade-up" data-aos-duration="500" data-aos-delay="1200"
                 style="background: #303030;color: rgb(255,255,255);border-radius: 32px;">
                <form method="post">
                    <div class="card-header py-3">
                        <button class="btn btn-primary" type="submit" style="border-radius: 8px;"><fmt:message
                                key="label.submit"/></button>
                    </div>
                    <div class="card-body p-4">
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="label.last_name"/></p>
                            </div>
                            <div class="col-6">
                                <input type="text" id="lastname" name="last_name"
                                       class="text-center text-white-50" style="border-radius: 8px;background: #404040;"
                                       value="${userinfo.lastName}" pattern="[A-ZА-Я][a-zа-яё]{1,20}"
                                       required="" minlength="1" maxlength="20">
                            </div>
                            <c:if test="${errors.get(5)!=null}">
                                <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                    <fmt:message key="${errors.get(5)}"/>
                                </p>
                            </c:if>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="label.first_name"/></p>
                            </div>
                            <div class="col-6">
                                <input type="text" id="firstname" name="first_name"
                                       class="text-center text-white-50" style="border-radius: 8px;background: #404040;"
                                       value="${userinfo.firstName}" pattern="[A-ZА-Я][a-zа-яё]{1,20}"
                                       required="" minlength="1" maxlength="20">
                            </div>
                            <c:if test="${errors.get(4)!=null}">
                                <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                    <fmt:message key="${errors.get(4)}"/>
                                </p>
                            </c:if>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="label.city"/></p>
                            </div>
                            <div class="col-6">
                                <input type="text" id="city" name="city"
                                       class="text-center text-white-50" style="border-radius: 8px;background: #404040;"
                                       value="${userinfo.city}"
                                       required="" minlength="1" maxlength="20">
                            </div>
                            <c:if test="${errors.get(6)!=null}">
                                <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                    <fmt:message key="${errors.get(6)}"/>
                                </p>
                            </c:if>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="label.region"/></p>
                            </div>
                            <div class="col-6">
                                <input type="text" id="region" name="region"
                                       class="text-center text-white-50" style="border-radius: 8px;background: #404040;"
                                       value="${userinfo.region}" pattern="[A-ZА-Я][a-zа-яё]{1,20}"
                                       required="" minlength="1" maxlength="20">
                            </div>
                            <c:if test="${errors.get(7)!=null}">
                                <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                    <fmt:message key="${errors.get(7)}"/>
                                </p>
                            </c:if>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="label.educ_inst"/></p>
                            </div>
                            <div class="col-6">
                                <input type="text" id="educationalInstitution" name="educational_institution"
                                       class="text-center text-white-50" style="border-radius: 8px;background: #404040;"
                                       value="${userinfo.educationalInstitution}" pattern="[A-ZА-Я][a-zа-яё]{1,20}"
                                       required="" minlength="1" maxlength="30">
                            </div>
                            <c:if test="${errors.get(8)!=null}">
                                <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                    <fmt:message key="${errors.get(8)}"/>
                                </p>
                            </c:if>
                        </div>
                        <div class="col-6">
                            <c:if test="${sessionScope.user.uploaded != null}">
                                <p style="color: aqua"><a
                                        href="/AdmissionsCommittee/images/${sessionScope.user.uploaded}"
                                        style="text-decoration: none" target="_blank">
                                    <fmt:message key="label.upload_suc"/></a></p>
                            </c:if>
                        </div>
                        <c:if test="${userinfo.uploaded !=null}">
                            <div class="col-6">
                                <input type="checkbox" id="del_certificate" name="upload_status" value="Delete">
                                <label for="del_certificate"><fmt:message key="label.delete_certificate"/></label><br>
                            </div>
                        </c:if>
                    </div>
                </form>
                <c:remove var="errors" scope="session"/>
                <c:remove var="validValues" scope="session"/>
            </div>
        </div>
    </div>
</div>

<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="assets/js/Table.js"></script>
<script src="assets/js/theme.js"></script>
</body>

</html>