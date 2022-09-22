<%@ page contentType="text/html;charset=UTF-8" %>
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
        style="font-family: 'Noto Sans', sans-serif;text-align: center;color: rgb(255,255,255);"><fmt:message
            key="test.app_reg_for"/> ${faculty_name} <fmt:message key="label.faculty"/></h3>
    <div class="card shadow" style="background: rgb(99,99,99);border-radius: 20px;">
        <form method="post">
            <div class="card-header py-3">
                <button class="btn btn-primary" type="submit" style="border-radius: 8px;"><fmt:message
                        key="label.add_all"/></button>
                <input type="hidden" name="faculty_id" value="${requestScope.faculty_id}">
                <input type="hidden" name="action" value="addall">
            </div>
        </form>
        <div class="card-body" style="background: #888888;">
            <div class="row">
                <div class="col-md-6">
                    <div class="text-md-end dataTables_filter" id="dataTable_filter"><label class="form-label"></label>
                    </div>
                </div>
            </div>
            <div class="table-responsive table mt-2" id="dataTable-1" role="grid" aria-describedby="dataTable_info">
                <table class="table my-0" id="dataTable">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.first_name"/></th>
                        <th><fmt:message key="label.last_name"/></th>
                        <th><fmt:message key="label.email"/></th>
                        <th><fmt:message key="label.total_score"/></th>
                        <th><fmt:message key="label.appl_status"/></th>
                        <th><fmt:message key="label.manage"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <jsp:useBean id="applicants" scope="request" type="java.util.List"/>
                    <c:forEach var="applicants" items="${applicants}">
                        <tr>
                            <td>${applicants.firstName}</td>
                            <td>${applicants.lastName}</td>
                            <td><i class="typcn typcn-mail"></i>${applicants.email}</td>
                            <td>${applicants.score}</td>
                            <td><c:if test="${applicants.enrollStatus == 0}"><fmt:message
                                    key="label.no_enroll"/></c:if>
                                <c:if test="${applicants.enrollStatus == 1}"><fmt:message
                                        key="label.contr_enroll"/></c:if>
                                <c:if test="${applicants.enrollStatus == 2}"><fmt:message
                                        key="label.budget_enroll"/></c:if>
                                <c:if test="${applicants.enrollStatus == 3}"><fmt:message
                                        key="label.no_partic"/></c:if>
                            </td>
                            <td class="text-center">
                                <c:if test="${applicants.statementStatus == true}">
                                    <em class="typcn typcn-plus-outline"></em>
                                </c:if>
                            </td>
                            <td class="text-center">
                                <c:set value="${applicants.statementStatus}" var="added"/>
                                <form method="post" action="addresult" style="display:inline;">
                                    <c:if test="${applicants.statementStatus == false}">
                                        <button class="btn btn-info" type="submit">
                                            <em class="typcn typcn-user-add"></em>
                                        </button>
                                    </c:if>
                                    <c:if test="${applicants.statementStatus == true}">
                                        <button class="btn btn-danger" type="submit">
                                            <em class="typcn typcn-user-delete"></em></button>
                                        </button>
                                    </c:if>
                                    <input type="hidden" name="user_id" value="${applicants.id}">
                                    <input type="hidden" name="faculty_id" value="${requestScope.faculty_id}">
                                    <input type="hidden" name="boolStatus" value="${added}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th><strong><fmt:message key="label.first_name"/></strong></th>
                        <th><strong><fmt:message key="label.last_name"/></strong></th>
                        <th><strong><fmt:message key="label.email"/></strong></th>
                        <th><strong><fmt:message key="label.total_score"/></strong></th>
                        <th><strong><fmt:message key="label.appl_status"/></strong></th>
                        <th><strong><fmt:message key="label.manage"/></strong></th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="assets/js/Table.js"></script>
</body>

</html>