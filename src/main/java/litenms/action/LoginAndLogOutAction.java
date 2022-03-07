package litenms.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import litenms.models.LoginModel;
import litenms.service.LoginService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class LoginAndLogOutAction extends ActionSupport implements ModelDriven, SessionAware {

    private LoginModel loginModel = new LoginModel();
    private SessionMap<String,Object> sessionMap;

    // login method for login action
    public String login(){
        HttpSession session = ServletActionContext.getRequest().getSession(true);

        if(loginModel.getUsername()!=null)
        {
//            System.out.println(BCrypt.hashpw(loginModel.getPassword(),BCrypt.gensalt()));
            if(LoginService.checkUserExists(loginModel))
            {
                sessionMap.put("username",loginModel.getUsername());
                return "login";
            }
            return "error";
        }
        else {
            String getSessionAttr = (String) session.getAttribute("username");
            if(getSessionAttr!=null){
                return "login";
            }
            else
            {
                return "error";
            }
        }
    }


    public String logout(){
        sessionMap.remove("username");
        sessionMap.invalidate();

        return "logout";
    }

    @Override
    public Object getModel() {
        return loginModel;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        sessionMap = (SessionMap<String, Object>) map;
    }
}
