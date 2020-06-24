package com.cht.controller;

import java.util.List;

import com.cht.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cht.entity.TblResident;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/resident")
public class ResidentController {

  @Autowired private ResidentService residentService;

 /* @GetMapping("/index")
  @ResponseBody
  public String hello() {
    return "hello world";
  }*/

  /** 查询全部 */
  @GetMapping("/all")
  @ResponseBody
  public List<TblResident> findAll() {
    return residentService.findAll();
  }

  /** 查询单个 */
  @GetMapping(value = "/one/{id}")
  @ResponseBody
  public TblResident findOne(@PathVariable Long id) {
    return residentService.findOne(id);
  }
  /** 删除单个 */
  @GetMapping(value = "/delete/{id}")
  @ResponseBody
  public String deleteOne(@PathVariable Long id) {
    int i = 0;
    String message = "删除成功！";
    String err_message = "";
    try {
      residentService.deleteOne(id);
      i = 1;

    } catch (Exception e) {

      e.printStackTrace();
      i = 2;
      err_message = e.getMessage();
    }
    if (i == 2) {
      message = "删除失败！失败信息：" + err_message;
    }
    return message;
  }
	/** 返回页面 */
    @RequestMapping(value="/index")
    public String index(){
        return "index";
    }

}
