package controller;


import domain.Warehouse;
import service.WarehouseService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*
仓库信息Controller
 */
@Controller
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    @ResponseBody
    @RequestMapping("/findById")
    public Result<Warehouse> findById(Integer id) {
        try {
            Warehouse warehouse=warehouseService.findNameById(id);
            if(warehouse==null){
                return new Result(false,"查询仓库失败！");
            }
            return new Result(true,"查询仓库成功",warehouse);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"查询仓库失败！");
        }
    }


    /**
     * 分页查询符合条件的货品信息
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/ShowWarehouse")
    public ModelAndView ShowWarehouse(Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        //查询到的货品信息
        PageResult pageResult = warehouseService.showWarehouse(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("warehouses");
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
    @RequestMapping(value = "/addWarehouse",produces ="text/html;charset=UTF-8")
    public Result addWarehouse(Warehouse warehouse) {
        try {
            if(warehouseService.addWarehouse(warehouse)){
                return new Result(true, "新增仓库成功!");
            }
            else
                return new Result(false, "新增仓库失败!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "新增仓库失败!");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editWarehouse",produces ="text/html;charset=UTF-8")
    public Result editWarehouse(Warehouse warehouse) {
        try {
            if(warehouseService.editWarehouse(warehouse)){
                return new Result(true, "修改仓库成功!");
            }
            else
                return new Result(false, "修改仓库失败!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "修改仓库失败!");
        }
    }


}
