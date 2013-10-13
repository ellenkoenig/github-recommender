<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>

<html>
<head>
  <title>GitHub Recommendations</title>
  <link rel="stylesheet" href="http://railsgirls.com/assets/bootstrap.css">
  <link href='css/main.css' rel='stylesheet' type='text/css' />
</head>

<body>

  <div class="container">
    
    <img class="logo" height='102' width='102' src="http://octodex.github.com/images/original.png">  
    <h1>Find interesting GitHub repos</h1>

    <form:form method="post" action="processUserPreferences.html">
      
      <div class="form">
        <p>Please enter your interests...</p>
        <form:input id="content" placeholder="Interest 1, Interest 2, ..." path="interests" type="text"/>
      </div>

      <div class="form">
        <a class="btn btn-default login" href="${authUrl}" target="_blank"> Xing Login </a> <br>  
        <p>and/or load interests from XING</p>
        <form:input id="content" placeholder="XING PIN (after XING login)" path="verifierCode" type="text"/>
      </div>
    
      <div class="form">
        <input class="btn btn-success" type="submit" value="Get my recommendations"/>
      </div>
    
    </form:form>
  
  </div>

</body>
</html>


