<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
    	<td>			
			��[
			<c:out value='${page.currentPage }' />
			]ҳ/��[
			<c:out value='${page.totalPage}' />
			]ҳ
		</td>
		
		<td><a href="${page.pageURI }?current_page=1" id="first">��һҳ</a></td>
        <td><a href="${page.pageURI }?current_page=${page.currentPage-1 }" >��һҳ</a></td>
        <td><a href="${page.pageURI }?current_page=${page.currentPage+1 }" >��һҳ</a></td>
        <td><a href="${page.pageURI }?current_page=${page.totalPage }" >���һҳ</a></td>
   		<td>      	
     	 ����<input name="goPage" id="topage" type="text" size="2" maxlength="5" onkeypress="goPage2();" />ҳ
      	</td>
<script type="text/javascript">
	function goPage2(){
		if(event.keyCode==13){
			var pageNum=document.getElementById("topage").value;
			window.location.href="${page.pageURI}?current_page="+pageNum;
		}
	}
</script>