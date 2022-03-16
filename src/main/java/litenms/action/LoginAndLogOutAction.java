package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.LoginModel;
import litenms.service.LoginService;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;

public class LoginAndLogOutAction extends ActionSupport implements ModelDriven<LoginModel>, SessionAware {

    private LoginModel loginModel = new LoginModel();

    private Map<String,Object> sessionMap;

    public String login(){

        if(loginModel.getUsername()!=null){

            if(LoginService.checkUserExists(loginModel))
            {
                sessionMap.put("username",loginModel.getUsername());

                return "login";
            }
        }
        else {
            if(sessionMap.get("username")!=null){

                return "login";
            }
        }
        return "error";
    }


    public String logout(){

        sessionMap.remove("username");

        return "logout";
    }

    @Override
    public LoginModel getModel() {

        return loginModel;
    }

    @Override
    public void setSession(Map<String, Object> map) {

        sessionMap = map;
    }
}
