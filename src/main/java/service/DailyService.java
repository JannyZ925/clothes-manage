package service;

import domain.Daily;
import entity.PageResult;

public interface DailyService {

    boolean addDaily(Daily daily);//添加日志

    PageResult findAll(Integer pageNum, Integer pageSize);//查看日志
}
