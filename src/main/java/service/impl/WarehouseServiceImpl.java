package service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import domain.Warehouse;
import mapper.WarehouseMapper;
import service.WarehouseService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public Warehouse findNameById(Integer id) {
        return warehouseMapper.findNameById(id);
    }

    @Override
    public PageResult showWarehouse(Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Warehouse> page=warehouseMapper.showWarehouse();
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Warehouse> findAll() {
        return warehouseMapper.findAll();
    }

    @Override
    public boolean addWarehouse(Warehouse warehouse) {
        return warehouseMapper.addWarehouse(warehouse);
    }

    @Override
    public boolean editWarehouse(Warehouse warehouse) {
        return warehouseMapper.editWarehouse(warehouse);
    }
}
