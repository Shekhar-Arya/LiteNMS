let ajaxCalls = {
    ajaxPostCall: function (request){
        $.ajax({
            url: request.url,
            type: "POST",
            cache: false,
            data: request.param,
            // timeout: 180000,
            success: function (data){
                var callbacks;
                if(request.callback!=undefined){
                    callbacks = $.Callbacks();
                    callbacks.add(request.callback);
                    request.data = data;
                    callbacks.fire(request);
                    callbacks.remove(request.callback);
                }
            },
            error: function (){console.log("ErrorRRRRRRR----")},
            dataType: "json",
        });
    },

    ajaxGetCall: function (request){
        $.ajax({
            url: request.url,
            type: "GET",
            cache: false,
            // data: request.param,
            // timeout: 180000,
            success: function (data){
                var callbacks;
                if(request.callback!=undefined){
                    callbacks = $.Callbacks();
                    callbacks.add(request.callback);
                    request.data = data;
                    callbacks.fire(request);
                    callbacks.remove(request.callback);
                }
            },
            error: function (){console.log("ErrorRRRRRRR----")},
            dataType: "json",
        });
    }
}