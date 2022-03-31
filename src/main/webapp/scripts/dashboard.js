

let dashboard = {

    loadDashboardPage: function ()
    {
        let dashboardSelector = $("#dashboard");

        dashboardSelector.click(function ()
        {
            clearInterval(moniterInterval);

            dashboardSelector.children("a").removeClass("collapsed");

            $("#monitor").children("a").addClass("collapsed");

            $("#discovery").children("a").addClass("collapsed");

            $("#main").html('<div class="pagetitle row mb-5 mt-3"> <h1 class="col-lg-10 col-sm-8">Dashboard</h1></div><!-- End Page Title --> <div class="container"> <div class="row" style="height: 300px;"> <div class="col-sm text-center"  style="margin: 30px; border-radius: 20px; background-color: rgb(255, 99, 132); color: white"> <h2 class="mt-3">Down <i class="bi bi-caret-down-square-fill ps-2"></i></h2> <hr style="height: 1px; opacity: 1;"> <h1 class="pt-4" id="totalDown"></h1> </div> <div class="col-sm text-center"  style="margin: 30px; color: white; border-radius: 20px; background-color: rgb(75, 192, 192);"> <h2 class="mt-3">Up <i class="bi bi-caret-up-square-fill ps-2"></i></h2> <hr style="height: 1px; opacity: 1;"> <h1 class="pt-4" id="totalUp"></h1> </div> <div class="col-sm text-center"  style="margin: 30px; border-radius: 20px; color:white; background-color: rgb(54, 162, 235);"> <h2 class="mt-3">Unknown <i class="bi bi-question-square-fill ps-2"></i></h2> <hr style="height: 1px; opacity: 1;"> <h1 class="pt-4" id="totalUnknown"></h1> </div> </div></div> <br> <br>  <div class="container-fluid"> <div class="row"> <div class="col">  <div class="card"> <div class="card-body"> <h5 class="card-title">Top 5 monitors by CPU (%)</h5> <ul id="cpuUsage" class="list-group list-group-flush"> </ul> </div> </div></div> <div class="col">  <div class="card"> <div class="card-body"> <h5 class="card-title">Top 5 monitors by disk (%)</h5> <ul id="diskUsage" class="list-group list-group-flush"> </ul> </div> </div></div> <div class="col">  <div class="card"> <div class="card-body"> <h5 class="card-title">Top 5 monitors by Used memory (GB)</h5> <ul id="usedMemory" class="list-group list-group-flush"> </ul> </div> </div></div> </div> </div>');

            sessionStorage.setItem("page","#dashboard");

            dashboard.getDashboardData();

            dashboard.setDashboardInterval();
        });
    },

    getDashboardData: function ()
    {
        let request = {
            url: "getDashboardData",
            callback:dashboard.onSuccessGetDashboardData
        };

        ajaxCalls.ajaxGetCall(request);
    },

    onSuccessGetDashboardData: function (request)
    {
        let data = request.data.result;

/*
        console.log(JSON.stringify(data));
*/

        $("#totalDown").text(data.totalStatusData.Down);

        $("#totalUp").text(data.totalStatusData.Up);

        $("#totalUnknown").text(data.totalStatusData.Unknown);

/*
        console.log(JSON.stringify(Object.keys(data.topFiveData.used_memory)));
*/

        let cpuHtmlData = '';

        $.each(data.topFiveData.cpu_usage,function (i,v)
        {
            let usedCpuUsage = Math.round(100-v.value);
            cpuHtmlData += '<li class="list-group-item p-0 m-0 pb-3 pt-3"> <div class="container-fluid p-0 m-0"> <div class="row"> <div class="col">'+v.ip+'</div> <div class="col"> <div class="d-flex flex-row-reverse">'+usedCpuUsage+'%</div>  <div class="progress mt-3"> <div class="progress-bar progress-bar-striped bg-info" role="progressbar" style="width: '+usedCpuUsage+'%" aria-valuenow="'+usedCpuUsage+'" aria-valuemin="0" aria-valuemax="100"></div></div>  </div> </div>    </div> </li>'
            console.log(JSON.stringify(v));
        });

        $("#cpuUsage").html(cpuHtmlData);

        let diskHtmlData = '';

        $.each(data.topFiveData.disk_usage,function (i,v)
        {
            let usedDiskUsage = Math.round(v.value);
            diskHtmlData += '<li class="list-group-item p-0 m-0 pb-3 pt-3"> <div class="container-fluid p-0 m-0"> <div class="row"> <div class="col">'+v.ip+'</div> <div class="col"> <div class="d-flex flex-row-reverse">'+usedDiskUsage+'%</div>  <div class="progress mt-3"> <div class="progress-bar progress-bar-striped bg-info" role="progressbar" style="width: '+usedDiskUsage+'%" aria-valuenow="'+usedDiskUsage+'" aria-valuemin="0" aria-valuemax="100"></div></div>  </div> </div>    </div> </li>'
            console.log(JSON.stringify(v));
        });

        $("#diskUsage").html(diskHtmlData);

        let usedMemoryHtmlData = '';

        $.each(data.topFiveData.used_memory,function (i,v)
        {
            let usedMemory = v.value;
            usedMemoryHtmlData += '<li class="list-group-item p-0 m-0 pb-3 pt-3"> <div class="container-fluid p-0 m-0"> <div class="row"> <div class="col">'+v.ip+'</div> <div class="col"> <div class="d-flex flex-row-reverse">'+usedMemory+' GB</div>  </div> </div>    </div> </li>'
            console.log(JSON.stringify(v));
        });

        $("#usedMemory").html(usedMemoryHtmlData);
    },

    setDashboardInterval: function ()
    {
        let func = dashboard.getDashboardData;

        dashboardInterval =  setInterval(func, 30000);
    },
}