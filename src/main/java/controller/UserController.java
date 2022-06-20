package controller;

import domain.User;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
    // 注入UserService
    @Autowired
    private UserService userService;

    /*
        用户登录
     */
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        try {
            User u = userService.login(user);
            if (u != null) {
                request.getSession().setAttribute("USER_SESSION", u);
                if ("SUPER ADMIN".equals(u.getRole())) {
                    return "redirect:/user/search";
                }
                return "redirect:/admin/main.jsp";
            }
            request.setAttribute("msg", "账号密码错误");
            return "forward:/admin/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "系统错误");
            return "forward:/admin/login.jsp";
        }
    }


    /*
        注销登录
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            //销毁session
            session.invalidate();
            return "forward:/admin/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "系统错误");
            return "forward:/admin/login.jsp";
        }
    }

    /*
        新增用户信息
     */
    @ResponseBody
    @RequestMapping(value = "addUser", produces = "text/html;charset=utf-8")
    public String addUser(User user) {
        String message;
        try {
            if (userService.addUser(user) != 0) message = "添加成功!";
            else message = "添加失败!";
        } catch (Exception e) {
            e.printStackTrace();
            message = "添加失败!";
        }
        return message;
    }


    /*
        编辑用户信息
     */
    @ResponseBody
    @RequestMapping(value = "/editUser", produces = "text/html;charset=utf-8")
    public String editUser(User user) {
        String message;
        try {
            if (userService.editUser(user) != 0) message = "修改成功!";
            else message = "修改失败!";
        } catch (Exception e) {
            e.printStackTrace();
            message = "修改失败!";
        }
        return message;
    }

    /*
        查询用户
     */
    @RequestMapping("/search")
    public ModelAndView search(User user, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult pageResult = userService.search(user, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框中
        modelAndView.addObject("search", user);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }


    /*
        根据用户名查询用户
     */
    @ResponseBody
    @RequestMapping("/findUserByName")
    public User findUserByName(String name) { return userService.findUserByName(name);}

}
