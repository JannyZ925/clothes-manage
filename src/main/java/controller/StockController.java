package controller;

import domain.Stock;
import domain.Warehouse;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.StockService;
import service.WarehouseService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;
    @Autowired
    private WarehouseService warehouseService;

    /*
        出库管理
     */
    /*
        查询出库信息
     */
    @RequestMapping("/searchStockOut")
    public ModelAndView searchStockOut(Stock stock, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult pageResult = stockService.searchStockOut(stock, pageNum, pageSize);
        List<Warehouse> warehouseList = warehouseService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stock_out");
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框中
        modelAndView.addObject("search", stock);
        modelAndView.addObject("warehouseList", warehouseList);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }


    /*
        添加出库信息
     */
    @ResponseBody
    @RequestMapping(value = "/addStockOut", produces = "text/html;charset=utf-8")
    public String addStockOut(Stock stock) {
        stock.setStatus("0");
        String stockId = setStockId();
        while (findStockOutById(stockId) != null) {
            stockId = setStockId();
        }
        stock.setId(stockId);
        String message;
        try {
            if (stockService.addStockOut(stock) != 0) {
                message = "添加成功!";
            } else message = "添加失败!";
        } catch (Exception e) {
            e.printStackTrace();
            message = "添加失败！";
        }
        return message;
    }


    /*
        修改出库单信息
     */
    @ResponseBody
    @RequestMapping(value = "/editStockOut", produces = "text/html;charset=utf-8")
    public String editStockOut(Stock stock) {
        stock.setStatus("0");
        String message;
        try {
            if (stockService.editStockOut(stock) != 0) {
                message = "修改成功!";
            } else message = "修改失败!";
        } catch (Exception e) {
            message = "修改失败！";
            e.printStackTrace();
        }
        return message;
    }



    /*
        根据id查询出库单信息
     */
    @ResponseBody
    @RequestMapping("/findStockOutById")
    public Stock findStockOutById(String id) {
        return stockService.findStockOutById(id);
    }


    //随机生成单据编号(年、月、日+四位随机数)
    String setStockId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String strDate = dateFormat.format(new Date());
        String stockId = strDate + String.format("%04d", new Random().nextInt(9999));
        return stockId;
    }







    /*
        入库管理
     */
    @ResponseBody
    @RequestMapping("/findById")
    public Result<Stock> findById(String id) {
        try {
            Stock stock=stockService.findById(id);
            if(stock==null){
                return new Result(false,"查询单据失败！");
            }
            return new Result(true,"查询单据成功",stock);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"查询单据失败！");
        }
    }

    /**
     * 分页查询符合条件的入库单信息
     * @param stock 查询的条件封装到stock中
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/searchCome")
    public ModelAndView searchCome(Stock stock, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult pageResult = stockService.searchCome(stock, pageNum, pageSize);
        List<Warehouse> warehouseList=warehouseService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("come_stocks");
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("warehouseList", warehouseList);
        //将查询的参数返回到页面，用于回显到查询的输入框中
        modelAndView.addObject("search", stock);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/addStockIn")
    public Result addStockIn(Stock stock) {
        stock.setStatus("1");
        String stockId = setStockId();
        while (stockService.findById(stockId) != null) {
            stockId = setStockId();
        }
        stock.setId(stockId);
        try {
            if(stockService.addStockIn(stock)){
                return new Result(true, "新增成功!");
            }
            else
                return new Result(false, "新增失败!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "新增失败!");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editStockIn")
    public Result editStockIn(Stock stock) {
        try {
            if(stockService.editStockIn(stock)){
                return new Result(true, "编辑成功!");
            }
            else
                return new Result(false, "编辑失败!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "编辑失败!");
        }
    }
    @ResponseBody
    @RequestMapping(value = "/deleteStockIn",produces ="text/html;charset=UTF-8")
    public Result deleteStock(String id) {
        try {
            if(stockService.deleteStockIn(id)){
                return new Result(true, "删除成功!");
            }
            else
                return new Result(false, "删除失败!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/findStockItemByStockId",produces ="text/html;charset=UTF-8")
    public Result<Stock> findStockItemByStockId(String id) {
        try {
            List<Stock> stockList=stockService.findStockItemByStockId(id);
            if(stockList==null){
                return new Result(false,"查询单据明细失败！");
            }
            return new Result(true,"查询单据明细成功",stockList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"查询单据明细失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/findStockItemAndProByStockId",produces ="text/html;charset=UTF-8")
    public Result<Stock> findStockItemAndProByStockId(String id) {
        try {
            Stock stock=stockService.findStockItemAndProByStockId(id);
            if(stock==null){
                System.out.println("查询失败");
                return new Result(false,"查询单据明细失败！");
            }
            System.out.println(stock.toString());
            return new Result(true,"查询单据明细成功",stock);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("查询失败");
            return new Result(false,"查询单据明细失败！");
        }
    }

}
