package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDAO {
    User queryByName(String username);

    void save(User user);

    User queryByCode(String code);

    void activateStatus(User user);

    User queryByPassword(String username, String password);
}
