package project.com.hotplace.party.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import project.com.hotplace.party.model.PartyDAO;
import project.com.hotplace.party.model.PartyVO;

@Service
@Slf4j
public class PartyService {
	
	@Autowired
	PartyDAO dao;

	public PartyService() {
		log.info("PartyService()...");
	}
	
	public List<PartyVO> selectAll(String searchKey, String searchWord, int status){
		return dao.selectAll(searchKey, searchWord, status);
	}
	
	public PartyVO selectOne(PartyVO vo) {
		return dao.selectOne(vo);
	}
	
	public List<PartyVO> searchList(String searchKey, String searchWord, int page, int status){
		return dao.searchList(searchKey, searchWord, page, status);
	}
	
	public int insert(PartyVO vo) {
		return dao.insert(vo);
	}
	
	public int update(PartyVO vo) {
		return dao.update(vo);
	}
	
	public int delete(PartyVO vo) {
		return dao.delete(vo);
	}
	
	public void vCountUp(PartyVO vo) {
		dao.vCountUp(vo);
	}

	public int approveOK(PartyVO vo) {
		return dao.approveOK(vo);
	}

	public List<PartyVO> myParty(PartyVO vo, int page) {
		return dao.myParty(vo, page);
	}

	public int totalCount(PartyVO vo) {
		return dao.totalCount(vo);
	}

	public List<PartyVO> myAppcants(PartyVO vo, int page) {
		return dao.myAppcants(vo, page);
	}

	public int myPartyCount(PartyVO vo) {
		return dao.myPartyCount(vo);
	}

	public int shopPartyCount(String searchWord) {
		return dao.shopPartyCount(searchWord);
	}
}
