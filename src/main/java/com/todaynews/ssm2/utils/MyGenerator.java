package com.todaynews.ssm2.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: LiBo
 * @Date: 2018/4/21下午 04:56
 */
public class MyGenerator {

    public void generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true; //指向逆向工程配置文件
        File configFile = new File("src/generatorConfig.xml");

        System.out.println(configFile.getAbsoluteFile());

        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator =
                new MyBatisGenerator(config, callback, warnings);

        myBatisGenerator.generate(null);
    }

    /*public static void main(String[] args) throws Exception {
        try {
            MyGenerator generatorSqlMap = new MyGenerator();
            generatorSqlMap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

}
