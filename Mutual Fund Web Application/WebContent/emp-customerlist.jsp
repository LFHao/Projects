<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="databeans.Employee"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Carnegie Financial Services</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="css/simple-sidebar.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">

</head>

<body>

    <div id="wrapper">
    <!-- Template -->
		<jsp:include page="template-top-emp.jsp" />

        <!-- Page content -->
        <div id="page-content-wrapper">
            <div class="content-header">
                <h1>
                    <a id="menu-toggle" href="#" class="btn btn-default"><i class="icon-reorder"></i></a>
                   
                </h1>
            </div>
            <!-- Keep all page content within th
             e page-content inset div! -->
            <div class="page-content inset">
                <ol class="breadcrumb">
                    <li><a href="emp-customerlist.do">Home</a></li>
                    <li class="active">Customer List</li>
                </ol>
                <%
                	Employee employee = (Employee) session.getAttribute("employee");
					if(employee == null) {	
					    out.print("Please login as employee first!");
					    return;
					}
					
					%>
                <jsp:include page="error-list.jsp"/>
                
                <div class="row">
                    <div class="col-md-12">
                        <p class="lead">List of Customers</p>
                    </div>
					
					<div class="col-md-12">
						<form method="post" action="emp-customerlist.do">
	                        <table class="table">
	                            <thead>
	                                <tr>
	                                    <th>Customer ID</th>
	                                    <th>Customer First Name</th>
	                                    <th>Customer Last Name</th>
	                                    <th>Username</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <c:forEach var="cust" items="${customers}">
	                                	<tr>
	                                		<td>${cust.customer_id}</td>
	                                		<td>${cust.firstname}</td>
											<td>${cust.lastname}</td>
											<td>${cust.username}</td>	 
											<td colspan="4"><input type="submit" name="manage_cust_${cust.customer_id}" value="Manage"/></td>                               		
	                                	</tr>
	                                </c:forEach>
	                            </tbody>
	                        </table>
                        </form>
                    </div>
                    <!-- List of Funds -->
            
                </div>
            </div>
        </div>

    </div>

    <!-- JavaScript -->
    <script src="js/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.js"></script>

    <!-- Custom JavaScript for the Menu Toggle -->
    <script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("active");
    });
    </script>
</body>

</html>
