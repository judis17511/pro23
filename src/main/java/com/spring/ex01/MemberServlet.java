package com.spring.ex01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member.do")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
		// doHandle로 보냅니다.
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO();
		MemberVO memberVO = new MemberVO();

		
		String action = request.getParameter("action");
		System.out.println("action 은 :" + action);
		String nextPage = "";
		
		if(action == null || action.equals("listMembers")) {
			List<MemberVO> membersList = dao.selectAllMemberList();
			request.setAttribute("membersList", membersList);
			
			nextPage = "test01/listMembers.jsp";
			
		} else if(action.equals("addMember")) {
			System.out.println("action은" + action);
			
			String id= request.getParameter("id");
			String pwd= request.getParameter("pwd");
			String name= request.getParameter("name");
			String email= request.getParameter("email");
			memberVO.setId(id);
			memberVO.setPwd(pwd);
			memberVO.setName(name);
			memberVO.setEmail(email);
			dao.addMember(memberVO);
			nextPage="/member.do?action=listMembers";
			
		} else if(action.equals("deleteMember")) {
			String id= request.getParameter("id");
			dao.deleteMember(id);
			nextPage="/member.do?action=listMembers";
			
		} else if(action.equals("searchMember")) {
			String id = request.getParameter("value");
			System.out.println("id 는 : " + id);
			List<MemberVO> membersList = dao.searchMemberById(id);
			request.setAttribute("membersList", membersList);
			System.out.println(membersList);
			nextPage="test01/listMembers.jsp";
		} else if(action.equals("searchPwd")) {
			String pwd = request.getParameter("value");
			System.out.println("id 는 : " + pwd);
			List<MemberVO> membersList = dao.searchMemberByPwd(pwd);
			request.setAttribute("membersList", membersList);
			System.out.println(membersList);
			nextPage="test01/listMembers.jsp";
			
		} else if(action.equals("modMember")) {
			String id = request.getParameter("id");
			MemberVO membersList = dao.searchOne(id);
			request.setAttribute("membersList", membersList);
			nextPage="test01/modMember.jsp";
		} else if(action.equals("updateMember")) {
			String id= request.getParameter("id");
			String pwd= request.getParameter("pwd");
			String name= request.getParameter("name");
			String email= request.getParameter("email");
			System.out.println(pwd);
			
			memberVO.setId(id);
			memberVO.setPwd(pwd);
			memberVO.setName(name);
			memberVO.setEmail(email);
			
			dao.updateMember(memberVO);
			nextPage="/member.do?action=listMembers";
			
		}
		
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

}
