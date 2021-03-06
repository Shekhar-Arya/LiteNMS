package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.LoginModel;
import litenms.service.LoginService;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;

public class LoginAndLogOutAction extends ActionSupport implements ModelDriven<LoginModel>, SessionAware
{
    private LoginModel loginModel = new LoginModel();

    private Map<String,Object> sessionMap;

    private LoginService loginService = new LoginService();

    public String login()
    {
        try
        {
            if(loginModel.getUsername()!=null){

                if(loginService.checkUserExists(loginModel))
                {
                    sessionMap.put("username",loginModel.getUsername());

                    return "login";
                }
            }
            else
            {
                if(sessionMap.get("username")!=null)
                {
                    return "login";
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "error";
    }


    public String logout()
    {
        try
        {
            sessionMap.remove("username");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "logout";
    }

    @Override
    public LoginModel getModel()
    {
        return loginModel;
    }

    @Override
    public void setSession(Map<String, Object> map)
    {
        sessionMap = map;
    }
}
