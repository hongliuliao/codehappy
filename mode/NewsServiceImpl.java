
import java.util.List;



/**
 * 新闻的业务层实现类
 *
 * @author finallygo
 * @version $Revision: 1.13 $
 * @since 1.0
 */
public class NewsServiceImpl implements NewsService {

	NewsDao newsDao=(NewsDao)DaoFactory.getDao(NewsDao.class);
	
	
	/**
	 * 删除新闻信息
	 * @param 新闻ID
	 * @return 结果对象
	 * @see com.wanczy.maxclachan.service.NewsService#doRemoveNews(int)
	 */
	public int deleteNews(int newsId) {
		return newsDao.removeNews(newsId);
	}

	/**
	 * 添加新闻信息
	 * @param 新闻对象
	 * @return 新添加的新闻ID
	 * @see com.wanczy.maxclachan.service.NewsService#doSaveNews(com.wanczy.maxclachan.pojo.News)
	 */
	public long addNews(News newsPojo) {
		return newsDao.saveNews(newsPojo);
	}

	/**
	 * 更新新闻信息
	 * @param 新闻对象
	 * @return 结果对象
	 * @see com.wanczy.maxclachan.service.NewsService#doUpdateNews(com.wanczy.maxclachan.pojo.News)
	 */
	public int updateNews(News news) {
		return newsDao.updateNews(news);	
	}
	/**
	 * 根据新闻ID找到相应的新闻
	 * @param newsId
	 * @return 新闻对象
	 */
	public News queryById(int newsId) {
		return newsDao.findById(newsId);
	}
}
