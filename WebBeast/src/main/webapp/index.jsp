<%--
  Created by IntelliJ IDEA.
  User: Armin Dedic & Lisette van Dam, Rene Bults & Roxanne Wolthuis
  Date: 02-02-2018
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<html>
  <head>
    <title>Webtool</title>
      <link rel="stylesheet" type="text/css" href="css/style.css">
      <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
      <link rel="stylesheet" type="text/css" href="css/style.css">
      <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
      <link rel="stylesheet" type="text/css" href="css/bootstrap-grid.css">
      <link rel="stylesheet" type="text/css" href="css/bootstrap-reboot.css">
      <link rel="stylesheet" type="text/css" href="css/style.css">

  </head>

  <body>
  <div class="container">
          <div class="row text-center">
              <div class="col-md-12">
                  <h1>What is the file you are working with?</h1>
                  <p> Please select one of the options below</p>
              </div>
          </div>

          <div class="row">
              <div class="col-md-12">

                  <p>This part of the webtool will convert a .nex file into a .xml file. The xml file can be used in step 2 or can be downloaded for later use</p>
                  <form action = "${pageContext.request.contextPath}/test" method = "POST" enctype = "multipart/form-data">
                      <div class="form-group">
                          <label for="exampleFormControlFile1">Upload a valid .nex file</label>
                          <input type="file" name="nexFile" class="form-control-file" id="exampleFormControlFile1" accept=".nex">
                      </div>
                      <button id="submit-nexus-file-button" type="submit" class="btn btn-primary">Submit</button>
                  </form>
                  <div class="loading">
                      <p id="process-running-label">The process is currently running...</p>
                      <p id="process-finished-label">The process is finished,<br>
                          click <a href="${pageContext.request.contextPath}/download">here</a> to download your zip.</p>
                      <div class="loader"></div>
                  </div>
              </div>
          </div>
      </div>

  </body>


  <footer>

    <script>
        $(function () {
            var all_done = false;
            var get_data = function() {
                $.when(
                    // Request data for any running benchmark
                    $.getJSON("results", function (data) {
                        processing = data;
                    })
                ).then(function () {
                    // Fill the tables with received data
                    console.log(processing);
                    // Add event to 'Step 1' button:
                    //      show 'spinner'
                    // While running:
                    //      show 'spinner'
                    // If finished:
                    //      Remove spinner, provide ZIP-file
                    if (processing === "finished") {
                        // if process is finished hide loader
                        $("div.loader").fadeOut(400);
                        // if process is finished running becomes finished
                        $("p#process-running-label").delay(100).fadeOut(400, function () {
                            $("p#process-finished-label").fadeIn(400);
                        });
                        all_done = true;
                    }
                });
            };

            // Retrieve data every 4 seconds and update the results tables
            window.setInterval(function(){
                if (!all_done)
                    get_data();
            }, 500);

            $("#submit-nexus-file-button").on("click",function() {
                // Show spinner
                $("div.loader").fadeIn(400);
                //show comment loader-running
                $("p#process-running-label").fadeIn(400);
            });
        });
    </script>
    <script type="text/javascript" src="js/bootstrap.bundle.js"></script>
    <script type="text/javascript" src="js/bootstrap.js"></script>
      <script type="text/javascript" src="js/progressbar.js"></script>
  </footer>
</html>
