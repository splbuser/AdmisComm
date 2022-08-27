<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<%@ page isELIgnored="false" %>

<%--<jsp:useBean id="user_name" scope="session" class="java.lang.String"/>--%>
<%--<jsp:useBean id="message" scope="request" class="java.lang.String"/>--%>
<jsp:useBean id="faculty" scope="request" type="java.util.List"/>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="label.register_for_faculty"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">
<%@include file="jspf/user-header.jspf" %>
<section class="py-4 py-xl-5">
    <div class="container" style="min-height: 400px;">
        <div class="text-white bg-dark border rounded border-0 p-4 p-md-5" style="height: 400px; margin-top: 80px;">
            <h2 class="fw-bold text-white mb-3"><fmt:message key="text.step"/> 3.</h2>
            <p class="mb-4"><fmt:message key="text.user_step_three"/></p>
            <p class="mb-3" style="color: #86b7fe">${requestScope.message}</p>
            <div id="faculty-list-check">
                <div class="row" style="height: 60px;">
                    <div class="col">
                        <div style="font-family: 'Noto Sans', sans-serif;">
                            <ul class="nav nav-tabs nav-fill" role="tablist">
                                <c:forEach var="faculty" items="${faculty}">
                                    <li class="nav-item" role="presentation"><a class="nav-link" role="tab"
                                                                                data-bs-toggle="tab"
                                                                                href="#tab-${faculty.id}"
                                                                                style="border-top-left-radius: 8px;border-top-right-radius: 8px;">${faculty.name}</a>
                                    </li>
                                </c:forEach>
                                <li class="nav-item" role="presentation" hidden><a class="nav-link active" role="tab"
                                                                            data-bs-toggle="tab" href="#tab-100"
                                                                            style="border-top-left-radius: 8px;border-top-right-radius: 8px;">Faculty_2</a>
                                </li>
                            </ul>

                            <div class="tab-content">
                                <c:forEach var="faculty" items="${faculty}">
                                    <div class="tab-pane" role="tabpanel" id="tab-${faculty.id}">
                                        <form method="post" style="margin: 20px;" action='<c:url value="/Reristerforthisfaculty" />'>
                                            <input type="hidden" name="faculty_id" value="${faculty.id}">
                                            <div id="subj-panel">
                                                    <div class="row">
                                                        <div class="col" style="max-width: 30%;">
                                                            <p class="font-monospace fs-4 text-light"
                                                               style="text-align: center;">${faculty.subjOne}</p>
                                                        </div>
                                                        <div class="col"><input class="form-control" type="number"
                                                                                data-bs-toggle="tooltip"
                                                                                data-bss-tooltip=""
                                                                                name="subjOne"
                                                                                style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                                                min="1" required="" max="12"
                                                                                title="Grade from 1 to 12"
                                                                                placeholder="Garde"></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col" style="max-width: 30%;">
                                                            <p class="font-monospace fs-4 text-light"
                                                               style="text-align: center;">${faculty.subjTwo}<br></p>
                                                        </div>
                                                        <div class="col"><input class="form-control" type="number"
                                                                                data-bs-toggle="tooltip"
                                                                                data-bss-tooltip=""
                                                                                name="subjTwo"
                                                                                style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                                                min="1" required="" max="12"
                                                                                title="Grade from 1 to 12"
                                                                                placeholder="Grade"></div>
                                                    </div>
                                            </div>
                                            <div id="submit-panel" class="my-3">
                                                <button class="btn btn-primary me-2" type="submit">Submit</button>
                                            </div>
                                        </form>
                                    </div>
                                </c:forEach>

                                <div class="tab-pane active" role="tabpanel" id="tab-100" hidden>
                                    <form action="Submitfaculty" method="post" style="margin: 20px;">
                                        <div id="subj-panel-1">
                                            <form method="post" action="Submitresult">
                                                <div class="row">
                                                    <div class="col" style="max-width: 30%;">
                                                        <p class="font-monospace fs-4 text-light"
                                                           style="text-align: center;">Subj_one_1</p>
                                                    </div>
                                                    <div class="col"><input class="form-control" type="number"
                                                                            data-bs-toggle="tooltip" data-bss-tooltip=""
                                                                            name="first_subject"
                                                                            style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                                            min="1" required="" max="12"
                                                                            title="Grade from 1 to 12"
                                                                            placeholder="Garde" disabled=""></div>
                                                </div>
                                                <div class="row">
                                                    <div class="col" style="max-width: 30%;">
                                                        <p class="font-monospace fs-4 text-light"
                                                           style="text-align: center;">Subj_two_2<br></p>
                                                    </div>
                                                    <div class="col"><input class="form-control" type="number"
                                                                            data-bs-toggle="tooltip" data-bss-tooltip=""
                                                                            name="second_subject"
                                                                            style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                                            min="1" required="" max="12"
                                                                            title="Grade from 1 to 12"
                                                                            placeholder="Grade" disabled=""></div>
                                                </div>
                                            </form>
                                        </div>
                                        <div id="submit-panel-1" class="my-3">
                                            <button class="btn btn-outline-primary disabled me-2" type="submit"
                                                    disabled="">Submit
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="assets/js/Table.js"></script>
<script src="assets/js/theme.js"></script>
</body>

</html>