<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring"%>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form" %>
<%@ taglib uri = "http://www.springframework.org/security/tags" prefix = "security" %>
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
        <a href="/roles?language=${pageContext.response.locale.language=='en'? 'fil' : 'en'}">
            <spring:message code="language.${pageContext.response.locale.language == 'en'? 'filipino': 'english'}" />
        </a>                
    </div>
    <a href="/persons">
        <spring:message code="navigation.persons" />
    </a>
    <div class="container">
        <div>
            <c:forEach items="${errorMessages}" var="errorMessage">
                <h3 class="error-message">${errorMessage}</h3>
            </c:forEach>
            <h3>${successMessage}</h3>
            <c:if test="${action == '/create'}">
                <security:authorize access="hasRole('ROLE_CREATE_ROLE')">
                    <h3>${headerTitle}</h3>
                    <form:form action="/roles${action}" method="POST">
                        <form:input type="hidden" path="id" />
                        <label for="name">
                            <spring:message code="role.form.label.name" />:
                        </label>
                        <form:input type="text" path="name" />
                        <button>
                            <spring:message code="form.button.submit" />
                        </button>
                    </form:form>                
                </security:authorize>
            </c:if>
            <c:if test="${action == '/update'}">
                <security:authorize access="hasRole('ROLE_UPDATE_ROLE')">
                    <h3>${headerTitle}</h3>
                    <form:form action="/roles${action}" method="POST">
                        <form:input type="hidden" path="id" />
                        <label for="name">
                            <spring:message code="role.form.label.name" />:
                        </label>
                        <form:input type="text" path="name" />
                        <button>
                            <spring:message code="form.button.submit" />
                        </button>
                    </form:form>                
                </security:authorize>
            </c:if>
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
                            <security:authorize access="hasRole('ROLE_UPDATE_ROLE')">
                                <th></th>
                            </security:authorize>
                            <security:authorize access="hasRole('ROLE_DELETE_ROLE')">
                                <th></th>
                            </security:authorize>
                        </thead>
                        <tbody>
                            <c:forEach items="${data}" var="role">
                                <tr>
                                    <td hidden>${role.id}</td>
                                    <td>${role.name}</td>
                                    <security:authorize access="hasRole('ROLE_UPDATE_ROLE')">
                                        <td>
                                            <form action="/roles" method="GET">
                                                <input type="hidden" name="id" value="${role.id}">
                                                <button>
                                                    <spring:message code="data.form.button.edit" />
                                                </button>
                                            </form>
                                        </td>
                                    </security:authorize>
                                    <security:authorize access="hasRole('ROLE_DELETE_ROLE')">
                                        <td>
                                            <form action="/roles/delete" method="POST">
                                                <input type="hidden" name="id" value="${role.id}">
                                                <input type="hidden" name="name" value="${role.name}">
                                                <button onclick="return confirm('<spring:message code="role.data.form.button.deleteConfirmation" arguments="${role.name}" />')">
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

</html>