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
    <title><fmt:message key="label.register"/></title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Noto+Sans&amp;display=swap">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/aos/2.3.4/aos.css">
    <link rel="stylesheet" href="assets/css/Navbar-Right-Links-Dark.css">

    <style>
        input:invalid {
            border: red solid 3px;
        }
    </style>
</head>

<%@include file="jsp/jspf/user-header.jspf" %>

<body style="background: #212121;">
<div class="container" style="padding-top: 50px;font-family: 'Noto Sans', sans-serif;">
    <div class="card shadow-lg o-hidden border-0 my-5" data-aos="fade" data-aos-duration="700" data-aos-delay="200"
         style="width: 934px;background: rgba(255,255,255,0);">
        <div class="card-body p-0"
             style="width: 938px;background: #303030;box-shadow: 0px 0px 8px 3px var(--bs-gray);border-radius: 20px;">
            <div class="row" style="width: 973px;">
                <div class="col-lg-7" style="width: 945.906px;">
                    <div class="p-5" style="font-family: 'Noto Sans', sans-serif;">
                        <div class="text-center" style="color: rgb(255,255,255);">
                            <h4 class="text-light mb-4" style="color: rgb(255,255,255);">
                                <fmt:message key="text.register_step1"/></h4>
                        </div>
                        <form class="user" action="Register" method="post">
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control" type="text"
                                                                          id="userNameInput"
                                                                          placeholder='<fmt:message key="label.user_name"/>*'
                                                                          name="user_name" value="${validValues.get(0)}"
                                                                          style="border-radius: 8px;" required=""
                                                                          autocomplete="off"
                                                                          pattern="^[a-zA-Z0-9_]{2,20}$"
                                                                          title="Alphanumeric, digits and underscore are allowed">
                                    <c:if test="${errors.get(0)!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${errors.get(0)}"/></p>
                                    </c:if>
                                    <c:if test="${error!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${error}"/></p>
                                    </c:if>
                                </div>
                                <div class="col-sm-6"><input class="form-control" type="email" id="EmailInput"
                                                             placeholder="e-mail*" name="email"
                                                             value="${validValues.get(3)}"
                                                             style="border-radius: 8px;" required=""
                                                             title="local-part + @ + domain part">
                                    <c:if test="${errors.get(3)!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${errors.get(3)}"/>
                                        </p>
                                    </c:if>
                                </div>

                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control form-control-user"
                                                                          type="password" id="PasswordInput"
                                                                          placeholder='<fmt:message key="text.pass"/>*'
                                                                          name="password"
                                                                          style="border-radius: 8px;" required=""
                                                                          autocomplete="off"
                                                                          pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                                                          title='<fmt:message key="text.pass_pattern"/>'>
                                    <c:if test="${errors.get(1)!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${errors.get(1)}"/></p>
                                    </c:if>
                                </div>
                                <div class="col-sm-6"><input class="form-control form-control-user" type="password"
                                                             id="RepeatPasswordInput"
                                                             placeholder='<fmt:message key="text.conf_pass"/>*'
                                                             name="password_repeat" style="border-radius: 8px;"
                                                             required="" oninput="check(this)"
                                                             pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                                                             title="retype password again">
                                    <script type='text/javascript'>
                                        function check(input) {
                                            if (input.value !== document.getElementById('PasswordInput').value) {
                                                input.setCustomValidity('Password Must be Matching.');
                                            } else {
                                                input.setCustomValidity('');
                                            }
                                        }
                                    </script>
                                    <c:if test="${errors.get(2)!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${errors.get(2)}"/></p>
                                    </c:if>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control" type="text"
                                                                          id="firstNameInput"
                                                                          placeholder='<fmt:message key="label.first_name"/>*'
                                                                          name="first_name" style="border-radius: 8px;"
                                                                          pattern="[A-ZА-Я][a-zа-яё]{1,20}"
                                                                          value="${validValues.get(4)}"
                                                                          required="">
                                    <c:if test="${errors.get(4)!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${errors.get(4)}"/>
                                        </p>
                                    </c:if>
                                </div>
                                <div class="col-sm-6"><input class="form-control" type="text" id="lastNameInput-1"
                                                             placeholder='<fmt:message key="label.last_name"/>*'
                                                             name="last_name"
                                                             value="${validValues.get(5)}"
                                                             pattern="[A-ZА-Я][a-zа-яё]{1,20}"
                                                             style="border-radius: 8px;" required="">
                                    <c:if test="${errors.get(5)!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${errors.get(5)}"/>
                                        </p>
                                    </c:if>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control" type="text"
                                                                          id="cityInput"
                                                                          placeholder='<fmt:message key="label.city"/>*'
                                                                          name="city"
                                                                          value="${validValues.get(6)}"
                                                                          pattern="[A-ZА-Я][a-zа-яё]{1,20}"
                                                                          style="border-radius: 8px;" required="">
                                    <c:if test="${errors.get(6)!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${errors.get(6)}"/>
                                        </p>
                                    </c:if>
                                </div>
                                <div class="col-sm-6"><input class="form-control" type="text" id="regionInput"
                                                             placeholder='<fmt:message key="label.region"/>*'
                                                             name="region"
                                                             value="${validValues.get(7)}"
                                                             pattern="[A-ZА-Я][a-zа-яё]{1,20}"
                                                             style="border-radius: 8px;" required="">
                                    <c:if test="${errors.get(7)!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${errors.get(7)}"/>
                                        </p>
                                    </c:if>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-6 mb-3 mb-sm-0" style="width: 100%;"><input class="form-control"
                                                                                               type="text"
                                                                                               id="EducationalinstitutionInput"
                                                                                               placeholder='<fmt:message key="label.educ_inst"/>*'
                                                                                               name="educational_institution"
                                                                                               value="${validValues.get(8)}"
                                                                                               style="border-radius: 8px;"
                                                                                               pattern="[A-ZА-Я][a-zа-яё]{1,30}"
                                                                                               required="">
                                    <c:if test="${errors.get(8)!=null}">
                                        <p class="small" style="color: rgba(255,29,48,0.8);text-decoration: none;">
                                            <fmt:message key="${errors.get(8)}"/>
                                        </p>
                                    </c:if>
                                </div>
                            </div>
                            <div class="mb-3">
                                <p class="text-light" style="font-family: 'Noto Sans', sans-serif;font-style: italic;">
                                    <fmt:message key="label.all_fields"/>
                                </p>
                            </div>
                            <button class="btn btn-primary d-block btn-user w-100" type="submit"
                                    style="border-radius: 8px;" data-bs-target="Register"><fmt:message
                                    key="label.register"/>
                            </button>
                            <hr>
                        </form>
                        <c:remove var="errors" scope="session"/>
                        <c:remove var="error" scope="session"/>
                        <c:remove var="validValues" scope="session"/>
                        <div class="text-center"><a class="small" href="forgotPassword"
                                                    style="color: rgba(255,255,255,0.5);text-decoration: none;"><fmt:message
                                key="label.forgot"/></a></div>
                        <div class="text-center"><a class="small" href="Login"
                                                    style="color: rgba(255,255,255,0.5);text-decoration: none;"><fmt:message
                                key="label.login_now"/></a></div>
                    </div>
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