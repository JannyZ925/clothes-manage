package controller;

import domain.Product;
import service.ProductService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
货品信息Controller
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    //注入ProductService对象
    @Autowired
    private ProductService productService;

    @ResponseBody
    @RequestMapping("/findById")
    public Result<Product> findById(Integer id) {
        try {
            Product product = productService.findProductById(id);
            if (product == null) {
                return new Result(false, "查询货品失败！");
            }
            return new Result(true, "查询货品成功", product);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询货品失败！");
        }
    }

    @RequestMapping("/findProductStockById")
    @ResponseBody
    public Integer findProductStockById(Integer id){
        return productService.findProductStockById(id);
    }


    @RequestMapping("/findByNumber")
    public ModelAndView ShowProductByNumber(String number) {//通过货号找货品
        ModelAndView modelAndView = new ModelAndView();
        List<Product> productList = productService.findByNumber(number);
        modelAndView.addObject("productList", productList);
        modelAndView.setViewName("showTest");
        return modelAndView;
    }

    /**
     * 分页查询符合条件的货品信息
     *
     * @param product  查询的条件封装到product中
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/search")
    public ModelAndView search(Product product, String model, String stock_id, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        if (model == null) {
            model = "productManage";
        }
        //查询到的货品信息
        PageResult pageResult = productService.search(product, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products");
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框中
        modelAndView.addObject("search", product);
        modelAndView.addObject("model", model);
        modelAndView.addObject("stock_id", stock_id);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/addProduct")
    public Result addProduct(Product product) {
        try {
            if (productService.addProduct(product)) {
                return new Result(true, "新增货品成功!");
            } else
                return new Result(false, "新增货品失败!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增货品失败!");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/editProduct")
    public Result editProduct(Product product) {
        try {
            if (productService.editProduct(product)) {
                return new Result(true, "编辑成功!");
            } else
                return new Result(false, "编辑失败!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "编辑失败!");
        }
    }


    @ResponseBody
    @RequestMapping("/deleteProduct")
    public Result deleteProduct(Integer id) {
        try {
            if (productService.deleteProduct(id)) {
                return new Result(true, "删除成功!");
            } else
                return new Result(false, "删除失败!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }


    @ResponseBody
    @RequestMapping("/findAllStock")
    public Result<Integer> findAllStock(Integer id) {
        try {
            int sum=0;
            List<Product> products=productService.findAllStock(id);
            if(products==null){
                return new Result(true,"查询货品成功！",sum);
            }
            else{
                for(int i=0;i<products.size();i++){
                    sum=products.get(i).getStock()+sum;
                }
                return new Result(true,"查询货品成功",sum);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"查询货品失败！");
        }
    }

    /**
     * 分页查询符合条件的货品信息
     * @param id 仓库号
     * @param pageNum  数据列表的当前页码
     * @param pageSize 数据列表1页展示多少条数据
     */
    @RequestMapping("/findByWarehouse")
    public ModelAndView findByWarehouse(Integer id,String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //查询到的货品信息
        PageResult pageResult = productService.findByWarehouse(id, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products");
        //将查询到的数据存放在 ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框中
        modelAndView.addObject("model", "showWarePro");
        modelAndView.addObject("wareName", name);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }
}
