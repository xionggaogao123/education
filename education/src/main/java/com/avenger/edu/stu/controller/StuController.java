package com.avenger.edu.stu.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avenger.edu.stu.model.ChangePW;
import com.avenger.edu.stu.model.Grade;
import com.avenger.edu.stu.model.IdTime;
import com.avenger.edu.stu.model.MainCourInfo;
import com.avenger.edu.stu.model.MajoCourInfo;
import com.avenger.edu.stu.model.Rank;
import com.avenger.edu.stu.model.Sche;
import com.avenger.edu.stu.model.ScheSite;
import com.avenger.edu.stu.model.Schedule;
import com.avenger.edu.stu.model.SeleCourInfo;
import com.avenger.edu.stu.model.Student;
import com.avenger.edu.stu.serviceimp.StuServiceImp;
import com.avenger.edu.stu.tools.HttpUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/stu")
public class StuController {

	@Autowired
	StuServiceImp stuServiceimp;

	/**
	 * 登录
	 * 
	 * @param s
	 * @return
	 */
	@PostMapping
	@ResponseBody
	public int login(@RequestBody Student s, HttpServletResponse resp) {
		int i = stuServiceimp.login(s);
		if (i == 2) {
			Cookie stuId = new Cookie("id", String.valueOf(s.getStuId()));
			resp.addCookie(stuId);
		}
		System.out.println(i);
		return i;
	}

	/**
	 * 获得cookie值并放回student信息
	 * 
	 * @param req
	 * @return
	 */
	@GetMapping
	@ResponseBody
	public Student student(HttpServletRequest req) {
		Cookie[] c = req.getCookies();
		String str = null;
		for (int i = 0; i < c.length; i++) {
			if ("id".equals(c[i].getName())) {
				str = c[i].getValue();
			}
		}
		return stuServiceimp.getStudentInfo(Integer.valueOf(str));

	}

	/**
	 * 获得个人信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@ResponseBody
	public Student studentInfo(@PathVariable int id) {
		return stuServiceimp.getStudentInfo(id);

	}

	/**
	 * 封装诗词api接口
	 * 本来想做个机器人，来实现诗词的回答
	 * @return
	 */
	@GetMapping("/poem")
	@ResponseBody
	public String st() {
	    String host = "https://jisutssbs.market.alicloudapi.com";
	    String path = "/tangshi/search";
	    String method = "GET";
	    String appcode = "3016782f6e734c848ed9aabda196bb56";
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("keyword", "青青");
	    HttpResponse response = null;
	    JSONObject json3 = null;
	    try {
	    	response = HttpUtils.doGet(host, path, method, headers, querys);
	    	System.out.println(response.toString());
//	    	System.out.println(EntityUtils.toString(response.getEntity()));
		    JSONObject json =JSONObject.fromObject(EntityUtils.toString(response.getEntity()));

		    JSONObject json1 = json.getJSONObject("result");
		    System.out.println(json1.get("list"));
		    JSONArray json2 = json1.getJSONArray("list");
		    json3 = json2.getJSONObject(0);
		    System.out.println(json3.get("title"));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return (String) json3.get("title");

	}

	/**
	 * 修改密码
	 * 
	 * @param id
	 * @param prePW
	 * @param NewPW
	 * @return
	 */
	@PostMapping("/uppw")
	@ResponseBody
	public boolean updatePassWord(@RequestBody ChangePW cpw) {
		return stuServiceimp.changePassWord(cpw.getId(), cpw.getPrePW(), cpw.getNewPW());
	}

	/**
	 * 可选修的课程
	 * 
	 * @param time
	 * @return
	 */
	@PostMapping("/allSelectCourse")
	@ResponseBody
	public List<SeleCourInfo> allSelectCourse(@RequestBody IdTime it) {
		System.out.println(it.getTime());
		return stuServiceimp.getAllSelectCourse(it.getTime());
	}

	/**
	 * 专业的必修课程信息
	 * 
	 * @param major
	 * @param time
	 * @return
	 */
	@PostMapping("/majorCourse")
	@ResponseBody
	public List<MajoCourInfo> majorCourse(@RequestBody IdTime it) {
		return stuServiceimp.getMajorCourse(it.getId(), it.getTime());
	}

	/**
	 * 学生的选修课程信息
	 * 
	 * @param id
	 * @param time
	 * @return
	 */
	@PostMapping("/selectCourse")
	@ResponseBody
	public List<SeleCourInfo> selectCourse(@RequestBody IdTime it) {
		return stuServiceimp.getSelectCourse(it.getId(), it.getTime());
	}

	/**
	 * 学生的主修课程信息
	 * 
	 * @param id
	 * @param time
	 * @return
	 */
	@PostMapping("/mainCourse")
	@ResponseBody
	public List<MainCourInfo> mainCourse(@RequestBody IdTime it) {
		return stuServiceimp.getMainCourse(it.getId(), it.getTime());
	}

	/**
	 * 学生课表
	 * 
	 * @param ss
	 * @return
	 */
	@PostMapping("/schedule")
	@ResponseBody
	public List<Sche> schedule(@RequestBody ScheSite ss) {
		return stuServiceimp.getSchedule(ss.getId(), ss.getTime(), ss.getWeek());
	}

	@PostMapping("/scheduleInfo")
	@ResponseBody
	public Schedule scheduleInfo(@RequestBody ScheSite ss) {
		return stuServiceimp.getScheduleInfo(ss.getId(), ss.getTime(), ss.getWeek(), ss.getDay(), ss.getPart());
	}

	/**
	 * 学生的成绩信息
	 * 
	 * @param id
	 * @param time
	 * @return
	 */
	@PostMapping("/grade")
	@ResponseBody
	public List<Grade> grade(@RequestBody IdTime it) {
		return stuServiceimp.getGrade(it.getId(), it.getTime());
	}

	/**
	 * time学期下的班级排名
	 * 
	 * @param id
	 * @param time
	 * @return
	 */
	@PostMapping("/classRank")
	@ResponseBody
	public Rank classRank(@RequestBody IdTime it) {
		return stuServiceimp.getClassRank(it.getId(), it.getTime());
	}

	/**
	 * time学期下的专业排名
	 * 
	 * @param id
	 * @param time
	 * @return
	 */
	@PostMapping("/majorRank")
	@ResponseBody
	public Rank majorRank(@RequestBody IdTime it) {
		return stuServiceimp.getMajorRank(it.getId(), it.getTime());
	}

	/**
	 * 学生的挂科情况
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/failCourse/{id}")
	@ResponseBody
	public List<Grade> failCourse(@PathVariable int id) {
		return stuServiceimp.getFailCourse(id);
	}

	/**
	 * 学生需要重修的课程
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/againCourse/{id}")
	@ResponseBody
	public List<String> againCourse(@PathVariable int id) {
		return stuServiceimp.getAgainCourse(id);
	}

}
