//屏幕尺寸判断
function checkScreenSize() {
    if(window.innerWidth<1700){
        $("#left-bar").attr("hidden",true);
        $("#article").attr("class","am-u-sm-8");
        $("#right-bar").attr("class","am-u-sm-4");
    }else{
        $("#left-bar").attr("hidden",false);
        $("#article").attr("class","am-u-sm-6");
        $("#right-bar").attr("class","am-u-sm-3");
    }
}
setInterval("checkScreenSize()",50);//定时执行屏幕大小判断方法
