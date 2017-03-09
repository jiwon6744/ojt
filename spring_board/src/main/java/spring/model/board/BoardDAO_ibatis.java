package spring.model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO_ibatis {
	@Autowired
	private SqlMapClientTemplate ibatis;
	@Autowired
	private BoardMgr Mgr; //추가
	 

	// test 클래스에서 사용 ...//
	public void setIbatis(SqlMapClientTemplate ibatis) {
		this.ibatis = ibatis;
	}

	public int deleteXE() throws Exception {

		int boardSeqs[] = { 12, 13, 14 };

		Map map = new HashMap();

		map.put("boardSeqs", boardSeqs);

		return ibatis.delete("board.deleteXE", map);
	}

	/**
	 * 글쓰기 처리
	 * 
	 * @param dto
	 * @return
	 */
	public boolean create(BoardDTO dto) throws Exception {
		boolean flag = false;

		// int cnt = ibatis.update("board.create", dto);
		// Object cnt = ibatis.insert("board.create",dto);
		int cnt = ibatis.update("board.create", dto);
		System.out.println("BoardDAO_ibatis : create method");
		System.out.println("cnt: " + cnt);
		if (cnt > 0)
			flag = true;
		return flag;
	}

	/**
	 * 전체 레코드 갯수
	 * 
	 * @param searchColumn
	 * @param searchWord
	 * @return
	 */
	public int getTotal(Map map) throws Exception {
		return (Integer) ibatis.queryForObject("board.total", map);
	}

	/**
	 * 게시판 글목록
	 * 
	 * @param searchColumn
	 * @param searchWord
	 * @param beginPerPage
	 * @param numPerPage
	 * @return
	 */
	public List<BoardDTO> getList(Map map) throws Exception {
		return ibatis.queryForList("board.list", map);
	}

	/**
	 * 조회수 증가
	 * 
	 * @param num
	 */
	public void upCount(int num) throws Exception {
		ibatis.update("board.upCount", num);
	}

	/**
	 * 게시판 글보기
	 * 
	 * @param num
	 * @return
	 */
	public BoardDTO read(int num) throws Exception {
		return (BoardDTO) ibatis.queryForObject("board.read", num);
	}

	/**
	 * 비밀번호 검사
	 * 
	 * @param num
	 * @param passwd
	 * @return
	 */
	public boolean passwdCheck(int num, String passwd) throws Exception {
		boolean flag = false;
		Map map = new HashMap();
		map.put("num", num);
		map.put("passwd", passwd);
		int cnt = (Integer) ibatis.queryForObject("board.passwdCheck", map);
		if (cnt > 0) {
			flag = true;
		}
		return flag;
	}

	public boolean update(BoardDTO dto) throws Exception {
		boolean flag = false;
		int cnt = ibatis.update("board.update", dto);
		if (cnt > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * ansnum 재정렬
	 * 
	 * @param ref
	 * @param ansnum
	 */
	public void upAnsnum(int ref, int ansnum) throws Exception {
		Map map = new HashMap();
		map.put("ref", ref);
		map.put("ansnum", ansnum);
		ibatis.update("board.upAnsnum", map);
	}

	/**
	 * 답변등록
	 * 
	 * @param dto
	 * @return
	 */
	public boolean insertReply(BoardDTO dto) throws Exception {
		boolean flag = false;

		int cnt = ibatis.update("board.insertReply", dto);
		if (cnt > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 부모글인지 확인
	 * 
	 * @param num
	 * @param con
	 * @return
	 */
	public boolean checkRefnum(int num) throws Exception {
		boolean flag = false;
		int cnt = (Integer) ibatis.queryForObject("board.checkRefnum", num);
		if (cnt > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 게시판 글 삭제
	 * 
	 * @param num
	 * @return
	 */
	public boolean delete(int num) throws Exception {
		boolean flag = false;
		int cnt = ibatis.delete("board.delete", num);
		if (cnt > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 부모의 ref,indent,ansnum 가져오기
	 * 
	 * @param num
	 * @return
	 */
	public BoardDTO readReply(int num) throws Exception {
		return (BoardDTO) ibatis.queryForObject("board.readReply", num);
	}
}
