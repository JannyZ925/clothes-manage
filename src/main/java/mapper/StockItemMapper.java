package mapper;

import com.github.pagehelper.Page;
import domain.Product;
import domain.StockItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StockItemMapper {
    /*
        出库明细
     */
    //根据出库单单号查找明细
    @Select("select * from stock_item where stock_id = #{stock_id}")
    @Results({
            @Result(id = true , column = "id" , property = "id"),
            @Result(column = "stock_id", property = "stock_id"),
            @Result(column = "product_id", property = "product_id"),
            @Result(column = "amount", property = "amount"),
            @Result(column = "product_id", property = "product",
            one = @One(select = "mapper.ProductMapper.findProductById"))
    })
    Page<StockItem> findStockItemsByStockId(String stockId);

    //添加出库单明细
    @Insert("insert into stock_item(stock_id, product_id, amount) values(#{stock_id}," +
            "#{product_id}, #{amount})")
    int addStockItem(StockItem stockItem);





    /*
        入库明细
     */
    @Select("select * from stock_item where stock_id=#{stock_id}")
    List<StockItem> selectStockItemByStockId(String stock_id);//通过单据编号找明细

    @Select("select * from stock_item where stock_id=#{stock_id}")
    @Results(id = "stock_itemMap",value = {
            @Result(id = true, column = "stock_id", property = "stock_id"),
            @Result(column = "product_id", property = "product_id"),
            @Result(column = "amount", property = "amount"),
            @Result(
                    property = "product",
                    column = "product_id",//根据这个字段找货品
                    javaType = Product.class,
                    one = @One(select = "mapper.ProductMapper.findProductById")
            )
    })
    Page<StockItem> findStockItemAndProByStockId(String stock_id);

    @Select("select * from stock_item where stock_id=#{stock_id}")
    @Results({
            @Result(id = true, column = "stock_id", property = "stock_id"),
            @Result(id = true, column = "product_id", property = "product_id"),
            @Result(column = "amount", property = "amount"),
            @Result(
                    property = "product",
                    column = "product_id",
                    javaType = Product.class,
                    one = @One(select = "mapper.ProductMapper.findByIdAndNum")
            )
    })
    Page<StockItem> searchStock_items(String number,String stock_id);//分页查询明细


    boolean addStock_item(StockItem stock_item);//新增明细

}
