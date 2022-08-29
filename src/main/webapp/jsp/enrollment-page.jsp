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
    <title><fmt:message key="label.enrollment"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">

<c:set value="4" var="type"/>
<%@include file="jspf/admin-header.jspf" %>

<div class="container-fluid" style="margin-top: 75px;font-family: 'Noto Sans', sans-serif;">
    <h3 class="text-light mb-4" style="font-family: 'Noto Sans', sans-serif;text-align: center;"><fmt:message
            key="label.enrollment"/></h3>
    <div class="card shadow" style="background: rgb(99,99,99);border-radius: 20px;">
        <form method="get">
            <div class="card-header py-3">
                <button class="btn btn-primary" type="submit" style="border-radius: 8px;"><fmt:message
                        key="button.notify"/></button>
                <input type="hidden" name="action" value="notify">
            </div>
        </form>
        <div class="card-body" style="background: #888888;">
            <div class="row">
                <div class="col-md-6 text-nowrap">
                    <div id="dataTable_length" class="dataTables_length" aria-controls="dataTable">
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" aria-expanded="false"
                                    data-bs-toggle="dropdown" data-bs-auto-close="outside" type="button"
                                    style="color: var(--bs-body-color);background: rgba(13,110,253,0.2);border-style: none;border-radius: 40px;">
                                <fmt:message key="label.sort_by"/>
                            </button>
                            <div class="dropdown-menu dropdown-menu-dark text-end"
                                 style="min-width: 100px;border-radius: 8px;line-height: 40px;">
                                <div class="dropend">
                                    <button class="dropdown-toggle" aria-expanded="false" data-bs-toggle="dropdown"
                                            type="button"
                                            style="background: rgba(255,255,255,0);color: rgb(255,255,255);border-style: none;">
                                        <fmt:message key="label.faculty"/>
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-dark"
                                         style="border-radius: 8px;min-width: 60px;"><a class="dropdown-item"
                                                                                        href="?type=ASC&sortBy=byFaculty"><fmt:message
                                            key="label.sort_up"/></a><a
                                            class="dropdown-item" href="?type=DSC&sortBy=byFaculty"><fmt:message
                                            key="label.sort_down"/></a></div>
                                </div>
                                <div class="dropend">
                                    <button class="dropdown-toggle" aria-expanded="false" data-bs-toggle="dropdown"
                                            type="button"
                                            style="background: rgba(255,255,255,0);color: rgb(255,255,255);border-style: none;">
                                        <fmt:message key="label.applicant"/>
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-dark"
                                         style="border-radius: 8px;min-width: 60px;"><a class="dropdown-item"
                                                                                        href="?type=ASC&sortBy=byLastName"><fmt:message
                                            key="label.sort_up"/></a><a
                                            class="dropdown-item" href="?type=DSC&sortBy=byLastName"><fmt:message
                                            key="label.sort_down"/></a></div>
                                </div>
                                <div class="dropend">
                                    <button class="dropdown-toggle" aria-expanded="false" data-bs-toggle="dropdown"
                                            type="button"
                                            style="background: rgba(255,255,255,0);color: rgb(255,255,255);border-style: none;">
                                        <fmt:message key="label.status"/>
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-dark"
                                         style="border-radius: 8px;min-width: 60px;"><a class="dropdown-item"
                                                                                        href="?type=ASC&sortBy=byStatus">0-9</a><a
                                            class="dropdown-item" href="?type=DSC&sortBy=byStatus">9-0</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="text-md-end dataTables_filter" id="dataTable_filter"><label class="form-label"></label>
                    </div>
                </div>
            </div>
            <div class="table-responsive table mt-2" id="dataTable-1" role="grid" aria-describedby="dataTable_info">
                <table class="table my-0" id="dataTable">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.faculty"/></th>
                        <th><fmt:message key="label.applicant"/></th>
                        <th><fmt:message key="label.stat"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <jsp:useBean id="enrollment" scope="request" type="java.util.List"/>
                    <c:forEach var="enrollment" items="${enrollment}">
                        <tr>
                            <td>${enrollment.faculty.name}</td>
                            <td>${enrollment.applicant.lastName} ${enrollment.applicant.firstName}</td>
                            <td>${enrollment.status}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td><strong><fmt:message key="label.faculty"/></strong></td>
                        <td><strong><fmt:message key="label.applicant"/><br></strong></td>
                        <td><strong><fmt:message key="label.stat"/><br></strong></td>
                    </tr>
                    </tfoot>
                </table>
            </div>
            <div class="row">
                <div class="col-md-6 align-self-center">
                    <div></div>
                </div>
                <div class="col-md-6">
                    <nav class="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">
                        <ul class="pagination"></ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="assets/js/Table.js"></script>

</body>

</html>