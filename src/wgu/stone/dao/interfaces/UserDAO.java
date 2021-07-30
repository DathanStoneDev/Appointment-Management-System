package wgu.stone.dao.interfaces;

public interface UserDAO {

    boolean checkUserInfo(String un, String up);
    int getUserInfo(String userName);

}
