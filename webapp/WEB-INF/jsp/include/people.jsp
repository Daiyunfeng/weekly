<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-12" id="peoplePage">
</div>
<script>
$.ajax(
{
	type : 'post',
	url : '${ctx}/findIndexUserAjax',
	dataType : 'json',
	data : 
	{
	},
	success : function(data) 
	{
		if (data.flag == "1") 
		{
			for (var i=0;i<data.res.length;i++)
			{
				if(data.role == "管理员")
				{
					$("#peoplePage").append('<div class="col-md-5 col-xs-12" style="text-align:center;width:240px; height:350px;margin-top:20px;margin-left:40px">'+
					'<img src="'+ data.res[i].head +'" style="width: 100%; height: 80%;display:block;" />'+
					'<a style="font-size:21px;" href="${ctx}/admin/user/'+ data.res[i].userID +'">'+ data.res[i].realname +'</a>'+
					'<p style="text-align:center; font-size:19px;">'+ data.res[i].phone +'</p></div>');
				}
				else
				{
					$("#peoplePage").append('<div class="col-md-5 col-xs-12" style="text-align:center;width:240px; height:350px;margin-top:20px;margin-left:40px">'+
							'<img src="'+ data.res[i].head +'" style="width: 100%; height: 80%;display:block;" />'+
							'<a style="font-size:21px;" href="${ctx}/user/'+ data.res[i].userID +'">'+ data.res[i].realname +'</a>'+
							'<p style="text-align:center; font-size:19px;">'+ data.res[i].phone +'</p></div>');
				}
			}
		} 
		else if (data.flag == "0") 
		{
			alert(data.msg);
		} 
	},
	error: function(XMLHttpRequest, textStatus, errorThrown) 
	{
		 alert(XMLHttpRequest.status+"	"+XMLHttpRequest.readyState+"	"+textStatus);
	}
});

</script>