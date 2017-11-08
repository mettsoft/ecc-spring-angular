<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form" %>
<html>

<head>
    <title>
        <spring:message code="user.title" />
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
        <a href="/users?language=${locale=='en'? 'fil' : 'en'}">
            <spring:message code="language.${locale == 'en'? 'filipino': 'english'}" />
        </a>                
    </div>
    <a href="/persons">
        <spring:message code="user.navigation" />
    </a>
    <div class="container">
        <div>
            <c:forEach items="${errorMessages}" var="errorMessage">
                <h3 class="error-message">${errorMessage}</h3>
            </c:forEach>
            <h3>${successMessage}</h3>
            <h3>${headerTitle}</h3>
            <form:form action="/users${action}" method="POST">
                <form:input type="hidden" path="id" />
                <label for="username">
                    <spring:message code="user.data.column.username" />:
                </label>
                <form:input type="text" path="username" />
                <label for="password">
                    <spring:message code="user.form.label.password" />:
                </label>
                <form:input type="password" path="password" />
                <button>
                    <spring:message code="form.button.submit" />
                </button>
            </form:form>
        </div>
        <div>
            <h3>
                <spring:message code="user.data.header" />
            </h3>
            <c:choose>
                <c:when test="${data.size() > 0}">
                    <table>
                        <thead>
                            <th hidden>
                                <spring:message code="user.data.column.id" />
                            </th>
                            <th>
                                <spring:message code="user.data.column.username" />
                            </th>
                            <th></th>
                            <th></th>
                        </thead>
                        <tbody>
                            <c:forEach items="${data}" var="user">
                                <tr>
                                    <td hidden>${user.id}</td>
                                    <td>${user.username}</td>
                                    <td>
                                        <form action="/users" method="GET">
                                            <input type="hidden" name="id" value="${user.id}">
                                            <button>
                                                <spring:message code="data.form.button.edit" />
                                            </button>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/users/delete" method="POST">
                                            <input type="hidden" name="id" value="${user.id}">
                                            <input type="hidden" name="username" value="${user.username}">
                                            <button onclick="return confirm('<spring:message code="user.data.form.button.deleteConfirmation" arguments="${user.username}" />')">
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