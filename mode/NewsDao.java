
import java.util.List;

/**
 * ����dao�Ľӿ�
 *
 * @author finallygo
 * @version $Revision: 1.10 $
 * @since 1.0
 */
public interface NewsDao {
	/**
	 * ����ID�ҵ�����
	 * @param newsId ����id
	 * @return ���Ŷ���
	 */
	public abstract News findById(int newsId);
	/**
	 * �������
	 * @param newsPojo
	 * @return ����ӵ�����ID
	 */
	public abstract long saveNews(News news);
	/**
	 * �Ƴ�����
	 * @param newsId ����ID
	 * @return Ӱ���¼��
	 */
	public abstract int removeNews(int newsId);
	/**
	 * ��������
	 * @param newsPojo Ҫ���µĶ���
	 * @return Ӱ���¼��
	 */
	public abstract int updateNews(News news);
	public List<News> findByPage(Page page, News news);
	public Integer countNews(News news);
}
