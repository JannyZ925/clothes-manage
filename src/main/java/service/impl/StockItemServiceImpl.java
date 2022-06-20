package service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import domain.StockItem;
import entity.PageResult;
import mapper.StockItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.StockItemService;

import java.util.List;

@Service
public class StockItemServiceImpl implements StockItemService {
    @Autowired
    private StockItemMapper stockItemMapper;

    //添加出库单明细
    @Override
    public int addStockItem(StockItem stockItem) {
        return stockItemMapper.addStockItem(stockItem);
    }

    //查询出库单明细
    @Override
    public PageResult findStockItemsByStockId(String id, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<StockItem> page = stockItemMapper.findStockItemsByStockId(id);
        return new PageResult(page.getTotal(), page.getResult());
    }


    /*
        入库管理
     */
    @Override
    public List<StockItem> selectStockItemByStockId(String stock_id) {
        return stockItemMapper.selectStockItemByStockId(stock_id);
    }

    @Override
    public List<StockItem> findStockItemAndProByStockId(String stock_id) {
        return stockItemMapper.findStockItemAndProByStockId(stock_id);
    }

    @Override
    public PageResult findStockItemAndProByStockId(String stock_id, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<StockItem> page = stockItemMapper.findStockItemAndProByStockId(stock_id);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public PageResult searchStock_items(String number, String stock_id, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<StockItem> page = stockItemMapper.searchStock_items(number, stock_id);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public boolean addStock_item(StockItem stock_item) {
        return stockItemMapper.addStock_item(stock_item);
    }

}
