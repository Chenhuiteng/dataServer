package com.cht.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cht.service.ResidentService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cht.entity.TblResident;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

  /**
   * 检查登录的用户名和密码
   * @return
   */
  @RequestMapping(value="/checkLogin")
  @ResponseBody
  public Map checkLogin(HttpServletRequest request, HttpServletResponse response){
     Map<String,Object> map=new HashMap<>();
     //传参数封装
    Map<String,Object> Params=new HashMap<>();
    String userName=request.getParameter("userName");
    String passWord=request.getParameter("passWord");
    Params.put("userName",userName);
    Params.put("passWord",passWord);
    map.put("code",1);
    map.put("message","登录成功");
    List<TblResident> list=residentService.listByParams(Params);
    //根据用户名密码查询用户是否存在
   if(list.size()==0){
     map.put("code",-1);
     map.put("message","登录失败！用户名或者密码错误！");
   }
    return map;
  }

}
