package com.hb;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 *
 * @ClassName: CodeGeneration
 * @Description: 代码生成器
 * @author lirc
 * @date 2019年07月08日 下午2:55:14
 */
public class AutoCode {

	/**
	 *
	 * @Title: main
	 * @Description: 生成
	 * @param args
	 */
	public static void main(String[] args) {
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir("D://project//logisticsPlan//src//main//java");// 输出文件路径
		gc.setFileOverride(true);
		gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor("lirc");// 作者

		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		// gc.setControllerName("%sController");
		gc.setServiceName("I%sService");
		gc.setServiceImplName("%sServiceImpl");
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sMapper");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("123456");
		dsc.setUrl("jdbc:mysql://localhost/logistics-plus");
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		// strategy.setTablePrefix(new String[] { "hb_" });// 此处可以修改为您的表前缀

		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setInclude(new String[] { "dormitory_dorm_stu_change" }); // 需要生成的表

		strategy.setSuperServiceClass(null);
		strategy.setSuperServiceImplClass(null);
		strategy.setSuperMapperClass(null);

		mpg.setStrategy(strategy);

		// 包配置
		StringBuffer names = new StringBuffer();
		for (int i = 0; i < strategy.getInclude()[0].split("_").length; i++) {
			if (i != 0) {
				names.append(strategy.getInclude()[0].split("_")[i]);
			}
		}
		PackageConfig pc = new PackageConfig();
		pc.setParent("");
		pc.setController("com.hb.controller");
		pc.setService("com.hb.service");
		pc.setServiceImpl("com.hb.service.impl");
		pc.setMapper("com.hb.mapper");
		pc.setEntity("com.hb.entity");
		pc.setXml("com.hb.mapper.xml");
		mpg.setPackageInfo(pc);

		// 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】 ${cfg.abc}
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
				this.setMap(map);
			}
		};

		TemplateConfig tc = new TemplateConfig();
		tc.setController("/templateMybatis/controller.java.vm");
		mpg.setTemplate(tc);

		// 执行生成
		mpg.execute();

		// 打印注入设置【可无】
		System.err.println(mpg.getCfg().getMap().get("abc"));
	}

}