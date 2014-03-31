
import java.util.List;



/**
 * ����service�ӿ�
 * 
 * @author finallygo
 * @version $Revision: 1.8 $
 * @since 1.0
 */
	public interface NewsService {
	/**
	 * ����ID�ҵ�����
	 * @param newsId ����id
	 * @return ���Ŷ���
	 */
	public abstract News queryById(int newsId);

	/**
	 * �������
	 * @param newsPojo
	 * @return ����ӵ�����ID
	 */
	public abstract long addNews(News newsPojo);

	/**
	 * �Ƴ�����
	 * @param newsId ����ID
	 * @return Ӱ���¼��
	 */
	public abstract int deleteNews(int newsId);

	/**
	 * ��������
	 * @param newsPojo Ҫ���µĶ���
	 * @return Ӱ���¼��
	 */
	public abstract int updateNews(News newsPojo);

}
