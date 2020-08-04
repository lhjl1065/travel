//根据传递过来的参数name获取对应的值
function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
    var r = location.search.substr(1).match(reg);
    if (r!=null) return (r[2]); return null;
}

//获取URL参数
function getParameter2(name){
    var reg=new RegExp('(^|&)'+name+'=([^&]*)(&|$)');
    var result=window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]):null;
}
