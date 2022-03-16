package litenms.service;

import litenms.dao.LoginDao;
import litenms.models.LoginModel;

public class LoginService
{
    public static boolean checkUserExists(LoginModel loginModel){
        return LoginDao.checkUserExist(loginModel);
    }
}
