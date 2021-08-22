package com.group.approval.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.approval.dao.ConfirmDocsDAO;
import com.group.approval.dao.DocsWriteDAO;
import com.group.approval.dto.Agreement;
import com.group.approval.dto.Approval;
import com.group.approval.dto.Document;
import com.group.approval.dto.DocumentType;
import com.group.approval.dto.Reference;
import com.group.exception.AddException;
import com.group.exception.FindException;
import com.group.employee.dto.Department;
import com.group.employee.dto.Employee;
@Service
public class DocsWriteService {
	@Autowired
	private DocsWriteDAO dao;

	/**
	 * 결재문서 기안
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void draftD(Document d) throws AddException {
		dao.draft(d);
	}
	/**
	 * 결재문서의 결재자 등록
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void draftApRe(Approval ap) throws AddException {
		dao.draftAp(ap);
	}
	/**
	 * 결재문서의 합의자 등록
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void draftAgRe(Agreement ag) throws AddException {
		dao.draftAg(ag);
	}
	/**
	 * 결재문서의 참조자 등록
	 * 
	 * @param d
	 * @throws AddException
	 */
	public void draftReRe(Reference re) throws AddException {
		dao.draftRe(re);
	}


//	
//	public int chkMaxNum(String document_type) throws FindException{
//		return dao.chkMaxNum(document_type);
//	}
//	
//	public static void main(String[] args) {
//		   DocsWriteService service = DocsWriteService.getInstance();
//		      try {
//				List<Employee> empList = service.staff("경영지원실");
//				for(Employee e : empList) {
//					System.out.println(e);
//				}
//			} catch (FindException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		      String word = "김";
//		      System.out.println("\"" + word + "\"단어를 포함한 사원목록");
//		      try {
//		         
//		         List<Employee> list = service.staff(word);
//		         for (Employee em : list) {
//		            System.out.println(em.getName());
//		         }
//		      } catch (FindException e) {
//		         System.out.println(e.getMessage());
//		      } catch (Exception e) {
//		         System.out.println(e.getMessage());
//		      }
		   
//		Document document = new Document();
//		document.setDocument_no("a");
//		document.setDocument_title("aa");
//		document.setDocument_content("aaaa");
//		DocumentType dtype = new DocumentType();
//		dtype.setDocument_type("지출");
//		document.setDocument_type(dtype);
//		Employee emp = new Employee();
//		emp.setEmployee_id("MSD002");
//		document.setEmployee(emp);
//		System.out.println("ac "+document);
//		
//		try {
//			service.complete(document);
//		} catch (AddException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		   Approval ap = new Approval();
//		   Document d = new Document();
//		   d.setDocument_no("BC-연락-20210622-0001");
//		   ap.setDocument_no(d);
//		   Employee emp = new Employee();
//		   emp.setEmployee_id("MSD002");
//		   ap.setEmployee_id(emp);
//		   ap.setAp_step(2);
//		   
//		   try {
//			service.completeApRegister(ap);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
		   
//		   Agreement ag = new Agreement();
//		   Document dag = new Document();
//		   dag.setDocument_no("BC-연락-20210622-0001");
//		   ag.setDocument_no(dag);
//		   Employee empag = new Employee();
//		   empag.setEmployee_id("MSD002");
//		   ag.setEmployee_id(empag);
//		   
//		   try {
//			service.completeAgRegister(ag);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
//		   Reference re = new Reference();
//		   Document dre = new Document();
//		   dre.setDocument_no("BC-연락-20210622-0001");
//		   re.setDocument_no(dre);
//		   Employee empre = new Employee();
//		   empre.setEmployee_id("MSD002");
//		   re.setEmployee_id(empre);
//		   
//		   try {
//			service.completeReRegister(re);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
//		   String document_type="지출";
//		   try {
//			int cnt = service.chkMaxNum(document_type);
//			System.out.println(cnt);
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
//	}
}
