package cn.rzedu.sf.user.feign;

import cn.rzedu.sf.resource.feign.ITextbookClient;
import cn.rzedu.sf.resource.vo.CourseLessonVO;
import cn.rzedu.sf.user.entity.*;
import cn.rzedu.sf.user.service.*;
import cn.rzedu.sf.user.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 书法用户课程 远程调用服务 控制器
 * @author
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class UserCourseClient implements IUserCourseClient {

	private IUserService userService;
    private ITextbookClient textbookClient;

    private ICourseUserService courseUserService;

    private ICourseUserLessonService courseUserLessonService;

	@Override
	public R<Boolean> createCourse(Integer userId, Integer courseId,Integer ownedType) {
		
		 CourseUser union = courseUserService.findUnion(userId, courseId);
	        if (union != null) {
	            return R.status(true);
	        }
		 LocalDateTime now = LocalDateTime.now();
	        union = new CourseUser();
	        union.setCourseId(courseId);
	        union.setUserId(userId);
	        union.setOwnedTime(now);
	        union.setOwnedType(ownedType);
	        union.setOwnedDeadline(now);
	        union.setCreateDate(now);
	        courseUserService.save(union);

	        Integer courseUserId = union.getId();
	        //课程所有单元
	        R<List<CourseLessonVO>> result = textbookClient.findCourseLessons(courseId);
	        List<CourseLessonVO> lessonVOList = result.getData();
	        //新建用户课程单元
	        //已有的用户单元不再添加
	        if (lessonVOList != null && !lessonVOList.isEmpty()) {
	            List<CourseUserLesson> userLessonList = new ArrayList<>();
	            CourseUserLesson cul = null;
	            List<Integer> idList = getLessonIdList(userId, courseId, lessonVOList);
	            for (Integer id : idList) {
	                cul = new CourseUserLesson();
	                cul.setUserId(userId);
	                cul.setCourseUserId(courseUserId);
	                cul.setLessonId(id);
	                cul.setHasFinished(false);
	                cul.setUnlocked(true);
	                cul.setCreateDate(now);
	                userLessonList.add(cul);
	            }
	            courseUserLessonService.saveBatch(userLessonList);
	        }
		return R.data(true);
	}
	 /**
     * 未添加的课程单元ids
     * @param userId
     * @param courseId
     * @param lessonVOList
     * @return
     */
    private List<Integer> getLessonIdList(Integer userId, Integer courseId, List<CourseLessonVO> lessonVOList) {
        List<Integer> result = new ArrayList<>();
        List<CourseUserLesson> existList = courseUserLessonService.findByUserAndCourse(userId, courseId);
        if (existList != null && !existList.isEmpty()) {
            for (CourseLessonVO vo : lessonVOList) {
                if (vo.getId() != null) {
                    //不再列表中的才需添加
                    boolean match = existList.stream().anyMatch(lesson -> lesson.getLessonId().equals(vo.getId()));
                    if (!match) {
                        result.add(vo.getId());
                    }
                }
            }
        } else {
            for (CourseLessonVO vo : lessonVOList) {
                if (vo.getId() != null) {
                    result.add(vo.getId());
                }
            }
        }
        return result;
    }
    @Override
    public R<List<UserVO>> findInitiatorUser(Integer greaterNumber, Integer lessNumber) {
        return R.data(userService.findInitiatorByAssistNumber(greaterNumber, lessNumber));
    }
	@Override
	public R<List<UserVO>> selectAllUser() {
		
		return R.data(userService.selectAllUser());
	}
   
}
