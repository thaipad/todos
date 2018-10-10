<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="pro.thaipad.todos.objects.Task" %>

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<html>
<head>
    <title>Открытые задачи</title>
</head>
<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Active tasks</h1>
</div>
<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">
        <div class="w3-container w3-light-blue">
            <h2>Task</h2>
        </div>

        <table>
            <%

                List<Task> tasks = (List<Task>) request.getAttribute("list");

                if (tasks != null && !tasks.isEmpty()) {
                    out.println("<tr class=\"w3-ul\">");
                    out.println("<th>Complete</th><th>Task</th><th>Description</th><th>Date</th>" +
                            "<th>Time</th><th>Duration</th>");
                    out.println("</tr>");
                    for (Task task : tasks) {
                        out.println("<tr>");
                        out.println("<td class=\"w3-hover-sand\">" + (task.isComplete() ? "V" : " ") + "</td>");
                        out.println("<td class=\"w3-hover-sand\">" + task.getName() + "</td>");
                        out.println("<td class=\"w3-hover-sand\">" + task.getDescription() + "</td>");
                        out.println("<td class=\"w3-hover-sand\">" + task.getDate() + "</td>");
                        out.println("<td class=\"w3-hover-sand\">" + task.getTime() + "</td>");
                        out.println("<td class=\"w3-hover-sand\">" + task.getDuration() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</ui>");
                } else out.println("<div class=\"w3-panel w3-red w3-display-container w3-card-4 w3-round\">\n"
                        +
                        "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                        "   class=\"w3-button w3-margin-right w3-display-right w3-round-large " +
                            "w3-hover-red w3-border w3-border-red w3-hover-border-grey\">×</span>\n" +
                        "   <h5>There are not active tasks!</h5>\n" +
                        "</div>");
            %>
        </table>
    </div>
</div>
</body>
</html>
