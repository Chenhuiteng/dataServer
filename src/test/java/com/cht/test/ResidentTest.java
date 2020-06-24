package com.cht.test;

import com.cht.DataServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cht.service.ResidentService;

@RunWith(SpringJUnit4ClassRunner.class) // 指定运行的主类
@SpringBootTest(classes=DataServerApplication.class) // 指定SpringBoot启动类
public class ResidentTest {
	
	@Autowired
	private ResidentService residentService;
	
	@Test
	public void findAll(){
		System.out.println(residentService.findOne(1L));
	}

}
