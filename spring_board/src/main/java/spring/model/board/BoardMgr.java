package spring.model.board;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardMgr {
	@Autowired
	private mReplyDAO rdao;
	@Autowired
	// private BoardDAO dao = null;
	private BoardDAO_ibatis dao = null;

	public void bdelete(int num) throws Exception {
		rdao.bdelete(num);
		dao.delete(num);
	}

	/**
	 * 글쓰기
	 * 
	 * @param dto
	 * @return
	 */
	public boolean create(BoardDTO dto) {
		boolean flag = false;
		try {
			flag = dao.create(dto);
			System.out.println("BoardMgr:create method");

		} catch (Exception e) {
			System.out.print(e);
		}
		return flag;
	}

	/**
	 * 전체 레코드 갯수
	 * 
	 * @return
	 */
	public int getTotal(Map map) {
		int total = 0;
		try {
			total = dao.getTotal(map);

		} catch (Exception e) {
			System.out.print(e);
		}
		return total;
	}

	/**
	 * 글 목록
	 * 
	 * @param searchColumn
	 *            검색컬럼
	 * @param searchWord
	 *            검색어
	 * @param beginPerPage
	 *            시작레코드번호
	 * @param numPerPage
	 *            한페이지당보여줄 레코드 갯수(10)
	 * @return 페이지에 보여줄 글의목록들
	 */
	public List<BoardDTO> getList(Map map) {

		List<BoardDTO> v = null;
		try {
			v = dao.getList(map);

		} catch (Exception e) {
			System.out.print(e);
		}

		return v;
	}

	/**
	 * 조회수 증가
	 * 
	 * @param num
	 */
	public void upCount(int num) {
		try {
			dao.upCount(num);
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	/**
	 * 특정게시판 내용보기
	 * 
	 * @param num
	 * @return
	 */
	public BoardDTO read(int num) {
		BoardDTO dto = null;
		try {
			dto = dao.read(num);

		} catch (Exception e) {
			System.out.print(e);
		}
		return dto;
	}

	/**
	 * 답변용 내용보기
	 * 
	 * @param num
	 * @return
	 */
	public BoardDTO readReply(int num) {
		BoardDTO dto = null;
		try {
			dto = dao.readReply(num);

		} catch (Exception e) {
			System.out.print(e);
		}
		return dto;
	}

	/**
	 * 비빌번호 확인
	 * 
	 * @param num
	 * @param passwd
	 * @return
	 */
	public boolean passwdCheck(int num, String passwd) {
		boolean flag = false;
		try {
			flag = dao.passwdCheck(num, passwd);
		} catch (Exception e) {
			System.out.print(e);
		}
		return flag;
	}

	/**
	 * 게시판 글수정
	 * 
	 * @param dto
	 * @return
	 */
	public boolean update(BoardDTO dto) {
		boolean flag = false;
		try {
			flag = dao.update(dto);
		} catch (Exception e) {
			System.out.print(e);
		}
		return flag;
	}

	/**
	 * 답변처리 1.부모의 또다른 답변의 순서(ansnum)를 1증가 시켜줌(UPDATE) 2.새로운 답변글을 인서트 함(INSERT)
	 * 
	 * @param dto
	 * @return
	 */
	public boolean reply(BoardDTO dto) {
		boolean flag = false;
		Connection con = null;
		try {
			// ansnum의 순서를 재정렬
			dao.upAnsnum(dto.getRef(), dto.getAnsnum());
			// 답변글을 등록(ref는부모글의 ref와 같아야하고,indent, ansnum
			// 부모글보다 1증가한 값으로 등록)
			flag = dao.insertReply(dto);
			// 실제로 DB에 적용하라...
			// 트랜잭션을 한 이유 insert에서 error 가 나도 upAnsnum까지 취소처리 하기 위해서 사용한다.
		} catch (Exception e) {
			System.out.print(e);
		}
		return flag;
	}

	/**
	 * 부모글인지 확인
	 * 
	 * @param num
	 * @return
	 */
	public boolean checkRefnum(int num) {
		boolean flag = false;
		try {
			flag = dao.checkRefnum(num);
		} catch (Exception e) {
			System.out.print(e);
		}
		return flag;
	}

	/**
	 * 게시판 글 삭제
	 * 
	 * @param num
	 * @return
	 */
	public boolean delete(int num) {
		boolean flag = false;
		try {
			flag = dao.delete(num);

		} catch (Exception e) {
			System.out.print(e);
		}
		return flag;
	}
}
