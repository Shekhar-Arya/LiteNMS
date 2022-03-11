let monitor = {
    loadMonitorData: function (){
        $("#monitor").click(function (){
            $("#monitor").children("a").removeClass("collapsed");
            $("#discovery").children("a").addClass("collapsed");
            $("#dashboard").children("a").addClass("collapsed");
            $("#main").html('<div class="pagetitle row mb-5 mt-3"> <h1 class="col-lg-10 col-sm-8">Monitor Table</h1></div><!-- End Page Title --> <section class="section"> <div class="row"> <div class="col-lg-12"> <div class="card"> <div class="card-body"> <!-- Table with stripped rows --> <table class="display cell-border" id="monitorTable"> <thead> <tr> <th scope="col">Ip</th> <th scope="col">Type</th> <th scope="col">Action</th> </tr></thead> <tbody id="monitorTableBody"> </tbody>  </table> <!-- End Table with stripped rows --> </div> </div> </div> </div> </section> <button id="displayMessageButton" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#displayMessageModal" style="display: none;"></button><div class="modal fade" id="displayMessageModal" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title">Action Message</h5> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body displayMessageBody"> </div> <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div></div><!-- End Basic Modal-->');

            addRow = $("table#monitorTable").DataTable({

                lengthMenu: [5, 10, 20, 50, 100, 200, 500]

            });
            monitor.getMonitorDevices();
        });
    },

    getMonitorDevices: function (){

        var request = {

            url: "getMonitorDevices",

            callback: monitor.onGetMonitorDevicesSuccess

        };

        ajaxCalls.ajaxGetCall(request);
    },

    onGetMonitorDevicesSuccess: function (request){

        console.log("devices got "+JSON.stringify(request.data.result));
        addRow.clear().draw();
        $.each(request.data.result.result,function (i,v){

            addRow.row.add([v.ip,v.type,'<button class="btn btn-outline-primary btn-sm" data-id="'+v.id+'"><i class="bi bi-display"></i></button>']).draw();

        });

    },
}