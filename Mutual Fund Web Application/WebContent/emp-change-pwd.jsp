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
                    <li class="active">Change Password</li>
                </ol>
                	<%
					Employee employee = (Employee) session.getAttribute("employee");
					if(employee == null) {	
					    out.print("Please login as employee first!");
					    return;
					}
					%>	
				<jsp:include page="error-list.jsp" />
                 <form method="post" action="emp_changePwd.do" class="form-horizontal">
                <div class="row">
    
			<div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">New Password:</label>
    <div class="col-md-9">
      <input type="password" name="newPassword"class="form-control" id="inputPassword3" placeholder="new Password...">
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Re-enter New:</label>
    <div class="col-md-9">
      <input type="password" name="confirmPassword" class="form-control" id="inputPassword3" placeholder="new Password...">
    </div>
  </div>
  <br><br>
  <div class="col-md-12" align="right">
                        <div class="btn-group">
                            <button type="submit" class="btn btn-default btn-lg">Submit Change</button>
                        </div>
                    </div>

                   
                    <div class="col-md-12">
                        <p class="lead"></p>
                    </div>
                </div>
				</form>
				<jsp:include page="template-footer.jsp" />
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
