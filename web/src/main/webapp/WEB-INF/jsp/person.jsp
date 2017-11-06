<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring"%>
<html>

<head>
    <title>
        <spring:message code="person.title" />
    </title>
    <style>
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
</head>
<body>
    <div style="float: right;">
        <a href="/person/list?language=${locale=='en'? 'fil' : 'en'}">
            <spring:message code="language.${locale == 'en'? 'filipino': 'english'}" />
        </a>                
    </div>
    <div id="role-mark-up" hidden>
        <select name="personRoleIds">
            <c:forEach items="${roleItems}" var="role">
                <option value="${role.id}">${role.name}</option>
            </c:forEach>
        </select>
        <button onclick="this.parentNode.remove()">
            <spring:message code="form.button.remove" />
        </button>
    </div>
    <div id="contact-mark-up" hidden>
        <select name="contactType">
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
        <input type="text" name="contactData">
    </div>

    <!-- Start Content -->
    <a href="/role/list">
        <spring:message code="person.navigation" />
    </a>
    <div class="container">
        <div class="form">
            <h3 class="error-message">${errorMessage}</h3>
            <h3>${successMessage}</h3>

            <fieldset>
                <legend><strong>${headerTitle}</strong></legend>
                <c:if test="${action == '/create'}">
                    <form action="/person/upload" method="POST" enctype="multipart/form-data"> 
                        <input type="file" name="file" accept=".json">
                        <button>
                            <spring:message code="form.button.upload" />
                        </button>
                    </form>
                </c:if>
                <form action="/person${action}" method="POST">
                    <button hidden></button>
                    <input type="hidden" name="id" value="${id}">
                    <input type="hidden" name="querySearchType" value="${querySearchType}">
                    <input type="hidden" name="queryLastName" value="${queryLastName}">
                    <input type="hidden" name="queryRoleId" value="${queryRoleId}">
                    <input type="hidden" name="queryBirthday" value="${queryBirthday}">
                    <input type="hidden" name="queryOrderBy" value="${queryOrderBy}">
                    <input type="hidden" name="queryOrderType" value="${queryOrderType}">
                        <fieldset>
                            <legend>
                                <spring:message code="person.form.label.name" />
                            </legend>
                            <div>
                                <label for="title">
                                    <spring:message code="person.form.label.name.title" />
                                </label>
                                <input type="text" id="title" name="title" value="${title}"> </div>
                            <div>
                                <label for="lastName">
                                    <spring:message code="person.form.label.name.lastName" />
                                </label>
                                <input type="text" id="lastName" name="lastName" value="${lastName}">
                            </div>
                            <div>
                                <label for="firstName">
                                    <spring:message code="person.form.label.name.firstName" />
                                </label>
                                <input type="text" id="firstName" name="firstName" value="${firstName}">
                            </div>
                            <div>
                                <label for="middleName">
                                    <spring:message code="person.form.label.name.middleName" />
                                </label>
                                <input type="text" id="middleName" name="middleName" value="${middleName}">
                            </div>
                            <div>
                                <label for="suffix">
                                    <spring:message code="person.form.label.name.suffix" />
                                </label>
                                <input type="text" id="suffix" name="suffix" value="${suffix}"> </div>
                        </fieldset>
                        <fieldset>
                            <legend>
                                <spring:message code="person.form.label.address" />
                            </legend>
                            <div>
                                <label for="streetNumber">
                                    <spring:message code="person.form.label.address.streetNumber" />
                                </label>
                                <input type="text" id="streetNumber" name="streetNumber" value="${streetNumber}"> </div>
                            <div>
                                <label for="barangay">
                                    <spring:message code="person.form.label.address.barangay" />
                                </label>
                                <input type="text" id="barangay" name="barangay" value="${barangay}"> </div>
                            <div>
                                <label for="municipality">
                                    <spring:message code="person.form.label.address.municipality" />
                                </label>
                                <input type="text" id="municipality" name="municipality" value="${municipality}"> </div>
                            <div>
                                <label for="zipCode">
                                    <spring:message code="person.form.label.address.zipCode" />
                                </label>
                                <input type="number" id="zipCode" name="zipCode" min="0" value="${zipCode}"> </div>
                        </fieldset>
                        <fieldset>
                            <legend>
                                <spring:message code="person.form.label.otherInformation" />
                            </legend>
                            <div>
                                <label for="birthday">
                                    <spring:message code="person.form.label.otherInformation.birthday" />
                                </label>
                                <input type="date" id="birthday" name="birthday" value="${birthday}"> </div>
                            <div>
                                <label for="GWA">
                                    <spring:message code="person.form.label.otherInformation.GWA" />
                                </label>
                                <input type="number" id="GWA" name="GWA" value="${GWA}" min="1" max="5" step="0.001"> </div>
                            <div>
                                <label>
                                    <spring:message code="person.form.label.otherInformation.currentlyEmployed" />
                                </label>
                                <input type="checkbox" name="currentlyEmployed" onclick="document.getElementById('dateHired').disabled=!this.checked">
                            </div>
                            <div>
                                <label for="dateHired">
                                    <spring:message code="person.form.label.otherInformation.dateHired" />
                                </label>
                                <input type="date" id="dateHired" name="dateHired" value="${dateHired}">
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>
                                <spring:message code="person.form.label.contactInformation" />
                            </legend>
                            <c:forEach items="${assignedContacts}" var="contact">
                                <div>
                                    <select class="assigned-contacts" name="contactType">
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
                                    <input type="text" name="contactData" value="${contact.data}">
                                </div>
                            </c:forEach>
                            <button onclick="this.parentNode.insertBefore(CONTACT_MARK_UP.cloneNode(true), this); return false;">
                                <spring:message code="form.button.add" />
                            </button>
                        </fieldset>
                        <fieldset>
                            <legend>
                                <spring:message code="person.form.label.roleAssignments" />
                            </legend>
                            <c:forEach items="${assignedRoleIds}" var="role">
                                <div>
                                    <select class="assigned-roles" name="personRoleIds">
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
                </form>
            </fieldset>
        </div>
        <div class="data">
            <h3>
                <spring:message code="person.data.header" />
            </h3>
            <form action="/person/list" method="GET">
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
                        <input type="text" id="queryLastName" name="queryLastName" value="${queryLastName}"> </div>
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
                        <input type="date" id="queryBirthday" name="queryBirthday" value="${queryBirthday}"> </div>
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
                            <th>C
                                <spring:message code="person.data.column.contacts" />
                            </th>
                            <th>
                                <spring:message code="person.data.column.roles" />
                            </th>
                            <th></th>
                            <th></th>
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
                                    <td>
                                        <form action="/person/list" method="GET">
                                            <input type="hidden" name="id" value="${person.id}">
                                            <input type="hidden" name="querySearchType" value="${querySearchType}">
                                            <input type="hidden" name="queryLastName" value="${queryLastName}">
                                            <input type="hidden" name="queryRoleId" value="${queryRoleId}">
                                            <input type="hidden" name="queryBirthday" value="${queryBirthday}">
                                            <input type="hidden" name="queryOrderBy" value="${queryOrderBy}">
                                            <input type="hidden" name="queryOrderType" value="${queryOrderType}">
                                            <button>
                                                <spring:message code="data.form.button.edit" />
                                            </button>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/person/delete" method="POST">
                                            <input type="hidden" name="id" value="${person.id}">
                                            <input type="hidden" name="querySearchType" value="${querySearchType}">
                                            <input type="hidden" name="queryLastName" value="${queryLastName}">
                                            <input type="hidden" name="queryRoleId" value="${queryRoleId}">
                                            <input type="hidden" name="queryBirthday" value="${queryBirthday}">
                                            <input type="hidden" name="queryOrderBy" value="${queryOrderBy}">
                                            <input type="hidden" name="queryOrderType" value="${queryOrderType}">
                                            <button onclick="return confirm('<spring:message code="person.data.form.button.deleteConfirmation" arguments="${person.name}" />')">
                                                <spring:message code="data.form.button.delete" />
                                            </button>
                                        </form>
                                    </td>
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
 
    if ('${querySearchType}') {
        document.getElementById('querySearchType').value = '${querySearchType}';
    }
 
    if ('${queryOrderBy}') {
        document.getElementById('queryOrderBy').value = '${queryOrderBy}';
    }
 
    if ('${queryOrderType}') {
        document.getElementById('queryOrderType').value = '${queryOrderType}';
    }
 
    if ('${queryRoleId}') {
        document.getElementById('queryRoleId').value = '${queryRoleId}';
    }
 
    if ('${currentlyEmployed}' === 'true') {
        document.getElementById('dateHired').disabled = false;
        document.getElementsByName('currentlyEmployed')[0].checked = true;
    } else {
        document.getElementById('dateHired').disabled = true;
        document.getElementsByName('currentlyEmployed')[1].checked = true;
    }
 
    var options = [document.getElementById('queryLastName'), document.getElementById('queryRoleId'), document.getElementById('queryBirthday')];

    document.getElementById('querySearchType').addEventListener("change", (element) => {
        options.forEach((value, index) => value.parentNode.hidden = value.disabled = element.target.value != index);
    });

    document.getElementById('querySearchType').dispatchEvent(new Event("change"));
</script>

</html>