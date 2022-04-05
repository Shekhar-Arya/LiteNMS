let monitor =
    {
    loadMonitorData: function ()
    {
        let monitorSelector = $("#monitor");

        monitorSelector.click(function ()
        {

            clearInterval(dashboardInterval);

            monitorSelector.children("a").removeClass("collapsed");

            $("#discovery").children("a").addClass("collapsed");

            $("#dashboard").children("a").addClass("collapsed");


            $("#main").html('<div class="pagetitle row mb-5 mt-3"> <h1 class="col-lg-10 col-sm-8">Monitor Table</h1></div><!-- End Page Title --> <section class="section"> <div class="row"> <div class="col-lg-12"> <div class="card"> <div class="card-body"> <!-- Table with stripped rows --> <table class="display cell-border" id="monitorTable"> <thead> <tr> <th scope="col">Ip</th> <th scope="col">Type</th> <th scope="col">Status</th> <th scope="col">Action</th> </tr></thead> <tbody id="monitorTableBody"> </tbody>  </table> <!-- End Table with stripped rows --> </div> </div> </div> </div> </section> <button type="button" class="btn btn-primary" id="dataDiagramButton" data-bs-toggle="modal" data-bs-target="#dataDiagram" style="display: none;"> </button> <div class="modal fade" id="dataDiagram" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h3 class="modal-title">Device Status</h3> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body" id="dataDiagramBody">  </div> <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div> </div><!-- End Basic Modal--> </div> </div> <button id="displayMessageButton" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#displayMessageModal" style="display: none;"></button><div class="modal fade" id="displayMessageModal" tabindex="-1"> <div class="modal-dialog"> <div class="modal-content"> <div class="modal-header"> <h5 class="modal-title">Action Message</h5> <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button> </div> <div class="modal-body displayMessageBody"> </div> <div class="modal-footer"> <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> </div> </div> </div></div>');

            datatable = $("table#monitorTable").DataTable({

                lengthMenu: [5, 10, 20, 50, 100, 200, 500]

            });
            sessionStorage.setItem("page","#monitor");

            monitor.getMonitorDevices();

            monitor.getMonitorData();

            monitor.deleteMonitorData();

            monitor.setMonitorInterval();
        });
    },

    getMonitorDevices: function ()
    {
        let request =
            {
                url: "getMonitorDevices",

                callback: monitor.onGetMonitorDevicesSuccess
        };

        ajaxCalls.ajaxGetCall(request);
    },

    onGetMonitorDevicesSuccess: function (request)
    {
        datatable.clear().draw();

        $.each(request.data.result.result,function (i,v)
        {
            datatable.row.add([v.ip,v.type,v.status,'<button class="btn btn-outline-primary btn-sm showMonitorData" data-id="'+v.id+'" data-ip="'+v.ip+'"><i class="bi bi-display"></i></button> <button class="btn btn-outline-primary btn-sm deleteMonitorData" data-id="'+v.id+'"><i class="bi bi-trash2"></i></button>']).draw();
        });
    },

    getMonitorData: function ()
    {
        $("#monitorTable").on("click",".showMonitorData",function (e)
        {
            let param =
            {
                monitor_id:$(e.currentTarget).data("id"),

                ip:$(e.currentTarget).data("ip")
            }
            let request =
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
        let dataMessage = request.data.message;

        if(dataMessage === "Unknown")
        {

            $('.modal-dialog').removeClass("modal-fullscreen");
            $("#dataDiagramBody").html('Device is Unknown (No Data Polled Yet).');

            $("#dataDiagramButton").click();

        }
        else
        {
            let dataModel = request.data.result.dataModel;

            let dataModelList = request.data.result.dataModelList;
            let packetLossColor= dataModel.packetLoss===100?"bad":"good";

            $("#dataDiagramBody").html('<div class="container"> <div class="row"> <div class="col-sm"><h1 class="d-flex justify-content-center">Ip: '+request.param.ip+'</h1></div> <div class="col-sm">   <h1 class="d-flex justify-content-center">Type: '+dataModel.type+'</h1>  </div></div></div> <div class="container"> <div class="row"> <div class="col-sm" style="margin: 30px;"> <canvas id="doughnutChart" style="max-height: 300px;"></canvas> <h5 class="d-flex justify-content-center mt-3"> Availability Chart </h5> </div> <div class="col-sm d-flex align-items-center '+packetLossColor+' justify-content-center"  style="margin: 30px;"> Packet Loss : '+dataModel.packetLoss+'% </div> <div class="col-sm d-flex justify-content-center align-items-center '+packetLossColor+'"  style="margin: 30px;">   Average RTT Time : '+dataModel.avgRtt+'ms  </div> </div></div> <br> <div class="container"> <div id="columnChart" style="height: 400px; font-size: large"><canvas id="myChart"></canvas><h5 class="d-flex justify-content-center mt-3" style="font-weight: bold"> Last 24 Hours Polled Data </h5></div></div> <br><br> <div class="container"> <div class="row"> <div class="col-sm" style="margin: 30px;"> <canvas id="donustChartSSHMemory" style="max-height: 300px;"></canvas><h5 class="d-flex justify-content-center mt-3 fade chartLabel"> Memory Chart (Total : '+dataModel.totalMemory+') </h5></div>  <div class="col-sm" style="margin: 30px;"> <canvas id="donustChartSSHCpu" style="max-height: 300px;"></canvas> <h5 class="d-flex justify-content-center mt-3 fade chartLabel"> CPU Usage </h5></div>  <div class="col-sm" style="margin: 30px;"> <canvas id="donustChartSSHDisk" style="max-height: 300px;"></canvas> <h5 class="d-flex justify-content-center mt-3 fade chartLabel"> Disk Usage </h5> </div> </div></div> <h3 id="sshError" class="d-flex justify-content-center fade"> Due to device down no latest data has been Polled </h3>');

            let packetReceive = [];

            let date = [];

            let chartLabelSelector = $('.chartLabel');

            let sshErrorSelector = $('#sshError');

            $.each(dataModelList,function (i,v)
            {
                    packetReceive[i] = 100-v.packetLoss;

                    date[i] = v.labelForBar;
            });

            sshErrorSelector.removeClass("show");

            chartLabelSelector.removeClass("show");

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

            console.log(dataModel.availability);

            const donustChart = new Chart($('#doughnutChart'), {

                type: 'doughnut',

                data: {

                    labels: [

                        'Available (%)',

                        'Not Available (%)'
                    ],

                    datasets: [{

                        label: 'My First Dataset',

                        data: [dataModel.availability, 100-dataModel.availability],

                        backgroundColor: [

                            'rgb(75, 192, 192)',

                            'rgb(255, 99, 132)'

                        ],

                        hoverOffset: 4
                    }]
                }
            });

            if(dataModel.type==="SSH" && dataModel.totalMemory!==0)
            {
                chartLabelSelector.addClass("show");

                if (dataModel.freeMemory!==-1 && dataModel.usedMemory!==-1 && dataModel.totalMemory!==-1)
                {
                    console.log(dataModel.freeMemory);

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

                                data: [dataModel.freeMemory, dataModel.usedMemory, dataModel.totalMemory-dataModel.freeMemory-dataModel.usedMemory],

                                backgroundColor: [

                                    'rgb(255, 99, 132)',

                                    'rgb(255, 205, 86)',

                                    'rgb(54, 162, 235)',

                                ],

                                hoverOffset: 4

                            }]
                        }
                    });
                }

                if(dataModel.cpuUsage!==-1)
                {
                    console.log(dataModel.cpuUsage);

                    const donustChartSSHCpu = new Chart($('#donustChartSSHCpu'), {

                        type: 'doughnut',

                        data: {

                            labels: [

                                'Used CPU (%)',

                                'Idle CPU (%)'

                            ],

                            datasets: [{

                                label: 'My First Dataset',

                                data: [100-dataModel.cpuUsage, dataModel.cpuUsage],

                                backgroundColor: [

                                    'rgb(255, 99, 132)',

                                    'rgb(255, 205, 86)'
                                ],

                                hoverOffset: 4
                            }]
                        }
                    });
                }

                if(dataModel.diskSpaceUsage!==-1)
                {
                    console.log(dataModel.diskSpaceUsage);

                    const donustChartSSHDisk = new Chart($('#donustChartSSHDisk'), {

                        type: 'doughnut',

                        data: {

                            labels: [

                                'Used Disk Usage (%)',

                                'Free Disk Usage (%)'

                            ],

                            datasets: [{

                                label: 'My First Dataset',

                                data: [dataModel.diskSpaceUsage, 100-dataModel.diskSpaceUsage],

                                backgroundColor: [

                                    'rgb(255, 99, 132)',

                                    'rgb(75, 192, 192)'
                                ],
                                hoverOffset: 4
                            }]
                        }
                    });
                }

            }
            else if(dataModel.type==="SSH")
            {
                sshErrorSelector.addClass("show");
            }

            $('.modal-dialog').addClass("modal-fullscreen");

            $('#dataDiagramButton').click();
        }
    },

    deleteMonitorData: function ()
    {
        $("#monitorTable").on("click",".deleteMonitorData",function (e)
        {
            iziToast.question({
                timeout: 20000,
                close: true,
                overlay: true,
                displayMode: 'once',
                id: 'question',
                zindex: 999,
                title: 'Delete',
                message: 'Are you sure?',
                position: 'center',
                buttons: [
                    ['<button><b>YES</b></button>', function (instance, toast) {

                        instance.hide({ transitionOut: 'fadeOut' }, toast, 'button');

                        let param =
                            {
                                id:$(e.currentTarget).data("id"),
                            }
                        let request =
                            {
                                url:"deleteMonitorData",

                                param:param,

                                callback: monitor.onDeleteMonitorDataSuccess
                            }
                        ajaxCalls.ajaxPostCall(request);


                    }, true],
                    ['<button>NO</button>', function (instance, toast) {

                        instance.hide({ transitionOut: 'fadeOut' }, toast, 'button');

                    }],
                ],
                onClosing: function(instance, toast, closedBy){
                    console.info('Closing | closedBy: ' + closedBy);
                },
                onClosed: function(instance, toast, closedBy){
                    console.info('Closed | closedBy: ' + closedBy);
                }
            });

        });

    },

    onDeleteMonitorDataSuccess: function (request)
    {

        let data = request.data.message;

        if(data.includes("Unsuccessfull"))
        {
            iziToast.error({
                title: 'Monitor',
                message: data,
                position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
                pauseOnHover: false
            });
        }
        else
        {
            iziToast.success({
                title: 'Monitor',
                message: data,
                position: 'topRight', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter, center
                pauseOnHover: false
            });
        }

        monitor.getMonitorDevices();
    },

    setMonitorInterval: function ()
    {
        let func = monitor.getMonitorDevices;

        moniterInterval =  setInterval(func, 30000);
    },
}


