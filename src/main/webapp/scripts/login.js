
let login = {
    checkValidity: function (){
        // used to display message for wrong username or password
        let req = new Request('');
        let reqList = req.url.split("/");
        //console.log(reqList[reqList.length-1]);
        if(reqList[reqList.length-1]!="" && reqList[reqList.length-1]!="logout"){
            $("#loginFailMessage").show();
        }

        // used to stop the form resubmission
        if ( window.history.replaceState ) {
            window.history.replaceState( null, null, window.location.href );
        }


        // Show password in text form
        $("#watchPassword").click(function (){
            let typeOfInput = $("#password").attr("type");
            if(typeOfInput==="password"){
                $("#password").attr("type","text");
                $(".bi-eye-fill").hide();
                $(".bi-eye-slash-fill").show();
            }
            else{
                $("#password").attr("type","password");
                $(".bi-eye-fill").show();
                $(".bi-eye-slash-fill").hide();
            }
        });
    },

    logout: function (){
        // to redirect on login page after logout
        if ( window.history.replaceState ) {
            window.history.replaceState( null, null, window.location.href );
        }
/*
        let response = new Response('');
        response.headers.append("Cache-Control","no-cache");
        response.headers.append("Cache-Control","no-store");
        response.headers.append("Cache-Control","must-revalidate");
        response.headers.append("Pragma","no-cache");
        response.headers.append("Expires","0");
*/

    }
}