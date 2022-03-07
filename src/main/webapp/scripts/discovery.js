let discovery = {

    loadDiscoveryPage: function (){
        $("#main").html('<div class="pagetitle row mb-5 mt-3"> <h1 class="col-lg-10 col-sm-8">Discovery Table</h1> <div class="col-lg-2 col-sm-4"> <button type="button" class="btn btn-secondary" id="addDevice" data-bs-toggle="modal" data-bs-target="#basicModal">Add Device</button> </div> </div><!-- End Page Title --> <section class="section"> <div class="row"> <div class="col-lg-12"> <div class="card"> <div class="card-body"> <!-- Table with stripped rows --> <table class="display cell-border" id="discoveryTable"> <thead> <tr> <th scope="col">id</th> <th scope="col">Name</th> <th scope="col">Position</th> <th scope="col">Age</th> <th scope="col">Start Date</th> </tr> </thead> <tbody> <tr> <th scope="row">1</th> <td>Brandon Jacob</td> <td>Designer</td> <td>28</td> <td>2016-05-25</td> </tr> <tr> <th scope="row">2</th> <td>Bridie Kessler</td> <td>Developer</td> <td>35</td> <td>2014-12-05</td> </tr> <tr> <th scope="row">3</th> <td>Ashleigh Langosh</td> <td>Finance</td> <td>45</td> <td>2011-08-12</td> </tr> <tr> <th scope="row">4</th> <td>Angus Grady</td> <td>HR</td> <td>34</td> <td>2012-06-11</td> </tr> <tr> <th scope="row">5</th> <td>Raheem Lehner</td> <td>Dynamic Division Officer</td> <td>47</td> <td>2011-04-19</td> </tr> </tbody> </table> <!-- End Table with stripped rows --> </div> </div> </div> </div> </section> <div class="modal fade" id="basicModal" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title">Add Device</h5> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body"> <form class="row g-3 needs-validation" novalidate> <div class="col-md-12"> <label for="name" class="form-label">Name</label> <div class="input-group has-validation"> <input type="text" class="form-control" id="name" required="required"><div class="invalid-feedback"> Please choose a name. </div> </div> </div> <div class="col-md-6"> <label for="ip" class="form-label">IP/Host</label> <input type="text" class="form-control" id="ip" required><div class="invalid-feedback"> Please choose a name. </div> </div> <div class="col-md-6"> <label for="type" class="form-label">Type</label> <select id="type" class="form-select" required> <option selected>Ping</option> <option>SSH</option> </select><div class="invalid-feedback"> Please choose a name. </div> </div> <div class="col-md-6" id="usernameDiv" style="display: none"> <label for="username" class="form-label">Username</label> <input type="text" class="form-control" id="username" required><div class="invalid-feedback"> Please choose a name. </div> </div> <div class="col-md-6" id="passwordDiv" style="display: none"> <label for="password" class="form-label">Password</label> <input id="password" type="password" class="form-control" required><div class="invalid-feedback"> Please choose a name. </div> </div> <div class="text-center"> <button type="submit" class="btn btn-primary">Submit</button> <button type="reset" class="btn btn-secondary">Reset</button> </div> </form> </div> <div class="modal-footer"> <button type="button" class="btn btn-outline-dark w-100" data-bs-dismiss="modal">Close</button> </div> </div> </div> </div>');
    },

    addDevice: function (){
        $("select#type").change(function () {
            console.log("hello");
            let selectedType = $(this).find(":selected").text();
            console.log($(this).find(":selected").text()==="SSH");
            if(selectedType==="SSH"){
                $("#usernameDiv").css("display","block");
                $("#passwordDiv").css("display","block");
            }
            else{
                $("#usernameDiv").css("display","none");
                $("#passwordDiv").css("display","none");
            }
        });
    },

}