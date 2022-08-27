<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>

<!DOCTYPE html>
<html lang="${cookie['lang'].value}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="label.enter_result"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">

<%@include file="jspf/user-header.jspf" %>

<section class="py-4 py-xl-5">
    <div class="container">
        <form method="post" action="Submitresult">
            <div class="text-white bg-dark border rounded border-0 p-4 p-md-5" style="margin-top: 80px;">
                <h2 class="fw-bold text-white mb-3"><fmt:message key="text.step"/> 2.</h2>
                <p class="mb-4"><fmt:message key="text.enter_result"/></p>
                <div>

                    <div class="row">
                        <div class="col" style="max-width: 30%;">
                            <p class="font-monospace fs-4 text-light" style="text-align: center;"><fmt:message key="subject.algebra"/> </p>
                        </div>
                        <div class="col"><input class="form-control" type="number" data-bs-toggle="tooltip"
                                                data-bss-tooltip="" name="algebra"
                                                style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                min="1" required="" max="12" title="<fmt:message key="text.enter_grades"/>"></div>
                    </div>
                    <div class="row">
                        <div class="col" style="max-width: 30%;">
                            <p class="font-monospace fs-4 text-light" style="text-align: center;"><fmt:message key="subject.biology"/></p>
                        </div>
                        <div class="col"><input class="form-control" type="number" data-bs-toggle="tooltip"
                                                data-bss-tooltip="" name="biology"
                                                style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                min="1" required="" max="12" title="<fmt:message key="text.enter_grades"/>"></div>
                    </div>
                    <div class="row">
                        <div class="col" style="max-width: 30%;">
                            <p class="font-monospace fs-4 text-light" style="text-align: center;"><fmt:message key="subject.chemistry"/></p>
                        </div>
                        <div class="col"><input class="form-control" type="number" data-bs-toggle="tooltip"
                                                data-bss-tooltip="" name="chemistry"
                                                style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                min="1" required="" max="12" title="<fmt:message key="text.enter_grades"/>"></div>
                    </div>
                    <div class="row">
                        <div class="col" style="max-width: 30%;">
                            <p class="font-monospace fs-4 text-light" style="text-align: center;"><fmt:message key="subject.english"/></p>
                        </div>
                        <div class="col"><input class="form-control" type="number" data-bs-toggle="tooltip"
                                                data-bss-tooltip="" name="english"
                                                style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                min="1" required="" max="12" title="<fmt:message key="text.enter_grades"/>"></div>
                    </div>
                    <div class="row">
                        <div class="col" style="max-width: 30%;">
                            <p class="font-monospace fs-4 text-light" style="text-align: center;"><fmt:message key="subject.literature"/></p>
                        </div>
                        <div class="col"><input class="form-control" type="number" data-bs-toggle="tooltip"
                                                data-bss-tooltip="" name="literature"
                                                style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                min="1" required="" max="12" title="<fmt:message key="text.enter_grades"/>"></div>
                    </div>
                    <div class="row">
                        <div class="col" style="max-width: 30%;">
                            <p class="font-monospace fs-4 text-light" style="text-align: center;"><fmt:message key="subject.history"/></p>
                        </div>
                        <div class="col"><input class="form-control" type="number" data-bs-toggle="tooltip"
                                                data-bss-tooltip="" name="history"
                                                style="border-radius: 8px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0;max-width: 100px;"
                                                min="1" required="" max="12" title="<fmt:message key="text.enter_grades"/>"></div>
                    </div>
                </div>
                <div class="my-3">
                    <button class="btn btn-primary me-2" type="submit"><fmt:message key="label.submit"/></button>
                </div>
            </div>
        </form>
    </div>
</section>

<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="assets/js/Table.js"></script>
<script src="assets/js/theme.js"></script>
</body>

</html>