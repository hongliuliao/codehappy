package com.wanczy.fin.dao.impl;

import com.wanczy.fin.dao.*;


import com.finallygo.dbutil.*;
import com.finallygo.pojo.User;


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
public class UserDaoImpl extends BaseDao implements UserDao {

	private static List<Method> getMethods = new ArrayList<Method>();
	static {
		Method[] methods = User.class.getDeclaredMethods();
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
	 * @see com.wanczy.maxclachan.dao.UserDao#findById()
	 */
	public User findById(int userId) {
		List<User> userList = template
				.executeQuery("select * from tb_user where user_id=" + userId,
						User.class);
		User user = null;
		if (userList.size() > 0) {
			user = userList.get(0);
		}
		return user;
	}

	/**
	 * ����IDɾ������
	 * 
	 * @see com.wanczy.maxclachan.dao.UserDao#removeUser(int)
	 */
	public int removeUser(int userId) {
		return template.executeUpdate("delete from tb_user where user_id="
				+ userId);
	}

	/**
	 * ����������Ϣ
	 * 
	 * @see com.wanczy.maxclachan.dao.UserDao#saveUser(com.wanczy.maxclachan.pojo.User)
	 */
	public long saveUser(User user) {
		return template.executeSave("insert into tb_user values(?,?,?)", user);
	}

	/**
	 * ����������Ϣ
	 * 
	 * @see com.wanczy.maxclachan.dao.UserDao#updateUser(com.wanczy.maxclachan.pojo.User)
	 */
	public int updateUser(User user) {

		return template.executeUpdate("update tb_user set user_name=?,user_age=?,type_id=? where user_id=?",user);
	}

	/**
	 * ����user�����ѯ�ܼ�¼��
	 * 
	 * @param user
	 *            Ҫ��ѯ�����ͼ���ѯ������
	 * @return Integer �ܼ�¼��
	 */
	public Integer countUser(User user) {
		String sql = null;
		Object keyword = null;
		try {
			for (Method m : getMethods) {
				keyword = m.invoke(user, new Object[] {});
				if (keyword != null) {
					sql = "select count(1) from tb_user where user_"+ getField(m.getName()) + " like ?";
					return (Integer) this.template.getSingleValue(sql,new Object[] { "%" + keyword + "%" });
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("����ʲô�쳣��", e);
		}
		sql = "select count(1) from tb_user";
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
		UserDao userDao = (UserDao) DaoFactory.getDao(UserDao.class);
		User u=userDao.findById(1);
		System.out.println(u.getType().getTypeName());
	}
}
