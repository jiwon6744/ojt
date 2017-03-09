package spring.sts.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.model.board.BoardDTO;
import spring.model.board.BoardMgr;
import spring.model.board.mReplyDAO;
import spring.model.board.mReplyDTO;
import spring.utility.board.Utility;

@Controller
public class BoardController {
	@Autowired
	private BoardMgr mgr;

	@Autowired
	private mReplyDAO rdao;

	@RequestMapping("/rdelete")
	public String rdelete(int rnum, int num, int nowPage, int nPage, String col, String word, Model model) {

		int total = rdao.total(num);// 댓글전체레코드값을 가져와서
		int totalPage = (int) (Math.ceil((double) total / 3)); // 전체 페이지
		if (rdao.delete(rnum)) {
			if (nPage != 1 && nPage == totalPage && total % 3 == 1) {// 마지막페이지의
																		// 마지막레코드이면(3은
																		// 한페이지당보여줄
																		// 레코드
																		// 갯수)
				nPage = nPage - 1; // 현재의 페이지값에서 1을 빼자
			}
			model.addAttribute("num", num);
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("nPage", nPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);

		} else {
			return "error/error";
		}
		return "redirect:./read";
	}

	@RequestMapping("/rupdate")
	public String rupdate(mReplyDTO dto, int nowPage, int nPage, String col, String word, Model model) {
		if (rdao.update(dto)) {
			model.addAttribute("num", dto.getnum());
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("nPage", nPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);
		} else {
			return "error/error";
		}

		return "redirect:./read";
	}

	@RequestMapping("/rcreate")
	public String rcreate(mReplyDTO dto, int nowPage, String col, String word, Model model) {

		if (rdao.create(dto)) {
			model.addAttribute("num", dto.getnum());
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);
		} else {
			return "error/error";
		}

		return "redirect:./read";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(int num, String passwd, String oldfile, HttpServletRequest request, String nowPage, String col,
			String word, Model model) {
		System.out.println("delete POST");
		String upDir = request.getRealPath("/storage");
		mgr.passwdCheck(num, passwd);
		String url = "error";
			try {
				mgr.bdelete(num);
				Utility.deleteFile(upDir, oldfile);
				model.addAttribute("nowPage", nowPage);
				model.addAttribute("col", col);
				model.addAttribute("word", word);
				url = "redirect:./list";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				url = "error";
			}
		return url;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(int num, Model model) {
		System.out.println("delete GET");
		boolean flag = mgr.checkRefnum(num);
		model.addAttribute("flag", flag);

		return "/board/delete";
	}

	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(BoardDTO dto, String nowPage, String col, String word, HttpServletRequest request,
			Model model) {
		System.out.println("REPLY POST");
		String basePath = request.getRealPath("/storage");
		int filesize = (int) dto.getFileMF().getSize();
		String filename = "";
		if (filesize > 0) {
			filename = Utility.saveFile(dto.getFileMF(), basePath);
		}
		dto.setFilesize(filesize);
		dto.setFilename(filename);
		dto.setIp(request.getRemoteAddr());

		if (mgr.reply(dto)) {
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);
			return "redirect:./list";
		} else {
			return "/board/passwdError";
		}
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String reply(int num, Model model) {
		System.out.println("REPLY GET");
		BoardDTO dto = mgr.readReply(num);
		model.addAttribute("dto", dto);
		return "/board/reply";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(BoardDTO dto, String oldfile, String nowPage, String col, String word,
			HttpServletRequest request, Model model) {
		System.out.println("UPDATE POST");
		String upDir = request.getRealPath("/storage");
		int filesize = (int) dto.getFileMF().getSize();
		String filename = "";
		if (filesize > 0) {
			filename = Utility.saveFile(dto.getFileMF(), upDir);
		}

		dto.setFilename(filename);
		dto.setFilesize(filesize);
		dto.setIp(request.getRemoteAddr()); // IP를 가져오는 부분.

		boolean pflag = mgr.passwdCheck(dto.getNum(), dto.getPasswd());

		if (pflag) {
			if (mgr.update(dto))
				Utility.deleteFile(upDir, oldfile);
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);

			return "redirect:./list";

		} else {
			return "/board/passwdError";
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(int num, Model model) {
		System.out.println("UPDATE GET");
		BoardDTO dto = mgr.read(num);
		model.addAttribute("dto", dto);
		return "/board/update";
	}

	@RequestMapping("/read")
	public String read(int num, Model model, int nowPage, String col, String word, HttpServletRequest request) {
		System.out.println("READ");
		mgr.upCount(num);
		BoardDTO dto = mgr.read(num);
		String content = dto.getContent().replaceAll("\r\n", "<br>");
		dto.setContent(content);
		model.addAttribute("dto", dto);
		/* 댓글 관련 시작 */
		String url = "read";
		int nPage = 1; // 시작 페이지 번호는 1부터

		if (request.getParameter("nPage") != null) {
			nPage = Integer.parseInt(request.getParameter("nPage"));
		}
		int recordPerPage = 3; // 한페이지당 출력할 레코드 갯수

		int sno = ((nPage - 1) * recordPerPage) + 1; //
		int eno = nPage * recordPerPage;

		Map map = new HashMap();
		map.put("sno", sno);
		map.put("eno", eno);
		map.put("num", num);

		List<mReplyDTO> list = rdao.list(map);

		int total = rdao.total(num);

		String paging = Utility.paging(total, nPage, recordPerPage, url, num, nowPage, col, word);

		model.addAttribute("rlist", list);
		model.addAttribute("paging", paging);
		model.addAttribute("nPage", nPage);

		/* 댓글 관련 끝 */
		return "/board/read";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create() {
		return "/board/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(BoardDTO dto, HttpServletRequest request) {
		String upDir = request.getRealPath("/storage");
		int filesize = (int) dto.getFileMF().getSize();
		String filename = "";
		if (filesize > 0) {
			filename = Utility.saveFile(dto.getFileMF(), upDir);
		}
		dto.setFilesize(filesize);
		dto.setFilename(filename);
		dto.setIp(request.getRemoteAddr());

		boolean flag = mgr.create(dto);
		if (flag) {
			return "redirect:/list";
		} else {
			return "error";
		}
	}

	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));

		// System.out.println("col"+col);
		// System.out.println("word"+word);

		if (col.equals("total")) {
			word = "";
		}
		int nowPage = 1;
		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}
		int recordPerPage = 5;

		int sno = ((nowPage - 1) * recordPerPage) + 1;
		int eno = nowPage * recordPerPage;

		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);

		List<BoardDTO> list = mgr.getList(map);
		int totalRecord = mgr.getTotal(map);
		String paging = Utility.paging3(totalRecord, nowPage, recordPerPage, col, word);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("/board/list");
		mv.addObject("list", list);
		mv.addObject("nowPage", nowPage);
		mv.addObject("col", col);
		mv.addObject("word", word);
		mv.addObject("paging", paging);
		request.setAttribute("rdao", rdao);

		return mv;
	}
}
