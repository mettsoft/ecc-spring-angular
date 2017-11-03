<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>

<head>
    <title>Person | Person Registry System</title>
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
    <div id="role-mark-up" hidden>
        <select name="personRoleIds">
            <c:forEach items="${roleItems}" var="role">
                <option value="${role.id}">${role.name}</option>
            </c:forEach>
        </select>
        <button onclick="this.parentNode.remove()">Remove</button>
    </div>
    <div id="contact-mark-up" hidden>
        <select name="contactType">
            <option value="Landline">Landline</option>
            <option value="Email">Email</option>
            <option value="Mobile">Mobile</option>
        </select>
        <button onclick="this.parentNode.remove()">Remove</button>
        <input type="text" name="contactData">
    </div>

    <!-- Start Content -->
    <a href="/role/list">Go to Role Registry</a>
    <div class="container">
        <div class="form">
            <h3 class="error-message">${errorMessage}</h3>
            <h3>${successMessage}</h3>
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
                    <legend><strong>${headerTitle}</strong></legend>
                    <fieldset>
                        <legend>Name:</legend>
                        <div>
                            <label for="title">Title:</label>
                            <input type="text" id="title" name="title" value="${title}"> </div>
                        <div>
                            <label for="lastName">Last name:</label>
                            <input type="text" id="lastName" name="lastName" value="${lastName}">
                        </div>
                        <div>
                            <label for="firstName">First name:</label>
                            <input type="text" id="firstName" name="firstName" value="${firstName}">
                        </div>
                        <div>
                            <label for="middleName">Middle name:</label>
                            <input type="text" id="middleName" name="middleName" value="${middleName}">
                        </div>
                        <div>
                            <label for="suffix">Suffix:</label>
                            <input type="text" id="suffix" name="suffix" value="${suffix}"> </div>
                    </fieldset>
                    <fieldset>
                        <legend>Address:</legend>
                        <div>
                            <label for="streetNumber">Street number:</label>
                            <input type="text" id="streetNumber" name="streetNumber" value="${streetNumber}"> </div>
                        <div>
                            <label for="barangay">Barangay:</label>
                            <input type="text" id="barangay" name="barangay" value="${barangay}"> </div>
                        <div>
                            <label for="municipality">Municipality:</label>
                            <input type="text" id="municipality" name="municipality" value="${municipality}"> </div>
                        <div>
                            <label for="zipCode">Zip code:</label>
                            <input type="number" id="zipCode" name="zipCode" min="0" value="${zipCode}"> </div>
                    </fieldset>
                    <fieldset>
                        <legend>Other Information:</legend>
                        <div>
                            <label for="birthday">Birthday:</label>
                            <input type="date" id="birthday" name="birthday" value="${birthday}"> </div>
                        <div>
                            <label for="GWA">GWA:</label>
                            <input type="number" id="GWA" name="GWA" value="${GWA}" min="1" max="5" step="0.001"> </div>
                        <div>
                            <label>Currently employed:</label>
                            <input type="radio" name="currentlyEmployed" value="Yes" onclick="document.getElementById('dateHired').disabled=false">Yes</input>
                            <input type="radio" name="currentlyEmployed" value="No" onclick="document.getElementById('dateHired').disabled=true">No</input>
                        </div>
                        <div>
                            <label for="dateHired">Date Hired:</label>
                            <input type="date" id="dateHired" name="dateHired" value="${dateHired}">
                        </div>
                    </fieldset>
                    <fieldset>
                        <legend>Contact Information:</legend>
                        <c:forEach items="${assignedContacts}" var="contact">
                            <div>
                                <select class="assigned-contacts" name="contactType">
                                    <option value="Landline">Landline</option>
                                    <option value="Email">Email</option>
                                    <option value="Mobile">Mobile</option>
                                </select>
                                <button onclick="this.parentNode.remove()">Remove</button>
                                <input type="text" name="contactData" value="${contact.data}">
                            </div>
                        </c:forEach>
                        <button onclick="this.parentNode.insertBefore(CONTACT_MARK_UP.cloneNode(true), this); return false;">Add</button>
                    </fieldset>
                    <fieldset>
                        <legend>Role Assignment:</legend>
                        <c:forEach items="${assignedRoleIds}" var="role">
                            <div>
                                <select class="assigned-roles" name="personRoleIds">
                                    <c:forEach items="${roleItems}" var="roleItem">
                                        <option value="${roleItem.id}">${roleItem.name}</option>
                                    </c:forEach>
                                </select>
                                <button onclick="this.parentNode.remove()">Remove</button>
                            </div>                           
                        </c:forEach>
                        <button onclick="this.parentNode.insertBefore(ROLE_MARK_UP.cloneNode(true), this); return false;">Add</button>
                    </fieldset>
                    <button>Submit</button>
                </fieldset>
            </form>
        </div>
        <div class="data">
            <h3>Persons</h3>
            <form action="/person/list" method="GET">
                <fieldset>
                    <legend>Search Parameters</legend>
                    <div>
                        <label for="querySearchType">Criteria:</label>
                        <select id="querySearchType" name="querySearchType">
                            <option value="0">Last Name</option>
                            <option value="1">Role</option>
                            <option value="2">Birthday</option>
                        </select>
                    </div>
                    <div>
                        <label for="queryLastName">Last name:</label>
                        <input type="text" id="queryLastName" name="queryLastName" value="${queryLastName}"> </div>
                    <div>
                        <label for="queryRoleId">Role:</label>
                        <select name="queryRoleId" id="queryRoleId">
                            <option value="">All</option>
                            <c:forEach items="${roleItems}" var="role">
                                <option value="${role.id}">${role.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label for="queryBirthday">Birthday:</label>
                        <input type="date" id="queryBirthday" name="queryBirthday" value="${queryBirthday}"> </div>
                    <div>
                        <label>Order By:</label>
                        <select name="queryOrderBy" id="queryOrderBy">
                            <option value="name.lastName">Last Name</option>
                            <option value="dateHired">Date Hired</option>
                            <option value="GWA">GWA</option>
                        </select>
                        <select name="queryOrderType" id="queryOrderType">
                            <option value="ASC">Ascending</option>
                            <option value="DESC">Descending</option>
                        </select>
                    </div>
                    <button>Search</button>
                </fieldset>
            </form>
            <c:choose>
                <c:when test="${data.size() > 0}">
                    <table>
                        <thead>
                            <th hidden>ID</th>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Birthday</th>
                            <th>GWA</th>
                            <th>Employment</th>
                            <th>Contacts</th>
                            <th>Roles</th>
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
                                            <button>Edit</button>
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
                                            <button onclick="return confirm('Are you sure you want to delete ${person.name}?')">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <h6>No records found!</h6>
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