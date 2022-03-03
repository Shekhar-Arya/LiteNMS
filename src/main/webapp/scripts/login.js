let num = 0;
let login = {
    checkValidity: function (){
        if(sessionStorage.getItem("num")>0){
            $("#loginFailMessage").html("              <div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\n" +
                "                Wrong Username or Password\n" +
                "                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n" +
                "              </div>");
            num = 0;
            sessionStorage.setItem("num",num)
        }
        $("#loginButton").click(function (){
            num+=1;
            sessionStorage.setItem("num",num);
        });
    },
}