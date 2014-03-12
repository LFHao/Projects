<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.*"%>
<%@page import="databeans.Mapping"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>zSpot - Search Result of ${location}</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.css" rel="stylesheet">

<!-- Custom Google Web Font -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link
	href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic'
	rel='stylesheet' type='text/css'>

<style>
body {
	background-color: #424242;
	color: #CEECF5;
	font-family: "Lato", "Helvetica Neue", Helvetica, Arial, sans-serif;
}

.box {
	width: 180px;
	margin-bottom: 0px;
	margin-left: -90px;
	padding: 0px 0px;
	align: center;
	display: block;
	position: fixed;
	top: 90%;
	left: 50%;
	z-index: 999;
}
</style>

<!--Load the AJAX API-->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	// Load the Visualization API and the piechart package.
	google.load('visualization', '1.0', {
		'packages' : [ 'corechart' ]
	});

	// Set a callback to run when the Google Visualization API is loaded.
	google.setOnLoadCallback(drawChart);

	// Callback that creates and populates a data table,
	// instantiates the pie chart, passes in the data and
	// draws it.
	function drawChart() {

		// Create the data table.
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Words');
		data.addColumn('number', 'Frequency');
		data
				.addRows([
<%ArrayList<Mapping> pops = (ArrayList<Mapping>) session.getAttribute("popular");
if (pops != null) {
    for (Mapping p : pops) {
        out.println("[\'"+ p.getKey() +"\' ,"+ p.getValue() + "], ");   
    }
}%>
	]);

		// Set chart options
		var options = {
			'width' : 600,
			'height' : 500,
			'backgroundColor' : '#BDBDBD',
			'legend' : {
				position : 'none'
			},
			vAxis : {
				title : 'Hot Words',
				baseline : {
					color : 'black'
				},
				gridlines : {
					color : 'black'
				},
				titleTextStyle : {
					color : 'black',
					fontName : 'Time New Roman',
					italic : 'false',
					bold : 'true'
				},
				textStyle : {
					color : 'black',
					fontName : 'Time New Roman',
					italic : 'false',
					bold : 'true'
				}
			},
			hAxis : {
				title : 'Word Frequrency from 500 Tweets in Last 5 Days',
				titleTextStyle : {
					color : 'black',
					fontName : 'Time New Roman',
					italic : 'false',
					bold : 'true'
				},
				textStyle : {
					color : 'black',
					fontName : 'Time New Roman',
					italic : 'false',
					bold : 'true'
				}
			},
			'backgroundColor.stroke' : '#E0F8F7',
			'backgroundColor.strokeWidth' : 2,
			'backgroundColor.fill' : 'white',

		};

		// Instantiate and draw our chart, passing in some options.
		var chart = new google.visualization.BarChart(document
				.getElementById('chart_div'));
		chart.draw(data, options);
	}
</script>

<script type="text/javascript"
	src="http://word-cumulus-goog-vis.googlecode.com/svn/trunk/wordcumulus.js"></script>
<script type="text/javascript"
	src="http://word-cumulus-goog-vis.googlecode.com/svn/trunk/swfobject.js"></script>
<script type="text/javascript">
	google.load("visualization", "1");

	// Set callback to run when API is loaded
	google.setOnLoadCallback(drawVisualization);

	// Called when the Visualization API is loaded.
	function drawVisualization() {

		// Create and populate a data table.
		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Words');
		data.addColumn('string', 'URL');
		data.addColumn('number', 'Font size');
<%if (pops != null) {
        	out.println("data.addRows(" + pops.size() + ");");
            for (int i = 0; i < pops.size(); i++) {
            	Mapping p = pops.get(i);
                out.println("data.setCell(" + i + ", 0, \'" + p.getKey() + "\');");
                out.println("data.setCell(" + i + ", 1, \'http://en.wikipedia.org/wiki/" + p.getKey().replace(" ", "_") + "\');");
                out.println("data.setCell(" + i + ", 2, 5+" + p.getValue()/4 + ");");
            }
        }%>
	var options = {
			'text_color' : '#31B404',
			'width' : 500,
			'height' : 500,
			'hover_text_color' : '#E0F8F7',
			'speed' : 100,

		};
		// Instantiate our table object.
		var vis = new gviz_word_cumulus.WordCumulus(document
				.getElementById('mydiv'));

		// Draw our table with the data we created locally.
		vis.draw(data, options);
	}
</script>
</head>

<body>
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="homepage.do">zSpot</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div
				class="collapse navbar-collapse navbar-right navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li><a href="#about">About</a></li>
					<li><a href="#services">Services</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<br>
	<br>
	<div class="row" style="background-color: #BDBDBD">
		<h3 align="center" style="color: #2E2E2E">10 Most Popular Words
			about ${location}</h3>
		<div class="col-md-6" align="center">
			<div id="chart_div"></div>
		</div>
		<div class="col-md-6" align="center">
			<div id="mydiv"></div>
		</div>
	</div>

	<div class="row" style="background-color: #424242">
		<div class="col-md-6" align="center">
			<h3 align="center">Flickr Pictures</h3>
			<table style="width: 100%">
				<c:forEach var="pic" items="${photos}" varStatus="status">
					<c:if test="${status.index % 2 == 0}">
						<tr>
					</c:if>
					
					<td align="center"><a href="${pic}" target="_blank"><img src="${pic}" height="225px"
						width="300px"></a></td>
					<c:if test="${status.index % 2 == 1}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
		
		<script src="http://platform.twitter.com/widgets.js" charset="utf-8"></script>
		<div class="col-md-6" align="center">
			<h3 align="center">Tweets</h3>
			<table style="width: 90%" align="center">
				<c:forEach var="tweet" items="${tweets}">
					<tr>
						<td align="center">
							<blockquote class="twitter-tweet" data-link-color="#F6E3CE"
								data-theme="dark" lang="en">
								<a href="${tweet.url}"></a>
							</blockquote>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

	<div align="center">
		<div class="box">
			<form class="form-signin" method="post" action="image.do">
				<button type="submit" class="btn btn-default btn-lg" name="collage"
					value="true" style="width: 160px">Get a Collage!</button>
			</form>
		</div>
	</div>

	<footer>
		<div class="container" align='center'>
			<div class="row">
				<div class="col-lg-12" style="padding:20px">
					<p class="copyright text-muted small">Copyright &copy; Team
						Zyxt 2014. All Rights Reserved</p>
				</div>
			</div>
		</div>
	</footer>

	<!-- JavaScript -->
	<script src="js/jquery-1.10.2.js"></script>
	<script src="js/bootstrap.js"></script>

</body>
</html>