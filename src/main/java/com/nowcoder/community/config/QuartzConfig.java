package com.nowcoder.community.config;


import com.nowcoder.community.quartz.AlphaJob;
import com.nowcoder.community.quartz.PostScoreRefreshJob;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.text.SimpleDateFormat;

//配置 ->数据库 -> 任务 -> 任务调度
@Configuration
public class QuartzConfig {

    // FactoryBean可简化Bean的实例化过程:
    // 1.通过FactoryBean封装Bean的实例化过程
    // 2.将FactoryBean装配到Spring容器里.
    // 3.将FactoryBean注入给其他的Bean.
    // 4.该Bean得到的是FactoryBean所管理的对象实例.

    // 配置JobDetail
    //@Bean
    public JobDetailFactoryBean alphaJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean .setJobClass(AlphaJob.class);
        factoryBean .setName("alphaJob");
        factoryBean .setGroup("alphaJobGroup");
        // 是否持久保存
        factoryBean .setDurability(true);
        // 是否可恢复
        factoryBean .setRequestsRecovery(true);
        return factoryBean;
    }

    // 配置Trigger(SimpleTriggerFactoryBean(简单的触发), CronTriggerFactoryBean)
    //@Bean
    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        // 关联JobDetail
        factoryBean .setJobDetail(alphaJobDetail);
        // 设置触发器的名字和组名
        factoryBean .setName("alphaTrigger");
        factoryBean .setGroup("alphaTriggerGroup");
        // 设置触发器的时间间隔
        factoryBean .setRepeatInterval(3000);
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }

    // 刷新帖子分数任务
    @Bean
    public JobDetailFactoryBean postScoreRefreshJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        // 关联JobDetail
        factoryBean .setJobClass(PostScoreRefreshJob.class);
        // 设置触发器的名字和组名
        factoryBean .setName("postScoreRefreshJob");
        factoryBean .setGroup("communityJobGroup");
        // 是否持久保存
        factoryBean .setDurability(true);
        // 是否可恢复
        factoryBean .setRequestsRecovery(true);
        return factoryBean;
    }

    // 刷新帖子分数触发器
    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        // 关联JobDetail
        factoryBean .setJobDetail(postScoreRefreshJobDetail);
        // 设置触发器的名字和组名
        factoryBean .setName("postScoreRefreshTrigger");
        factoryBean .setGroup("communityTriggerGroup");
        // 设置触发器的时间间隔
        factoryBean .setRepeatInterval(1000 * 60 * 5);
        factoryBean.setJobDataMap(new JobDataMap());
        return factoryBean;
    }
}
