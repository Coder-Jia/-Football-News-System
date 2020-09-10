package com.jy.infonet.config;

import com.jy.infonet.interceptor.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * 服务配置类
 */
@Configuration
@EnableScheduling//启动定时任务
public class ServerConfig {

    @Value("${upload.folder.cover}")
    private String UPLOADED_FOLDER_COVER ;//上传图片文件地址
    @Value("${upload.folder.content}")
    private String UPLOADED_FOLDER_CONTENT ;//上传资讯正文地址
    @Value("${upload.folder.type}")
    private String TYPE_COVER_FOLDER;//上传类别封面地址


    @Bean//mvc配置
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/login").setViewName("view/login");
                registry.addViewController("/register").setViewName("view/register");
                registry.addViewController("/homePage").setViewName("view/homePage");
                registry.addViewController("/reg.html").setViewName("view/register");
                registry.addViewController("/add").setViewName("mana/manageNews_add");
                registry.addViewController("/login_mana").setViewName("mana/loginMana");
            }

            @Override//配置拦截器
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginPageInteceptor()).addPathPatterns(Arrays.asList("/login","/register"));
                registry.addInterceptor(new HomeInteceptor()).addPathPatterns("/homePage");
                registry.addInterceptor(new ListPageInterceptor()).addPathPatterns("/view/list");
                registry.addInterceptor(new ManaPageInterceptor()).addPathPatterns(Arrays.asList("/add","/del","/update","/news","/types","/newsById"));
                registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns(Arrays.asList("/commentLike","/comments","/reply","/likeNews"));
            }

            @Override//添加静态资源路径
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/content/**").addResourceLocations("file:"+UPLOADED_FOLDER_CONTENT);
                registry.addResourceHandler("/cover/**").addResourceLocations("file:"+UPLOADED_FOLDER_COVER);
                registry.addResourceHandler("/images/**").addResourceLocations("file:"+TYPE_COVER_FOLDER);
            }
        };
    }

    @Bean//定时任务配置
    public SchedulingConfigurer schedulingConfigurer(){
        return new SchedulingConfigurer() {
            @Override
            public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
                final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
                taskScheduler.setPoolSize(4);//4个定时任务
                taskScheduler.initialize();
                scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
            }
        };
    }

}
