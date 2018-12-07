<!DOCTYPE html>
<html style="height: 100%">
   <head>
       <meta charset="utf-8"> 
       <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
       <script>
       	$(document).ready(function() {
             //准备数据  
             var json;  
             $.ajax({  
                 url: "../system/getInfo",
                 type: 'post',  
                 dataType: 'json',  
                 async: true,  
                 success: function (data) {  
                     json = data;
                     cupcharts(json);
                 }, 
                 error: function (data) {  
                     console.log("加载失败，请联系管理员！");  
                 }  
       	     }); 
       	 });
       </script>
   </head>
   <body style="height: 100%; margin: 0">
	   <div id="container" style="height: 48%; border: 1px solid red; width:99%; margin-left:0px; float: left;"></div>
	   <div id="container_cpu" style="height: 48%; width:99%; margin-left:0px; float: left;"></div>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
       <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
       <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
       <script type="text/javascript">
	       	function cupcharts(json) { 
	       		console.log(json);
				var dom = document.getElementById("container_cpu");
				var myChart = echarts.init(dom);
				var app = {};
	       		option = {
		       		tooltip : {
				        formatter: "{a} <br/>{c} {b}"
				    },
				    toolbox: {
				        show: true,
				        feature: {
				            restore: {show: true},
				            saveAsImage: {show: true}
				        }
				    },
				    series : [
				        {
				            name: '单位%',
				            type: 'gauge',
				            z: 3,
				            min: 0,
				            max: 100,
				            splitNumber: 10,
				            radius: '99%',
				            axisLine: {            // 坐标轴线
				                lineStyle: {       // 属性lineStyle控制线条样式
				                    width: 10
				                }
				            },
				            axisTick: {            // 坐标轴小标记
				                length: 15,        // 属性length控制线长
				                lineStyle: {       // 属性lineStyle控制线条样式
				                    color: 'auto'
				                }
				            },
				            splitLine: {           // 分隔线
				                length: 20,         // 属性length控制线长
				                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
				                    color: 'auto'
				                }
				            },
				            axisLabel: {
				                backgroundColor: 'auto',
				                borderRadius: 2,
				                color: '#eee',
				                padding: 3,
				                textShadowBlur: 2,
				                textShadowOffsetX: 1,
				                textShadowOffsetY: 1,
				                textShadowColor: '#222'
				            },
				            title : {
				                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
				                fontWeight: 'bolder',
				                fontSize: 20,
				                fontStyle: 'italic'
				            },
				            detail : {
				                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
				                formatter: function (value) {
				                    value = (value + '').split('.');
				                    value.length < 2 && (value.push('00'));
				                    return ('00' + value[0]).slice(-2)
				                        + '%';
				                },
				                fontWeight: 'bolder',
				                borderRadius: 3,
				                backgroundColor: '#444',
				                borderColor: '#aaa',
				                shadowBlur: 5,
				                shadowColor: '#333',
				                shadowOffsetX: 0,
				                shadowOffsetY: 3,
				                borderWidth: 2,
				                textBorderColor: '#000',
				                textBorderWidth: 2,
				                textShadowBlur: 2,
				                textShadowColor: '#fff',
				                textShadowOffsetX: 0,
				                textShadowOffsetY: 0,
				                fontFamily: 'Arial',
				                width: 100,
				                color: '#eee',
				                rich: {}
				            },
				            data:[{value: json.cpuRatio, name: 'CPU占有率'}]
				        },
				        {
				            name: '线程数',
				            type: 'gauge',
				            center: ['33%', '50%'],    // 默认全局居中
				            radius: '88%',
				            min:0,
				            max:100,
				            endAngle:45,
				            splitNumber:10,
				            axisLine: {            // 坐标轴线
				                lineStyle: {       // 属性lineStyle控制线条样式
				                    width: 8
				                }
				            },
				            axisTick: {            // 坐标轴小标记
				                length:12,        // 属性length控制线长
				                lineStyle: {       // 属性lineStyle控制线条样式
				                    color: 'auto'
				                }
				            },
				            splitLine: {           // 分隔线
				                length:20,         // 属性length控制线长
				                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
				                    color: 'auto'
				                }
				            },
				            pointer: {
				                width:5
				            },
				            title: {
				                offsetCenter: [0, '-30%'],       // x, y，单位px
				            },
				            detail: {
				                // 其余属性默认使用全局文本样式，详见TEXTSTYLE
				                fontWeight: 'bolder'
				            },
				            data:[{value: json.totalThread, name: '线程'}]
				        },
				        {
				            name: '物理内存',
				            type: 'gauge',
				            center: ['67%', '50%'],    // 默认全局居中
				            radius: '70%',
				            min: 0,
				            max: json.totalMemorySize,
				            startAngle: 135,
				            endAngle: 45,
				            splitNumber: 3,
				            axisLine: {            // 坐标轴线
				                lineStyle: {       // 属性lineStyle控制线条样式
				                    width: 8
				                }
				            },
				            axisTick: {            // 坐标轴小标记
				                splitNumber: 5,
				                length: 10,        // 属性length控制线长
				                lineStyle: {        // 属性lineStyle控制线条样式
				                    color: 'auto'
				                }
				            },
				            axisLabel: {
				                formatter:function(v){
				                    switch (v + '') {
				                        case '0' : return 'E';
				                        case '1' : return 'MB';
				                        case '2' : return 'F';
				                    }
				                }
				            },
				            splitLine: {           // 分隔线
				                length: 15,         // 属性length控制线长
				                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
				                    color: 'auto'
				                }
				            },
				            pointer: {
				                width:2
				            },
				            title : {
				                show: false
				            },
				            detail : {
				                show: false
				            },
				            data:[{value: json.usedMemory, name: 'MB'}]
				        },
				        {
				            name: '虚拟内存',
				            type: 'gauge',
				            center : ['67%', '50%'],    // 默认全局居中
				            radius : '70%',
				            min: 0,
				            max: json.maxMemory,
				            startAngle: 315,
				            endAngle: 225,
				            splitNumber: 3,
				            axisLine: {            // 坐标轴线
				                lineStyle: {       // 属性lineStyle控制线条样式
				                    width: 8
				                }
				            },
				            axisTick: {            // 坐标轴小标记
				                show: false
				            },
				            axisLabel: {
				                formatter:function(v){
				                    switch (v + '') {
				                        case '0' : return 'H';
				                        case '1' : return 'MB';
				                        case '2' : return 'C';
				                    }
				                }
				            },
				            splitLine: {           // 分隔线
				                length: 15,         // 属性length控制线长
				                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
				                    color: 'auto'
				                }
				            },
				            pointer: {
				                width:2
				            },
				            title: {
				                show: false
				            },
				            detail: {
				                show: false
				            },
				            data:[{value: json.totalMemory, name: 'MB'}]
				        }
				    ]
				};
				
				setInterval(function (){
					$.ajax({  
		                 url: "../system/getInfo",
		                 type: 'post',  
		                 dataType: 'json',  
		                 async: true,  
		                 success: function (data) {  
		                    json = data;  
						    option.series[0].data[0].value = json.cpuRatio;
						    option.series[1].data[0].value = json.totalThread;
						    option.series[2].data[0].value = json.usedMemory;
						    option.series[3].data[0].value = json.totalMemory;
				    		myChart.setOption(option, true);
		                 }, 
		                 error: function (data) {  
		                     console.log("加载失败，请联系管理员！");  
		                 }  
		       	     });
				},2000);
			}
       </script>
       
   </body>
</html>