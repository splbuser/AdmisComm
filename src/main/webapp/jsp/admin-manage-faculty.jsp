<%@ page contentType="text/html;charset=UTF-8" %>
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
    <title><fmt:message key="label.manage_faculty"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/material-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">

<c:set value="1" var="type"/>
<%@include file="jspf/admin-header.jspf" %>

<div class="container-fluid" style="margin-top: 75px;font-family: 'Noto Sans', sans-serif;">
    <h3 class="text-light mb-4" style="font-family: 'Noto Sans', sans-serif;text-align: center;"><fmt:message
            key="label.manage_faculty"/></h3>
    <div class="card shadow" style="background: rgb(99,99,99);border-radius: 20px;">
        <div class="card-header py-3"><a class="btn btn-primary" role="button"
                                         href='<c:url value="/create" />'><fmt:message key="label.add_faculty"/></a>
        </div>
        <div class="card-body" style="background: #888888;">
            <div class="row">
                <div class="col-md-6 text-nowrap">
                    <div id="dataTable_length" class="dataTables_length" aria-controls="dataTable"></div>
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
                                    <fmt:message key="label.name"/>
                                </button>
                                <div class="dropdown-menu dropdown-menu-dark"
                                     style="border-radius: 8px;min-width: 60px;"><a class="dropdown-item"
                                                                                    href="?type=ASC&sortBy=byName"><fmt:message
                                        key="label.sort_up"/></a><a
                                        class="dropdown-item" href="?type=DSC&sortBy=byName"><fmt:message
                                        key="label.sort_down"/></a></div>
                            </div>
                            <div class="dropend">
                                <button class="dropdown-toggle" aria-expanded="false" data-bs-toggle="dropdown"
                                        type="button"
                                        style="background: rgba(255,255,255,0);color: rgb(255,255,255);border-style: none;">
                                    <fmt:message key="label.budget_places"/>
                                </button>
                                <div class="dropdown-menu dropdown-menu-dark"
                                     style="border-radius: 8px;min-width: 60px;"><a class="dropdown-item"
                                                                                    href="?type=ASC&sortBy=byBudget">0-9</a><a
                                        class="dropdown-item" href="?type=DSC&sortBy=byBudget">9-0</a></div>
                            </div>
                            <div class="dropend">
                                <button class="dropdown-toggle" aria-expanded="false" data-bs-toggle="dropdown"
                                        type="button"
                                        style="background: rgba(255,255,255,0);color: rgb(255,255,255);border-style: none;">
                                    <fmt:message key="label.total_places"/>
                                </button>
                                <div class="dropdown-menu dropdown-menu-dark"
                                     style="border-radius: 8px;min-width: 60px;"><a class="dropdown-item"
                                                                                    href="?type=ASC&sortBy=byTotal">0-9</a><a
                                        class="dropdown-item" href="?type=DSC&sortBy=byTotal">9-0</a></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="text-md-end dataTables_filter" id="dataTable_filter">
                        <form method="get"><label class="form-label">
                            <input
                                    type="text" class="form-control form-control-sm" aria-controls="dataTable"
                                    placeholder="<fmt:message key="label.search"/>"
                                    name="search" style="background: rgb(166,166,166);">
                        </label>
                            <%--                            <button class="btn btn-primary" type="submit" style="border-radius: 8px;">Search</button>--%>
                        </form>
                    </div>
                </div>
            </div>
            <div class="table-responsive table mt-2" id="dataTable-1" role="grid" aria-describedby="dataTable_info">
                <table class="table my-0" id="dataTable">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.name"/></th>
                        <th><fmt:message key="label.budget_places"/></th>
                        <th><fmt:message key="label.total_places"/></th>
                        <th><fmt:message key="label.manage"/></th>
                        <th><fmt:message key="label.applicants_list"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <td>${sessionScope.message}</td>
                    <jsp:useBean id="faculty" scope="request" type="java.util.List"/>
                    <c:forEach var="faculty" items="${faculty}">
                        <tr>
                            <td>${faculty.name}</td>
                            <td>${faculty.budgetPlaces}</td>
                            <td>${faculty.totalPlaces}</td>
                            <td style="text-align: left;padding-left: 0px;width: 200px;"><a
                                    href='<c:url value="/edit?id=${faculty.id}" />'>
                                <em class="material-icons" data-bs-toggle="tooltip" data-bss-tooltip=""
                                   style="color: var(--bs-teal);"
                                   title="<fmt:message key="tooltip.edit_faculty"/>">edit</em></a>
                                <form method="post" action='<c:url value="/delete" />' style="display:inline;"
                                      onclick="return confirmer();">
                                    <input type="hidden" name="id" value="${faculty.id}">
                                    <input type="submit" value="<fmt:message key="key.delete"/>">
                                </form>
                                <script>
                                    function confirmer() {
                                        if (confirm("<fmt:message key="test.confirm_delete"/>")) {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    }
                                </script>
                            </td>
                                <%--                            <td style="text-align: left;padding-left: 0px;width: 200px;">--%>
                                <%--                                <div class="btn-group" role="group">--%>
                                <%--                                    <a class="btn" role="button" style="padding-top: 0;padding-left: 0;padding-bottom: 0;">--%>
                                <%--                                        <i class="material-icons" data-bs-toggle="tooltip" style="color: var(--bs-teal);" title="Edit faculty">edit</i>--%>
                                <%--                                    </a>--%>
                                <%--                                    <button class="btn" type="submit" style="padding-top: 0;padding-right: 0;padding-bottom: 0;">--%>
                                <%--                                        <i class="material-icons" data-bs-toggle="tooltip" style="color: var(--bs-red);" title="Delete faculty">delete_forever</i>--%>
                                <%--                                    </button>--%>
                                <%--                                </div>--%>
                                <%--                                <a href="/edit"></a><input type="hidden" name="id" value="faculty.id" />--%>
                                <%--                            </td>--%>

                            <td><a href='<c:url value="/watchlist?id=${faculty.id}" />'
                                   style="text-decoration: none;color: #212529;font-style: italic;"><fmt:message
                                    key="label.watch_list"/></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td><strong><fmt:message key="label.name"/></strong></td>
                        <td><strong><strong><fmt:message key="label.budget_places"/></strong><br></strong></td>
                        <td><strong><strong><fmt:message key="label.total_places"/></strong><br></strong></td>
                        <td><strong><fmt:message key="label.manage"/></strong></td>
                        <td><strong><fmt:message key="label.applicants_list"/></strong></td>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.js"></script>
<script src="assets/js/Table.js"></script>
</body>

</html>