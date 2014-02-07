<%@page import="databeans.Customer"%>
<%@page import="databeans.Fund"%>
<%@page import="databeans.FundPriceHistory"%>
<%@page import="java.util.*" %>
<%@page import="utility.Format" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

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
	<script type="text/javascript" src="scripts/jquery.js"></script>
	
	<script>
	var financeData = [];
	var fundName = "No Fund";
	<%
	ArrayList<FundPriceHistory> histories = (ArrayList<FundPriceHistory>) request.getAttribute("histories");
	Fund fund = (Fund) request.getAttribute("fund");
	out.println("financeData = [ ");
	if (histories!=null) {
	    for (FundPriceHistory history : histories) {
	        out.println("["+history.getPrice_date().getTime()+","+ history.getPrice()/100.0 + "],");   
	    }
	}
	out.println("];");
	if(fund!=null)
	    out.println("fundName = '" + fund.getName() + "';");
	%>
	</script>
	
	<script type="text/javascript" src="scripts/draw.js"></script>

</head>

<body>
	<div id="wrapper">
		<!-- Template -->
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
                    <li><a href="#">Home</a></li>
                    <li class="active">Fund Details</li>
                </ol>
	<%
	Customer customer = (Customer) session.getAttribute("customer");
	if(customer == null) {
	    out.print("Please login as customer first!");
	    return;
	}
	%>	
			</div>
			
			<div id="content">			
	    	</div>
	    	
		</div>
	</div>                    

	<script src="scripts/highstock.js"></script>
	
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
