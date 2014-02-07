<%@page import="databeans.Customer"%>
<%@page import="databeans.Fund"%>
<%@page import="databeans.FundPriceHistory"%>
<%@page import="java.util.*" %>
<%@page import="utility.Format" %>
<%@page import= "java.text.DecimalFormat" %>
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
                    <li class="active">Research Fund</li>
                </ol>
<%
Customer customer = (Customer) session.getAttribute("customer");
if(customer == null) {
    out.print("Please login as customer first!");
    return;
}
%>                    
                   <jsp:include page="error-list.jsp" />

                    <div class="col-md-12">
                        <p class="lead">All Funds List</p>
                    </div>
                    <% 	ArrayList<Fund> allFunds = (ArrayList<Fund>)request.getSession().getAttribute("allFunds");
                    	ArrayList<Long> allFundPrices = (ArrayList<Long>)request.getSession().getAttribute("allFundPrices");
                    	DecimalFormat dfAmount = new DecimalFormat("###,###,###,###,###,###,##0.00");
                    	if(allFunds!=null && allFunds.size()!=0){
                    %>
                    
                    
                    <div class="col-md-12">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Fund ID</th>
                                    <th>Fund Name</th>
                                    <th>Fund Symbol</th>
                                    <th><div align='right'>Current Prices</div></th>
                                    <th><div align='center'>Performance</div></th>
                                </tr>
                            </thead>
                            <tbody>
                            <% 
                            	for(int i=0; i<allFunds.size();i++){ 
                            %>
                           
                                <tr>                 
                                    <td><%=allFunds.get(i).getFund_id()%></td>
                                    <td><%=allFunds.get(i).getName()%></td>
                                    <td><%=allFunds.get(i).getSymbol()%></td>
                                    
                                    <%if(allFundPrices.get(i)==-1) {%>
                                    <td><div align='right'><%="Not Initialized" %></div></td>
                                    <%}else{ %>
                                    <td><div align='right'><%= dfAmount.format(allFundPrices.get(i)/100.0)%></div></td>
                                    <%} %>
                                    
                                    <%if(allFundPrices.get(i)==-1) {%>
                                    <td><div align='center'><%="Not Avaiable" %></div></td>
                                    <%}else{ %>
                                    <td><div align='center'>
                                    	<form method="post" action="cus_getFundDetails.do">
                                    	<button type="submit" class="btn btn-default btn-xs" name="fund_id" value="<%=allFunds.get(i).getFund_id()%>">
                                    	View Now</button>
                                    	</form>
                                    	</div>
                                    </td>
                                    <%} %>
                                </tr>
                                
                             <%
                             	}
                            }else{
                            %>
                           </tbody>
	                     <div class="col-md-12">
		                  <p>No funds, Ask for help!</p>
		                 </div>  
		                <%}
                    	%> 
                        </table>
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
