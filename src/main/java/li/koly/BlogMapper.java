package li.koly;

/**
 * message
 *
 * @author koly
 * @date 17-10-30
 */
public interface BlogMapper {
    //    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlog(Long id);
}
