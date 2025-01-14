package com.group.main.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.approval.dto.Document;
import com.group.board.dto.Board;
import com.group.calendar.dto.Schedule;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
import com.group.employee.dto.Leave;
import com.group.exception.FindException;
import com.group.main.service.MainService;

@RestController
@RequestMapping("/main/*")
public class MainController {
	private Logger log = Logger.getLogger(MainController.class.getClass());

	@Autowired
	private MainService service;

	@PostMapping("/login")
	public Map<String, Object> login(HttpSession session, @RequestBody Employee emp) {
		Map<String, Object> map = new HashMap<>();

		session.removeAttribute("loginInfo");
		if("admin".equals(emp.getEmployeeId())) {
			map.put("status", 0);
			return map;
		}else {
			try {
				Employee loginInfo = service.login(emp.getEmployeeId(), emp.getPassword());
				session.setAttribute("id", loginInfo.getEmployeeId());
				session.setAttribute("pwd", loginInfo.getPassword());
				session.setAttribute("dept", loginInfo.getDepartment().getDepartmentId());
				
				map.put("status", 1);
				return map;
				
			} catch (FindException e) {
				e.printStackTrace();
				map.put("status", -1);
				map.put("msg", e.getMessage());
				return map;
			}			
		}
	}

	@GetMapping("/chk-login")
	public Map<String, Integer> chkLogin(HttpSession session) {
		Map<String, Integer> map = new HashMap<>();
		int status;
		String id = session.getAttribute("id").toString();
		if (id == null || "admin".equals(id))  {
			status = 0;
		} else {
			status = 1;
		}
		map.put("status", status);
		return map;
	}

	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate(); // 세션제거
		ResponseEntity<String> entity = new ResponseEntity<String>(HttpStatus.OK);
		return entity;
	}

	@GetMapping("/profile")
	public Object getProfile(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String id = session.getAttribute("id").toString();
//		String id = "MSD002";
		try {
			Employee emp = service.showProfile(id);
			return emp;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@GetMapping("/leave")
	public Object getLeave(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String id = session.getAttribute("id").toString();
//		String id = "MSD002";
		try {
			Leave leave = service.showLeave(id);
			return leave;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@GetMapping("/document")
	public Object getDocExpected(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String id = session.getAttribute("id").toString();
//		String id = "MSD002";
		try {
			List<Document> docList = service.showDocExpected(id);
			return docList;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@GetMapping("/board")
	public Object getLatestBoardList() {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Board> bdList = service.showRecentBd();
			return bdList;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@GetMapping("/today-skd")
	public Object getTodaySkdList(HttpSession session) {
		String deptId = session.getAttribute("dept").toString();
		String id = session.getAttribute("id").toString();
		Employee emp = new Employee();
		Department dept = new Department();
		dept.setDepartmentId(deptId);
		emp.setEmployeeId(id);
		emp.setDepartment(dept);

		Map<String, Object> map = new HashMap<>();
		try {
			List<Schedule> skdList = service.showTodaySkd(emp);
			return skdList;
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", -1);
			map.put("msg", e.getMessage());
		}
		return map;
	}
}
