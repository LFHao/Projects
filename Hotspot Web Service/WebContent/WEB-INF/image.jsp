<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>zSpot - Collage of ${location}</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.css" rel="stylesheet">

<!-- Custom Google Web Font -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link
	href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic'
	rel='stylesheet' type='text/css'>

<!-- Add custom CSS here -->
<style>
body {
	background-color: #424242;
	color: #CEECF5;
	font-family: "Lato", "Helvetica Neue", Helvetica, Arial, sans-serif;
}
</style>

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
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>
	
	<br>
	<br>
	
	<div class="row" align="center" style="background-color: #BDBDBD; padding-top:20px; padding-bottom:10px">
		<h3 align="center" style="color:#2E2E2E">Collage of Popular Pictures and Words about ${location}</h3>
		<div style="width: 100%" align="center">
			<img src="image.do?get=true">
		</div>
	</div>

	<div class="row" align="center" style="background-color: #424242; padding-top:30px; padding-bottom:30px">
		<form class="form-signin" method="post" action="auth.do">
			<div align="center">
				<input class="btn btn-primary btn-lg" type="submit" name="share"
					value="Share the Collage to My Twitter">
			</div>
		</form>
	</div>
	
	<footer>
		<div class="container" align='center'>
			<div class="row">
				<div class="col-lg-12">
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