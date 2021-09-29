package com.company.homemaking.common.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis-Plus代码生成
 * @author 胡东斌
 * @since 2020-05-14
 */
public class MysqlGenerator {

    public static void Generator(String[] tableName) {

        String projectPath = System.getProperty("user.dir");
        String author = System.getProperty("user.name");
        //============================== 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java")
                .setActiveRecord(true)// 是否支持 AR
                .setAuthor(author) //设置作者名字
                .setFileOverride(true) //文件覆盖(全新文件)
                .setIdType(IdType.AUTO)//主键策略
                .setBaseResultMap(true) //SQL 映射文件
                .setBaseColumnList(true)//SQL 片段
//                .setServiceName("%sService")
//                加Bus前缀
                .setEntityName("Bus%s")
                .setMapperName("Bus%sMapper")
                .setXmlName("Bus%sMapper")
                .setServiceName("Bus%sService")
                .setServiceImplName("Bus%sServiceImpl")
                .setControllerName("Bus%sController")
                .setOpen(false);
        //============================== 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MARIADB)
                .setUrl("jdbc:mysql://47.94.139.210:63306/homemaking")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("mysql")
//                .setSchemaName("test_table")
                .setPassword("Ceshi@mysql@2020");
        //==============================包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.company.homemaking")//配置父包路径
                .setModuleName("business")//配置业务包路径
                .setMapper("dao")
                .setEntity("entity")
                .setService("service")
                .setController("controller")
                .setServiceImpl("service.impl"); //会自动生成 impl，可以不设定
        //============================== 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        //============================== 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)//设置命名规则  underline_to_camel 底线变驼峰
                .setColumnNaming(NamingStrategy.underline_to_camel)//设置设置列命名  underline_to_camel 底线变驼峰
                .setTablePrefix("tb_")
                .setEntityLombokModel(true)//是否加入lombok
                .setInclude(tableName)//设置表名
                .setControllerMappingHyphenStyle(true);//设置controller映射联字符
        //============================== 生成配置
        AutoGenerator mpg = new AutoGenerator();
        mpg.setCfg(cfg)
                .setTemplate(new TemplateConfig().setXml(null))
                .setGlobalConfig(gc)
                .setDataSource(dsc)
                .setPackageInfo(pc)
                .setStrategy(strategy)
                // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
                .setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
    public static void main(String[] args) {
//        Generator(new String[]{"tb_contract","tb_coupon","tb_customer","tb_customer_address","tb_customer_ext_info","tb_fee","tb_inquiry","tb_inquiry_detail","tb_Insurance","tb_order","tb_order_detail","tb_order_feedback","tb_order_log","tb_price","tb_sale_project","tb_sale_project_detail","tb_service_catagory","tb_service_item","tb_sys_log","tb_sys_role","tb_sys_user","tb_sys_user_role_bind","tb_worker","tb_worker_baojie_info"});
        Generator(new String[]{"tb_Insurance","tb_fee"});
//        Generator(new String[]{"tb_contract","tb_coupon","tb_customer","tb_customer_address","tb_customer_ext_info","tb_fee","tb_inquiry","tb_inquiry_detail","tb_Insurance","tb_order","tb_order_detail","tb_order_feedback","tb_order_log","tb_price","tb_sale_project","tb_sale_project_detail","tb_service_catagory","tb_service_item","tb_worker","tb_worker_baojie_info"});
    }
}
