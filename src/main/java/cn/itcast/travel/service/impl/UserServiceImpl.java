package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDAO;
import cn.itcast.travel.dao.impl.UserDAOImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDAO dao = new UserDAOImpl();

    @Override
    public boolean register(User user) {
        //查询数据库是否存在用户名
        User query = dao.queryByName(user.getUsername());
        //不存在则创建用户
        if (query == null) {
            //设置激活码
            user.setCode(UuidUtil.getUuid());
            //设置未激活状态
            user.setStatus("N");
            //保存用户到数据库
            dao.save(user);
            //发送激活邮件
            String mail = "<a href='http://localhost/travel/user/activate?code=" + user.getCode() + "'>点击激活</a>";
            MailUtils.sendMail(user.getEmail(), mail, "激活邮件");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean activate(String code) {
        User user = dao.queryByCode(code);
        if (user == null) {
            return false;
        }
        dao.activateStatus(user);
        return true;
    }

    @Override
    public User login(User user) {
        return dao.queryByPassword(user.getUsername(), user.getPassword());
    }
}
