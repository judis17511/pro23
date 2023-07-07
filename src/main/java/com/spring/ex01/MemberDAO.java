package com.spring.ex01;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession; 
// 마이바티스와 작업하는 주요 자바 인터페이스
// 이 인터페이스를 통해 명령어 실행, 매퍼들 가져오기, 트랜잭션(DB all or nothing) 관리
import org.apache.ibatis.session.SqlSessionFactory; // DB 접속을 위한 공장
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {

	
	// DB 연결 코드 작성
	
	public static SqlSessionFactory sqlMapper = null;
	
	// 동적(필요시) 객체 생성하는 메서드
	private static SqlSessionFactory getInstance() {
		// sqlMapper 유효성(null 여부) 검사
		
		if(sqlMapper == null) {
			String resource = "mybatis/SqlMapConfig.xml";
			
			try {
				Reader reader = Resources.getResourceAsReader(resource);
				
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				
				reader.close();
				
			} catch (IOException e) {
				System.out.println("SqlMap 설정파일 불러오는데 있어서 오류남");
			} 
			
			
		}
		return sqlMapper;
	}
	
	
	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		
		SqlSession session = sqlMapper.openSession();
		
		List<MemberVO> memberList = session.selectList("mapper.member.selectAllMemberList");
		
		return memberList;
	}
	
	public int addMember(MemberVO memberVO) {
		sqlMapper = getInstance();
		System.out.println("sqlMapper는 : " + sqlMapper);
		SqlSession session = sqlMapper.openSession();
		System.out.println("openSession은 : " + sqlMapper.openSession());
		int result = 0;
		result = session.insert("mapper.member.addMember", memberVO);
		System.out.println("result 는 " + result);
		session.commit();
		return result;
	}
	
	public void deleteMember(String id) {
		sqlMapper = getInstance();
		System.out.println("sqlMapper는 : " + sqlMapper);
		SqlSession session = sqlMapper.openSession();
		System.out.println("openSession은 : " + sqlMapper.openSession());
		int result = 0;
		result = session.delete("mapper.member.deleteMember", id);
		System.out.println("result 는 " + result);
		session.commit();	
	}
	
	public List<MemberVO> searchMemberById(String id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memberList = session.selectList("mapper.member.searchMember", id);
		return memberList;
	}
	
	public List<MemberVO> searchMemberByPwd(String pwd) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memberList = session.selectList("mapper.member.searchMemberByPwd", pwd);
		return memberList;
		
	}
	
	public void updateMember(MemberVO memberVO) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		
		session.update("mapper.member.updateMember", memberVO);
		session.commit();
	}
	
	public MemberVO searchOne(String id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		return session.selectOne("mapper.member.searchMember", id);
	}
}
