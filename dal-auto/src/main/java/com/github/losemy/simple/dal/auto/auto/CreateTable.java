package com.github.losemy.simple.dal.auto.auto;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: utils
 * Created by lose on 19-4-5 下午3:39
 */
public class CreateTable {

    public static void main(String[] args){


        String currentDir = System.getProperty("user.dir") + File.separator + "dal-auto";
        String name = "/docs/datas/simple-sql.xlsx";
//        String name = "/docs/datas/test.xlsx";
        List<TableInfo> tables = ReadFromExcelUtil.readFile(name);

        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("mysql.btl");

        // map指定初始化大小 可以避免扩容 默认16
        Map<String,Object> context = new HashMap<>();
        context.put("tables",tables);
        // db名称 需要额外指定
        context.put("db","simple");
        String result = template.render(context);

        System.out.println(result);

    }
}
