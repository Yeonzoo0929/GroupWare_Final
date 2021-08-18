package com.group.calendar.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.calendar.dto.Schedule;
import com.group.calendar.service.ScheduleService;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.exception.FindException;

/**
 * Servlet implementation class ShowByContent
 */
public class ShowByContent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  ObjectMapper mapper;
	      mapper = new ObjectMapper();
	      mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm"));
	      String jsonStr = "";
	      
	      String id = request.getParameter("id");
	      String title = request.getParameter("skd_title");
	      String content = request.getParameter("skd_content");
	      ScheduleService service;
	      ServletContext sc = getServletContext();
	      ScheduleService.envProp = sc.getRealPath(sc.getInitParameter("env"));
	      service = ScheduleService.getInstance();
	      
	      try {
	    	 Employee em = new Employee();
	    	 em.setEmployeeId(id);
	         Schedule skd = new Schedule(em, title, content);
	         skd.setSkdId(em);
	         List<Schedule> list = service.findByContent(skd);
	         jsonStr = mapper.writeValueAsString(list);
	         response.setContentType("application/json;charset=utf-8");
	         response.getWriter().print(jsonStr);
	       //  System.out.println(jsonStr);
	      } catch (FindException e) {
	         e.printStackTrace();
	      }
	}

}
