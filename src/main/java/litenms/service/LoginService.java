package litenms.service;

import litenms.dao.LoginDao;
import litenms.models.LoginModel;

public class LoginService
{
    LoginDao loginDao = new LoginDao();

    public boolean checkUserExists(LoginModel loginModel)
    {
        return loginDao.checkUserExist(loginModel);
    }
}
