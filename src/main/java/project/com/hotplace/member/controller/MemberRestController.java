package project.com.hotplace.member.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import project.com.hotplace.email.Email;
import project.com.hotplace.email.EmailSender;
import project.com.hotplace.member.model.MemberVO;
import project.com.hotplace.member.service.MemberService;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class MemberRestController {

	@Autowired
	ServletContext sContext;

	@Autowired
	HttpSession session;
	
	@Autowired
    EmailSender emailSender;
	
	@Autowired
	private MemberService service;

	@RequestMapping(value = "/member/json/selectAll.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> selectAll(String searchKey, String searchWord, int page) {
		int pageNumber = 1;
		int nextPageNumber = page + 1;

		log.info("member/json/selectAll.do");
		if (page > 0) {
			pageNumber = page;
		}

		// selectAll, searchList
		List<MemberVO> vos = service.selectAll(searchKey, searchWord, pageNumber);
		List<MemberVO> vos2 = service.selectAll(searchKey, searchWord, nextPageNumber);

		boolean isLast = vos2.isEmpty();
		log.info("vos.size():{}", vos.size());

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("vos", vos);
		response.put("isLast", isLast);

		return response;
	}

	@RequestMapping(value = "/member/json/selectOne.do", method = RequestMethod.POST)
	@ResponseBody
	public MemberVO selectOne(MemberVO vo) {
		log.info("member/json/selectOne...{}", vo);

		MemberVO vo2 = service.selectOne(vo);
		log.info("selectOne matching member...{}", vo2);

		return vo2;
	}

	@RequestMapping(value = "member/json/nickNameCheck.do", method = RequestMethod.GET)
	@ResponseBody
	public String nickNameCheck(MemberVO vo) {
		log.info("nickNameCheck:{}", vo);

		MemberVO vo2 = service.nickNameCheck(vo);
		log.info("{}", vo2);
		if (vo2 == null) {
			return "{\"result\":\"OK\"}";
		} else {
			return "{\"result\":\"NotOK\"}";
		}
	}

	@RequestMapping(value = "member/json/emailCheck.do", method = RequestMethod.GET)
	@ResponseBody
	public String emailCheck(MemberVO vo) {
		log.info("emailCheck:{}", vo);

		MemberVO vo2 = service.emailCheck(vo);
		log.info("{}", vo2);
		if (vo2 == null) {
			int num = ThreadLocalRandom.current().nextInt(100_000, 1_000_000); // 100000 이상 1000000 미만의 난수 생성
			String formattedNum = String.format("%06d", num); // 6자리로 포맷팅된 문자열 생성
			
			session.setAttribute("authNum", formattedNum);
			session.setMaxInactiveInterval(5*60);
			
			Email email = new Email();
			String reciver = vo.getEmail(); //받는사람
			String subject = "[HOTPLACE] 이메일 인증 이메일 입니다"; 
			String content = System.getProperty("line.separator") + "안녕하세요 회원님" + System.getProperty("line.separator")
			+ "HOTPLACE 이메일 인증번호는 [" + formattedNum + "] 입니다." + System.getProperty("line.separator"); // 
			
			try {
				email.setReciver(reciver);
				email.setSubject(subject);
				email.setContent(content);
				
				emailSender.SendEmail(email);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return "{\"result\":\"OK\"}";
		} else {
			return "{\"result\":\"NotOK\"}";
		}
	}

	@RequestMapping(value = "member/json/insertOK.do", method = RequestMethod.POST)
	@ResponseBody
	public String insertOK(MemberVO vo) {
		log.info("insert...{}", vo);

		int result = service.insertOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "{\"result\":\"OK\"}";
		} else {
			return "{\"result\":\"NotOK\"}";
		}
	}

	@RequestMapping(value = "member/json/updateOK.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateOK(MemberVO vo) throws IllegalStateException, IOException {
		log.info("updateOK...{}", vo);

		if (vo.getMultipartFile() != null && !vo.getMultipartFile().isEmpty()) {
			String getOriginalFilename = vo.getMultipartFile().getOriginalFilename();
			int fileNameLength = vo.getMultipartFile().getOriginalFilename().length();
			log.info("getOriginalFilename:{}", getOriginalFilename);
			log.info("fileNameLength:{}", fileNameLength);
//		if (fileNameLength != 0) {
			String originalFilename = vo.getMultipartFile().getOriginalFilename();
			String newFilename = vo.getNum() + ".png";

			String realPath = sContext.getRealPath("resources/ProfileImage");
			String filePath = realPath + File.separator + newFilename;
			File file = new File(filePath);
			vo.getMultipartFile().transferTo(file);
			log.info("RealPath..{}", realPath);

			// 썸네일 이미지 생성 코드
			BufferedImage originalBufferedImage = ImageIO.read(file);
			BufferedImage thumbnailBufferedImage = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D graphics = thumbnailBufferedImage.createGraphics();
			graphics.drawImage(originalBufferedImage, 0, 0, 200, 200, null);

			String thumbnailFilePath = realPath + File.separator + vo.getNum() + ".png";
			File thumbnailFile = new File(thumbnailFilePath);
			ImageIO.write(thumbnailBufferedImage, ".png", thumbnailFile);

			int result = service.updateOK(vo);
			log.info("result:{}", result);
			if (result == 1) {
				return "{\"result\":\"OK\"}";
			} else {
				return "{\"result\":\"NotOK\"}";
			}
		} else {
			int result = service.updateOK(vo);
			log.info("result:{}", result);
			if (result == 1) {
				return "{\"result\":\"OK\"}";
			} else {
				return "{\"result\":\"NotOK\"}";
			}
		}
	}
	@RequestMapping(value = "account/json/pwResetOK.do", method = RequestMethod.POST)
	@ResponseBody
	public String pwResetOK(MemberVO vo){
		log.info("updateOK...{}", vo);
			
			int result = service.pwResetOK(vo);
			log.info("result:{}", result);
			if (result == 1) {
				return "{\"result\":\"OK\"}";
			} else {
				return "{\"result\":\"NotOK\"}";
			}
	}

	@RequestMapping(value = "account/json/loginOK.do", method = RequestMethod.POST)
	@ResponseBody
	public String loginOK(MemberVO vo, HttpSession session) {
		log.info("loginOK...{}", vo);

		MemberVO vo2 = service.login(vo);
		log.info("result:{}", vo2);

		if (vo2 == null) {
			return "{\"result\":\"NotOK\"}";
		} else {
			session.setAttribute("num", vo2.getNum());
			session.setAttribute("grade", vo2.getGrade());
			session.setAttribute("nick_name", vo2.getNick_name());
			session.setMaxInactiveInterval(5 * 60);
			return "{\"result\":\"OK\"}";
		}
	}

	@RequestMapping(value = "member/json/upgradeOK.do", method = RequestMethod.POST)
	@ResponseBody
	public String upgradeOK(MemberVO vo) {
		log.info("upgradeOK...{}", vo);

		int result = service.upgradeOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "{\"result\":\"OK\"}";
		} else {
			return "{\"result\":\"NotOK\"}";
		}
	}

	@RequestMapping(value = "member/json/deleteOK.do", method = RequestMethod.POST)
	@ResponseBody
	public String deleteOK(MemberVO vo) {
		log.info("insert...{}", vo);

		int result = service.deleteOK(vo);
		log.info("result:{}", result);
		if (result == 1) {
			return "{\"result\":\"OK\"}";
		} else {
			return "{\"result\":\"NotOK\"}";
		}
	}

	@RequestMapping(value = "/account/json/authCheck.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> authCheck(HttpServletRequest request, HttpSession session) {
		log.info("authCheck...inputNum{}", request.getParameter("num"));
		log.info("authCheck...sessionauthNum{}", session.getAttribute("authNum"));
		Map<Object, Object> response = new HashMap<Object, Object>();
		if(request.getParameter("num").equals(session.getAttribute("authNum"))){
			response.put("result", "OK");
			if(session.getAttribute("email")!=null) {
			response.put("email", session.getAttribute("email"));
			session.removeAttribute("email");
			}
			session.removeAttribute("authNum");
			return response;
		} else {
			response.put("result", "NotOK");
			return response;
		}
	}

}
