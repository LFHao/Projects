<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import= "java.text.DecimalFormat" %>
<%@page import="databeans.Position"%>
<%@page import="databeans.Fund"%>
<%@page import="java.util.*" %>
<%@page import="databeans.Customer"%>
    
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
                    <li class="active">Sell Fund</li>
                </ol>
    <%
	Customer customer = (Customer) session.getAttribute("customer");
	if(customer == null) {
	    out.print("Please login as customer first!");
	    return;
	}
	%>
                
                
                <div class="row">
                    <div class="col-md-12">
                        <p class="lead">Sell Fund</p>
                    </div>
                    <jsp:include page="error-list.jsp" />
                    <form method="post" action="cus_sellFund.do" class="form-horizontal">
	                    <div class="col-md-12">
	                        <div class="input-group">
	                            <span class="input-group-addon">Fund Symbol</span>
	                            <input type="text" class="form-control" placeholder="Fund Symbol" name="fundSymbol">
	                        </div>
	                    </div>
	                    <div class="col-md-12">
	                        <p class="lead"> </p>
	                    </div>
	                    <div class="col-md-12">
	                        <div class="input-group">
	                            <span class="input-group-addon">Shares</span>
	                            <input type="text" class="form-control" placeholder="Shares (No more than you own)" name="shares">
	                                </div>
	                    </div>          
	                    <div class="col-md-12">
	                        <p class="lead"> </p>
	                    </div>
	                    <div class="col-md-2">
	                        <p class="lead"> </p>
	                    </div>
	                    <div class="col-md-2">
	                        <p class="lead"> </p>
	                    </div>
	                   
	                   <div class="col-md-12" align="right">
                        <div class="btn-group">
                            <button type="submit" class="btn btn-default btn-lg">Sell Now</button>
                        </div>
                    </div>
                    </form>
                    <div class="col-md-4">
                        <p class="lead"></p>
                    </div>
                    <div class="col-md-12">
                        <p class="lead"></p>
                    </div>
                    
                    <!-- List of Funds -->
                    <div class="col-md-12">
                        <p class="lead">Your Funds List</p>
                    </div>
                    <%
                    Position[] pos = (Position[])request.getAttribute("pos");
                    ArrayList<Long> prices = (ArrayList<Long>) request.getAttribute("prices");
                    ArrayList<Fund> funds = (ArrayList<Fund>) request.getAttribute("funds");
                    DecimalFormat dfAmount = new DecimalFormat("###,###,###,###,###,###,##0.00");
					DecimalFormat dfShare = new DecimalFormat("###,###,###,###,###,###,##0.000");
					if(pos!=null && pos.length!=0){
					%>                   
                    <div class="col-md-12">
	                        <table class="table">
	                            <thead>
	                                <tr>
	                                    <th>Fund ID</th>
	                                    <th>Fund Name</th>
	                                    <th>Fund Symbol</th>            
	                                    <th><div align='right'>Shares</div></th>
	                                    <th><div align='right'>Latest Price</div></th>
	                                </tr>
	                            </thead>
	                            <tbody>
                       <%                    		
                       		for(int i=0; i<pos.length;i++){ 
                       %>
                                	<tr>
                                		<td><%=funds.get(i).getFund_id()%></td>
                                		<td><%=funds.get(i).getName()%></td>
                                		<td><%=funds.get(i).getSymbol()%></td>
                                		<td><div align='right'><%=dfShare.format(pos[i].getShares()/1000.0)%></div></td>
   										<td><div align='right'><%=dfAmount.format(prices.get(i)/100.0) %></div></td>
                                	</tr>

						<%
							}
                       	}else{
						%>                      
						<div class="col-md-12">
                            You own no funds, Buy some now!
                        </div>  
  
                       <%}
                    	%> 
                                </tbody>
	                        </table>
                    </div>                  
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
