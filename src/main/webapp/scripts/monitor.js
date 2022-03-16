let monitor =
    {
    loadMonitorData: function ()
    {
        $("#monitor").click(function ()
        {
            $("#monitor").children("a").removeClass("collapsed");

            $("#discovery").children("a").addClass("collapsed");

            $("#dashboard").children("a").addClass("collapsed");

            $("#main").html('<div class="pagetitle row mb-5 mt-3"> <h1 class="col-lg-10 col-sm-8">Monitor Table</h1></div><!-- End Page Title --> <section class="section"> <div class="row"> <div class="col-lg-12"> <div class="card"> <div class="card-body"> <!-- Table with stripped rows --> <table class="display cell-border" id="monitorTable"> <thead> <tr> <th scope="col">Ip</th> <th scope="col">Type</th> <th scope="col">Status</th> <th scope="col">Action</th> </tr></thead> <tbody id="monitorTableBody"> </tbody>  </table> <!-- End Table with stripped rows --> </div> </div> </div> </div> </section> <button type="button" class="btn btn-primary" id="dataDiagramButton" data-bs-toggle="modal" data-bs-target="#dataDiagram" style="display: none;"> </button> <div class="modal fade" id="dataDiagram" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h3 class="modal-title">Device Status</h3> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body" id="dataDiagramBody">  </div> <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div> </div><!-- End Basic Modal--> </div> </div>');

            addRow = $("table#monitorTable").DataTable({

                lengthMenu: [5, 10, 20, 50, 100, 200, 500]

            });
            sessionStorage.setItem("page","#monitor");

            monitor.getMonitorDevices();

            monitor.getMonitorData();

            monitor.setMonitorInterval();
        });
    },

    getMonitorDevices: function ()
    {
        var request =
            {
                url: "getMonitorDevices",

                callback: monitor.onGetMonitorDevicesSuccess
        };

        ajaxCalls.ajaxGetCall(request);
    },

    onGetMonitorDevicesSuccess: function (request)
    {
        addRow.clear().draw();

        $.each(request.data.result.result,function (i,v)
        {
            addRow.row.add([v.ip,v.type,v.status,'<button class="btn btn-outline-primary btn-sm showMonitorData" data-id="'+v.id+'" data-ip="'+v.ip+'"><i class="bi bi-display"></i></button>']).draw();
        });
    },

    getMonitorData: function ()
    {
        $("#monitorTable").on("click",".showMonitorData",function (e)
        {
            var param =
            {
                monitor_id:$(e.currentTarget).data("id"),

                ip:$(e.currentTarget).data("ip")
            }
            var request =
            {
                url:"getMonitorData",

                param:param,

                callback: monitor.onGetMonitorDataSuccess
            }
            ajaxCalls.ajaxPostCall(request);
        });
    },

    onGetMonitorDataSuccess: function (request)
    {
        if(request.data.message == "Unknown")
        {
            $("#dataDiagramBody").html('Device is Unknown (No Data Polled Yet).');

            $("#dataDiagramButton").click();
        }
        else
        {
            let packetLossColor= request.data.result.dataModel.packetLoss==100?"bad":"good";

            $("#dataDiagramBody").html('<div class="container"> <div class="row"> <div class="col-sm"><h1 class="d-flex justify-content-center">Ip: '+request.param.ip+'</h1></div> <div class="col-sm">   <h1 class="d-flex justify-content-center">Type: '+request.data.result.dataModel.type+'</h1>  </div></div></div> <div class="container"> <div class="row"> <div class="col-sm" style="margin: 30px;"> <canvas id="doughnutChart" style="max-height: 300px;"></canvas> <h5 class="d-flex justify-content-center mt-3"> Availability Chart </h5> </div> <div class="col-sm d-flex align-items-center '+packetLossColor+' justify-content-center"  style="margin: 30px;"> Packet Loss : '+request.data.result.dataModel.packetLoss+'% </div> <div class="col-sm d-flex justify-content-center align-items-center '+packetLossColor+'"  style="margin: 30px;">   Average RTT Time : '+request.data.result.dataModel.avgRtt+'ms  </div> </div></div> <br> <div class="container"> <div id="columnChart" style="height: 400px; font-size: large"><canvas id="myChart"></canvas><h5 class="d-flex justify-content-center mt-3" style="font-weight: bold"> Last 24 Hours Polled Data </h5></div></div> <br><br> <div class="container"> <div class="row"> <div class="col-sm" style="margin: 30px;"> <canvas id="donustChartSSHMemory" style="max-height: 300px;"></canvas><h5 class="d-flex justify-content-center mt-3 fade chartLabel"> Memory Chart (Total : '+request.data.result.dataModel.totalMemory+') </h5></div>  <div class="col-sm" style="margin: 30px;"> <canvas id="donustChartSSHCpu" style="max-height: 300px;"></canvas> <h5 class="d-flex justify-content-center mt-3 fade chartLabel"> CPU Usage </h5></div>  <div class="col-sm" style="margin: 30px;"> <canvas id="donustChartSSHDisk" style="max-height: 300px;"></canvas> <h5 class="d-flex justify-content-center mt-3 fade chartLabel"> Disk Memory Usage </h5> </div> </div></div> <h3 id="sshError" class="d-flex justify-content-center fade"> Due to device down no latest data has been Polled </h3>');

            var packetReceive = [];

            var date = [];

            $.each(request.data.result.dataModelList,function (i,v)
            {
                    packetReceive[i] = 100-v.packetLoss;

                    date[i] = v.labelForBar;
            });

            $("#sshError").removeClass("show");

            $(".chartLabel").removeClass("show");

            const data =
            {
                labels: date,

                datasets: [
                    {
                        label: 'Packet Receive (%)',

                        data: packetReceive,

                        borderColor: "rgb(75, 192, 192)",

                        backgroundColor: "rgb(75, 192, 192)"
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

                                font: {

                                    size: 14
                                }
                            }
                        },

                    }
                },
            };
            const myChart = new Chart(

                $('#myChart'),

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

            if(request.data.result.dataModel.type=="SSH" && request.data.result.dataModel.totalMemory!=0)
            {
                $(".chartLabel").addClass("show");

                const donustChartSSHMemory = new Chart($('#donustChartSSHMemory'), {

                    type: 'doughnut',

                    data: {

                        labels: [

                            'Free Memory (GB)',

                            'Used Memory (GB)',

                            'Buff/Cache (GB)'

                        ],

                        datasets: [{

                            label: 'My First Dataset',

                            data: [request.data.result.dataModel.freeMemory, request.data.result.dataModel.usedMemory, request.data.result.dataModel.totalMemory-request.data.result.dataModel.freeMemory-request.data.result.dataModel.usedMemory],

                            backgroundColor: [

                                'rgb(255, 99, 132)',

                                'rgb(255, 205, 86)',

                                'rgb(54, 162, 235)',

                            ],

                            hoverOffset: 4

                        }]
                    }
                });

                const donustChartSSHCpu = new Chart($('#donustChartSSHCpu'), {

                    type: 'doughnut',

                    data: {

                        labels: [

                            'Used CPU (%)',

                            'Idle CPU (%)'

                        ],

                        datasets: [{

                            label: 'My First Dataset',

                            data: [100-request.data.result.dataModel.cpuUsage, request.data.result.dataModel.cpuUsage],

                            backgroundColor: [

                                'rgb(255, 99, 132)',

                                'rgb(255, 205, 86)'
                            ],

                            hoverOffset: 4
                        }]
                    }
                });

                const donustChartSSHDisk = new Chart($('#donustChartSSHDisk'), {

                    type: 'doughnut',

                    data: {

                        labels: [

                            'Used Disk Space Usage (%)',

                            'Free Disk Space Usage (%)'

                                ],

                    datasets: [{

                        label: 'My First Dataset',

                        data: [request.data.result.dataModel.diskSpaceUsage, 100-request.data.result.dataModel.diskSpaceUsage],

                            backgroundColor: [

                                'rgb(255, 99, 132)',

                                'rgb(75, 192, 192)'
                        ],
                    hoverOffset: 4
                                }]
                    }
                });

            }
            else if(request.data.result.dataModel.type=="SSH")
            {
                $("#sshError").addClass("show");
            }

            $(".modal-dialog").addClass("modal-fullscreen");

            $("#dataDiagramButton").click();
        }
    },

    setMonitorInterval: function ()
    {
        let func = monitor.getMonitorDevices;

        moniterInterval =  setInterval(func, 30000);
    },
}


