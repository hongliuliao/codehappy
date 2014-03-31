
import java.util.List;



/**
 * 新闻service接口
 * 
 * @author finallygo
 * @version $Revision: 1.8 $
 * @since 1.0
 */
	public interface NewsService {
	/**
	 * 根据ID找到新闻
	 * @param newsId 新闻id
	 * @return 新闻对象
	 */
	public abstract News queryById(int newsId);

	/**
	 * 添加新闻
	 * @param newsPojo
	 * @return 新添加的新闻ID
	 */
	public abstract long addNews(News newsPojo);

	/**
	 * 移除新闻
	 * @param newsId 新闻ID
	 * @return 影响记录数
	 */
	public abstract int deleteNews(int newsId);

	/**
	 * 更新新闻
	 * @param newsPojo 要更新的对象
	 * @return 影响记录数
	 */
	public abstract int updateNews(News newsPojo);

}
