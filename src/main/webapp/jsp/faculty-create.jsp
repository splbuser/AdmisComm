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
    <title><fmt:message key="label.create_faculty"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">
    <style>
        input:invalid {
            border: red solid 3px;
            color: red;
        }
    </style>
</head>

<body style="background: #212121;">
<%@include file="jspf/admin-custom-header.jspf" %>

<div class="container-fluid" style="margin-top: 75px;font-family: 'Noto Sans', sans-serif;">
    <h3 class="text-light mb-4" style="font-family: 'Noto Sans', sans-serif;text-align: center;"><fmt:message
            key="label.create_faculty"/></h3>
    <div class="card shadow" style="background: rgb(99,99,99);border-radius: 20px;">
        <form method="post">
            <div class="card-header py-3">
                <button class="btn btn-primary" type="submit" style="border-radius: 8px;"><fmt:message
                        key="label.submit"/></button>
            </div>
            <div class="card-body" style="background: #888888;">
                <div class="row">
                    <div class="col-md-6 text-nowrap">
                        <div id="dataTable_length" class="dataTables_length" aria-controls="dataTable"></div>
                    </div>
                    <div class="col-md-6">
                        <div class="text-md-end dataTables_filter" id="dataTable_filter"><label
                                class="form-label"></label>
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
                                    <td><input class="form-control" type="text" id="facultyNameInput"
                                               value="${validValues.get(0)}"
                                               placeholder="<fmt:message key="label.enter_faculty_name"/>" name="name"
                                               style="width: 200px;background: rgba(255,255,255,0.5);border-radius: 8px;border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);"
                                               pattern="[A-ZА-я][a-zа-яё]{1,20}(\s?[A-Za-z][a-z]{1,20})*"
                                               required="" minlength="1" maxlength="20">
                                        <c:if test="${errors.get(0)!=null}">
                                            <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                                <fmt:message key="${errors.get(0)}"/></p>
                                        </c:if>
                                        <c:if test="${error!=null}">
                                            <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                                <fmt:message key="${error}"/></p>
                                        </c:if>
                                    </td>
                                    <td><input class="form-control" type="number" id="BudgetplacesInput"
                                               placeholder="<fmt:message key="label.enter_budget_places"/>"
                                               name="budget_places"
                                               style="border-radius: 8px;max-width: 200px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);"
                                               min="0" max="100" required="">
                                        <c:if test="${errors.get(1)!=null}">
                                            <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                                <fmt:message key="${errors.get(1)}"/>
                                            </p>
                                        </c:if>
                                    </td>
                                    <td><input class="form-control" type="number" id="TotalplacesInput"
                                               placeholder="<fmt:message key="label.enter_total_places"/>"
                                               name="total_places"
                                               style="border-radius: 8px;max-width: 200px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);" oninput="checkPlaces(this)"
                                               min="2" max="100" required=""></td>
                                    <script type='text/javascript'>
                                        function checkPlaces(input) {
                                            if (input.value < document.getElementById('BudgetplacesInput').value) {
                                                input.setCustomValidity('The total number must be greater than the budgeted amount');
                                            } else {
                                                input.setCustomValidity('');
                                            }
                                        }
                                    </script>
                                    <td style="text-align: left;padding-left: 0px;width: 200px;"><input
                                            class="form-control" type="text" id="firstSubjectInput"
                                            value="${validValues.get(2)}"
                                            placeholder="<fmt:message key="label.enter_first_subj"/>"
                                            name="first_subject"
                                            style="border-radius: 8px;max-width: 200px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);"
                                            pattern="[A-ZА-я][a-zа-яё]{1,20}(\s?[A-Za-z][a-z]{1,20})*"
                                            required="" minlength="1" maxlength="20">
                                        <c:if test="${errors.get(2)!=null}">
                                            <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                                <fmt:message key="${errors.get(2)}"/>
                                            </p>
                                        </c:if>
                                    </td>
                                    <td><input class="form-control" type="text" id="secondSubjectInput"
                                               value="${validValues.get(3)}"
                                               placeholder="<fmt:message key="label.enter_second_subj"/>"
                                               name="second_subject"
                                               style="border-radius: 8px;max-width: 200px;background: rgba(255,255,255,0.5);border-color: var(--bs-table-bg);border-bottom-width: 0px;border-bottom-color: var(--bs-table-bg);" oninput="check(this)"
                                               pattern="[A-ZА-я][a-zа-яё]{1,20}(\s?[A-Za-z][a-z]{1,20})*"
                                               required="" minlength="1" maxlength="20">
                                        <script type='text/javascript'>
                                            function check(input) {
                                                if (input.value === document.getElementById('firstSubjectInput').value) {
                                                    input.setCustomValidity('Subject Mustnt matching.');
                                                } else {
                                                    input.setCustomValidity('');
                                                }
                                            }
                                        </script>
                                        <c:if test="${errors.get(3)!=null}">
                                            <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                                <fmt:message key="${errors.get(3)}"/>
                                            </p>
                                        </c:if>
                                    </td>
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
        <c:remove var="errors" scope="session"/>
        <c:remove var="error" scope="session"/>
        <c:remove var="validValues" scope="session"/>
    </div>
</div>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/js/bs-init.js"></script>
<script src="assets/js/Table.js"></script>
</body>

</html>