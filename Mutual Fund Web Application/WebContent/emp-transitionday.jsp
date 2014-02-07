<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import= "java.text.SimpleDateFormat" %>
<%@page import= "java.text.DecimalFormat" %>
<%@page import="java.util.*" %>
<%@page import="databeans.FundPriceHistory"%>
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
            <!-- Keep all page content within the page-content inset div! -->
            <div class="page-content inset">
                <ol class="breadcrumb">
                    <li><a href="emp-customerlist.do">Home</a></li>
                    <li class="active">Transition Day</li>
                </ol>
                
                <jsp:include page="error-list.jsp"/>
                
                <% 
                Employee employee = (Employee) session.getAttribute("employee");
				if(employee == null) {	
				    out.print("Please login as employee first!");
				    return;
				}
                Date lastday = (Date)request.getAttribute("lastday"); 
                ArrayList<FundPriceHistory> prices = (ArrayList<FundPriceHistory>)request.getAttribute("prices"); 
        		ArrayList<String> names = (ArrayList<String>)request.getAttribute("names");
        		ArrayList<String> symbols = (ArrayList<String>)request.getAttribute("symbols");
            	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                DecimalFormat dfAmount = new DecimalFormat("###,###,###,###,###,###,##0.00"); 
                
                %>
                
                <div class="row">	
					<div class="col-md-12">
						<form method="post" action="emp-transitionday.do">
							<div class="col-md-12">
								<div class="col-md-6" align="center">
									Last Transition Day<br/>
									<%if(lastday == null){ %>
									N/A
									<%}else{ %>
									<%=sdf.format(lastday) %>
									<%} %>
								</div>
								<div class="col-md-6" align="center">
									New Transition Day<br/> 
    								<input type="text" style="width:20%;" name="imonth" placeholder="MM"> / 
    								<input type="text" style="width:20%;" name="iday" placeholder="DD"> /
    								<input type="text" style="width:20%;" name="iyear" placeholder="YYYY">
								</div>
							</div>
                        	
                        	<!-- List of Funds -->
                        	<p class="lead">All Funds List</p>
	                        <table class="table">
	                            <thead>
	                                <tr>
	                                    <th>Fund ID</th>
	                                    <th>Fund Name</th>
	                                    <th>Fund Symbol</th>
	                                    <th><div align='right'>Current Price</div></th>
	                                    <th><div align='center'>New Price</div></th>
	                                </tr>
	                            </thead>
	                            <tbody>
			                   <%
			                   if(names != null && names.size()!=0){
		                       		for(int i=0; i<prices.size();i++){ 
		                       %>
		                           	<tr>
		                               <td><%=prices.get(i).getFund_id()%></td>
		                               <td><%=names.get(i)%></td>
		                               <td><%=symbols.get(i)%></td>
		                               <%if(prices.get(i).getPrice()<0) {%>
		                               <td><div align='right'><%="Not Initialized" %></div></td>
		                               <%}else{ %>
		                               <td><div align='right'><%=dfAmount.format(prices.get(i).getPrice()/100.0) %></div></td>
		                               <%} %>
		                               <td><div align='center'><input type="text" name="price_<%=prices.get(i).getFund_id()%>"/></div></td>
		                           	</tr>
		                        <%
		                        	}
		                       } else {
		                       %>
		                      	<tr>
                        			<td colspan="5" align="center">No funds, create some now!</td>
                    			</tr>
		                       <%}
		                    	%>
		                    	<tr>
		                    		<td colspan="5" align="right">
			                    	<div class="btn-group">
	                            		<button type="submit" name="transbutton" class="btn btn-default btn-lg" value="Submit">Submit</button>
	                        		</div>
	                        		</td>
                        		</tr>
<!-- 		                    <tr>
                        			<td colspan="5" align="center"><input type="submit" name="transbutton" value="Submit"/></td>
                    			</tr> -->
		                      </tbody>
		                      </table>	                            
                        </form>
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
