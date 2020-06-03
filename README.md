# Baackend
This is the Spring boot application.

This application has a endpoint ("/index") which accepts String in the query paramter..

Method Signature is added here
 @GetMapping(value="/index")
    public JSONObject[] index(@RequestParam("query") String query)
    
    
   And this controller calls a method from the ServiceImpl class. ServiceImpl class implements Service class.
   
   
   The data is present in the products.txt file which is in the src/main/resources.
