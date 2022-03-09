
let addRow ;
    let discovery = {

    loadDiscoveryPage: function (){
         $("#main").html('<div class="pagetitle row mb-5 mt-3"> <h1 class="col-lg-10 col-sm-8">Discovery Table</h1> <div class="col-lg-2 col-sm-4"> <button type="button" class="btn btn-secondary" id="addDevice" data-bs-toggle="modal" data-bs-target="#basicModal">Add Device</button> </div> </div><!-- End Page Title --> <section class="section"> <div class="row"> <div class="col-lg-12"> <div class="card"> <div class="card-body"> <!-- Table with stripped rows --> <table class="display cell-border" id="discoveryTable"> <thead> <tr> <th scope="col">id</th> <th scope="col">Name</th> <th scope="col">Ip</th> <th scope="col">Type</th> <th scope="col">Action</th> </tr></thead> <tbody id="discoveryTableBody"> </tbody>  </table> <!-- End Table with stripped rows --> </div> </div> </div> </div> </section> <div class="modal fade" id="basicModal" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title">Add Device</h5> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body"> <form class="row g-3 needs-validation" id="discoveryForm"> <div class="col-md-12"> <label for="name" class="form-label">Name</label>  <input type="text" class="form-control" id="name" required="required"/> </div> <div class="col-md-6"> <label for="ip" class="form-label">IP/Host</label> <input type="text" class="form-control" id="ip" required/></div> <div class="col-md-6"> <label for="type" class="form-label">Type</label> <select id="type" class="form-select" required> <option selected>Ping</option> <option>SSH</option> </select> </div> <div class="col-md-6" id="usernameDiv" style="display: none"> <label for="username" class="form-label">Username</label> <input type="text" class="form-control" id="username"/> </div> <div class="col-md-6" id="passwordDiv" style="display: none"> <label for="password" class="form-label">Password</label> <input id="password" type="password" class="form-control"/></div> <div class="text-center"> <button type="submit" class="btn btn-primary">Submit</button> <button type="reset" class="btn btn-secondary">Reset</button> </div> </form></div> <div class="modal-footer"> <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div> </div> <button id="editForm" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editDiscoveryModal" style="display: none;"></button><div class="modal fade" id="editDiscoveryModal" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title">Edit Device</h5> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body"> <form class="row g-3 needs-validation" id="discoveryFormEdit"> <div class="col-md-12" style="display: none;"> <label for="editid" class="form-label">Id</label> <input type="text" class="form-control" id="editid" required="required" value="" disabled/> </div> <div class="col-md-12"> <label for="editname" class="form-label">Name</label> <input type="text" class="form-control" id="editname" required="required"/> </div> <div class="col-md-6"> <label for="editip" class="form-label">IP/Host</label> <input type="text" class="form-control" id="editip" required/></div> <div class="col-md-6"> <label for="edittype" class="form-label">Type</label> <select id="edittype" class="form-select" required disabled> <option selected>Ping</option> <option>SSH</option> </select> </div> <div class="col-md-6" id="editusernameDiv" style="display: none;"> <label for="editusername" class="form-label">Username</label> <input type="text" class="form-control" id="editusername"/> </div> <div class="col-md-6" id="editpasswordDiv" style="display: none;"> <label for="editpassword" class="form-label">Password</label> <input id="editpassword" type="password" class="form-control"/></div> <div class="text-center"> <button type="submit" class="btn btn-primary">Submit</button> <button type="reset" class="btn btn-secondary" style="display: none;">Reset</button> </div> </form> </div> <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div></div><!-- End Basic Modal-->');

        addRow = $("table#discoveryTable").DataTable({

            lengthMenu: [5, 10, 20, 50, 100, 200, 500]

        });
    },

    displaySSHFields: function () {

        $("select#type").change(function () {

            console.log("hello");

            let selectedType = $(this).find(":selected").text();

            console.log($(this).find(":selected").text() === "SSH");

            if (selectedType === "SSH") {

                $("#usernameDiv").css("display", "block");

                $("#username").attr("required","required");

                $("#passwordDiv").css("display", "block");

                $("#password").attr("required","required");

            } else {

                $("#usernameDiv").css("display", "none");

                $("#passwordDiv").css("display", "none");
            }

        });

        $("button[type='reset']").click(function () {

            $("#usernameDiv").css("display", "none");

            $("#passwordDiv").css("display", "none");

        });
    },

    onAddDeviceSuccesfull: function (request){

        $("#addDevice").click();

        // alert(request.data.message);
        $("button[type='reset']").click();

        discovery.getDiscoveryDevices();
    },

    addDevice: function (){

        $("#discoveryForm").submit(function (e)
        {

            var param = {

                name:$("#name").val(),

                ip:$("#ip").val(),

                type:$("#type").find(":selected").text(),

                username:$("#username").val(),

                password:$("#password").val()

            }
            // console.log(param);
            var request = {

                url:"addDeviceForDiscovery",

                param:param,

                callback:discovery.onAddDeviceSuccesfull

            };
            // console.log(request);
            ajaxCalls.ajaxPostCall(request);
            // return false;

            e.preventDefault();
        });
    },

    getDiscoveryDevices: function (){

        var request = {

          url: "getDiscoveryDevices",

          callback: discovery.onGetDiscoveryDeviceSuccess

        };

        ajaxCalls.ajaxGetCall(request);
    },

    onGetDiscoveryDeviceSuccess: function (request){

        console.log("devices got "+JSON.stringify(request.data.result.result));
        addRow.clear().draw();
        $.each(request.data.result.result,function (i,v){

            addRow.row.add([v.id,v.name,v.ip,v.type,'<button class="btn btn-outline-primary btn-sm" data-id="'+v.id+'"><i class="bi bi-play-btn"></i></button> <button class="btn btn-outline-primary btn-sm editDiscoveryRow" data-id="'+v.id+'"><i class="bi bi-pencil-square"></i></button> <button class="btn btn-outline-primary btn-sm deleteDiscoveryRow" data-id="'+v.id+'"><i class="bi bi-trash2"></i></button>']).draw();

        });

    },

    deleteDiscoveryRow: function()
    {

        $("#discoveryTable").on("click",".deleteDiscoveryRow",function (e){
            var param =
                {
                    id:$(e.currentTarget).data("id"),
                }
            // console.log(param);
            var request =
                {
                    url:"deleteDiscoveryRow",
                    param: param,
                    callback:discovery.onDeleteSuccessfull
                }
            ajaxCalls.ajaxPostCall(request);
        });
    },


    onDeleteSuccessfull: function (request)
    {
        discovery.getDiscoveryDevices();
    },

    getDiscoveryRow: function (){
      $("#discoveryTable").on("click",".editDiscoveryRow", function (e){
         /*var rowData = $(e.currentTarget).parentsUntil("#discoveryTableBody").children();
         var editData = [];
         $.each(rowData,function (i,v){
             if($(v).is("td") && $(v).text()!="  ")
             {
                editData.push($(v).text());
             }
         });
         */
          var param =
              {
                  id:$(e.currentTarget).data("id"),
              }
          var request =
              {
                  url:"getDiscoveryRow",
                  param: param,
                  callback:discovery.editDiscoveryRow
              }
              ajaxCalls.ajaxPostCall(request);
      });
    },

    editDiscoveryRow: function (request){
        $("button[type='reset']").click();

        $("#editusernameDiv").css("display", "none");

        $("#editusername").removeAttr("required");

        $("#editpasswordDiv").css("display", "none");

        $("#editpassword").removeAttr("required");

        console.log(request.data.result.result);
        $("#editid").val(request.data.result.result.id);
        $("#editname").val(request.data.result.result.name);
        $("#editip").val(request.data.result.result.ip);
        $("#edittype").val(request.data.result.result.type);
        if(request.data.result.result.type=="SSH")
        {
            $("#editusername").val(request.data.result.result.username);

            $("#editusernameDiv").css("display", "block");

            $("#editusername").attr("required","required");

            $("#editpasswordDiv").css("display", "block");

            $("#editpassword").attr("required","required");
        }
        $("#editForm").click();

        $("#discoveryFormEdit").submit(function (e){
            var param = {

                id:$("#editid").val(),

                name:$("#editname").val(),

                ip:$("#editip").val(),

                type:$("#edittype").find(":selected").text(),

                username:$("#editusername").val(),

                password:$("#editpassword").val()

            }
            // console.log(param);
            var requestCall = {

                url:"updateDiscoveryRow",

                param:param,

                callback:discovery.onUpdateDiscoveryRowSuccess
            };
            ajaxCalls.ajaxPostCall(requestCall);
            e.preventDefault();
        });
    },

    onUpdateDiscoveryRowSuccess:function (request){
        $("#editForm").click();
        discovery.getDiscoveryDevices();
    }

}


