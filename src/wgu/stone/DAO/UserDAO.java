package wgu.stone.DAO;

public interface UserDAO {

    boolean checkUserInfo(String un, String up);
    int getUserInfo(String userName);

}
