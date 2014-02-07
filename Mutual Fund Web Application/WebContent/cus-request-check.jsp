<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@page import="databeans.Customer"%>
    
<%@page import="java.text.DecimalFormat" %>
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
         <jsp:include page="template-top-cus.jsp" />
     
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
                    <li><a href="cus-view-account.jsp">Home</a></li>
                    <li class="active">Request Check</li>
                </ol>
                
	<%
	Customer customer = (Customer) session.getAttribute("customer");
	if(customer == null) {
	    out.print("Please login as customer first!");
	    return;
	}
	%>
                <jsp:include page="error-list.jsp" />
                <form method="post" action="cus_requestCheck.do">
                <table>
                    <tr>
				<div class="col-md-12" align="center">
 					<div class="panel panel-info" >
  						<div class="panel-heading">
  						<%
                            DecimalFormat nf = new DecimalFormat("#,##0.00");
                            nf.setMaximumFractionDigits(2);
                           	nf.setMinimumFractionDigits(2);
	                        Long cash = (Long) request.getAttribute("cash");
	                        if (cash!=null)
	                     %>
  						  <h3 class="panel-title">Available Balance : $ <%=nf.format(cash/100.0)%> </h3>
  						</div>
                        <td>    
                        
                        </td>
                    </tr>
                    <tr>
						<div class="panel-body">
					<div class="col-md-12" >
	                    <label class="control-label"><h4>Withdraw Amount:</h4></label>
                    </div>
                    <div class="input-group">
					  <span class="input-group-addon">$</span>
					  <input type="text" name="withdraw" class="form-control" placeholder="No more than your available balance">
					</div>
 					</div>

  					</div>
 					</div>
						
                    </tr>
                    <tr>
                        <div class="col-md-12" align="right">
                        <div class="btn-group">
                            <button type="submit" class="btn btn-default btn-lg">Request Check</button>
                        </div>
                    </div>

                    </tr>
                </table>
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