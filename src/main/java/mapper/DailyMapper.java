package mapper;

import com.github.pagehelper.Page;
import domain.Daily;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface DailyMapper {

    @Insert("insert into daily(username, oldpassword, newpassword,date) values(#{username}, #{oldpassword}, #{newpassword},#{date})")
    boolean addDaily(Daily daily);//添加日志

    @Select("select * from daily ")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "oldpassword",property = "oldpassword"),
            @Result(column = "newpassword",property = "newpassword"),
            @Result(column = "date",property = "date")
    })
    Page<Daily> findAll();//查看日志
}
