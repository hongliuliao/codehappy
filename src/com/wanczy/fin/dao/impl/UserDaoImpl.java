package com.wanczy.fin.dao.impl;

import com.wanczy.fin.dao.*;


import com.finallygo.dbutil.*;
import com.finallygo.pojo.User;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻Dao的实现类
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
	 * 根据ID找到新闻
	 * 
	 * @param 新闻id
	 * @return 找到的新闻对象
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
	 * 根据ID删除新闻
	 * 
	 * @see com.wanczy.maxclachan.dao.UserDao#removeUser(int)
	 */
	public int removeUser(int userId) {
		return template.executeUpdate("delete from tb_user where user_id="
				+ userId);
	}

	/**
	 * 增加新闻信息
	 * 
	 * @see com.wanczy.maxclachan.dao.UserDao#saveUser(com.wanczy.maxclachan.pojo.User)
	 */
	public long saveUser(User user) {
		return template.executeSave("insert into tb_user values(?,?,?)", user);
	}

	/**
	 * 更新新闻信息
	 * 
	 * @see com.wanczy.maxclachan.dao.UserDao#updateUser(com.wanczy.maxclachan.pojo.User)
	 */
	public int updateUser(User user) {

		return template.executeUpdate("update tb_user set user_name=?,user_age=?,type_id=? where user_id=?",user);
	}

	/**
	 * 根据user对象查询总记录数
	 * 
	 * @param user
	 *            要查询的类型及查询的内容
	 * @return Integer 总记录数
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
			throw new RuntimeException("这是什么异常？", e);
		}
		sql = "select count(1) from tb_user";
		keyword = "";
		return (Integer) this.template.getSingleValue(sql);
	}
	/**
	 * 通过方法名找出属性名（去除pojo名）
	 * @param m 方法名字符串
	 * @return 属性名
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
