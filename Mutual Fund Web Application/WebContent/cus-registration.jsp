<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
                    <li><a href="emp-customerlist.jsp">Home</a></li>
                    <li class="active">Customer Registration</li>
                </ol>
                    <%
					Employee employee = (Employee) session.getAttribute("employee");
					if(employee == null) {	
					    out.print("Please login as employee first!");
					    return;
					}
					%>
                
                <jsp:include page="error-list.jsp" />
                     <form class="form-signin" role="form" method="post" action="cus-registration.do">
     				 <div class="row">
    
			<div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">First Name:</label>
    <div class="col-md-9">
      <input name="firstname" type="text" class="form-control" id="inputPassword3" placeholder="first name...">
    </div>
  </div>
  <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Last Name:</label>
    <div class="col-md-9">
      <input name="lastname" type="text" class="form-control" id="inputPassword3" placeholder="last name...">
    </div>
  </div>
  <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">User Name:</label>
    <div class="col-md-9">
      <input name="username" type="text" class="form-control" id="inputPassword3" placeholder="user name...">
    </div>
  </div>
  <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Password:</label>
    <div class="col-md-9">
      <input name="password" type="password" class="form-control" id="inputPassword3" placeholder="password...">
    </div>
  </div>
  <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Address Line 1:</label>
    <div class="col-md-9">
      <input name="addrline1" type="text" class="form-control" id="inputPassword3" placeholder="Address Line 1...">
    </div>
  </div>
  <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Address Line 2:</label>
    <div class="col-md-9">
      <input name="addrline2" type="text" class="form-control" id="inputPassword3" placeholder="Address Line 2...">
    </div>
  </div>
  <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Zip Code:</label>
    <div class="col-md-9">
      <input name="zip" type="text" class="form-control" id="inputPassword3" placeholder="zipcode...">
    </div>
  </div>
  <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">City:</label>
    <div class="col-md-9">
      <input name="city" type="text" class="form-control" id="inputPassword3" placeholder="city...">
    </div>
  </div>
  <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">State:</label>
    <div class="col-md-9">
      <input name="state" type="text" class="form-control" id="inputPassword3" placeholder="state...">
    </div>
  </div>
  <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Cash:</label>
    <div class="col-md-9">
      <input name="cash" type="text" class="form-control" id="inputPassword3" placeholder="cash...">
    </div>
  </div>
  <br><br>
  <div class="col-md-12" align="right">
                        <div class="btn-group">
                            <button type="submit" class="btn btn-default btn-lg">Register</button>
                        </div>
                    </div>
       				
       				</form>			
     	
                 
            </div>
        </div>
<jsp:include page="template-footer.jsp" />
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
