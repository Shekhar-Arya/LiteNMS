
let monitor = {
    loadMonitorData: function (){
        $("#monitor").click(function (){
            $("#monitor").children("a").removeClass("collapsed");
            $("#discovery").children("a").addClass("collapsed");
            $("#dashboard").children("a").addClass("collapsed");
            // $("head").append('<meta http-equiv="refresh" content="30">');
            $("#main").html('<div class="pagetitle row mb-5 mt-3"> <h1 class="col-lg-10 col-sm-8">Monitor Table</h1></div><!-- End Page Title --> <section class="section"> <div class="row"> <div class="col-lg-12"> <div class="card"> <div class="card-body"> <!-- Table with stripped rows --> <table class="display cell-border" id="monitorTable"> <thead> <tr> <th scope="col">Ip</th> <th scope="col">Type</th> <th scope="col">Status</th> <th scope="col">Action</th> </tr></thead> <tbody id="monitorTableBody"> </tbody>  </table> <!-- End Table with stripped rows --> </div> </div> </div> </div> </section> <button type="button" class="btn btn-primary" id="dataDiagramButton" data-bs-toggle="modal" data-bs-target="#dataDiagram" style="display: none;"> </button> <div class="modal fade" id="dataDiagram" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h3 class="modal-title">Device Status</h3> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body" id="dataDiagramBody">  </div> <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div> </div><!-- End Basic Modal--> </div> </div>');
            /*<button id="displayMessageButton" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#displayMessageModal" style="display: none;"></button><div class="modal fade" id="displayMessageModal" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title">Action Message</h5> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body displayMessageBody"> </div> <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div></div><!-- End Basic Modal-->*/
            addRow = $("table#monitorTable").DataTable({

                lengthMenu: [5, 10, 20, 50, 100, 200, 500]

            });
            sessionStorage.setItem("page","#monitor");
            monitor.getMonitorDevices();
            monitor.getMonitorData();
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

            addRow.row.add([v.ip,v.type,v.status,   '<button class="btn btn-outline-primary btn-sm showMonitorData" data-id="'+v.id+'"><i class="bi bi-display"></i></button>']).draw();

        });

    },

    getMonitorData: function ()
    {
        $("#monitorTable").on("click",".showMonitorData",function (e){
            var param = {
                monitor_id:$(e.currentTarget).data("id")
            }

            var request = {
                url:"getMonitorData",
                param:param,
                callback: monitor.onGetMonitorDataSuccess
            }
            ajaxCalls.ajaxPostCall(request);
        });
    },

    onGetMonitorDataSuccess: function (request){
        if(request.data.message == "Unknown")
        {
            $("#dataDiagramBody").html('Device is Unknown (No Data Polled Yet).');
            $("#dataDiagramButton").click();
        }
        else
        {
            let availability= request.data.result.dataModel.availability==0?"bad":"good";
                $("#dataDiagramBody").html('<h1 class="d-flex justify-content-center">Type: '+request.data.result.dataModel.type+'</h1> <div class="container"> <div class="row"> <div class="col-sm" style="margin: 30px;"> <canvas id="doughnutChart" style="max-height: 300px;"></canvas> </div> <div class="col-sm d-flex align-items-center '+availability+' justify-content-center"  style="margin: 30px;"> Packet Loss : '+request.data.result.dataModel.packetLoss+'% </div> <div id="availability" class="col-sm d-flex justify-content-center align-items-center good"  style="margin: 30px;">   Average RTT Time : '+request.data.result.dataModel.avgRtt+'ms  </div> </div></div> <div class="container"> <div id="columnChart" style="height: 400px; font-size: large"><canvas id="myChart"></canvas></div></div>');
            var avgRTT = [];
            var packetReceive = [];
            var date = [];
            $.each(request.data.result.dataModelList,function (i,v){
                avgRTT[i] = v.avgRtt;
                packetReceive[i] = 100-v.packetLoss;
                date[i] = v.date;
            });

              const data = {
                labels: date,
                datasets: [
                    {
                        label: 'Packet Receive (%)',
                        data: packetReceive,
                        borderColor: "rgb(75, 192, 192)",
                        backgroundColor: "rgb(75, 192, 192)",
                    },
                    {
                        label: 'Average RTT (ms)',
                        data: avgRTT,
                        borderColor: "rgb(255, 99, 132)",
                        backgroundColor: "rgb(255, 99, 132)",
                    }
                ]
            };
            const config = {
                type: 'bar',
                data: data,
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            suggestedMin: 100,
                            suggestedMax: 100
                        },
                        x: {
                            ticks: {
                                maxRotation: 45,
                                minRotation: 45
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            position: 'top',
                            labels: {
                                // This more specific font property overrides the global property
                                font: {
                                    size: 14
                                }
                            }
                        },
                        title: {
                            display: true,
                            text: 'Last 24 hours Polled Data',
                            font: {
                                size: 20
                            }
                        }
                    }
                },
            };
            const myChart = new Chart(
                document.getElementById('myChart'),
                config
            );

            const donustChart = new Chart($('#doughnutChart'), {
                type: 'doughnut',
                data: {
                    labels: [
                        'Available (%)',
                        'Not Available (%)'
                    ],
                    datasets: [{
                        label: 'My First Dataset',
                        data: [request.data.result.dataModel.availability, 100-request.data.result.dataModel.availability],
                        backgroundColor: [
                            'rgb(75, 192, 192)',
                            'rgb(255, 99, 132)'
                        ],
                        hoverOffset: 4
                    }]
                }
            });

            $(".modal-dialog").addClass("modal-fullscreen");
            $("#dataDiagramButton").click();
        }
    }
}