
let login = {
    checkValidity: function (){

        let req = new Request('');

        let reqList = req.url.split("/");

        if(reqList[reqList.length-1]!="" && reqList[reqList.length-1]!="logout")
        {
            $("#loginFailMessage").show();
        }

        if ( window.history.replaceState )
        {
            window.history.replaceState( null, null, window.location.href );
        }


        $("#watchPassword").click(function ()
        {
            let typeOfInput = $("#password").attr("type");

            if(typeOfInput==="password")
            {
                $("#password").attr("type","text");

                $(".bi-eye-fill").hide();

                $(".bi-eye-slash-fill").show();
            }
            else
            {
                $("#password").attr("type","password");

                $(".bi-eye-fill").show();

                $(".bi-eye-slash-fill").hide();
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