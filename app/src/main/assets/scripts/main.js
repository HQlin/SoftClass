// 判断是否是IE的办法
/*var ie = (function() {
	var undef,
		div = document.createElement('div'),
		all = div.getElementsByTagName('i'),
		version = 3;
	while (
		div.innerHTML = '<!--[if gt IE ' + (++version) + ']><i></i><![endif]-->', all[0]
	);
	return version >= 4 ? version : undef;
}());
(function() {
	// use to set the svg scope
	var SVGW = 3000,
		SVGH = 2400,
		// SVGW = document.body.scrollWidth,
		// SVGH = document.body.scrollHeight,

		// subway data, d3 to generate painting
		subwayPointRadius = 5,
		subwayPointStroke = '#000',
		subwayPointStrokeWidth = 2,
		subwayPointFill = '#ccc',

	// defined svg scale
	xScale = d3.scale.linear()
						.domain([0, SVGH]),

	yScale = d3.scale.linear()
					.domain([0, SVGH]),

	zoomScale = d3.scale.linear()
						.domain([1,6])
						.range([1,6])
						.clamp(true),

	d3SvgObj = d3.select('#d3-svg-container')
							.append('svg:svg')
							.attr({
								'xmlns': 	'http://www.w3.org/2000/svg',
								'width': 	SVGW,
								'height': 	SVGH,
								'id': 		'subwayGraph',
								'viewBox': 	'0 0 ' + SVGW + ' ' + SVGH,
								'preserveAspectRatio': 'xMidYMid meet'
							});

	function getViewportSize(w) {
		var w = w || window;
		if (w.innerWidth != null) {
			return {
				'w': w.innerWidth,
				'h': w.innerHeight,
				'x': w.pageXOffset,
				'y': w.pageYOffset
			}
		}

		var d = w.document;

		// 根据文档的渲染模式来决定使用的对象
		// CSS1Compat是"标准规范模式"，BackCompat为混杂模式
		if (document.compatMode === 'CSS1Compat') {
			return {
				'w': d.documentElement.clientWidth,
				'h': d.documentElement.clientHeight,
				'x': d.documentElement.scrollLeft,
				'y': d.documentElement.scrollTop
			}
		} else {
			return {
				'w': d.body.clientWidth,
				'h': d.body.clientHeight,
				'x': d.body.scrollLeft,
				'y': d.body.scrollTop
			}
		}
	}
	function init() {

		// var width = Math.min(SVGH, SVGW)

		// d3ForceObj = d3.layout.force()
		// 					.charge(-320)
		// 					.size( [SVGW, SVGH] )
		// 					.linkStrength( function(d,idx) { return d.weight; } );

		d3.json('/scripts/subway-data.json',
			function(data) {
				console.log(data);
				var subwayStations = data.line4.subwayStations;
				var graphParent = d3SvgObj
									.append('svg:g')
									.call(d3.behavior.zoom()
											.scaleExtent([0.1, 10])
											.on('zoom', rescale))
									.on("dblclick.zoom", null)
									.attr('class', 'graphParent');

				var graphNodes = graphParent.append('svg:g')
										.attr('class', 'graph-node')
										.selectAll('circle')
										.data(subwayStations)
										.enter().append('circle')
										.attr({
											'cx': function(node) {
												return node.position.x;
											},
											'cy': function(node) {
												return node.position.y;
											},
											'r': subwayPointRadius,
											'stroke': subwayPointStroke,
											'stroke-width': subwayPointStrokeWidth,
											'fill': subwayPointFill
										});
				function rescale(obj) {
					console.log(obj);
					var translate = d3.event.translate,
						scale = d3.event.scale;

					graphParent.attr('transform',
							'translate(' + translate + ')'
							+ ' scale(' + scale + ')');
				}

				// d3SvgObj
				// 	.selectAll('rect')
				// 	.data(subwayStations)
				// 	.enter()
				// 	.append('circle')
				// 	.attr({
				// 		'cx': function(d) {
				// 			return d.position.x;
				// 		},
				// 		'cy': function(d) {
				// 			return d.position.y;
				// 		},
				// 		'r': subwayPointRadius,
				// 		'stroke': subwayPointStroke,
				// 		'stroke-width': subwayPointStrokeWidth,
				// 		'fill': subwayPointFill
				// 	});
			}
		)
	}
	init();
})();
*/

/*
{
    "line4": {
        "subwayStations": [
            {
                "name": "金洲",
                "position": {
                    // "x": 2767,
                    // "y": 2300
                    "x": 200,
                    "y": 200
                }
            },
            {
                "name": "蕉门",
                "position": {
                    // "x": 2688,
                    // "y": 2300
                    "x": 260,
                    "y": 230
                }
            }
        ]
    }
}
 */

(function($) {
	'use strict';
	var self = self || window;

	var $swSvg = $('#sw_svg'),
		$swSvgEllipse = $swSvg.find('ellipse'),
		$swSvgImage = $swSvg.find('image');

	function cleanAnimate() {
		$swSvgEllipse.html('');
		$swSvgImage.html('');
	}
	function isObject(obj) {
		return Object.prototype.toString.apply(obj) === '[object Object]' ? true : false;
	}

	var testObj = {
		'stationName': '五羊邨',
		'stationLine': '地铁五号线',
		'timeTable': ['窖口方向 始06:14 末23:21', '文冲方向 始06:14 末23:21']
	}
	var makeToolTip = (function() {
		// var $tooltip = $(
		// 	'<div class="tooltip">'+
		// 		'<header class="tooltip-head"></header>'+
		// 		'<div class="tooltip-content">'+
		// 			'<header class="station-head"></header>'+
		// 			'<div class="station-content">'+
		// 				'<ul><li></li><li></li><li></li></ul>'+
		// 				'<ul><li></li><li></li><li></li></ul>'+
		// 			'</div>'+
		// 		'</div>'+
		// 	'</div>'
		// 	);
		var $tooltip = $('<div class="tooltip"></div>'),
			$tooltipHead = $('<header class="tooltip-head"></header>'),
			$tooltipContent = $('<div class="tooltip-content"></div>'),
			$stationHead = $('<header class="station-head"></header>'),
			$stationContent = $('<div class="station-content"></div>');

		return function(swMsg) {
			if (!isObject(swMsg)) {
				return;
			}

			$tooltip.html('');
			$tooltipHead.html('');
			$tooltipContent.html('');
			$stationHead.html('');
			$stationContent.html('');

			$tooltipHead.text(swMsg.stationName);
			$stationHead.text(swMsg.stationLine);

			var $ul = $('<ul></ul>');
			for (var i = 0, j = swMsg.timeTable.length; i < j; i++) {
				$ul.append('<li>' + swMsg.timeTable[i] + '</li>');
				$stationContent.append($ul);
			}

			$tooltipContent.append($stationHead)
							.append($stationContent);

			$tooltip.append($tooltipHead)
					.append($tooltipContent);

			return $tooltip;
		};
	})();

	var tooltip = makeToolTip(testObj);
	
	$swSvg.on('touchend', 'ellipse, image', function(e) {
		var $that = $(this),
			$targetName = $(e.target).get(0).nodeName,
			$swStation = $that.next('text')
								.find('tspan')
								.text();

		if ($targetName === 'ellipse') {
			cleanAnimate();
			$that.html(
				'<animate attributeName="rx" from="6.5" to="10" dur="0.5s" repeatCount="indefinite" />' +
				'<animate attributeName="ry" from="6.5" to="10" dur="0.5s" repeatCount="indefinite" />'
			);
		} else {
			cleanAnimate();
			var $x = $that.attr('x'),
				$y = $that.attr('y'),
				$width = $that.attr('width'),
				$height = $that.attr('height'),

				$newX = parseInt($x) - 5,
				$newY = parseInt($y) - 5,
				$newWidth = parseInt($width) + 10,
				$newHeight = parseInt($height) + 10;
			$that.html(
				'<animate attributeName="x" from="' + $x +'" to="' + $newX +'" dur="0.5s" repeatCount="indefinite" />' +
				'<animate attributeName="y" from="' + $y +'" to="' + $newY +'" dur="0.5s" repeatCount="indefinite" />' +
				'<animate attributeName="width" from="' + $width +'" to="' + $newWidth +'" dur="0.5s" repeatCount="indefinite" />' +
				'<animate attributeName="height" from="' + $height +'" to="' + $newHeight +'" dur="0.5s" repeatCount="indefinite" />'
			);
		}
		JavaScriptInterface.showToast($swStation);
	});


})(jQuery);
