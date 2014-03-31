
import java.util.List;

/**
 * 新闻dao的接口
 *
 * @author finallygo
 * @version $Revision: 1.10 $
 * @since 1.0
 */
public interface NewsDao {
	/**
	 * 根据ID找到新闻
	 * @param newsId 新闻id
	 * @return 新闻对象
	 */
	public abstract News findById(int newsId);
	/**
	 * 添加新闻
	 * @param newsPojo
	 * @return 新添加的新闻ID
	 */
	public abstract long saveNews(News news);
	/**
	 * 移除新闻
	 * @param newsId 新闻ID
	 * @return 影响记录数
	 */
	public abstract int removeNews(int newsId);
	/**
	 * 更新新闻
	 * @param newsPojo 要更新的对象
	 * @return 影响记录数
	 */
	public abstract int updateNews(News news);
	public List<News> findByPage(Page page, News news);
	public Integer countNews(News news);
}
