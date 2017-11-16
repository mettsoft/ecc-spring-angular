<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form" %>
<%@ taglib uri = "http://www.springframework.org/security/tags" prefix = "security" %>
<html>

<head>
    <title>
        <spring:message code="person.title" />
    </title>
    <style>
        .browse-button-container {
            display: inline-flex;
        }

        .browse-button-container label {
            margin: auto;
            font-size: 14;
        }

        div.container {
            display: inline-flex;
            width: 100%;
        }
        
        div.form {
            width: 30%;
            margin: 16px;
        }
        
        div.data {
            width: 70%;
            margin: 16px;
        }
        
        div.form label {
            display: block;
        }
        
        table,
        th,
        td {
            border: 1px solid black;
        }

        .error-message {
            color: red;
        }
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/datejs/1.0/date.min.js"></script>
</head>
<body>
    <div style="float: right;">
        <a href="/persons?language=${pageContext.response.locale.language == 'en'? 'fil': 'en'}">
            <spring:message code="language.${pageContext.response.locale.language == 'en'? 'filipino': 'english'}" />
        </a>
        <form id="logoutForm" action="/logout" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <a href="#" onclick="document.getElementById('logoutForm').submit()">Logout</a>
        </form>
    </div>
    <div id="role-mark-up" hidden>
        <select class="roles">
            <c:forEach items="${roleItems}" var="role">
                <option value="${role.id}">${role.name}</option>
            </c:forEach>
        </select>
        <button onclick="this.parentNode.remove()">
            <spring:message code="form.button.remove" />
        </button>
    </div>
    <div id="contact-mark-up" hidden>
        <select class="contactTypes">
            <option value="Landline">
                <spring:message code="person.contactType.landline" />
            </option>
            <option value="Email">
                <spring:message code="person.contactType.email" />
            </option>
            <option value="Mobile">
                <spring:message code="person.contactType.mobile" />
            </option>
        </select>
        <button onclick="this.parentNode.remove()">
            <spring:message code="form.button.remove" />
        </button>
        <input type="text" class="contactData">
    </div>

    <!-- Start Content -->
    <div>
        <a href="/roles">
            <spring:message code="navigation.roles" />
        </a>        
    </div>
    <security:authorize access="hasRole('ROLE_ADMIN')">
        <div>
            <a href="/users">
                <spring:message code="navigation.users" />
            </a>        
        </div>
    </security:authorize>
    <div class="container">
        <div class="form">
            <c:forEach items="${errorMessages}" var="errorMessage">
                <h3 class="error-message">${errorMessage}</h3>
            </c:forEach>
            <h3>${successMessage}</h3>
            <c:if test="${action == '/create'}">
                <security:authorize access="hasRole('ROLE_CREATE_PERSON')">
                    <fieldset>
                        <legend><strong>${headerTitle}</strong></legend>
                        <c:if test="${action == '/create'}">
                            <form action="/persons/upload" method="POST" enctype="multipart/form-data">
                                <div class="browse-button-container">
                                    <button id="browse-button">
                                        <spring:message code="form.button.chooseFile" />
                                    </button>
                                    <label for="browse-button">
                                        <spring:message code="form.button.noFileSelected" />
                                    </label>
                                </div>
                                <input id="file-button" hidden type="file" name="file" accept=".txt">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <button hidden id="upload-button">
                                    <spring:message code="form.button.upload" />
                                </button>
                            </form>
                        </c:if>
                        <form:form action="/persons${action}" method="POST" onsubmit="return onSubmit()">
                            <button hidden></button>
                            <form:input type="hidden" path="id" />
                            <input type="hidden" name="querySearchType" value="${param.querySearchType}">
                            <input type="hidden" name="queryLastName" value="${param.queryLastName}">
                            <input type="hidden" name="queryRoleId" value="${param.queryRoleId}">
                            <input type="hidden" name="queryBirthday" value="${param.queryBirthday}">
                            <input type="hidden" name="queryOrderBy" value="${param.queryOrderBy}">
                            <input type="hidden" name="queryOrderType" value="${param.queryOrderType}">
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.name" />
                                    </legend>
                                    <div>
                                        <form:label path="name.title">
                                            <spring:message code="person.form.label.name.title" />:
                                        </form:label>
                                        <form:input type="text" path="name.title" />
                                        </div>
                                    <div>
                                        <form:label path="name.lastName">
                                            <spring:message code="person.form.label.name.lastName" />:
                                        </form:label>
                                        <form:input type="text" path="name.lastName" />
                                    </div>
                                    <div>
                                        <form:label path="name.firstName">
                                            <spring:message code="person.form.label.name.firstName" />:
                                        </form:label>
                                        <form:input type="text" path="name.firstName" />
                                    </div>
                                    <div>
                                        <form:label path="name.middleName">
                                            <spring:message code="person.form.label.name.middleName" />:
                                        </form:label>
                                        <form:input type="text" path="name.middleName" />
                                    </div>
                                    <div>
                                        <form:label path="name.suffix">
                                            <spring:message code="person.form.label.name.suffix" />:
                                        </form:label>
                                        <form:input type="text" path="name.suffix" /></div>
                                </fieldset>
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.address" />:
                                    </legend>
                                    <div>
                                        <form:label path="address.streetNumber">
                                            <spring:message code="person.form.label.address.streetNumber" />:
                                        </form:label>
                                        <form:input type="text" path="address.streetNumber" /> 
                                    </div>
                                    <div>
                                        <form:label path="address.barangay">
                                            <spring:message code="person.form.label.address.barangay" />:
                                        </form:label>
                                        <form:input type="text" path="address.barangay" /> 
                                    </div>
                                    <div>
                                        <form:label path="address.municipality">
                                            <spring:message code="person.form.label.address.municipality" />:
                                        </form:label>
                                        <form:input type="text" path="address.municipality" /> 
                                    </div>
                                    <div>
                                        <form:label path="address.zipCode">
                                            <spring:message code="person.form.label.address.zipCode" />:
                                        </form:label>
                                        <form:input type="number" name="zipCode" path="address.zipCode" min="0" /> 
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.otherInformation" />:
                                    </legend>
                                    <div>
                                        <form:label path="birthday">
                                            <spring:message code="person.form.label.otherInformation.birthday" />:
                                        </form:label>
                                        <form:input type="date" path="birthday" />  
                                    </div>
                                    <div>
                                        <form:label path="GWA">
                                            <spring:message code="person.form.label.otherInformation.GWA" />:
                                        </form:label>
                                        <form:input type="number" path="GWA" min="1" max="5" step="0.001" /> 
                                    </div>
                                    <div>
                                        <form:label path="currentlyEmployed">
                                            <spring:message code="person.form.label.otherInformation.currentlyEmployed" />:
                                        </form:label>
                                        <form:checkbox path="currentlyEmployed" onclick="document.getElementById('dateHired').disabled=!this.checked" />
                                    </div>
                                    <div>
                                        <form:label path="dateHired">
                                            <spring:message code="person.form.label.otherInformation.dateHired" />:
                                        </form:label>
                                        <form:input type="date" path="dateHired" />
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.contactInformation" />:
                                    </legend>
                                    <c:forEach items="${command.contacts}" var="contact">
                                        <div>
                                            <select class="contactTypes assigned-contacts">
                                                <option value="Landline">
                                                    <spring:message code="person.contactType.landline" />
                                                </option>
                                                <option value="Email">
                                                    <spring:message code="person.contactType.email" />
                                                </option>
                                                <option value="Mobile">
                                                    <spring:message code="person.contactType.mobile" />
                                                </option>
                                            </select>
                                            <button onclick="this.parentNode.remove()">
                                                <spring:message code="form.button.remove" />
                                            </button>
                                            <input type="text" class="contactData" value="${contact.data}">
                                        </div>
                                    </c:forEach>
                                    <button onclick="this.parentNode.insertBefore(CONTACT_MARK_UP.cloneNode(true), this); return false;">
                                        <spring:message code="form.button.add" />
                                    </button>
                                </fieldset>
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.roleAssignments" />:
                                    </legend>
                                    <c:forEach items="${command.roles}" var="role">
                                        <div>
                                            <select class="roles assigned-roles" path="role.id">
                                                <c:forEach items="${roleItems}" var="roleItem">
                                                    <option value="${roleItem.id}">${roleItem.name}</option>
                                                </c:forEach>
                                            </select>
                                            <button onclick="this.parentNode.remove()">
                                                <spring:message code="form.button.remove" />
                                            </button>
                                        </div>                           
                                    </c:forEach>
                                    <button onclick="this.parentNode.insertBefore(ROLE_MARK_UP.cloneNode(true), this); return false;">
                                        <spring:message code="form.button.add" />
                                    </button>
                                </fieldset>
                                <button>
                                    <spring:message code="form.button.submit" />
                                </button>
                        </form:form>
                    </fieldset>
                </security:authorize>
            </c:if>
            <c:if test="${action == '/update'}">
                <security:authorize access="hasRole('ROLE_UPDATE_PERSON')">
                    <fieldset>
                        <legend><strong>${headerTitle}</strong></legend>
                        <c:if test="${action == '/create'}">
                            <form action="/persons/upload" method="POST" enctype="multipart/form-data">
                                <div class="browse-button-container">
                                    <button id="browse-button">
                                        <spring:message code="form.button.chooseFile" />
                                    </button>
                                    <label for="browse-button">
                                        <spring:message code="form.button.noFileSelected" />
                                    </label>
                                </div>
                                <input id="file-button" hidden type="file" name="file" accept=".txt">
                                <button hidden id="upload-button">
                                    <spring:message code="form.button.upload" />
                                </button>
                            </form>
                        </c:if>
                        <form:form action="/persons${action}" method="POST" onsubmit="return onSubmit()">
                            <button hidden></button>
                            <form:input type="hidden" path="id" />
                            <input type="hidden" name="querySearchType" value="${param.querySearchType}">
                            <input type="hidden" name="queryLastName" value="${param.queryLastName}">
                            <input type="hidden" name="queryRoleId" value="${param.queryRoleId}">
                            <input type="hidden" name="queryBirthday" value="${param.queryBirthday}">
                            <input type="hidden" name="queryOrderBy" value="${param.queryOrderBy}">
                            <input type="hidden" name="queryOrderType" value="${param.queryOrderType}">
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.name" />
                                    </legend>
                                    <div>
                                        <form:label path="name.title">
                                            <spring:message code="person.form.label.name.title" />:
                                        </form:label>
                                        <form:input type="text" path="name.title" />
                                        </div>
                                    <div>
                                        <form:label path="name.lastName">
                                            <spring:message code="person.form.label.name.lastName" />:
                                        </form:label>
                                        <form:input type="text" path="name.lastName" />
                                    </div>
                                    <div>
                                        <form:label path="name.firstName">
                                            <spring:message code="person.form.label.name.firstName" />:
                                        </form:label>
                                        <form:input type="text" path="name.firstName" />
                                    </div>
                                    <div>
                                        <form:label path="name.middleName">
                                            <spring:message code="person.form.label.name.middleName" />:
                                        </form:label>
                                        <form:input type="text" path="name.middleName" />
                                    </div>
                                    <div>
                                        <form:label path="name.suffix">
                                            <spring:message code="person.form.label.name.suffix" />:
                                        </form:label>
                                        <form:input type="text" path="name.suffix" /></div>
                                </fieldset>
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.address" />:
                                    </legend>
                                    <div>
                                        <form:label path="address.streetNumber">
                                            <spring:message code="person.form.label.address.streetNumber" />:
                                        </form:label>
                                        <form:input type="text" path="address.streetNumber" /> 
                                    </div>
                                    <div>
                                        <form:label path="address.barangay">
                                            <spring:message code="person.form.label.address.barangay" />:
                                        </form:label>
                                        <form:input type="text" path="address.barangay" /> 
                                    </div>
                                    <div>
                                        <form:label path="address.municipality">
                                            <spring:message code="person.form.label.address.municipality" />:
                                        </form:label>
                                        <form:input type="text" path="address.municipality" /> 
                                    </div>
                                    <div>
                                        <form:label path="address.zipCode">
                                            <spring:message code="person.form.label.address.zipCode" />:
                                        </form:label>
                                        <form:input type="number" name="zipCode" path="address.zipCode" min="0" /> 
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.otherInformation" />:
                                    </legend>
                                    <div>
                                        <form:label path="birthday">
                                            <spring:message code="person.form.label.otherInformation.birthday" />:
                                        </form:label>
                                        <form:input type="date" path="birthday" />  
                                    </div>
                                    <div>
                                        <form:label path="GWA">
                                            <spring:message code="person.form.label.otherInformation.GWA" />:
                                        </form:label>
                                        <form:input type="number" path="GWA" min="1" max="5" step="0.001" /> 
                                    </div>
                                    <div>
                                        <form:label path="currentlyEmployed">
                                            <spring:message code="person.form.label.otherInformation.currentlyEmployed" />:
                                        </form:label>
                                        <form:checkbox path="currentlyEmployed" onclick="document.getElementById('dateHired').disabled=!this.checked" />
                                    </div>
                                    <div>
                                        <form:label path="dateHired">
                                            <spring:message code="person.form.label.otherInformation.dateHired" />:
                                        </form:label>
                                        <form:input type="date" path="dateHired" />
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.contactInformation" />:
                                    </legend>
                                    <c:forEach items="${command.contacts}" var="contact">
                                        <div>
                                            <select class="contactTypes assigned-contacts">
                                                <option value="Landline">
                                                    <spring:message code="person.contactType.landline" />
                                                </option>
                                                <option value="Email">
                                                    <spring:message code="person.contactType.email" />
                                                </option>
                                                <option value="Mobile">
                                                    <spring:message code="person.contactType.mobile" />
                                                </option>
                                            </select>
                                            <button onclick="this.parentNode.remove()">
                                                <spring:message code="form.button.remove" />
                                            </button>
                                            <input type="text" class="contactData" value="${contact.data}">
                                        </div>
                                    </c:forEach>
                                    <button onclick="this.parentNode.insertBefore(CONTACT_MARK_UP.cloneNode(true), this); return false;">
                                        <spring:message code="form.button.add" />
                                    </button>
                                </fieldset>
                                <fieldset>
                                    <legend>
                                        <spring:message code="person.form.label.roleAssignments" />:
                                    </legend>
                                    <c:forEach items="${command.roles}" var="role">
                                        <div>
                                            <select class="roles assigned-roles" path="role.id">
                                                <c:forEach items="${roleItems}" var="roleItem">
                                                    <option value="${roleItem.id}">${roleItem.name}</option>
                                                </c:forEach>
                                            </select>
                                            <button onclick="this.parentNode.remove()">
                                                <spring:message code="form.button.remove" />
                                            </button>
                                        </div>                           
                                    </c:forEach>
                                    <button onclick="this.parentNode.insertBefore(ROLE_MARK_UP.cloneNode(true), this); return false;">
                                        <spring:message code="form.button.add" />
                                    </button>
                                </fieldset>
                                <button>
                                    <spring:message code="form.button.submit" />
                                </button>
                        </form:form>
                    </fieldset>
                </security:authorize>
            </c:if>
        </div>
        <div class="data">
            <h3>
                <spring:message code="person.data.header" />
            </h3>
            <form action="/persons" method="GET">
                <fieldset>
                    <legend>
                        <spring:message code="person.data.searchParameters" />
                    </legend>
                    <div>
                        <label for="querySearchType">
                            <spring:message code="person.data.searchParameters.criteria" />
                        </label>
                        <select id="querySearchType" name="querySearchType">
                            <option value="0">
                                <spring:message code="person.data.searchParameters.criteria.lastName" />
                            </option>
                            <option value="1">
                                <spring:message code="person.data.searchParameters.criteria.role" />
                            </option>
                            <option value="2">
                                <spring:message code="person.data.searchParameters.criteria.birthday" />
                            </option>
                        </select>
                    </div>
                    <div>
                        <label for="queryLastName">
                            <spring:message code="person.data.searchParameters.lastName" />
                        </label>
                        <input type="text" id="queryLastName" name="queryLastName" value="${param.queryLastName}"> </div>
                    <div>
                        <label for="queryRoleId">
                            <spring:message code="person.data.searchParameters.role" />
                        </label>
                        <select name="queryRoleId" id="queryRoleId">
                            <option value="">
                                <spring:message code="person.data.searchParameters.options.all" />
                            </option>
                            <c:forEach items="${roleItems}" var="role">
                                <option value="${role.id}">${role.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label for="queryBirthday">
                            <spring:message code="person.data.searchParameters.birthday" />
                        </label>
                        <input type="date" id="queryBirthday" name="queryBirthday" value="${param.queryBirthday}"> </div>
                    <div>
                        <label>
                            <spring:message code="person.data.searchParameters.orderBy" />
                        </label>
                        <select name="queryOrderBy" id="queryOrderBy">
                            <option value="name.lastName">
                                <spring:message code="person.data.searchParameters.orderBy.lastName" />
                            </option>
                            <option value="dateHired">
                                <spring:message code="person.data.searchParameters.orderBy.dateHired" />
                            </option>
                            <option value="GWA">
                                <spring:message code="person.data.searchParameters.orderBy.GWA" />
                            </option>
                        </select>
                        <select name="queryOrderType" id="queryOrderType">
                            <option value="ASC">
                                <spring:message code="person.data.searchParameters.order.ascending" />
                            </option>
                            <option value="DESC">
                                <spring:message code="person.data.searchParameters.order.descending" />
                            </option>
                        </select>
                    </div>
                    <button>
                        <spring:message code="form.button.search" />
                    </button>
                </fieldset>
            </form>
            <c:choose>
                <c:when test="${data.size() > 0}">
                    <table>
                        <thead>
                            <th hidden>
                                <spring:message code="person.data.column.id" />
                            </th>
                            <th>
                                <spring:message code="person.data.column.name" />
                            </th>
                            <th>
                                <spring:message code="person.data.column.address" />
                            </th>
                            <th>
                                <spring:message code="person.data.column.birthday" />
                            </th>
                            <th>
                                <spring:message code="person.data.column.GWA" />
                            </th>
                            <th>
                                <spring:message code="person.data.column.employment" />
                            </th>
                            <th>
                                <spring:message code="person.data.column.contacts" />
                            </th>
                            <th>
                                <spring:message code="person.data.column.roles" />
                            </th>
                            <security:authorize access="hasRole('ROLE_UPDATE_PERSON')">
                                <th></th>
                            </security:authorize>
                            <security:authorize access="hasRole('ROLE_DELETE_PERSON')">
                                <th></th>
                            </security:authorize>
                        </thead>
                        <tbody>
                            <c:forEach items="${data}" var="person">
                                <tr>
                                    <td hidden>${person.id}</td>
                                    <td>${person.name}</td>
                                    <td>${person.address}</td>
                                    <td>${person.birthday}</td>
                                    <td>${person.GWA}</td>
                                    <td>${person.employmentStatus}</td>
                                    <td>
                                        <c:forEach items="${person.contacts}" var="contact">
                                            ${contact}
                                            <br />
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach items="${person.roles}" var="role">
                                            ${role}
                                            <br />
                                        </c:forEach>
                                    </td>
                                    <security:authorize access="hasRole('ROLE_UPDATE_PERSON')">
                                        <td>
                                            <form action="/persons" method="GET">
                                                <input type="hidden" name="id" value="${person.id}">
                                                <input type="hidden" name="querySearchType" value="${param.querySearchType}">
                                                <input type="hidden" name="queryLastName" value="${param.queryLastName}">
                                                <input type="hidden" name="queryRoleId" value="${param.queryRoleId}">
                                                <input type="hidden" name="queryBirthday" value="${param.queryBirthday}">
                                                <input type="hidden" name="queryOrderBy" value="${param.queryOrderBy}">
                                                <input type="hidden" name="queryOrderType" value="${param.queryOrderType}">
                                                <button>
                                                    <spring:message code="data.form.button.edit" />
                                                </button>
                                            </form>
                                        </td>
                                    </security:authorize>
                                    <security:authorize access="hasRole('ROLE_DELETE_PERSON')">
                                        <td>
                                            <form action="/persons/delete" method="POST">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                                <input type="hidden" name="id" value="${person.id}">
                                                <input type="hidden" name="name.title" value="${person.name.title}">
                                                <input type="hidden" name="name.lastName" value="${person.name.lastName}">
                                                <input type="hidden" name="name.middleName" value="${person.name.middleName}">
                                                <input type="hidden" name="name.firstName" value="${person.name.firstName}">
                                                <input type="hidden" name="name.suffix" value="${person.name.suffix}">
                                                <input type="hidden" name="querySearchType" value="${param.querySearchType}">
                                                <input type="hidden" name="queryLastName" value="${param.queryLastName}">
                                                <input type="hidden" name="queryRoleId" value="${param.queryRoleId}">
                                                <input type="hidden" name="queryBirthday" value="${param.queryBirthday}">
                                                <input type="hidden" name="queryOrderBy" value="${param.queryOrderBy}">
                                                <input type="hidden" name="queryOrderType" value="${param.queryOrderType}">
                                                <button onclick="return confirm('<spring:message code="person.data.form.button.deleteConfirmation" arguments="${person.name}" />')">
                                                    <spring:message code="data.form.button.delete" />
                                                </button>
                                            </form>
                                        </td>
                                    </security:authorize>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <h6>
                        <spring:message code="data.noRecordsFound" />
                    </h6>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
<script>
    var ROLE_MARK_UP = document.getElementById('role-mark-up').cloneNode(true);
    ROLE_MARK_UP.hidden = false;
    var CONTACT_MARK_UP = document.getElementById('contact-mark-up').cloneNode(true);
    CONTACT_MARK_UP.hidden = false;

    var contactTypes = [${assignedContactTypes}];
    document.querySelectorAll('select.assigned-contacts').forEach((e, i) => e.value = contactTypes[i]);
    var rolesId = [${assignedRoleIds}];
    document.querySelectorAll('select.assigned-roles').forEach((e, i) => e.value = rolesId[i]);

    flatpickr("input[type=date]", {});
 
    if ('${param.querySearchType}') {
        document.getElementById('querySearchType').value = '${param.querySearchType}';
    }
 
    if ('${param.queryOrderBy}') {
        document.getElementById('queryOrderBy').value = '${param.queryOrderBy}';
    }
 
    if ('${param.queryOrderType}') {
        document.getElementById('queryOrderType').value = '${param.queryOrderType}';
    }
 
    if ('${param.queryRoleId}') {
        document.getElementById('queryRoleId').value = '${param.queryRoleId}';
    }
 
    if ('${command.currentlyEmployed}' === 'true' && document.getElementById('dateHired')) {
        document.getElementById('dateHired').disabled = false;
    } else if (document.getElementById('dateHired')) {
        document.getElementById('dateHired').disabled = true;
    }
 
    var options = [document.getElementById('queryLastName'), document.getElementById('queryRoleId'), document.getElementById('queryBirthday')];

    document.getElementById('querySearchType').addEventListener("change", (element) => {
        options.forEach((value, index) => value.parentNode.hidden = value.disabled = element.target.value != index);
    });

    document.getElementById('querySearchType').dispatchEvent(new Event("change"));

    var birthday = document.getElementById('birthday').getAttribute('value');
    if (birthday && !birthday.match('^\\d{4}-\\d{1,2}-\\d{1,2}$')) {
    	document.getElementById('birthday').value = 
    		Date.parse(birthday.slice(0, 19) + "UTC").toISOString().slice(0,10);
    }

    var dateHired = document.getElementById('dateHired').getAttribute('value');
    if (dateHired && !dateHired.match('^\\d{4}-\\d{1,2}-\\d{1,2}$')) {
    	document.getElementById('dateHired').value =  
    		Date.parse(dateHired.slice(0, 19) + "UTC").toISOString().slice(0,10);
    }


    function onSubmit() {
        document.querySelectorAll('select.roles').forEach((e, i) => { 
            e.name = 'roles[' + (i-1) + '].id';
            var input = document.createElement('input');
            input.hidden = true;
            input.name = 'roles[' + (i-1) + '].name';
            input.value = e.value;
            e.parentNode.insertBefore(input, e);
        });

        document.querySelectorAll('select.contactTypes').forEach((e, i) => e.name = 'contacts[' + (i-1) + '].contactType');

        document.querySelectorAll('input.contactData').forEach((e, i) => e.name = 'contacts[' + (i-1) + '].data');
    }

    document.getElementById('browse-button') && 
    document.getElementById('browse-button').addEventListener("click", (element) => {
        document.getElementById('file-button').click();
        element.preventDefault();
    });

    document.getElementById('file-button') && 
    document.getElementById('file-button').addEventListener("change", (element) => {
        document.querySelector('.browse-button-container label').innerHTML = element.target.value.split("\\").pop();
        document.getElementById('upload-button').hidden = false;
    });
</script>

</html>