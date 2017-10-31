package li.koly;

import org.apache.ibatis.annotations.Select;

/**
 * message
 *
 * @author koly
 * @date 17-10-30
 */
public interface BlogMapper {
    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlog(Long id);
}
