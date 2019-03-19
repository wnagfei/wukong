package com.wukong.testFreemaker;

import freemarker.template.Configuration;

public class TestFarker {

	public void test(){
		//创建模板文件
		//创建一个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//设置模板所在的路径
		
		//设置模板字符集
		//使用Configuration对象加载一个模板文件。需要指定模板的文件名
		//创建一个数据集，可以使pojo也可以是map
		//创建一个writer对象，指定输出文件的路径及文件名
		//使用模板对象的process方法输出文件
		//关闭流
	}
	
}
