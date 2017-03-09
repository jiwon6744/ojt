package spring.model.board;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class mReplyDAO{

	@Autowired
	private SqlMapClientTemplate ibatis;

	public void setSqlMapClientTemplate(SqlMapClientTemplate ibatis) {
		this.ibatis = ibatis;
	}
	
	public int rcount(int num){
	    return (Integer) ibatis.queryForObject("breply.rcount", num);
	}
	
	public boolean create(mReplyDTO dto) {
		boolean flag = false;

		int cnt = (Integer) ibatis.update("breply.create", dto);
		if (cnt > 0)
			flag = true;

		return flag;
	}

	public mReplyDTO read(int rnum) {

		return (mReplyDTO) ibatis.queryForObject("breply.read", rnum);

	}

	public boolean update(mReplyDTO dto) {
		boolean flag = false;

		int cnt = ibatis.update("breply.update", dto);
		if (cnt > 0)
			flag = true;

		return flag;
	}

	public List<mReplyDTO> list(Map map) {

		return ibatis.queryForList("breply.list", map);
	}

	public int total(int memono) {
		return (Integer) ibatis.queryForObject("breply.total", memono);
	}

	public boolean delete(int rnum) {
		boolean flag = false;
		int cnt = ibatis.delete("breply.delete", rnum);
		if (cnt > 0)
			flag = true;

		return flag;
	}

	/** 하나의 글의 여러댓글들 삭제 */
	public int bdelete(int memono) throws Exception {
		return ibatis.delete("breply.bdelete", memono);

	}
}