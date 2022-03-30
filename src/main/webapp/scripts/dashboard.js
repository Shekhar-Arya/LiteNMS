

let dashboard = {

    loadDashboardPage: function ()
    {
        let dashboardSelector = $("#dashboard");

        dashboardSelector.click(function ()
        {
            dashboardSelector.children("a").removeClass("collapsed");

            $("#monitor").children("a").addClass("collapsed");

            $("#discovery").children("a").addClass("collapsed");

            $("#main").html('<div class="pagetitle row mb-5 mt-3"> <h1 class="col-lg-10 col-sm-8">Dashboard</h1></div><!-- End Page Title --> <div class="container"> <div class="row" style="height: 300px;"> <div class="col-sm text-center"  style="margin: 30px; border-radius: 20px; background-color: rgb(255, 99, 132); color: white"> <h2 class="mt-3">Down <i class="bi bi-caret-down-square-fill ps-2"></i></h2> <hr style="height: 1px; opacity: 1;"> <h1 class="pt-4" id="totalDown"></h1> </div> <div class="col-sm text-center"  style="margin: 30px; color: white; border-radius: 20px; background-color: rgb(75, 192, 192);"> <h2 class="mt-3">Up <i class="bi bi-caret-up-square-fill ps-2"></i></h2> <hr style="height: 1px; opacity: 1;"> <h1 class="pt-4" id="totalUp"></h1> </div> <div class="col-sm text-center"  style="margin: 30px; border-radius: 20px; color:white; background-color: rgb(54, 162, 235);"> <h2 class="mt-3">Unknown <i class="bi bi-question-square-fill ps-2"></i></h2> <hr style="height: 1px; opacity: 1;"> <h1 class="pt-4" id="totalUnknown"></h1> </div> </div></div>  <div class="container-fluid"> <div class="row"> <div class="col">  <div class="card"> <div class="card-body"> <h5 class="card-title">Top 5 devices</h5> <ul class="list-group list-group-flush"> </ul> </div> </div></div> <div class="col">  <div class="card"> <div class="card-body"> <h5 class="card-title">Top 5 devices</h5> <ul class="list-group list-group-flush"> </ul> </div> </div></div> <div class="col">  <div class="card"> <div class="card-body"> <h5 class="card-title">Top 5 devices</h5> <ul class="list-group list-group-flush"> </ul> </div> </div></div> </div> </div>');

            sessionStorage.setItem("page","#dashboard");

            dashboard.getDashboardData();
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

        console.log(JSON.stringify(data));

        $("#totalDown").text(data.totalStatusData.Down);

        $("#totalUp").text(data.totalStatusData.Up);

        $("#totalUnknown").text(data.totalStatusData.Unknown);

        $.each(data.topFiveData,function (i,v)
        {
            let htmlData;
            console.log(JSON.stringify(v));
        });
    }
}