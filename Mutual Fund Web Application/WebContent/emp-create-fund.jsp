<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="databeans.Employee"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
        <div class="page-content-wrapper">
        	<div class="content-header">
                <h1>
                    <a id="menu-toggle" href="#" class="btn btn-default"><i class="icon-reorder"></i></a>
                   
                </h1>
            </div>
            
            <div class="page-content inset">
                <ol class="breadcrumb">
                    <li><a href="emp-customerlist.do">Home</a></li>
                    <li class="active">Create Fund</li>
                </ol>
                                                	<%
					Employee employee = (Employee) session.getAttribute("employee");
					if(employee == null) {	
					    out.print("Please login as employee first!");
					    return;
					}
					%>
                
                            
	            <jsp:include page="error-list.jsp" />
	            
	            <div class="row">
		            <div class="col-md-12">
					<form method="post" action="create-fund-emp.do">
					
					<div class="row">
					<div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Fund Name:</label>
    <div class="col-md-9">
      <input type="text" class="form-control" name="fundName" placeholder="Fund Name" value="${fund.name}">
    </div>
  </div>
 <br><br>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Fund Symbol:</label>
    <div class="col-md-9">
      <input type="text" class="form-control" name="fundSymbol" placeholder="Fund Symbol (4-6 characters)" value="${fund.symbol}">
    </div>
  </div>
  <br><br>
   <br><br>
  <div class="col-md-12" align="right">
                        <div class="btn-group">
                            <button type="submit" name="button" class="btn btn-default btn-lg">Create Fund</button>
                        </div>
                    </div>
                    </div>
					
						
					</form>
					</div>
				</div>
        	</div>
		</div>
</body>
</html>