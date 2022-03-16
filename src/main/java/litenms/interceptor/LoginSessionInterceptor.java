package litenms.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.StrutsStatics;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LoginSessionInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception
    {
        Map<String,Object> sessionMap = actionInvocation.getInvocationContext().getSession();

        HttpServletRequest request = (HttpServletRequest) actionInvocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);

        if(sessionMap !=null && sessionMap.get("username")!=null)
        {
            return actionInvocation.invoke();
        }
        else if(request.getRequestURI().equals("/login"))
        {
            return actionInvocation.invoke();
        }
        else
        {
            return "loginError";
        }
    }
}
