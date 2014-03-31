<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
    	<td>			
			第[
			<c:out value='${page.currentPage }' />
			]页/共[
			<c:out value='${page.totalPage}' />
			]页
		</td>
		
		<td><a href="${page.pageURI }?current_page=1" id="first">第一页</a></td>
        <td><a href="${page.pageURI }?current_page=${page.currentPage-1 }" >上一页</a></td>
        <td><a href="${page.pageURI }?current_page=${page.currentPage+1 }" >下一页</a></td>
        <td><a href="${page.pageURI }?current_page=${page.totalPage }" >最后一页</a></td>
   		<td>      	
     	 跳至<input name="goPage" id="topage" type="text" size="2" maxlength="5" onkeypress="goPage2();" />页
      	</td>
<script type="text/javascript">
	function goPage2(){
		if(event.keyCode==13){
			var pageNum=document.getElementById("topage").value;
			window.location.href="${page.pageURI}?current_page="+pageNum;
		}
	}
</script>