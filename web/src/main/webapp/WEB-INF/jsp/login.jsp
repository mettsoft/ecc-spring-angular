<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://www.springframework.org/tags" prefix = "spring"%>
<html>
    <head>
        <title>
            <spring:message code="login.title" />
        </title>
        <style>
            .error-message {
                color: red;
            }
        </style>
    </head>
    <body>
        <c:if test = "${param.error != null}">        
            <h3 class="error-message">
                <spring:message code="login.errorMessage" />
            </h3>
        </c:if>
        <c:if test = "${param.logout != null}">
            <h3>
                <spring:message code="login.logoutMessage" />            
            </h3>
        </c:if>
        <form action = "/login" method = "POST">
            <div>
                <label> 
                    <spring:message code="user.data.column.username" />:
                </label>
                <input type="text" name="username"/> 
            </div>
            <div>
                <label> 
                    <spring:message code="user.form.label.password" />:
                </label> 
                <input type="password" name="password"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </div>
            <div>
                <button>
                    <spring:message code="login.form.button.signIn" />
                </button>
            </div>
        </form>
    </body>
</html>