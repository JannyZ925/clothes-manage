package service;

import domain.Stock;
import entity.PageResult;

import java.util.List;

public interface StockService {
    /*
        出库管理
     */
    //查询出库信息
    PageResult searchStockOut(Stock stock, Integer pageNum, Integer pageSize);

    //根据id查询出库单信息
    Stock findStockOutById(String id);

    //添加出库信息
    int addStockOut(Stock stock);

    //修改出库单信息
    int editStockOut(Stock stock);

    //根据出库单单号查询明细信息
    Stock findStockItemsByStockId(String id);


    /*
        入库管理
     */
    //通过单号找出入库单
    Stock findById(String id);

    //分页查询入库单
    PageResult searchCome(Stock stock, Integer pageNum, Integer pageSize);

    //添加入库单
    boolean addStockIn(Stock stock);

    //修改入库单
    boolean editStockIn(Stock stock);

    //删除入库单
    boolean deleteStockIn(String id);

    //根据单据编号查询入库明细信息
    List<Stock> findStockItemByStockId(String id);

    //通过单据编号找到单据明细列表，明细在找到货品信息
    Stock findStockItemAndProByStockId(String id);
}
