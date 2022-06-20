package config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

@MapperScan("mapper")
public class MyBatisConfig {
    /**配置PageInterceptor分页插件*/
    @Bean
    public PageInterceptor getPageInterceptor() {
        PageInterceptor pageIntercptor = new PageInterceptor();
        Properties properties = new Properties();
        //开启分页
        properties.setProperty("value", "true");
        pageIntercptor.setProperties(properties);
        return pageIntercptor;
    }
    /*
    定义MyBatis的核心连接工厂bean，
    等同于<bean class="org.mybatis.spring.SqlSessionFactoryBean">
     参数使用自动装配的形式加载dataSource，
    为set注入提供数据源，dataSource来源于JdbcConfig中的配置
     */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Autowired DataSource dataSource,@Autowired PageInterceptor pageIntercptor){
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        Configuration configuration = new Configuration();
        configuration.setLogImpl(Log4j2Impl.class);
        ssfb.setConfiguration(configuration);
        //等同于<property name="dataSource" ref="dataSource"/>
        ssfb.setDataSource(dataSource);
        Interceptor[] plugins={pageIntercptor};
        ssfb.setPlugins(plugins);
        return ssfb;
    }

    /*
    定义MyBatis的映射扫描，
    等同于<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     */
//    @Bean
//    public MapperScannerConfigurer getMapperScannerConfigurer(){
//        MapperScannerConfigurer msc = new MapperScannerConfigurer();
//        //等同于<property name="basePackage" value="com.itheima.dao"/>
//        msc.setBasePackage("mapper");
//        return msc;
//    }

}