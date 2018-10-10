<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
    <title>Home</title>

<!--    <script src="//ajax/googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript">
        function doAjax() {
            $.ajax({
                url: 'check-strength',
                data: ({password: $('#password').val()}),
                success: function(data) {
                    $('#strengthValue').html(data);
                }
            });
        }
    </script>
</head>
<body>


<form:form method="post" commandName="user" action="check-user" class="login">
    <p class="container">
    <p>
        <span style="float: right">
            <a href="?lang=en"><c:message code="english"/></a>
            <a href="?lang=ru"><c:message code="russian"/></a>
        </span>
    </p>
    <h1><c:message code="greeting"/></h1>
    <p>
        <form:label path="email"><c:message code="usermail"/></form:label>
        <form:input path="email"/>
        <form:errors path="email" cssClass="error"/>
    </p>

    <p>
        <form:label path="password"><c:message code="password"/></form:label>
        <form:password path="password" onkeyup="doAjax()"/>
        <form:errors path="password" cssClass="error"/>
        <span style="float: right" id="strengthValue"></span>
    </p>

    <p class="remember_me">
        <label>
            <input type="checkbox" name="remember_me" id="remember_me">
            <c:message code="remember_me"/>
        </label>
    </p>
    <p class="submit"><input type="submit" name="commit" value="<c:message code="commit_button"/>"></p>
    </section>
</form:form>
<!--    </div>

 -->
</body>
</html>
