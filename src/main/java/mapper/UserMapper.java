package mapper;

import com.github.pagehelper.Page;
import domain.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Select("select * from user where name = #{name} and password = #{password}")
    @Results(id = "userMap", value = {
            @Result(id = true, column = "name", property = "name"),
            @Result(column = "password", property = "password"),
            @Result(column = "role", property = "role"),
            @Result(column = "status", property = "status")
    })

    //用户登录
    User login(User user);


    @Insert("insert into user(name, password, role, status) values(#{name}, #{password}, #{role}, #{status})")
    //新增用户信息
    int addUser(User user);

    @Update("update user set password = #{password}, role = #{role}, status = #{status} where name = #{name}")
    //修改用户信息
    int editUser(User user);

    @Select({"<script>" +
            "SELECT * FROM user " +
            "where 1=1 " +
            "<if test=\"name != null\"> AND name like  CONCAT('%', #{name},'%') </if>" +
            "</script>"
    })
    @ResultMap("userMap")
    //查询用户信息
    Page<User> search(User user);

    @Select("select * from user where name = #{name}")
    @ResultMap("userMap")
    //根据用户名查询用户
    User findUserByName(String name);
}
