package com.wanczy.fin.dao;

import com.wanczy.fin.dao.*;



import com.finallygo.dbutil.*;
import com.finallygo.pojo.User;


import java.util.List;

/**
 * ����dao�Ľӿ�
 *
 * @author finallygo
 * @version $Revision: 1.10 $
 * @since 1.0
 */
public interface UserDao {
	/**
	 * ����ID�ҵ�����
	 * @param userId ����id
	 * @return ���Ŷ���
	 */
	public abstract User findById(int userId);
	/**
	 * �������
	 * @param userPojo
	 * @return ����ӵ�����ID
	 */
	public abstract long saveUser(User user);
	/**
	 * �Ƴ�����
	 * @param userId ����ID
	 * @return Ӱ���¼��
	 */
	public abstract int removeUser(int userId);
	/**
	 * ��������
	 * @param userPojo Ҫ���µĶ���
	 * @return Ӱ���¼��
	 */
	public abstract int updateUser(User user);

	public Integer countUser(User user);
}
