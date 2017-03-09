package spring.sts.board;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import spring.model.board.BoardDAO_ibatis;

public class BoardTest {

	public static void main(String[] args) throws Exception {

		Resource rs = new ClassPathResource("board.xml");

		BeanFactory factory = new XmlBeanFactory(rs);

		BoardDAO_ibatis dao = (BoardDAO_ibatis) factory.getBean("dao");

		int cnt = dao.deleteXE();

		System.out.println("cnt:" + cnt);
		if (cnt > 0) {
			System.out.println("성공");
		} else {
			System.out.println("실패");
		}
	}

}