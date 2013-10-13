<?xml version="1.0" encoding="UTF-8" ?>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href='css/main.css' rel='stylesheet' type='text/css' />
<title>Recommendations</title>
</head>

<body>
	<div class="container">
	
 	<h2>Recommendations</h2>
	    <ul>
		  <c:forEach var="reco" items="${recommendations}">
		  <li>
		  	<a href="http://github.com/${reco.name}"> <c:out value="${reco.name}"/></a>
		    	: <c:out value="${reco.description}"/>
		  </li>
		  </c:forEach>
	    </ul>

	    <script type="text/javascript">
	    <!--
	     var imlocation = "http://octodex.github.com/images/";
	     var currentdate = 0;
	     var image_number = 0;
	     function ImageArray (n) {
	       this.length = n;
	       for (var i =1; i <= n; i++) {
	         this[i] = ' '
	       }
	     }
	     image = new ImageArray(19)
	     image[0] = "labtocat.png"
	     image[1] = "dunetocat.png"
	     image[2] = 'octoliberty.png'
	     image[3] = "femalecodertocat.png"
	     image[4] = "daftpunktocat-thomas.gif"
	     image[5] = "motherhubbertocat.png"
	     image[6] = "deckfailcat.png"
	     image[7] = "pusheencat.png"
	     image[8] = "droidtocat.png"
	     image[9] = "twenty-percent-cooler-octocat.png"
	     image[10] = "codercat.jpg"
	     image[11] = "hipster-partycat.jpg"
	     image[12] = "oktobercat.png"
	     image[13] = "octdrey-catburn.jpg"
	     image[14] = "andycat.jpg"
	     image[15] = "collabocats.jpg"
	     image[16] = "pythocat.png"
	     image[17] = "benevocats.png"
	     image[18] = "notocat.jpg"
	     var rand = 60/image.length
	     function randomimage() {
	      currentdate = new Date()
	      image_number = currentdate.getSeconds()
	      image_number = Math.floor(image_number/rand)
	      return(image[image_number])
	     }
	     document.write("<img height='222' width='222' src='" + imlocation + randomimage()+ "'>");
	    //-->
	    </script>
  
	    <iframe class="span6 iframe" src="https://api.github.com/zen"> </iframe>

	  </div>   
	</body>
</html>