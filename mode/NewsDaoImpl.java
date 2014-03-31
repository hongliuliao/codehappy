
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ����Dao��ʵ����
 * 
 * @author finallygo
 * @version $Revision: 1.10 $
 * @since 1.0
 */
public class NewsDaoImpl extends BaseDao implements NewsDao {

	private static List<Method> getMethods = new ArrayList<Method>();
	static {
		Method[] methods = News.class.getDeclaredMethods();
		for (Method m : methods) {
			if (m.getName().startsWith("get")) {
				getMethods.add(m);
			}
		}
	}

	/**
	 * ����ID�ҵ�����
	 * 
	 * @param ����id
	 * @return �ҵ������Ŷ���
	 * @see com.wanczy.maxclachan.dao.NewsDao#findById()
	 */
	public News findById(int newsId) {
		List<News> newsList = template
				.executeQuery("select * from tb_news where news_id=" + newsId,
						News.class);
		News news = null;
		if (newsList.size() > 0) {
			news = newsList.get(0);
		}
		return news;
	}

	/**
	 * ����IDɾ������
	 * 
	 * @see com.wanczy.maxclachan.dao.NewsDao#removeNews(int)
	 */
	public int removeNews(int newsId) {
		return template.executeUpdate("delete from tb_news where news_id="
				+ newsId);
	}

	/**
	 * ����������Ϣ
	 * 
	 * @see com.wanczy.maxclachan.dao.NewsDao#saveNews(com.wanczy.maxclachan.pojo.News)
	 */
	public long saveNews(News news) {
		return template.executeSave("fin_insert", news);
	}

	/**
	 * ����������Ϣ
	 * 
	 * @see com.wanczy.maxclachan.dao.NewsDao#updateNews(com.wanczy.maxclachan.pojo.News)
	 */
	public int updateNews(News news) {

		return template.executeUpdate("fin_update",news);
	}
	/**
	 * �Լ����ķ�ҳ
	 * 
	 * @param page
	 *            ҳ�Ŷ���
	 * @return ��ѯ�������ż���
	 */
	public List<News> findByPage(Page page, News news) {
		// String field="news_title";
		Object keyword="";
		String condition = null;
		boolean isNull=true;

		try {
			for (Method m : getMethods) {
				keyword = m.invoke(news, new Object[] {});
				if (keyword != null) {
					condition = "news_"+ m.getName().substring(7) + " like '%"+keyword+"%'";
					isNull=false;
					break;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("����ʲô�쳣��", e);
		}
		if(isNull){
			condition = "1=1";
		}
		String sql = "select top " + page.getPageSize()
				+ " * from tb_news where " + condition
				+ " and news_id not in(select top "
				+ (page.getCurrentPage() - 1) * page.getPageSize()
				+ " news_id from tb_news where " + condition + ")";
		return this.template.executeQuery(sql, News.class);
	}

	/**
	 * ����news�����ѯ�ܼ�¼��
	 * 
	 * @param news
	 *            Ҫ��ѯ�����ͼ���ѯ������
	 * @return Integer �ܼ�¼��
	 */
	public Integer countNews(News news) {
		String sql = null;
		Object keyword = null;
		try {
			for (Method m : getMethods) {
				keyword = m.invoke(news, new Object[] {});
				if (keyword != null) {
					sql = "select count(1) from tb_news where news_"+ getField(m.getName()) + " like ?";
					return (Integer) this.template.getSingleValue(sql,new Object[] { "%" + keyword + "%" });
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("����ʲô�쳣��", e);
		}
		sql = "select count(1) from tb_news";
		keyword = "";
		return (Integer) this.template.getSingleValue(sql);
	}
	/**
	 * ͨ���������ҳ���������ȥ��pojo����
	 * @param m �������ַ���
	 * @return ������
	 */
	public String getField(String m){
		char[] ch=new char[m.length()];
		m.getChars(0, m.length(), ch, 0);
		int j=0,pos=0;
		for(int i=0;i<ch.length;i++){
			if(Character.isUpperCase(ch[i])){
				j++;
				if(j==2){
					pos=i;
				}
			}
		}
		return m.substring(pos);
	}
	
	public static void main(String[] args) {
		NewsDao newsDao = (NewsDao) DaoFactory.getDao(NewsDao.class);
		try {
			Class clazz = Class.forName("com.wanczy.maxclachan.pojo.UserPojo");
			Method[] methods = clazz.getDeclaredMethods();
			Field[] fields = clazz.getDeclaredFields();
			for (Method m : methods) {
				System.out.println(m.getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
