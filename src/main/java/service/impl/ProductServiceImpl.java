package service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import domain.Product;
import mapper.ProductMapper;
import org.apache.ibatis.annotations.Select;
import service.ProductService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public Product findProductById(Integer id) {
        return productMapper.findProductById(id);
    }

    @Override
    public Integer findProductStockById(Integer id){
        return productMapper.findProductStockById(id);
    }

    @Override
    public Product findByIdAndNum(Integer id, String number) {
        return productMapper.findByIdAndNum(id, number);
    }

    @Override
    public List<Product> findByNumber(String number) {
        return productMapper.findByNumber(number);
    }

    @Override
    public PageResult search(Product product, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Product> page=productMapper.searchProducts(product);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Product> findAllStock(Integer id) {
        return productMapper.findAllStock(id);
    }

    @Override
    public PageResult findByWarehouse(Integer id, Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Product> page=productMapper.findByWarehouse(id);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public boolean addProduct(Product product) {
        return productMapper.addProduct(product);
    }

    @Override
    public boolean editProduct(Product product) {
        return productMapper.editProduct(product);
    }

    @Override
    public boolean deleteProduct(Integer id) {
        return productMapper.deleteProduct(id);
    }
}
