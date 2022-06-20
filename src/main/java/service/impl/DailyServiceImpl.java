package service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import domain.Daily;
import mapper.DailyMapper;
import service.DailyService;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DailyServiceImpl implements DailyService {

    @Autowired
    private DailyMapper dailyMapper;

    @Override
    public boolean addDaily(Daily daily) {
        return dailyMapper.addDaily(daily);
    }

    @Override
    public PageResult findAll(Integer pageNum, Integer pageSize) {
        // 设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Daily> page=dailyMapper.findAll();
        return new PageResult(page.getTotal(),page.getResult());
    }
}
