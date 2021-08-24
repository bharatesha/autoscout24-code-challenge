# Autoscout24 coding challenge

Project consists of backend and frontend folders, which has spring and react based applications respectively


##Backend:

Pre-requesite to set up project

1. Java 8
2. Maven
3. Intellij (Any IDE support java)

Run: Import autoscout24 project under backend to Intellij (File -> New -> Project from existing sources, select maven)
     Intellij should build project automatically otherwise build project with the help of pom.xml

Launch: Run Autoscout24Application.java under src folder to launch application

Access API documentation (swagger-ui) to test application : http://localhost:8080/swagger-ui.html

Test case: run test cases under src/test folder for unit testing 

##Frontend:

Pre-requesite

1. Node.js
2. npm

Run : Import project report-viewer under frontend to IDE and run below commands (more information can be found in Readme.md under report-viewer project)
  
      1. npm install
	  2. npm start
	  
Application can be accessible under http://localhost:3000/

After uploading file, please refresh page to load latest data

Upload first listings and then contacts to generate report. contacts id in listings validation not implemented



