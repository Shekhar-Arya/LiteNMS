package litenms.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Map;

public class LoginSessionInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map<String,Object> sessionMap = actionInvocation.getInvocationContext().getSession();

        if(sessionMap.get("username")!=null || sessionMap !=null){
            return actionInvocation.invoke();
        }
        else{
            return "loginError";
        }
    }
}
