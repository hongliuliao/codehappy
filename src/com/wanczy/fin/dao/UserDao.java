package com.wanczy.fin.dao;

import com.wanczy.fin.dao.*;



import com.finallygo.dbutil.*;
import com.finallygo.pojo.User;


import java.util.List;

/**
 * 新闻dao的接口
 *
 * @author finallygo
 * @version $Revision: 1.10 $
 * @since 1.0
 */
public interface UserDao {
	/**
	 * 根据ID找到新闻
	 * @param userId 新闻id
	 * @return 新闻对象
	 */
	public abstract User findById(int userId);
	/**
	 * 添加新闻
	 * @param userPojo
	 * @return 新添加的新闻ID
	 */
	public abstract long saveUser(User user);
	/**
	 * 移除新闻
	 * @param userId 新闻ID
	 * @return 影响记录数
	 */
	public abstract int removeUser(int userId);
	/**
	 * 更新新闻
	 * @param userPojo 要更新的对象
	 * @return 影响记录数
	 */
	public abstract int updateUser(User user);

	public Integer countUser(User user);
}
