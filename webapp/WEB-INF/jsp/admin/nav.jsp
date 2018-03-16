<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id='cssmenu' class="align-center">
	<ul>
		<li class="index">
			<a href='${ctx}/admin/'>
				<span>首页</span>
			</a>
		</li>
		<li class="edit has-sub">
			<a href='${ctx}/admin/editPhotos'>
				<span>修改首页</span>
			</a>
			<ul >
				<li class="sub" style="width:126px;">
					<a href='${ctx}/admin/editResearch'>
						<span>修改研究方向</span>	
					</a>
			 	</li>
				<li class="sub" style="width:126px;">
					<a href='${ctx}/admin/editPublication'>
						<span>修改发表</span>
					</a>
			 	</li>
				<li class="sub" style="width:126px;">
					<a href='${ctx}/admin/editTeaching'>
						<span>修改教学经历</span>
					</a>
			 	</li>
			 	<li class="sub" style="width:126px;">
				 	<a href='${ctx}/admin/editPhotos'>
				 		<span>修改照片</span>
				 	</a>
			 	</li>
			 	<li class="sub" style="width:126px;">
					<a href='${ctx}/admin/editPeople'>
						<span>修改成员</span>
					</a>
			 	</li>
		  	</ul>
		</li>
		<li class="list">
			<a href='${ctx}/admin/list'>
				<span>查找周报</span>
			</a>
		</li>
		<li class="addArticle">
			<a href='${ctx}/admin/addArticle'>
				<span>编写周报</span>
			</a>
		</li>
		<li class="userList">
			<a href='${ctx}/admin/userList'>
				<span>查找用户</span>
			</a>
		</li>
		<li class='last center'>
			<a href='${ctx}/admin/center'>
				<span>个人中心</span>
			</a>
		</li>
	</ul>
</div>