var msgdsq;
//错误时：提示调用方法
function show_err_msg(msg){

	 clearTimeout(msgdsq);
	 $('body').append('<div class="sub_err" style="position:absolute;top:45%; left:50%; width:148px; height:37px; line-height:38px; z-index:999999; background:rgba(0,0,0,0.7); border-radius:0px; margin:-17px 0 0 -76px; display:none;"></div>');
	 var errhtml='<div style="color:#fff; text-align:center;font-size:14px;">';
	 var errhtmlfoot='</div>';	 
	 $('.sub_err').html(errhtml+msg+errhtmlfoot);
	 $('.sub_err').fadeIn();
	 msgdsq=setTimeout(function(){				     
		 $('.sub_err').fadeOut();
		 setTimeout(function(){
			 //错误提示移除
			 $('.sub_err').remove();
		 },300);
	 }, "1400"); 
}


//正确时：提示调用方法
function show_msg(msg,url){	
	clearTimeout(msgdsq);
	$('body').append('<div class="sub_yes">'+msg+'</div>');
	$('.sub_yes').fadeIn();
	 msgdsq=setTimeout(function(){				     
		 $('.sub_yes').fadeOut();
		 setTimeout(function(){
			 //错误提示移除
			 $('.sub_yes').remove();
			 if(url!=''){	 
			 	location.href=url;
			 }
		 },200);
	 }, "1000"); 	
}