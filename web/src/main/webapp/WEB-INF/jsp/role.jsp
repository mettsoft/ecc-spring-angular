<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form" %>
<html>

<head>
    <title>
        <spring:message code="role.title" />
    </title>
    <style>
        div.container {
            display: inline-flex;
            width: 100%;
        }
        
        div.container div {
            width: 50%;
            margin: 16px;
        }
        
        label {
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
</head>

<body>
    <div style="float: right;">
        <a href="/role/list?language=${locale=='en'? 'fil' : 'en'}">
            <spring:message code="language.${locale == 'en'? 'filipino': 'english'}" />
        </a>                
    </div>
    <a href="/person/list">
        <spring:message code="role.navigation" />
    </a>
    <div class="container">
        <div>
            <!-- TODO -->
            <h3 class="error-message">${errorMessage}</h3>
            <h3>${successMessage}</h3>
            <h3>${headerTitle}</h3>
            <form:form action="/role${action}" method="POST">
                <form:input type="hidden" path="id" />
                <label for="name">
                    <spring:message code="role.form.label.roleName" />
                </label>
                <form:input type="text" path="name" />
                <button>
                    <spring:message code="form.button.submit" />
                </button>
            </form:form>
        </div>
        <div>
            <h3>
                <spring:message code="role.data.header" />
            </h3>
            <c:choose>
                <c:when test="${data.size() > 0}">
                    <table>
                        <thead>
                            <th hidden>
                                <spring:message code="role.data.column.id" />
                            </th>
                            <th>
                                <spring:message code="role.data.column.name" />
                            </th>
                            <th></th>
                            <th></th>
                        </thead>
                        <tbody>
                            <c:forEach items="${data}" var="role">
                                <tr>
                                    <td hidden>${role.id}</td>
                                    <td>${role.name}</td>
                                    <td>
                                        <form action="/role/list" method="GET">
                                            <input type="hidden" name="id" value="${role.id}">
                                            <button>
                                                <spring:message code="data.form.button.edit" />
                                            </button>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/role/delete" method="POST">
                                            <input type="hidden" name="id" value="${role.id}">
                                            <input type="hidden" name="name" value="${role.name}">
                                            <button onclick="return confirm('<spring:message code="role.data.form.button.deleteConfirmation" arguments="${role.name}" />')">
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

</html>