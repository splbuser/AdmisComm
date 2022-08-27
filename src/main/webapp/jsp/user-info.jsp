<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<%@ page isELIgnored="false" %>

<jsp:useBean id="currentUser" scope="request" type="com.splb.model.entity.Applicant"/>
<jsp:useBean id="result" scope="request" type="com.splb.model.entity.ApplicantResult"/>
<jsp:useBean id="statement" scope="request" type="java.util.List"/>
<jsp:useBean id="faculties" scope="request" type="java.util.List"/>
<jsp:useBean id="enrollment" scope="request" type="com.splb.model.entity.Enrollment"/>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="label.userinfo"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">
<%@include file="jspf/user-header.jspf" %>

<div class="container py-4 py-xl-5" style="font-family: 'Noto Sans', sans-serif; margin-top: 80px;">
    <div class="row mb-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <h2 style="color: rgb(255,255,255);">${currentUser.firstName} ${currentUser.lastName}</h2>
            <p class="w-lg-50" style="font-style: italic;color: rgb(255,255,255);">${currentUser.email}</p>
        </div>
    </div>
    <div class="row mb-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <div class="card" data-aos="fade-up" data-aos-duration="500" data-aos-delay="1200"
                 style="background: #303030;color: rgb(255,255,255);border-radius: 32px;">
                <div class="card-body p-4">
                    <div class="row">
                        <div class="col-6">
                            <p><fmt:message key="label.city"/></p>
                        </div>
                        <div class="col-6">
                            <p class="text-center text-white-50" style="border-radius: 8px;background: #404040;">
                                ${currentUser.city}</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <p><fmt:message key="label.region"/></p>
                        </div>
                        <div class="col-6">
                            <p class="text-center text-white-50" style="border-radius: 8px;background: #404040;">
                                ${currentUser.region}</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-6">
                            <p><fmt:message key="label.educ_inst"/></p>
                        </div>
                        <div class="col-6">
                            <p class="text-center text-white-50" style="border-radius: 8px;background: #404040;">
                                ${currentUser.educationalInstitution}</p>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${resultCheck}">
                <div class="card" data-aos="fade-up" data-aos-duration="500" data-aos-delay="1200"
                     style="background: #303030;color: rgb(255,255,255);border-radius: 32px;margin-top: 20px;">
                    <div class="card-body p-4">
                        <h5 class="justify-content-xl-center align-items-xl-center card-title"><fmt:message key="label.grades"/></h5>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="subject.algebra"/></p>
                            </div>
                            <div class="col-2">
                                <p class="text-white-50"
                                   style="border-radius: 8px;background: #404040;">${result.algebra}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="subject.biology"/></p>
                            </div>
                            <div class="col-2">
                                <p class="text-white-50"
                                   style="border-radius: 8px;background: #404040;">${result.biology}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="subject.chemistry"/></p>
                            </div>
                            <div class="col-2">
                                <p class="text-white-50"
                                   style="border-radius: 8px;background: #404040;">${result.chemistry}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="subject.english"/></p>
                            </div>
                            <div class="col-2">
                                <p class="text-white-50"
                                   style="border-radius: 8px;background: #404040;">${result.english}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="subject.literature"/></p>
                            </div>
                            <div class="col-2">
                                <p class="text-white-50"
                                   style="border-radius: 8px;background: #404040;">${result.literature}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6">
                                <p><fmt:message key="subject.history"/></p>
                            </div>
                            <div class="col-2">
                                <p class="text-white-50"
                                   style="border-radius: 8px;background: #404040;">${result.worldHistory}</p>
                            </div>
                        </div>
                        <h5 class="justify-content-xl-center align-items-xl-center card-title"><fmt:message key="label.you_reg_for"/></h5>
                        <c:forEach var="faculties" items="${faculties}">
                        <p class="card-text">${faculties.name}</p>
<%--                        <div class="row">--%>
<%--                            <div class="col-4 text-center">--%>
<%--                                <p>statement.faculty.subjOne</p>--%>
<%--                            </div>--%>
<%--                            <div class="col-2">--%>
<%--                                <p class="text-white-50 justify-content-xl-center"--%>
<%--                                   style="border-radius: 8px;background: #404040;">statement.firstSubject</p>--%>
<%--                            </div>--%>
<%--                            <div class="col-4">--%>
<%--                                <p>statement.faculty.subjTwo</p>--%>
<%--                            </div>--%>
<%--                            <div class="col-2">--%>
<%--                                <p class="text-white-50"--%>
<%--                                   style="border-radius: 8px;background: #404040;">statement.secondSubject</p>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <div class="card" data-aos="fade-up" data-aos-duration="500" data-aos-delay="1200"
                 style="background: #303030;color: rgb(255,255,255);border-radius: 32px;margin-top: 20px;">
                <p class="card-body p-4">
                <h5 class="card-title" style="border-bottom-color: rgb(176,35,35);"><fmt:message
                        key="label.statement"/></h5>
                <p class="card-text">
                    <c:if test="${requestScope.empty_list_msg != null}">
                        <fmt:message key="text.sorry_no_avail" />
                        </c:if>
                </p>
                <c:forEach var="statement" items="${statement}">
                    <p class="card-text">${statement.faculty.name}</p>
                    <div class="row">
                        <div class="col-4 text-center">
                            <p>${statement.faculty.subjOne}</p>
                        </div>
                        <div class="col-2">
                            <p class="text-white-50 justify-content-xl-center"
                               style="border-radius: 8px;background: #404040;">${statement.firstSubject}</p>
                        </div>
                        <div class="col-4">
                            <p>${statement.faculty.subjTwo}</p>
                        </div>
                        <div class="col-2">
                            <p class="text-white-50"
                               style="border-radius: 8px;background: #404040;">${statement.secondSubject}</p>
                        </div>
                    </div>
                </c:forEach>
                <h5 class="card-title" style="border-bottom-color: rgb(176,35,35);margin-top: 20px;">
                    <fmt:message key="label.enrollment"/></h5>
                <div class="row">
                    <c:if test="${requestScope.empty_enroll != null}">
                        <fmt:message key="text.sorry_no_enroll" />
                    </c:if>
                    <div class="col-6 text-center">
                        <p>${enrollment.faculty.name}</p>
                    </div>
                    <div class="col-4">
                        <p class="text-info justify-content-xl-center"
                           style="border-radius: 8px;background: #404040;">${enrollment.status}</p>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<%--</div>--%>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="assets/js/Table.js"></script>
<script src="assets/js/theme.js"></script>
</body>

</html>