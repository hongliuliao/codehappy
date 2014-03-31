
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页信息
 * @author Administrator
 * @version 1.0 2009-02-18
 */
public class Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int currentPage=1;//当前页
	private int totalRecord;//总记录数
	private int totalPage;//总页数
	private int pageSize=5;//一页显示记录数
	private String pageURI;//设置页面的URI
	public Page(){
		
	}
	public Page(int currentPage,int totalRecord,HttpServletRequest request){
		this.totalRecord=totalRecord;
		if(currentPage>totalRecord){
			currentPage=totalRecord;
		}else{
			this.currentPage=currentPage;
		}
		this.pageURI=request.getRequestURI();
	}
	public String getPageURI() {
		return pageURI;
	}
	public void setPageURI(String pageURI) {
		this.pageURI = pageURI;
	}
	public int getCurrentPage() {
		return currentPage<1?1:currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
//		return totalPage;
		return (totalRecord%pageSize==0?totalRecord/pageSize:totalRecord/pageSize+1);
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
}
