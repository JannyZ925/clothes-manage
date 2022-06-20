package service;

import domain.StockItem;
import entity.PageResult;

import java.util.List;

public interface StockItemService {
    //添加出库单明细
    int addStockItem(StockItem stockItem);

    //查找出库单明细
    PageResult findStockItemsByStockId(String id, Integer pageNum, Integer pageSize);



    /*
        入库单明细
     */
    List<StockItem> selectStockItemByStockId(String stock_id);//通过单据编号找明细

    List<StockItem> findStockItemAndProByStockId(String stock_id);

    //分页找单据明细
    PageResult findStockItemAndProByStockId(String stock_id, Integer pageNum, Integer pageSize);


    //分页查询单据明细
    PageResult searchStock_items(String number,String stock_id, Integer pageNum, Integer pageSize);

    boolean addStock_item(StockItem stock_item);//新增明细
}
