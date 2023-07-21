package project.com.hotplace.party.model;

import java.util.List;

public interface PartyDAO {

	public List<PartyVO> selectAll(String searchKey, String searchWord, int status);
	
	public PartyVO selectOne(PartyVO vo);
	
	public List<PartyVO> searchList(String searchKey, String searchWord, int page, int status);
	
	public int insert(PartyVO vo);
	
	public int update(PartyVO vo);
	
	public int delete(PartyVO vo);
	
	public void vCountUp(PartyVO vo);

	public int approveOK(PartyVO vo);

	public List<PartyVO> myParty(PartyVO vo, int page);

	public int totalCount(PartyVO vo);

	public List<PartyVO> myAppcants(PartyVO vo, int page);

	public int myPartyCount(PartyVO vo);

	public int shopPartyCount(String searchWord);
	
}
