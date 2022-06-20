package service;

import domain.Warehouse;
import entity.PageResult;

import java.util.List;

public interface WarehouseService {


    Warehouse findNameById(Integer id);//通过id找仓库名

    PageResult showWarehouse(Integer pageNum, Integer pageSize);//分页显示所有仓库

    List<Warehouse> findAll();//返回所有仓库信息

    boolean addWarehouse(Warehouse warehouse);//添加仓库

    boolean editWarehouse(Warehouse warehouse);//修改仓库
}
