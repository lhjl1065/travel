package com.lhjl.travel.service.impl;

import com.lhjl.travel.dao.UserDao;
import com.lhjl.travel.dao.impl.UserDaoImpl;
import com.lhjl.travel.domain.User;
import com.lhjl.travel.service.UserService;
import com.lhjl.travel.util.MailUtils;
import com.lhjl.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao=new UserDaoImpl();
    @Override
    public boolean register(User user) {
        //查询用户名是否已经存在
        int number = userDao.findByName(user.getUsername());
        if (number==0){
            //用户不穿在,可以注册,并且设置新注册的用户状态为N
            user.setStatus("N");
            //生成随机数当激活码
            String uuid = UuidUtil.getUuid();
            //设置用户code为uid
            user.setCode(uuid);
            //保存用户
            userDao.addUser(user);
            //邮件内容
            String content="<h3>尊敬的"+user.getName()+"用户您好,请点击右侧链接激活账号  <a href='http://119.27.179.153:8080/travel/user/active?code="+uuid+"'>珺岚旅游网账号激活</a></h3>";
            //发送邮件给用户
            MailUtils.sendMail(user.getEmail(),content,"邮件激活");
            return true;
        }
        return false;
    }

    @Override
    public boolean active(String code) {
        int number=userDao.findByCode(code);
        if (number==0){
            return false;
        }
        userDao.updateStatus(code);
        return true;
    }

    @Override
    public User login(User user) {
        return userDao.findByUserAndPassword(user);
    }
}
