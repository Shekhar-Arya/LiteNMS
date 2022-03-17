
let login = {
    checkValidity: function (){

        let req = new Request('');

        let reqList = req.url.split("/");

        let reqUrl = reqList[reqList.length-1];

        if(reqUrl!=="" && reqUrl!=="logout")
        {
            $("#loginFailMessage").show();
        }

        if ( window.history.replaceState )
        {
            window.history.replaceState( null, null, window.location.href );
        }


        $("#watchPassword").click(function ()
        {
            let passwordSelector = $("#password");

            let passwordShowIconSelector = $(".bi-eye-fill");

            let passwordHideIconSelector = $(".bi-eye-slash-fill");

            let typeOfInput = passwordSelector.attr("type");

            if(typeOfInput==="password")
            {
                passwordSelector.attr("type","text");

                passwordShowIconSelector.hide();

                passwordHideIconSelector.show();
            }
            else
            {
                passwordSelector.attr("type","password");

                passwordShowIconSelector.show();

                passwordHideIconSelector.hide();
            }
        });
    },

    logout: function ()
    {
        if ( window.history.replaceState )
        {
            window.history.replaceState( null, null, window.location.href );
        }

    }
}