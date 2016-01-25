var SERVERJSON = Config.FILTER;
var obj;
var split;
var FJS;
var map;
var markers;
var personsListGUI = [];
var institutesListGUI = [];
var partnersListGUI = [];
var sponsorsListGUI = [];
var disciplineListGUI = [];
var periodListGUI = [];
var spaceListGUI = [];
var blueMarker, greenMarker, redMarker;

$(document).ready(function() {
	// init
	$.ajaxSetup({
		async: false
	});
	// set leaflet map baselayer
	map = L.map('map').setView([50, 0], 1);
	L.tileLayer('http://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png', {
		maxZoom: 17,
		attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>, Tiles courtesy of <a href="http://hot.openstreetmap.org/" target="_blank">Humanitarian OpenStreetMap Team</a>'
	}).addTo(map);
	var MarkerIcon = L.Icon.extend({
		options: {
			iconAnchor: [9, 30],
			popupAnchor: [0, -30]
		}
	});
	blueMarker = new MarkerIcon({
		iconUrl: 'img/marker-icon_blue.png'
	});
	greenMarker = new MarkerIcon({
		iconUrl: 'img/marker-icon_green.png'
	});
	redMarker = new MarkerIcon({
		iconUrl: 'img/marker-icon_red.png'
	});
	$("#map").hide();
	// get data from server
	$.getJSON(SERVERJSON, function(response) {
		try {
			response = JSON.parse(response);
		} catch (e) {}
		obj = response;
		console.log(obj);
		for (var i = 0; i < obj.length; i++) {
			split = obj[i].projektleitung;
			for (var j = 0; j < split.length; j++) {
				if (split[j] != "") {
					personsListGUI.push(split[j]);
				}
			}
			split = obj[i].mitwirkende;
			for (var j = 0; j < split.length; j++) {
				if (split[j] != "") {
					personsListGUI.push(split[j]);
				}
			}
			split = obj[i].projektpartner;
			for (var j = 0; j < split.length; j++) {
				if (split[j] != "") {
					partnersListGUI.push(split[j]);
				}
			}
			// Förderer
			split = obj[i].foerderer;
			for (var j = 0; j < split.length; j++) {
				if (split[j] != "") {
					sponsorsListGUI.push(split[j]);
				}
			}
			institutesListGUI.push(obj[i].institut);
			disciplineListGUI.push(obj[i].disziplin);
			periodListGUI.push(obj[i].epoche);
			spaceListGUI.push(obj[i].geografischerraum);
		}
		// get single elements in array
		function remDoub(arr) {
			var temp = new Array();
			arr.sort();
			for (i = 0; i < arr.length; i++) {
				if (arr[i] == arr[i + 1]) {
					continue
				}
				temp[temp.length] = arr[i];
			}
			return temp;
		}
		// fill Filter GUI values
		personsListGUI = remDoub(personsListGUI);
		for (var i = 0; i < personsListGUI.length; i++) {
			var string = "<div class='checkbox'><label><input type='checkbox' value='" + personsListGUI[i] + "' id='persons_criteria-" + i + "'><span>" + personsListGUI[i] + "</span></label></div>";
			$(string).appendTo("#persons_criteria");
		}
		institutesListGUI = remDoub(institutesListGUI);
		for (var i = 0; i < institutesListGUI.length; i++) {
			var string = "<div class='checkbox'><label><input type='checkbox' value='" + institutesListGUI[i] + "' id='institutes_criteria-" + i + "'><span>" + institutesListGUI[i] + "</span></label></div>";
			$(string).appendTo("#institutes_criteria");
		}
		partnersListGUI = remDoub(partnersListGUI);
		for (var i = 0; i < partnersListGUI.length; i++) {
			var string = "<div class='checkbox'><label><input type='checkbox' value='" + partnersListGUI[i] + "' id='partners_criteria-" + i + "'><span>" + partnersListGUI[i] + "</span></label></div>";
			$(string).appendTo("#partners_criteria");
		}
		sponsorsListGUI = remDoub(sponsorsListGUI);
		for (var i = 0; i < sponsorsListGUI.length; i++) {
			var string = "<div class='checkbox'><label><input type='checkbox' value='" + sponsorsListGUI[i] + "' id='sponsors_criteria-" + i + "'><span>" + sponsorsListGUI[i] + "</span></label></div>";
			$(string).appendTo("#sponsors_criteria");
		}
		disciplineListGUI = remDoub(disciplineListGUI);
		for (var i = 0; i < disciplineListGUI.length; i++) {
			var string = "<div class='checkbox'><label><input type='checkbox' value='" + disciplineListGUI[i] + "' id='discipline_criteria-" + i + "'><span>" + disciplineListGUI[i] + "</span></label></div>";
			$(string).appendTo("#discipline_criteria");
		}
		periodListGUI = remDoub(periodListGUI);
		for (var i = 0; i < periodListGUI.length; i++) {
			var string = "<div class='checkbox'><label><input type='checkbox' value='" + periodListGUI[i] + "' id='period_criteria-" + i + "'><span>" + periodListGUI[i] + "</span></label></div>";
			$(string).appendTo("#period_criteria");
		}
		spaceListGUI = remDoub(spaceListGUI);
		for (var i = 0; i < spaceListGUI.length; i++) {
			var string = "<div class='checkbox'><label><input type='checkbox' value='" + spaceListGUI[i] + "' id='period_criteria-" + i + "'><span>" + spaceListGUI[i] + "</span></label></div>";
			$(string).appendTo("#space_criteria");
		}
		// show number of elements
		$('#total_data').text(obj.length);
		// init
		initFiltersHTML();
	});
});

function initFiltersHTML() {
	$('#persons_criteria :checkbox').prop('checked', false);
	$('#institutes_criteria :checkbox').prop('checked', false);
	$('#partners_criteria :checkbox').prop('checked', false);
	$('#sponsors_criteria :checkbox').prop('checked', false);
	$('#discipline_criteria :checkbox').prop('checked', false);
	$('#period_criteria :checkbox').prop('checked', false);
	$('#space_criteria :checkbox').prop('checked', false);
	$("#foedersumme_slider").slider({
		min: 0,
		max: 1000000,
		values: [0, 1000000],
		step: 10000,
		range: true,
		slide: function(event, ui) {
			$("#foedersumme_range_label").html(ui.values[0] + '-' + ui.values[1]);
			$('#foedersumme_filter').val(ui.values[0] + '-' + ui.values[1]).trigger('change');
		}
	});
	$("#years_slider").slider({
		min: 1980,
		max: 2050,
		values: [1980, 2050],
		step: 1,
		range: true,
		slide: function(event, ui) {
			$("#years_range_label").html(ui.values[0] + '-' + ui.values[1]);
			$('#years_filter').val(ui.values[0] + '-' + ui.values[1]).trigger('change');
		}
	});
	$("#laufzeit_slider").slider({
		min: 0,
		max: 30,
		values: [0, 30],
		step: 1,
		range: true,
		slide: function(event, ui) {
			$("#laufzeit_range_label").html(ui.values[0] + '-' + ui.values[1]);
			$('#laufzeit_filter').val(ui.values[0] + '-' + ui.values[1]).trigger('change');
		}
	});
	initFilters();
}

function initFilters() {
	FJS = FilterJS(obj, '#data', {
		template: '#main_template',
		criterias: [{
			field: 'foerdersumme',
			ele: '#foedersumme_filter',
			type: 'range',
			delimiter: '-'
		}, {
			field: 'beginn',
			ele: '#years_filter',
			type: 'range',
			delimiter: '-'
		}, {
			field: 'laufzeit',
			ele: '#laufzeit_filter',
			type: 'range',
			delimiter: '-'
		}, {
			field: 'persons',
			ele: '#persons_criteria input:checkbox'
		}, {
			field: 'institut',
			ele: '#institutes_criteria input:checkbox'
		}, {
			field: 'projektpartner',
			ele: '#partners_criteria input:checkbox'
		}, {
			field: 'foerderer',
			ele: '#sponsors_criteria input:checkbox'
		}, {
			field: 'disziplin',
			ele: '#discipline_criteria input:checkbox'
		}, {
			field: 'epoche',
			ele: '#period_criteria input:checkbox'
		}, {
			field: 'geografischerraum',
			ele: '#space_criteria input:checkbox'
		}],
		search: {
			ele: '#searchbox'
		},
		callbacks: {
			afterFilter: function(result, jQ) {
				//console.log(result);
				$('#total_data').text(result.length);
				// set marker
				try {
					map.removeLayer(markers);
				} catch (e) {}
				markers = new L.FeatureGroup();
				for (var i = 0; i < result.length; i++) {
					var lat = Number(result[i].lat);
					var lon = Number(result[i].lon);
					var marker = L.marker([lat, lon]);
					/*marker.bindPopup(result[i].akronym, {
						showOnMouseOver: true
					});*/
					marker.setIcon(blueMarker);
					marker.options.title = result[i].id;
					markers.addLayer(marker);
				}
				markers.on('click', function(e) {
					highlight(e.layer._latlng.lat, e.layer._latlng.lng, e.layer.options.title, 1);
				});
				map.on('click', function(e) {
					resethighlight();
				});
				resethighlight();
				map.addLayer(markers);
			}
		}
	});
	window.FJS = FJS;
	// init filters
	$("#foedersumme_filter").val('0' + '-' + '999999').trigger('change');
	$("#foedersumme_filter").val('0' + '-' + '1000000').trigger('change');
	$("#years_filter").val('1980' + '-' + '2049').trigger('change');
	$("#years_filter").val('1980' + '-' + '2050').trigger('change');
	$("#laufzeit_filter").val('0' + '-' + '29').trigger('change');
	$("#laufzeit_filter").val('0' + '-' + '30').trigger('change');
}

var highlight = function(lat, lon, projektname, opt) {
	for (element in markers._layers) {
		if (markers._layers[element]._latlng.lat === lat && markers._layers[element]._latlng.lng === lon) {
			markers._layers[element].setIcon(greenMarker);
		} else {
			markers._layers[element].setIcon(blueMarker);
		}
	}
	var thumbnails = document.getElementsByClassName("thumbnail");
	for (var i = 0; i < thumbnails.length; i++) {
		if (projektname === thumbnails[i].id) {
			$(thumbnails[i]).addClass("active");
		} else {
			$(thumbnails[i]).removeClass("active");
		}
	}
}

var resethighlight = function() {
	for (element in markers._layers) {
		markers._layers[element].setIcon(blueMarker);
	}
	var thumbnails = document.getElementsByClassName("thumbnail");
	for (var i = 0; i < thumbnails.length; i++) {
		$(thumbnails[i]).removeClass("active");
	}
}

var showMap = function() {
	//console.log($("#map").is(":visible"));
	if ($("#map").is(":visible")) {
		$("#map").hide();
		resethighlight();
	} else {
		$("#map").show();
		resethighlight();
	}
}