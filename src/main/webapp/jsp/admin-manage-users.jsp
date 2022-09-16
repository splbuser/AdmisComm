<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>
<!DOCTYPE html>
<html lang="${cookie['lang'].value}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="label.manage_applicants"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/typicons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">

<c:set value="2" var="type"/>
<%@include file="jspf/admin-header.jspf" %>

<div class="container-fluid" style="margin-top: 75px;font-family: 'Noto Sans', sans-serif;">
    <h3 class="text-light mb-4"
        style="font-family: 'Noto Sans', sans-serif;text-align: center;color: rgb(255,255,255);"><fmt:message
            key="label.manage_applicants"/></h3>
    <div class="card shadow" style="background: rgb(99,99,99);border-radius: 20px;">
        <div class="card-header py-3"></div>
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
                                        <fmt:message key="label.last_name"/>
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
                                        <fmt:message key="label.city"/>
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-dark"
                                         style="border-radius: 8px;min-width: 60px;"><a class="dropdown-item" href="?type=ASC&sortBy=byCity"><fmt:message
                                            key="label.sort_up"/></a><a
                                            class="dropdown-item" href="?type=DSC&sortBy=byCity"><fmt:message
                                            key="label.sort_down"/></a></div>
                                </div>
                                <div class="dropend">
                                    <button class="dropdown-toggle" aria-expanded="false" data-bs-toggle="dropdown"
                                            type="button"
                                            style="background: rgba(255,255,255,0);color: rgb(255,255,255);border-style: none;">
                                        <fmt:message key="label.region"/>
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-dark"
                                         style="border-radius: 8px;min-width: 60px;"><a class="dropdown-item"
                                                                                        href="?type=ASC&sortBy=byRegion"><fmt:message
                                            key="label.sort_up"/></a><a
                                            class="dropdown-item" href="?type=DSC&sortBy=byRegion"><fmt:message
                                            key="label.sort_down"/></a></div>
                                </div>
                                <div class="dropend">
                                    <button class="dropdown-toggle" aria-expanded="false" data-bs-toggle="dropdown"
                                            type="button"
                                            style="background: rgba(255,255,255,0);color: rgb(255,255,255);border-style: none;">
                                        <fmt:message key="label.enroll_status"/>
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-dark"
                                         style="border-radius: 8px;min-width: 60px;"><a class="dropdown-item"
                                                                                        href="?type=ASC&sortBy=byStatus"><fmt:message
                                            key="label.sort_up"/></a><a
                                            class="dropdown-item" href="?type=DSC&sortBy=byStatus"><fmt:message
                                            key="label.sort_down"/></a></div>
                                </div>
                            </div>
                        </div>
                        <label class="form-label text-dark">
                            <%--                            <div class="col-md-6 align-self-center">--%>
                            <p id="dataTable_info" class="dataTables_info" role="status" aria-live="polite">
                                <fmt:message key="label.showing"/> ${requestScope.currentPage}
                                <fmt:message key="label.of"/> ${requestScope.noOfPages}</p>
                            <%--                            </div>--%>
                            <form method="post" action="DisplayApplicants">
                                <c:set var="numbers" value="5,10,25,50" scope="application"/>
                                <select name="limit" class="d-inline-block form-select form-select-sm"
                                        style="background: rgb(166,166,166);width: 36px;padding-right: 8px;">
                                    <c:forEach items="${fn:split(numbers, ',')}" var="number">
                                        <option value="${number}" ${qd.number == number ? 'selected' : ''}>${number}</option>
                                    </c:forEach>
                                </select>
                                <input type="submit" value="<fmt:message key="label.show"/>"/>
                            </form>
                        </label>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="text-md-end dataTables_filter" id="dataTable_filter">
                        <form method="get"><label class="form-label">
                            <input
                                    type="search" class="form-control form-control-sm" aria-controls="dataTable"
                                    placeholder="<fmt:message key="label.search"/>" name="search"
                                    style="background: rgb(166,166,166);">
                        </label></form>
                    </div>
                </div>
            </div>
            <div class="table-responsive table mt-2" id="dataTable-1" role="grid" aria-describedby="dataTable_info">
                <table class="table my-0" id="dataTable">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.user_name"/></th>
                        <th><fmt:message key="label.first_name"/></th>
                        <th><fmt:message key="label.last_name"/></th>
                        <th><fmt:message key="label.email"/></th>
                        <th><fmt:message key="label.city"/></th>
                        <th><fmt:message key="label.region"/></th>
                        <th><fmt:message key="label.educ_inst"/></th>
                        <th><fmt:message key="label.enroll_status"/></th>
                        <th><fmt:message key="label.block_status"/></th>
                        <th><fmt:message key="label.manage"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <jsp:useBean id="applicants" scope="request" type="java.util.List"/>
                    <c:forEach var="applicants" items="${applicants}">
                        <tr>
                            <td><em class="typcn typcn-user"></em>${applicants.userName}
                                <c:if test="${applicants.uploaded != null}">
                                    <a href="/AdmissionsCommittee/images/${applicants.uploaded}"
                                       style="text-decoration: none" target="_blank">
                                        <em class="typcn typcn-attachment"></em></a>
                                </c:if>
                            </td>
                            <td>${applicants.firstName}</td>
                            <td>${applicants.lastName}</td>
                            <td><em class="typcn typcn-mail"></em>${applicants.email}</td>
                            <td>${applicants.city}</td>
                            <td>${applicants.region}</td>
                            <td>${applicants.educationalInstitution}</td>
                            <td>
                                <c:if test="${applicants.enrollStatus == 0}"><fmt:message
                                        key="label.no_enroll"/></c:if>
                                <c:if test="${applicants.enrollStatus == 1}"><fmt:message
                                        key="label.contr_enroll"/></c:if>
                                <c:if test="${applicants.enrollStatus == 2}"><fmt:message
                                        key="label.budget_enroll"/></c:if>
                                <c:if test="${applicants.enrollStatus == 3}"><fmt:message
                                        key="label.no_partic"/></c:if>
                            </td>
                            <td>
                                <c:if test="${applicants.blockStatus == true}"><em
                                        class="typcn typcn-lock-closed fs-6"></em></c:if>
                            </td>
                            <td class="text-center">
                                <form method="post" action="blockbusting">
                                    <c:if test="${applicants.blockStatus == false}">
                                        <button class="btn btn-danger" type="submit">
                                            <i class="typcn typcn-lock-closed"></i>
                                        </button>
                                    </c:if>
                                    <c:if test="${applicants.blockStatus == true}">
                                        <button class="btn btn-warning" type="submit">
                                            <i class="typcn typcn-lock-open"></i>
                                        </button>
                                    </c:if>
                                    <input type="hidden" name="id" value="${applicants.id}">
                                    <input type="hidden" name="status" value="${applicants.blockStatus}">
                                </form>
                            </td>
                                <%--                                                                <div style="text-align: center;">--%>
                                <%--                                                                    <c:set value="${applicants.blockStatus}" var="block"/>--%>
                                <%--                                                                    <form method="post" action='<c:url value="${'/blockbusting'}" />'--%>
                                <%--                                                                          style="display:inline;">--%>
                                <%--                                                                        <input type="hidden" name="id" value="${applicants.id}">--%>
                                <%--                                                                        <input type="hidden" name="status" value="${applicants.blockStatus}">--%>
                                <%--                                                                        <c:if test="${not block}"> <input type="submit"--%>
                                <%--                                                                                                          value="<fmt:message key="label.block"/>"></c:if>--%>
                                <%--                                                                        <c:if test="${block}"> <input type="submit"--%>
                                <%--                                                                                                      value="<fmt:message key="label.unblock"/>"></c:if>--%>
                                <%--                                                                    </form>--%>
                                <%--                                                                </div>--%>

                                <%--                                                            </td>--%>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th><strong><fmt:message key="label.user_name"/></strong></th>
                        <th><strong><fmt:message key="label.first_name"/></strong></th>
                        <th><strong><fmt:message key="label.last_name"/></strong></th>
                        <th><strong><fmt:message key="label.email"/></strong></th>
                        <th><strong><fmt:message key="label.city"/></strong></th>
                        <th><strong><fmt:message key="label.region"/></strong></th>
                        <th><strong><fmt:message key="label.educ_inst"/></strong></th>
                        <th><strong><fmt:message key="label.enroll_status"/></strong></th>
                        <th><strong><fmt:message key="label.block_status"/></strong></th>
                        <th><strong><fmt:message key="label.manage"/></strong></th>
                    </tr>
                    </tfoot>
                </table>
            </div>
            <div class="row">
                <div class="col">
                    <nav class="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">
                        <ul class="pagination">
                            <li class="page-item" style="opacity: 0.50;"><a class="page-link"
                                                                            aria-label="Previous"
                                                                            href="?page=1">
                                <span aria-hidden="true">«</span></a></li>
                            <c:if test="${requestScope.currentPage != 1}">
                                <li class="page-item" style="opacity: 0.50;"><a class="page-link"
                                                                                href="?page=${requestScope.currentPage - 1}">${requestScope.currentPage - 1}</a>
                                </li>
                            </c:if>
                            <li class="page-item active"><a class="page-link" href="#">${requestScope.currentPage}</a>
                            </li>
                            <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                <li class="page-item" style="opacity: 0.50;"><a class="page-link"
                                                                                href="?page=${requestScope.currentPage + 1}">${requestScope.currentPage +1}</a>
                                </li>
                            </c:if>
                            <li class="page-item" style="opacity: 0.50;"><a class="page-link" aria-label="Next"
                                                                            href="?page=${requestScope.noOfPages}">
                                <span aria-hidden="true">»</span></a></li>
                        </ul>
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