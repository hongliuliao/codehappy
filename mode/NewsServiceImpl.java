
import java.util.List;



/**
 * ���ŵ�ҵ���ʵ����
 *
 * @author finallygo
 * @version $Revision: 1.13 $
 * @since 1.0
 */
public class NewsServiceImpl implements NewsService {

	NewsDao newsDao=(NewsDao)DaoFactory.getDao(NewsDao.class);
	
	
	/**
	 * ɾ��������Ϣ
	 * @param ����ID
	 * @return �������
	 * @see com.wanczy.maxclachan.service.NewsService#doRemoveNews(int)
	 */
	public int deleteNews(int newsId) {
		return newsDao.removeNews(newsId);
	}

	/**
	 * ���������Ϣ
	 * @param ���Ŷ���
	 * @return ����ӵ�����ID
	 * @see com.wanczy.maxclachan.service.NewsService#doSaveNews(com.wanczy.maxclachan.pojo.News)
	 */
	public long addNews(News newsPojo) {
		return newsDao.saveNews(newsPojo);
	}

	/**
	 * ����������Ϣ
	 * @param ���Ŷ���
	 * @return �������
	 * @see com.wanczy.maxclachan.service.NewsService#doUpdateNews(com.wanczy.maxclachan.pojo.News)
	 */
	public int updateNews(News news) {
		return newsDao.updateNews(news);	
	}
	/**
	 * ��������ID�ҵ���Ӧ������
	 * @param newsId
	 * @return ���Ŷ���
	 */
	public News queryById(int newsId) {
		return newsDao.findById(newsId);
	}
}
