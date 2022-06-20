package service;

import domain.Product;
import entity.PageResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductService {
    //根据id查询货品
    Product findProductById(Integer id);

    //查找货品库存
    Integer findProductStockById(Integer id);


    //通过id和货号找
    Product findByIdAndNum(Integer id, String number);//通过id和货号找

    //根据货号查询货品
    List<Product> findByNumber(String number);

    //分页查询货品
    PageResult search(Product product, Integer pageNum, Integer pageSize);

    //获取仓库里货品的所有库存总和
    List<Product> findAllStock(Integer id);

    //分页查询仓库货品
    PageResult findByWarehouse(Integer id, Integer pageNum, Integer pageSize);

    //添加货品
    boolean addProduct(Product product);

    //修改货品
    boolean editProduct(Product product);

    //删除货品
    boolean deleteProduct(Integer id);
}
