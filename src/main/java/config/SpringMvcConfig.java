package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
//等同于<context:component-scan base-package="com.itheima.controller"/>
@ComponentScan({"controller"})
/*@Import({MyWebMvcConfig.class})*/
@EnableWebMvc
public class SpringMvcConfig  implements WebMvcConfigurer {

    /*
     *开启对静态资源的访问
     * 类似在Spring MVC的配置文件中设置<mvc:default-servlet-handler/>元素
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // 配置视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/admin/",".jsp");
    }

}




