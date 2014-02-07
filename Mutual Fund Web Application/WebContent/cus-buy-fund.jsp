<%@page import="databeans.Customer"%>
<%@page import="databeans.Fund"%>
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
                    <li class="active">Buy Fund</li>
                </ol>
<%
Customer customer = (Customer) session.getAttribute("customer");
DecimalFormat dfAmount = new DecimalFormat("###,###,###,###,###,###,##0.00");
if(customer == null) {
    out.print("Please login as customer first!");
    return;
}
%>
				<div class="col-md-12" align="center">
 					<div class="panel panel-info" >
  						<div class="panel-heading">
  						  <h3 class="panel-title">Available Balance: <span><%="$" + " " + dfAmount.format(customer.getCash()/100.0)%></span></h3>
  						</div>
                     </div>
                </div>
                    
                <div class="col-md-12">
                    <p class="lead">Buy Fund</p>
                </div>
                <jsp:include page="error-list.jsp" />
                    
                <form method="post" action="cus_buyFund.do">
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
                         <span class="input-group-addon">Amount</span>
                         <input type="text" class="form-control" placeholder="Amount (No more than your balance)" name="amount">
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
                        <button type="submit" class="btn btn-default btn-lg">Buy Now</button>
                    </div>
                	</div>
                </form>
                    
                    
               <div class="col-md-12">
                   <p class="lead"></p>
               </div>
               <div class="col-md-12">
                   <p class="lead"></p>
               </div>
               
               <div class="col-md-12">
                   <p class="lead"></p>
               </div>
               <div class="col-md-12">
                   <p class="lead"></p>
               </div>
               <div class="col-md-12">
                   <p class="lead">All Funds List</p>
               </div>
               <% 	
               ArrayList<Fund> allFunds = (ArrayList<Fund>)request.getSession().getAttribute("allFunds");
               ArrayList<Long> allFundPrices = (ArrayList<Long>)request.getSession().getAttribute("allFundPrices");
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
                               <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>

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
                               <td><div align='right'><%=dfAmount.format(allFundPrices.get(i)/100.0) %></div></td>
                               <%} %>
                               <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                           </tr>
                        <%
                        	}
                       }else{
                       %>

               <div class="col-md-12">
              		<p>No funds, Ask for help!</p>
               </div>                              
                            <%}
                    		%>     
                   		</tbody>
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
