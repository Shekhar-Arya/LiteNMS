package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.LoginModel;
import litenms.service.LoginService;

public class LoginAndLogOutAction extends ActionSupport implements ModelDriven {

    private LoginModel loginModel = new LoginModel();

    public String login(){

        if(LoginService.checkUserExists(loginModel))
        {
            System.out.println(loginModel.getPassword());
            return "login";
        }
        return "error";
    }

    @Override
    public Object getModel() {
        return loginModel;
    }
}
