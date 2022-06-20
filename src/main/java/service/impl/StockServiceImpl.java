package service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import domain.Stock;
import entity.PageResult;
import mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.StockService;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper stockMapper;

    /*
        出库管理
     */
    //查询出库信息
    @Override
    public PageResult searchStockOut(Stock stock, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Stock> page = stockMapper.searchStockOut(stock);
        return new PageResult(page.getTotal(),page.getResult());
    }


    //根据单号查询出库单信息
    @Override
    public Stock findStockOutById(String id) {
        return stockMapper.findStockOutById(id);
    }

    //添加出库信息
    @Override
    public int addStockOut(Stock stock) {
        return stockMapper.addStockOut(stock);
    }


    //编辑出库单信息
    @Override
    public int editStockOut(Stock stock) {
        return stockMapper.editStockOut(stock);
    }


    //根据出库单单号查找明细信息
    @Override
    public Stock findStockItemsByStockId(String id) {
        return stockMapper.findStockItemsByStockId(id);
    }





    /*
        入库管理
     */
    @Override
    public Stock findById(String id) {
        return stockMapper.findById(id);
    }

    @Override
    public PageResult searchCome(Stock stock, Integer pageNum, Integer pageSize) {//分页查询入库
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Stock> page=stockMapper.searchComeStocks(stock);
        return new PageResult(page.getTotal(),page.getResult());
    }


    @Override
    public boolean addStockIn(Stock stock) {
        return stockMapper.addStockIn(stock);
    }

    @Override
    public boolean editStockIn(Stock stock) {
        return stockMapper.editStockIn(stock);
    }

    @Override
    public boolean deleteStockIn(String id) {
        return stockMapper.deleteStockIn(id);
    }

    @Override
    public List<Stock> findStockItemByStockId(String id) {
        return stockMapper.findStockItemByStockId(id);
    }

    @Override
    public Stock findStockItemAndProByStockId(String id) {
        return stockMapper.findStockItemAndProByStockId(id);
    }


}
