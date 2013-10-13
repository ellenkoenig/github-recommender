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
    
    <h1>Find interesting GitHub repos</h1>

    <form:form method="post" action="processUserPreferences.html">
      <div>Please enter your interests...</div>
      
      <div class="input-append">
        <form:input id="content" placeholder="Interest 1, interest 2, ..." path="interests" type="text"/>
      </div>
      
       <div>and/or load your interests from XING</div>
   
      <div class="input-append">
        <a class="btn btn-default login" href="${authUrl}" target="_blank"> Xing Login </a>
        <form:input id="content" placeholder="XING Login PIN (after XING login)" path="verifierCode" type="text"/>
        </div>
        <div>
        <input class="btn" type="submit" value="Get my recommendations"/>
      </div>
    </form:form>
  </div>
</body>
</html>


