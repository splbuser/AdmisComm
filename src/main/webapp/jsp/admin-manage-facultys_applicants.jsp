<%@page language="java" import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="message"/>
<!DOCTYPE html>
<html lang="${cookie['lang'].value}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="label.manage_registered_applicant"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/typicons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">
<%@include file="jspf/admin-custom-header.jspf" %>

<div class="container-fluid" style="margin-top: 75px;font-family: 'Noto Sans', sans-serif;">
    <jsp:useBean id="faculty_name" scope="request" type="java.lang.String"/>
    <h3 class="text-light mb-4"
        style="font-family: 'Noto Sans', sans-serif;text-align: center;color: rgb(255,255,255);"><fmt:message key="test.app_reg_for"/> ${faculty_name} <fmt:message key="label.faculty"/></h3>
    <div class="card shadow" style="background: rgb(99,99,99);border-radius: 20px;">
        <div class="card-header py-3"></div>
        <div class="card-body" style="background: #888888;">
            <div class="row">
<%--                <div class="col-md-6 text-nowrap">--%>
<%--                    <div id="dataTable_length" class="dataTables_length" aria-controls="dataTable"><label--%>
<%--                            class="form-label text-dark"><fmt:message key="label.show"/><select--%>
<%--                            class="d-inline-block form-select form-select-sm"--%>
<%--                            style="background: rgb(166,166,166);width: 36px;padding-right: 8px;">--%>
<%--                        <option value="10" selected="">10</option>--%>
<%--                        <option value="25">25</option>--%>
<%--                        <option value="50">50</option>--%>
<%--                    </select>&nbsp;</label></div>--%>
<%--                </div>--%>
                <div class="col-md-6">
                    <div class="text-md-end dataTables_filter" id="dataTable_filter"><label class="form-label"></label>
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
                        <th><fmt:message key="label.appl_status"/></th>
                        <th><fmt:message key="label.manage"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <jsp:useBean id="applicants" scope="request" type="java.util.List"/>
<%--                    <jsp:useBean id="faculty_id" scope="request" type="java.lang.Integer"/>--%>
                    <c:forEach var="applicants" items="${applicants}">
                        <tr>
                            <td><i class="typcn typcn-user"></i>${applicants.userName}</td>
                            <td>${applicants.firstName}</td>
                            <td>${applicants.lastName}</td>
                            <td><i class="typcn typcn-mail"></i>${applicants.email}</td>
                            <td>${applicants.city}</td>
                            <td>${applicants.region}</td>
                            <td>${applicants.educationalInstitution}</td>
                            <td>
                                <c:set value="${applicants.statementStatus}" var="added"/>
                                <c:out value="${added == true ? 'OK': ' '}"/>
                            </td>
                            <td>
                                <div style="text-align: center;">
                                    <form method="post" action='<c:url value="${'/addresult'}" />'
                                          style="display:inline;">
                                        <input type="hidden" name="user_id" value="${applicants.id}">
                                        <input type="hidden" name="faculty_id" value="${requestScope.faculty_id}">
                                        <input type="hidden" name="boolStatus" value="${added}">
                                        <c:if test="${not added}"> <input type="submit" value="<fmt:message key="label.add_result"/>"></c:if>
                                        <c:if test="${added}"> <input type="submit" value="<fmt:message key="label.remove_result"/>"></c:if>
                                    </form>
                                </div>
                            </td>
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
                        <th><strong><fmt:message key="label.appl_status"/></strong></th>
                        <th><strong><fmt:message key="label.manage"/></strong></th>
                    </tr>
                    </tfoot>
                </table>
            </div>
<%--            <div class="row">--%>
<%--                <div class="col-md-6 align-self-center">--%>
<%--                    <p id="dataTable_info" class="dataTables_info" role="status" aria-live="polite">Showing 1 to 10 of--%>
<%--                        27</p>--%>
<%--                </div>--%>
<%--                <div class="col">--%>
<%--                    <nav class="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers">--%>
<%--                        <ul class="pagination">--%>
<%--                            <li class="page-item disabled" style="opacity: 0.50;"><a class="page-link"--%>
<%--                                                                                     aria-label="Previous"--%>
<%--                                                                                     href="#"><span--%>
<%--                                    aria-hidden="true">«</span></a></li>--%>
<%--                            <li class="page-item active"><a class="page-link" href="#">1</a></li>--%>
<%--                            <li class="page-item" style="opacity: 0.50;"><a class="page-link" href="#">2</a></li>--%>
<%--                            <li class="page-item" style="opacity: 0.50;"><a class="page-link" href="#">3</a></li>--%>
<%--                            <li class="page-item" style="opacity: 0.50;"><a class="page-link" aria-label="Next"--%>
<%--                                                                            href="#"><span--%>
<%--                                    aria-hidden="true">»</span></a></li>--%>
<%--                        </ul>--%>
<%--                    </nav>--%>
<%--                </div>--%>
<%--            </div>--%>
        </div>
    </div>
</div>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="assets/js/Table.js"></script>
</body>

</html>