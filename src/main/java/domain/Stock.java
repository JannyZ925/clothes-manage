package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Stock {
    private String id;  //单据编号
    private String user_name; //经办人

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;  //日期
    private String strDate;

    private Integer warehouse;  //仓库
    private String wareName;  //仓库名
    private Integer wareCapacity;  //仓库容量
    private String remarks;  //备注
    private String origin_or_whereabouts; //来源或去向
    private String status;  //出入库状态，1表示入库，0表示出库

    private List<StockItem> stockItemList;  //出库单明细集合

    public List<StockItem> getStockItemList() {
        return stockItemList;
    }

    public void setStockItemList(List<StockItem> stockItemList) {
        this.stockItemList = stockItemList;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public Integer getWareCapacity() {
        return wareCapacity;
    }

    public void setWareCapacity(Integer wareCapacity) {
        this.wareCapacity = wareCapacity;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.strDate = dateFormat.format(date);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        if (date != null){
            this.setStrDate(date);
        }
    }

    public Integer getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Integer warehouse) {
        this.warehouse = warehouse;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrigin_or_whereabouts() {
        return origin_or_whereabouts;
    }

    public void setOrigin_or_whereabouts(String origin_or_whereabouts) {
        this.origin_or_whereabouts = origin_or_whereabouts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id='" + id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", date=" + date +
                ", warehouse='" + warehouse + '\'' +
                ", remarks='" + remarks + '\'' +
                ", origin_or_whereabouts='" + origin_or_whereabouts + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
