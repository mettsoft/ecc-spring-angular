<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>

<head>
    <title>Role | Person Registry System</title>
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
    </style>
</head>

<body><a href="/">Go to Person Registry</a>
    <div class="container">
        <div>
            <h3>${message}</h3>
            <h3>${headerMessage}</h3>
            <form action="/role" method="POST">
                <input type="hidden" name="mode" value="${mode}">
                <input type="hidden" name="id" value="${id}">
                <label for="name">Enter the role name:</label>
                <input type="text" id="name" name="name" value="${name}">
                <button>Submit</button>
            </form>
        </div>
        <div>
            <h3>Roles</h3>

            <c:choose>
                <c:when test="${data.size() > 0}">
                    <table>
                        <thead>
                            <th hidden>ID</th>
                            <th>Name</th>
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
                                            <input type="hidden" name="id" value="${role.id}"><button>Edit</button>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/role/delete" method="POST">
                                            <input type="hidden" name="id" value="${role.id}"><button>Delete</button>
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

</html>