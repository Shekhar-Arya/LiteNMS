let moniterInterval;

let addRow ;

    let discovery = {

    loadDiscoveryPage: function ()
    {
        let discoverySelector = $("#discovery");

        discoverySelector.click(function ()
        {

            discoverySelector.children("a").removeClass("collapsed");

            $("#monitor").children("a").addClass("collapsed");

            $("#main").html('<div class="pagetitle row mb-5 mt-3"> <h1 class="col-lg-10 col-sm-8">Discovery Table</h1> <div class="col-lg-2 col-sm-4"> <button type="button" class="btn btn-secondary" id="addDevice" data-bs-toggle="modal" data-bs-target="#basicModal">Add Device</button> </div> </div><!-- End Page Title --> <section class="section"> <div class="row"> <div class="col-lg-12"> <div class="card"> <div class="card-body"> <!-- Table with stripped rows --> <table class="display cell-border" id="discoveryTable"> <thead> <tr> <th scope="col">Id</th> <th scope="col">Name</th> <th scope="col">Ip</th> <th scope="col">Type</th> <th scope="col">Action</th> </tr></thead> <tbody id="discoveryTableBody"> </tbody>  </table> <!-- End Table with stripped rows --> </div> </div> </div> </div> </section> <div class="modal fade" id="basicModal" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title">Add Device</h5> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body"> <form class="row g-3 needs-validation" id="discoveryForm"> <div class="col-md-12"> <label for="name" class="form-label">Name</label>  <input type="text" class="form-control" id="name" required="required"/> </div> <div class="col-md-6"> <label for="ip" class="form-label">IP/Host</label> <input type="text" class="form-control" id="ip" required/></div> <div class="col-md-6"> <label for="type" class="form-label">Type</label> <select id="type" class="form-select" required> <option selected>Ping</option> <option>SSH</option> </select> </div> <div class="col-md-6" id="usernameDiv" style="display: none"> <label for="username" class="form-label">Username</label> <input type="text" class="form-control" id="username"/> </div> <div class="col-md-6" id="passwordDiv" style="display: none"> <label for="password" class="form-label">Password</label> <input id="password" type="password" class="form-control"/></div> <div class="text-center"> <button type="submit" class="btn btn-primary">Submit</button> <button type="reset" class="btn btn-secondary">Reset</button> </div> </form></div> <div class="modal-footer"> <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div> </div> <button id="editForm" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editDiscoveryModal" style="display: none;"></button><div class="modal fade" id="editDiscoveryModal" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title">Edit Device</h5> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body"> <form class="row g-3 needs-validation" id="discoveryFormEdit"> <div class="col-md-12" style="display: none;"> <label for="editid" class="form-label">Id</label> <input type="text" class="form-control" id="editid" required="required" value="" disabled/> </div> <div class="col-md-12"> <label for="editname" class="form-label">Name</label> <input type="text" class="form-control" id="editname" required="required"/> </div> <div class="col-md-6"> <label for="editip" class="form-label">IP/Host</label> <input type="text" class="form-control" id="editip" required/></div> <div class="col-md-6"> <label for="edittype" class="form-label">Type</label> <select id="edittype" class="form-select" required disabled> <option selected>Ping</option> <option>SSH</option> </select> </div> <div class="col-md-6" id="editusernameDiv" style="display: none;"> <label for="editusername" class="form-label">Username</label> <input type="text" class="form-control" id="editusername"/> </div> <div class="col-md-6" id="editpasswordDiv" style="display: none;"> <label for="editpassword" class="form-label">Password</label> <input id="editpassword" type="password" class="form-control"/></div> <div class="text-center"> <button type="submit" class="btn btn-primary">Submit</button> <button type="reset" class="btn btn-secondary" style="display: none;">Reset</button> </div> </form> </div> <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div></div><!-- End Basic Modal--> <button id="displayMessageButton" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#displayMessageModal" style="display: none;"></button><div class="modal fade" id="displayMessageModal" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title">Action Message</h5> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body displayMessageBody"> </div> <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div></div><!-- End Basic Modal--> <div id="loading" class="d-flex justify-content-center align-items-center"> <div class="spinner-border text-primary" style="width: 7rem; height: 7rem; border-width: 5px; display: none;" role="status"> <span class="visually-hidden">Loading...</span> </div></div>');

            addRow = $("table#discoveryTable").DataTable({

                lengthMenu: [5, 10, 20, 50, 100, 200, 500]

            });

            clearInterval(moniterInterval);

            sessionStorage.setItem("page","#discovery");

            discovery.addDevice();

            discovery.displaySSHFields();

            discovery.getDiscoveryDevices();

            discovery.deleteDiscoveryRow();

            discovery.getDiscoveryRow();

            discovery.runDiscoveryDevice();

            discovery.addDeviceToMonitor();
        });
    },

    displaySSHFields: function ()
    {
        let usernameDivSelector = $("#usernameDiv");

        let usernameSelector = $("#username");

        let passwordDivSelector = $("#passwordDiv");

        let passwordSelector = $("#password");

        $("select#type").change(function ()
        {

            let selectedType = $(this).find(":selected").text();

            if (selectedType === "SSH")
            {

                usernameDivSelector.css("display", "block");

                usernameSelector.attr("required","required");

                passwordDivSelector.css("display", "block");

                passwordSelector.attr("required","required");

            }
            else
            {

                usernameDivSelector.css("display", "none");

                passwordDivSelector.css("display", "none");

                usernameSelector.removeAttr("required");

                passwordSelector.removeAttr("required");
            }

        });

        $("button[type='reset']").click(function ()
        {

            usernameDivSelector.css("display", "none");

            passwordDivSelector.css("display", "none");

            usernameSelector.removeAttr("required");

            passwordSelector.removeAttr("required");

        });
    },

    onAddDeviceSuccesfull: function (request)
    {
        $("#addDevice").click();

        $("button[type='reset']").click();

        $(".displayMessageBody").text(request.data.message);

        $("#displayMessageButton").click();

        discovery.getDiscoveryDevices();
    },

    addDevice: function (){

        $("#discoveryForm").submit(function (e)
        {
            let param = {

                name:$("#name").val(),

                ip:$("#ip").val(),

                type:$("#type").find(":selected").text(),

                username:$("#username").val(),

                password:$("#password").val()

            }

            let request = {

                url:"addDeviceForDiscovery",

                param:param,

                callback:discovery.onAddDeviceSuccesfull

            };
            ajaxCalls.ajaxPostCall(request);

            e.preventDefault();
        });
    },

    getDiscoveryDevices: function ()
    {

        let request = {

          url: "getDiscoveryDevices",

          callback: discovery.onGetDiscoveryDeviceSuccess

        };

        ajaxCalls.ajaxGetCall(request);
    },

    onGetDiscoveryDeviceSuccess: function (request)
    {

        addRow.clear().draw();

        $.each(request.data.result.result,function (i,v)
        {
            addRow.row.add([v.id,v.name,v.ip,v.type,'<button class="btn btn-outline-primary btn-sm pingDiscoveryDevice" data-id="'+v.id+'"><i class="bi bi-play-btn"></i></button> <button class="btn btn-outline-primary btn-sm editDiscoveryRow" data-id="'+v.id+'"><i class="bi bi-pencil-square"></i></button> <button class="btn btn-outline-primary btn-sm deleteDiscoveryRow" data-id="'+v.id+'"><i class="bi bi-trash2"></i></button> <button class="btn btn-outline-primary btn-sm addDeviceToMonitor addDeviceToMonitor'+v.id+'" data-id="'+v.id+'" style="display: none;"><i class="bi bi-eye-fill"></i></button>']).draw();

            if (v.status===1)
            {
                $(".addDeviceToMonitor"+v.id).css("display","");
            }
        });
    },

    deleteDiscoveryRow: function()
    {

        $("#discoveryTable").on("click",".deleteDiscoveryRow",function (e)
        {
            let param =
                {
                    id:$(e.currentTarget).data("id"),
                }
            let request =
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
        $(".displayMessageBody").text(request.data.message);

        $("#displayMessageButton").click();

        discovery.getDiscoveryDevices();
    },

    getDiscoveryRow: function ()
    {
      $("#discoveryTable").on("click",".editDiscoveryRow", function (e)
      {
          let param =
              {
                  id:$(e.currentTarget).data("id"),
              }
          let request =
              {
                  url:"getDiscoveryRow",

                  param: param,

                  callback:discovery.editDiscoveryRow
              }

              ajaxCalls.ajaxPostCall(request);
      });
    },

    editDiscoveryRow: function (request)
    {
        let editUsernameDivSelector = $('#editusernameDiv');

        let editUsernameSelector = $('#editusername');

        let editPasswordDivSelector = $('#editpasswordDiv');

        let editPasswordSelector = $('#editpassword');

        let result = request.data.result.result;

        $("button[type='reset']").click();

        editUsernameDivSelector.css("display", "none");

        editUsernameSelector.removeAttr("required");

        editPasswordDivSelector.css("display", "none");

        editPasswordSelector.removeAttr("required");

        $("#editid").val(result.id);

        $("#editname").val(result.name);

        $("#editip").val(result.ip);

        $("#edittype").val(result.type);

        if(result.type==="SSH")
        {
            editUsernameSelector.val(result.username);

            editUsernameDivSelector.css("display", "block");

            editUsernameSelector.attr("required","required");

            editPasswordDivSelector.css("display", "block");

            editPasswordSelector.attr("required","required");
        }
        $("#editForm").click();

        $("#discoveryFormEdit").submit(function (e){

            let param = {

                id:$("#editid").val(),

                name:$("#editname").val(),

                ip:$("#editip").val(),

                type:$("#edittype").find(":selected").text(),

                username:$("#editusername").val(),

                password:$("#editpassword").val()

            }

            let requestCall = {

                url:"updateDiscoveryRow",

                param:param,

                callback:discovery.onUpdateDiscoveryRowSuccess
            };

            ajaxCalls.ajaxPostCall(requestCall);

            e.preventDefault();
        });
    },

    onUpdateDiscoveryRowSuccess:function (request)
    {
        $("#editForm").click();

        $(".displayMessageBody").text(request.data.message);

        $("#displayMessageButton").click();

        discovery.getDiscoveryDevices();
    },

    runDiscoveryDevice: function ()
    {
        $("#discoveryTable").on("click",".pingDiscoveryDevice", function (e)
        {
            let param =
            {
                id:$(e.currentTarget).data("id")
            }
            let request =
            {
                url:"runDiscoveryDevice",

                param:param,

                callback:discovery.onRunDiscoverySuccessfull
          }

            $("#loading").addClass("loading");

            $("#sidebar").addClass("sidebarLoading");

            $("#header").addClass("headerLoading");

            $(".spinner-border").css("display","block");

            ajaxCalls.ajaxPostCall(request);
      });
    },

    onRunDiscoverySuccessfull: function (request)
    {
        $("#loading").removeClass("loading");

        $(".spinner-border").css("display","none");

        $("#sidebar").removeClass("sidebarLoading");

        $("#header").removeClass("headerLoading");

        $(".displayMessageBody").text(request.data.message);

        $("#displayMessageButton").click();

        discovery.getDiscoveryDevices();
    },

    addDeviceToMonitor: function ()
    {
        $("#discoveryTable").on("click",".addDeviceToMonitor", function (e)
        {
            let param =
                {
                    id:$(e.currentTarget).data("id"),
                }

            let request =
                {
                    url:"addDeviceToMonitor",

                    param: param,

                    callback:discovery.onAddDeviceToMonitorSuccessFull
                }

            ajaxCalls.ajaxPostCall(request);
        });
    },

    onAddDeviceToMonitorSuccessFull: function (request)
    {
        $(".displayMessageBody").text(request.data.message);

        $("#displayMessageButton").click();
    }


}


