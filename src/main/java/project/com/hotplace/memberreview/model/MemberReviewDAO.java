package project.com.hotplace.memberreview.model;

import java.util.List;

public interface MemberReviewDAO {

	public List<MemberReviewVO> selectAll(MemberReviewVO vo, Integer page);

	public int insert(MemberReviewVO vo);

	public int update(MemberReviewVO vo);

	public int delete(MemberReviewVO vo);

	int totalCount(MemberReviewVO vo);

}
