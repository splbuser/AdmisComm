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
    <title><fmt:message key="label.edit_faculty"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
</head>

<body style="background: #212121;">
<%@include file="jspf/admin-custom-header.jspf" %>

<jsp:useBean id="faculty" scope="request" type="com.splb.model.entity.Faculty"/>
<div class="container-fluid" style="margin-top: 75px;font-family: 'Noto Sans', sans-serif;">
    <h3 class="text-light mb-4" style="font-family: 'Noto Sans', sans-serif;text-align: center;"><fmt:message key="label.edit"/> ${faculty.name} <fmt:message key="label.faculty"/></h3>
    <div class="card shadow" style="background: rgb(99,99,99);border-radius: 20px;">
        <form method="post">
        <div class="card-header py-3">
            <button class="btn btn-primary" type="submit" style="border-radius: 8px;"><fmt:message key="label.submit"/></button>
        </div>
        <div class="card-body" style="background: #888888;">
            <div class="row">
                <div class="col-md-6 text-nowrap">
                    <div id="dataTable_length" class="dataTables_length" aria-controls="dataTable"></div>
                </div>
                <div class="col-md-6">
                    <div class="text-md-end dataTables_filter" id="dataTable_filter"><label class="form-label"></label>
                    </div>
                </div>
                <div class="col">
                    <div class="table-responsive table mt-2" id="dataTable-1" role="grid"
                         aria-describedby="dataTable_info">
                        <table class="table my-0" id="dataTable">
                            <thead>
                            <tr>
                                <th><fmt:message key="label.name"/></th>
                                <th><fmt:message key="label.budget_places"/></th>
                                <th><fmt:message key="label.total_places"/></th>
                                <th><fmt:message key="label.first_subject"/></th>
                                <th><fmt:message key="label.second_subject"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><input type="text" id="facultyNameInput" name="name"
                                           style="width: 200px;background: rgba(255,255,255,0.5);border-radius: 8px;border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);"
                                           value="${faculty.name}"
                                           required="" minlength="1" maxlength="32"></td>
                                <td><input type="number" id="BudgetplacesInput" name="budget_places"
                                           style="border-radius: 8px;max-width: 200px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);"
                                           value="${faculty.budgetPlaces}"
                                           min="0" required=""></td>
                                <td><input type="number" id="TotalplacesInput" name="total_places"
                                           style="border-radius: 8px;max-width: 200px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);"
                                           value="${faculty.totalPlaces}"
                                           min="1" required=""></td>
                                <td style="text-align: left;padding-left: 0px;width: 200px;"><input type="text"
                                                                                                    id="firstSubjectInput"
                                                                                                    name="first_subject"
                                                                                                    style="border-radius: 8px;max-width: 200px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);"
                                                                                                    value="${faculty.subjOne}"
                                                                                                    required=""
                                                                                                    minlength="1"
                                                                                                    maxlength="32"></td>
                                <td><input type="text" id="firstSecondInput" name="second_subject"
                                           style="border-radius: 8px;max-width: 200px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);"
                                           value="${faculty.subjTwo}"
                                           required="" minlength="1" maxlength="32"></td>
                            </tr>
                            </tbody>
                            <tfoot style="font-style: italic;">
                            <tr>
                                <td><fmt:message key="label.all_fields"/></td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>
</div>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="assets/js/Table.js"></script>
</body>

</html>