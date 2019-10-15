##Creating a webtool for BEAST2

* **Author(s)**:            Roxanne Wolthuis, René Bults, Armin Dedic, Lisette van Dam 
* **Student numbers**:      347958, 342589, 342615, 313185
* **Class**:                BFV3
* **Course**:               Thema 10
* **Study**:                Bio-informatics
* **Institute**:            Institute for Lifescience & Technology
* **Lector**:               Marcel Kempenaar
* **Commissioned by**:      Jasper Bosman
* **Date**:                 01 - 03 - 2018


## The project description ##
Bayesian inference phylogeny is a popular method for creating phylogenetic trees. 
There are several tools and programs available that work with this method. 
Programs like these are runned from a command line which makes it hard to understand for the average user. 
One of the programs that uses Bayesian inference phylogeny methods is BEAST2. 
The purpose of this project was to create a webtool for BEAST2 that will make it easy to work and process 
data with. BEAST2 works with three different programs. The idea was to create a web-tool with three different 
steps that are linked to these programs. A Bootstrap grid was used for shaping the front-end of the webtool 
and Java was used in the back-end for creating and working with servlets, subprocessing and error handling.

## Beast installation ##
For the use of our web tool the installation of beast is mandatory 

https://www.beast2.org/

When beast is installed, the absolute path of where beast is installed has to be put into the web.xml file. Make
sure this path is correct and check if you run the web tool on a windows or linux device.

Example beast location on Windows: 
C:\Users\IdeaProjects\webbeast\webbeast\programs\BEAST\lib\beast.jar 

Example beast location on Linux:
/homes/lvandam/IdeaProjects/webbeast/webbeast/programs/beast/lib/beast.jar

When using linux instead of windows change the paths from the files intro the following paths to make sure the paths work

Sessions.java       line 28: File sessionDirectory = new File(inputFilePath + "/" + sessionID);
UploadServlet.java  line 56: nexFilePart.write(inputFilePath + "/" + sessionID + "/" + nexFileName);
RunningPrograms     line 32/33/37: Process process = Runtime.getRuntime().exec(beautiPath + " -nex " + sessionDirectory + "/" +
nexFileName + " -exitaction writexml -out " + sessionDirectory + "/" + xmlName);
RunBeast            line 73: Process p = Runtime.getRuntime().exec("java -jar " + beastPath + " " + sessionDirectory + "/" +
xmlName););

## Results information ##
The result forms a webTool where the user can choose a .NEXUS file to produce .XML and .Trees files.
The front-end of the website is created with a Bootstrap grid and can be adjusted to ones likings. 
The back-end consists of a Java Servlet and different Java Classes that help with receiving the files, 
modifying the files and returning the modified files on the website for the user to download it as .ZIP

## User usage ##
 * If the user has a .nex file, then follow the instructions from the first step. Start by uloading 
the .nex file and press the submit button. This produces the corresponding .xml file which will be 
stored in its sessiondirectory.
  
## Webbrowser support ##
Internet explore is not supported by our web beast tool. We recommend the use of other browsers like chrome or firefox.
FireFor browsers version 55 and the Chrome browser from version 60 to now is supported. 

