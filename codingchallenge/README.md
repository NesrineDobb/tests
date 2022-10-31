UP42 Backend Coding Challenge

Requirements:
For building and running the application you need:
JDK 15

1. Getting started

1.1 Retrieve Sources:
$ git clone https://github.com/NesrineDobb/tests/codingchallenge.git

-Make sure there is no application running on 8080
-Build the application using mvn clean install

1.2 APIS
Method	   Path	                              Description
GET	   /features	                      retrieve all the features
GET	   /features/{id}	              retrieve one feature by its ID
GET        /features/{id}/quicklook	      retrieve an image by its feature id

