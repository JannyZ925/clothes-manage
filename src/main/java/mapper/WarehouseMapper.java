package mapper;

import com.github.pagehelper.Page;
import domain.Product;
import domain.Warehouse;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface WarehouseMapper {

    @Select("select * from warehouse")
    @Results(id = "warehouseMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "capacity",property = "capacity")
    })
    Page<Warehouse> showWarehouse();//分页显示所有仓库

    @Select("select * from warehouse where id=#{id}")
    @ResultMap("warehouseMap")
    Warehouse findNameById(Integer id);//通过id找仓库名

    @Select("select * from warehouse")
    @ResultMap("warehouseMap")
    List<Warehouse> findAll();//返回所有仓库信息

    boolean addWarehouse(Warehouse warehouse);//添加仓库

    boolean editWarehouse(Warehouse warehouse);//修改仓库


    @Select("select distinct product.* from stock,stock_item,product " +
            "where stock.id=stock_item.stock_id AND stock_item.product_id=product.id " +
            "AND stock.warehouse=#{id}")
    List<Product> showWarehouseProduct(Integer id);//分页显示仓库所有货品

}
