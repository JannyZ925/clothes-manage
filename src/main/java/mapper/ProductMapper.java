package mapper;

import com.github.pagehelper.Page;
import domain.Product;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper {
    //通过id查找货品
    @Select("select * from product where id = #{id}")
    Product findProductById(Integer id);

    //查找货品库存
    @Select("select stock from product where id = #{id}")
    Integer findProductStockById(Integer id);


    @Select({"<script>" +
            "select * from product where id=#{id} "+
            "<if test=\"number != null\"> AND  number  like  CONCAT('%',#{number},'%')</if>" +
            "</script>"
    })
    @Results(id = "productMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "number",property = "number"),
            @Result(column = "name",property = "name"),
            @Result(column = "color",property = "color"),
            @Result(column = "size",property = "size"),
            @Result(column = "stock",property = "stock")
    })
    Product findByIdAndNum(Integer id,String number);//通过id和货号找


    @Select("select * from product where number=#{number}")
    @ResultMap("productMap")
    List<Product> findByNumber(String number);//根据货号找货品

    @Select({"<script>" +
            "SELECT * FROM product " +
            "where id !=''" +
            "<if test=\"number != null\"> AND number  like  CONCAT('%',#{number},'%')</if>" +
            "<if test=\"name != null\"> AND name like  CONCAT('%', #{name},'%') </if>" +
            "<if test=\"color != null\"> AND color like  CONCAT('%', #{color},'%')</if>" +
            "<if test=\"size != null\"> AND size like  CONCAT('%', #{size},'%')</if>" +
            "order by stock" +
            "</script>"
    })
    @ResultMap("productMap")
    Page<Product> searchProducts(Product product);//分页查询货品

    @Select("select distinct product.* from stock,stock_item,product " +
            "where stock.id=stock_item.stock_id AND stock_item.product_id=product.id " +
            "AND stock.warehouse=#{id}")
    @ResultMap("productMap")
    Page<Product> findByWarehouse(Integer id);//分页查询仓库货品

    @Select("select distinct product.* from stock,stock_item,product " +
            "where stock.id=stock_item.stock_id AND stock_item.product_id=product.id " +
            "AND stock.warehouse=#{id}")
    @ResultMap("productMap")
    List<Product> findAllStock(Integer id);//获取仓库里货品的所有库存总和

    boolean addProduct(Product product);//添加货品

    boolean editProduct(Product product);//修改商品

    boolean deleteProduct(Integer id);//删除商品
}
