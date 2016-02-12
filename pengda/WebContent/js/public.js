/**
 * 项目公共js方法
 * 
 * @author gongxiaosong
 */

//转化日期格式
var dateformatter = function(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + "-" + m + "-" + d;
};

//转化人员状态
var p_personstatus = function(value, row, index) {
	if (row.status)
		return '正常';
	else
		return '冻结';
};

//转化设备状态
var p_devicestatus = function(value, row, index) {
	if (value == '1')
		return '启用';
	else 
		return '停用';
}


//转化设备服务状态
var p_status = function(value, row, index) {
	if (value == '1')
		return '正常';
	else 
		return '关闭';
}

//转化卡状态
var p_cardstatus = function(value, row, index) {
	if (value == '1')
		return '正常';
	else if (value == '2')
		return '挂失/黑名单';
}
//+---------------------------------------------------  
//| 求两个时间的天数差 日期格式为 YYYY-MM-dd   
//+---------------------------------------------------  
function daysBetween(DateOne,DateTwo)  
{   
  var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));  
  var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);  
  var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));  

  var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));  
  var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);  
  var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));  

  var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);   
  return Math.abs(cha);  
} 


//计算比较传入时间与目前相差的天数
//如果相差天数小于defaulttime则为报警1，如果相差天数大于defaulttime则为正常0，如果相差天数小于0则为失效2
function compareDate(d1, defaulttime) {
	var now = new Date().getTime();
	try {
		var arr = d1.split("-");
	    var starttime = new Date(arr[0], arr[1]-1, arr[2]).getTime();
	    var day = (starttime - now)/86400000;
	    if (0 < day && day  <= defaulttime) return 1;
	    else if (day <= 0) return 2;
	} catch (e){}
	return 0;
}

//判断主键是否存在
function haveKey (e, fun) {
	if (e.data.key){  
        $.post(e.data.url,{id:e.data.key},fun,'json');  
    }
}