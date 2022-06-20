package controller;


import domain.Daily;
import service.DailyService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/*
日志信息Controller
 */
@Controller
@RequestMapping("/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;

    /**
     * 分页查询符合条件的货品信息
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/findAll")
    public ModelAndView search( Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //查询到的货品信息
        PageResult pageResult = dailyService.findAll( pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("daily");
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框中

        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/addDaily",produces ="text/html;charset=UTF-8")
    public Result<Daily> addDaily(String username,String oldpassword,String newpassword) {
        try {
            Daily daily=new Daily();
            //获取系统时间
            String date=null;
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date=df.format(System.currentTimeMillis());
            //给daily赋值
            daily.setUsername(username);
            daily.setOldpassword(oldpassword);
            daily.setNewpassword(newpassword);
            daily.setDate(date);
            if(dailyService.addDaily(daily)){
                return new Result(true, "新增日志成功!",daily);
            }
            else
                return new Result(false, "新增日志失败!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "新增日志失败!");
        }
    }

}
