package controller;

import domain.Product;
import domain.StockItem;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.ProductService;
import service.StockItemService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/stockItem")
public class StockItemController{

    @Autowired
    private StockItemService stockItemService;
    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping("/addStockOutItem")
    public String addStockOutItem(StockItem stockItem){
        String message;
        Product product = productService.findProductById(stockItem.getProduct_id());
        if(product.getStock() >= stockItem.getAmount()){
            product.setStock(product.getStock() - stockItem.getAmount());
            if(productService.editProduct(product) && stockItemService.addStockItem(stockItem) != 0){
                    message = "添加成功！";
            }else{
                message = "添加失败！";
            }
        }else {
            message = "库存不足！";
        }
        return message;
    }


    @RequestMapping("/findStockItemsByStockId")
    public ModelAndView findStockItemsByStockId(String stock_id, Integer pageNum, Integer pageSize, HttpServletRequest request){
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageResult pageResult = stockItemService.findStockItemsByStockId(stock_id, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stock_item");
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框中
        modelAndView.addObject("stock_id", stock_id);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        System.out.println(request.getRequestURI());
        return modelAndView;
    }






    /*
        入库单明细
     */
    @ResponseBody
    @RequestMapping("/findByStockId")
    public Result<StockItem> findStockItemByStockId(String id) {
        try {
            List<StockItem> stock_itemList=stockItemService.selectStockItemByStockId(id);
            if(stock_itemList==null){
                return new Result(false,"查询单据明细失败！");
            }
            return new Result(true,"查询单据明细成功",stock_itemList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"查询单据明细失败！");
        }
    }

    /**
     * 分页查询符合条件的单据明细信息
     * @param stock_id  单据编号
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/searchStock_item")
    public ModelAndView searchStock_item(String stock_id, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //查询到的图书信息
        PageResult pageResult = stockItemService.findStockItemAndProByStockId(stock_id, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stock_items");
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框中
        modelAndView.addObject("stock_id", stock_id);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/addStock_item")
    public Result addStock_item(StockItem stock_item) {
        try {
            //给货品存款加上入库数量
            Product product=productService.findProductById(stock_item.getProduct_id());
            int previous=product.getStock();//获取之前的库存
            product.setStock(previous+stock_item.getAmount());
            if(productService.editProduct(product)) {//修改货品
                if(stockItemService.addStock_item(stock_item)){
                    return new Result(true, "新增成功!");
                }
                return new Result(false, "新增失败!");
            }
            return new Result(false, "新增失败!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "新增失败!");
        }
    }

}
