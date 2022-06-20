package service;

import domain.User;
import entity.PageResult;

import java.util.List;

public interface UserService {
    //通过User的用户名和密码查询用户信息
    User login(User user);

    //新增用户信息
    int addUser(User user);

    //修改用户信息
    int editUser(User user);

    //查询用户信息
    PageResult search(User user, Integer pageNum, Integer pageSize);

    //根据用户名查询用户
    User findUserByName(String name);

}
