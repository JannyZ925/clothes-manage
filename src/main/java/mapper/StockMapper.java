package mapper;

import com.github.pagehelper.Page;
import domain.Stock;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StockMapper {
    /*
        出库管理
     */
    @Select({"<script>" +
            "SELECT stock.*, warehouse.name FROM stock, warehouse " +
            "where stock.warehouse = warehouse.id and status = '0' " +
            "<if test=\"id != null and id != ''\"> AND stock.id like CONCAT('%', #{id},'%') </if>" +
            "<if test=\"user_name != null and user_name != ''\"> AND user_name like CONCAT('%', #{user_name},'%') </if>" +
            "<if test=\"date != null\"> AND date like CONCAT('%', #{date},'%') </if>" +
            "<if test=\"warehouse != null and warehouse != ''\"> AND warehouse like CONCAT('%', #{warehouse},'%') </if>" +
            "</script>"
    })
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_name", property = "user_name"),
            @Result(column = "date", property = "date"),
            @Result(column = "warehouse", property = "warehouse"),
            @Result(column = "remarks", property = "remarks"),
            @Result(column = "origin_or_whereabouts", property = "origin_or_whereabouts"),
            @Result(column = "status", property = "status"),
            @Result(column = "name", property = "wareName")
    })
    //查询出库信息
    Page<Stock> searchStockOut(Stock stock);


    @Select("select * from stock where id = #{id}")
    //根据id查询出库单信息
    Stock findStockOutById(String id);


    @Select("select * from stock where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_name", property = "user_name"),
            @Result(column = "date", property = "date"),
            @Result(column = "warehouse", property = "warehouse"),
            @Result(column = "remarks", property = "remarks"),
            @Result(column = "origin_or_whereabouts", property = "origin_or_whereabouts"),
            @Result(column = "status", property = "status"),
            @Result(column = "id", property = "stockItemList",
                    many = @Many(select = "mapper.StockItemMapper.findStockItemsByStockId"))
    })
    //根据出库单单号查询出库单明细
    Stock findStockItemsByStockId(String id);


    @Insert("insert into stock values(#{id}, #{user_name}, #{date}, #{warehouse}, #{remarks}, " +
            "#{origin_or_whereabouts}, #{status})")
    //添加出库信息
    int addStockOut(Stock stock);


    @Update("update stock set user_name = #{user_name}, date = #{date}, warehouse = #{warehouse}," +
            "remarks = #{remarks}, origin_or_whereabouts = #{origin_or_whereabouts}, status = #{status} " +
            "where id = #{id}")
        //修改出库单信息
    int editStockOut(Stock stock);





    /*
        入库管理
     */
    @Select("select stock.* ,warehouse.capacity from stock,warehouse WHERE stock.warehouse=warehouse.id AND stock.id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "user_name",property = "user_name"),
            @Result(column = "date",property = "date"),
            @Result(column = "warehouse",property = "warehouse"),
            @Result(column = "origin_or_whereabouts",property = "origin_or_whereabouts"),
            @Result(column = "remarks",property = "remarks"),
            @Result(column = "status",property = "status"),
            @Result(column = "capacity",property = "wareCapacity")
    })
    Stock findById(String id);//通过单号找入库单


    @Select({"<script>" +
            "select stock.* ,warehouse.name from stock,warehouse " +
            "where status =1 AND stock.warehouse=warehouse.id " +
            "<if test=\"id != null\"> AND  stock.id  like  CONCAT('%',#{id},'%')</if>" +
            "<if test=\"user_name != null\"> AND user_name like  CONCAT('%', #{user_name},'%') </if>" +
            "<if test=\"date != null\"> AND date like  CONCAT('%', #{date},'%')</if>" +
            "<if test=\"warehouse != null\"> AND stock.warehouse like  CONCAT('%', #{warehouse},'%')</if>" +
            "order by date" +
            "</script>"
    })
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "user_name",property = "user_name"),
            @Result(column = "date",property = "date"),
            @Result(column = "warehouse",property = "warehouse"),
            @Result(column = "origin_or_whereabouts",property = "origin_or_whereabouts"),
            @Result(column = "remarks",property = "remarks"),
            @Result(column = "status",property = "status"),
            @Result(column = "name",property = "wareName")
    })
    Page<Stock> searchComeStocks(Stock stock);//分页入库查询货品

    //添加入库单
    boolean addStockIn(Stock stock);

    //修改入库单
    boolean editStockIn(Stock stock);

    //删除入库单
    boolean deleteStockIn(String id);

    //根据单据编号查询出入库明细信息
    @Select("select * from stock where id=#{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_name", property = "user_name"),
            @Result(column = "date", property = "date"),
            @Result(column = "warehouse",property = "warehouse"),
            @Result(column = "origin_or_whereabouts",property = "origin_or_whereabouts"),
            @Result(column = "remarks",property = "remarks"),
            @Result(column = "status",property = "status"),
            @Result(
                    property = "stockItemList",
                    column = "id",
                    javaType = List.class,
                    many = @Many(select = "com.itheima.mapper.Stock_itemMapper.findStockItemByStockId")
            )
    })
    List<Stock> findStockItemByStockId(String id);

    //通过单据编号找到单据明细列表，明细在找到货品信息
    @Select("select * from stock where id=#{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_name", property = "user_name"),
            @Result(column = "date", property = "date"),
            @Result(column = "warehouse",property = "warehouse"),
            @Result(column = "origin_or_whereabouts",property = "origin_or_whereabouts"),
            @Result(column = "remarks",property = "remarks"),
            @Result(column = "status",property = "status"),
            @Result(
                    property = "stockItemList",
                    column = "id",
                    javaType = List.class,
                    many = @Many(select = "com.itheima.mapper.Stock_itemMapper.findStockItemAndProByStockId")
            )
    })
    Stock findStockItemAndProByStockId(String id);



}
