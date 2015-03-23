/*
 * ---------------------------------------------------------------------
 * Custom Plug-ins
 * ---------------------------------------------------------------------
 */
(function($) {
	$.fn.isEmpty = function(options) {
		// check if selector is present in DOM or not
		return this.length <= 0;
	};

	$.fn.refreshWidget = function(options) {
		
		// check if selector is present in DOM or not
		if (this.isEmpty()) return this;
		
		// fetch the ID attribute of the widget
		var baseName = this.selector;
		
		// default settings
		var settings = $.extend({
			replaceSelector: "",
			refreshBtn: baseName + "-refresh",
			url: $(baseName + "-refresh").attr("href"),
			refreshTip: baseName + "-refresh-tip",
			refreshLoader: baseName + "-refresh-loader",
			list: baseName + "-list",
			delay: this.attr("data-delay"),
			preLoad: function () {},
			postLoad: function() {},
			refreshOnLoad: true
		}, options);
		
		var isRefreshing = false;
		
		var refresh = function(noAnimation) {
			
			if (isRefreshing) return;
			
			console.log("Refreshing " + baseName + "...");
			isRefreshing = true;
			
			settings.preLoad();
			if (!noAnimation) {
				$(settings.refreshBtn).addClass("disabled");
				$(settings.refreshTip).hide();
				$(settings.refreshLoader).show();
				$(settings.list).fadeOut();
			}
			
			// replace response content if particular area is to be replaced
			var loadUrl = settings.replaceSelector.length > 0? 
					settings.url + " " + settings.replaceSelector
					: settings.url;
			
			$(settings.list).load(loadUrl, function() {
				console.log("Refreshed " + baseName);
				isRefreshing = false;
				
				settings.postLoad();
				if (!noAnimation) {
					$(settings.list).fadeIn(function() {
						$(settings.refreshBtn).removeClass("disabled");
						$(settings.refreshTip).show();
						$(settings.refreshLoader).hide();
					});
				}
			});
		};
		
		
		// bind refresh to click event of refresh button
		$(settings.refreshBtn).click(function() {
			refresh(false);
			return false;
		});
		
		// initialize
		$(settings.refreshTip).show();
		$(settings.refreshLoader).hide();
		
		if (settings.refreshOnLoad)
			refresh(true);
		
		setInterval(refresh, settings.delay);
		
		console.log("refreshWidget init called for " + baseName);
		console.log("url = " + settings.url);
		console.log("replaceSelector = " + settings.replaceSelector);
		console.log("refreshBtn = " + settings.refreshBtn);
		console.log("refreshTip = " + settings.refreshTip);
		console.log("refreshLoader = " + settings.refreshLoader);
		console.log("list = " + settings.list);
		console.log("delay = " + settings.delay);
		
		return this;
	};
}(jQuery));

/*
 * ---------------------------------------------------------------------
 * Global functions and variables declaration
 * ---------------------------------------------------------------------
 */
//generate a random number
function randNum()
{
	return (Math.floor( Math.random()* (1+40-20) ) ) + 20;
}

// generate a random number within a range (PHP's mt_rand JavaScript implementation)
function mt_rand (min, max) 
{
	// http://kevin.vanzonneveld.net
	// +   original by: Onno Marsman
	// +   improved by: Brett Zamir (http://brett-zamir.me)
	// +   input by: Kongo
	// *     example 1: mt_rand(1, 1);
	// *     returns 1: 1
	var argc = arguments.length;
	if (argc === 0) {
		min = 0;
		max = 2147483647;
	}
	else if (argc === 1) {
		throw new Error('Warning: mt_rand() expects exactly 2 parameters, 1 given');
	}
	else {
		min = parseInt(min, 10);
		max = parseInt(max, 10);
	}
	return Math.floor(Math.random() * (max - min + 1)) + min;
}

// scroll to element animation
function scrollTo(id)
{
	if ($(id).length)
		$('html,body').animate({scrollTop: $(id).offset().top},'slow');
}

// handle menu toggle button action
function toggleMenuHidden()
{
	//console.log('toggleMenuHidden');
	$('.container-fluid:first').toggleClass('menu-hidden');
	$('#menu').toggleClass('hidden-phone', function()
	{
		if ($('.container-fluid:first').is('.menu-hidden'))
		{
			if (typeof resetResizableMenu != 'undefined') 
				resetResizableMenu(true);
		}
		else 
		{
			removeMenuHiddenPhone();
			
			if (typeof lastResizableMenuPosition != 'undefined') 
				lastResizableMenuPosition();
		}
		
		if (typeof $.cookie != 'undefined')
			$.cookie('menuHidden', $('.container-fluid:first').is('.menu-hidden'), { path: baseUrl() });
	});
	
	if (typeof masonryGallery != 'undefined') 
		masonryGallery();	
}

function removeMenuHiddenPhone()
{
	if (!$('.container-fluid:first').is('.menu-hidden') && $('#menu').is('.hidden-phone'))
		$('#menu').removeClass('hidden-phone');
}

/*
 * Helper function for JQueryUI Sliders Create event
 */
function JQSliderCreate()
{
	$(this)
		.removeClass('ui-corner-all ui-widget-content')
		.wrap('<span class="ui-slider-wrap"></span>')
		.find('.ui-slider-handle')
		.removeClass('ui-corner-all ui-state-default');
}
// Ergo Admin utility functons - END

// utility functions
function baseUrl() {
	return $("head base").attr("href");
}

function commafyNumber(number) {
	var withcommas;
	withcommas = String(number).replace(/(^|[^\w.])(\d{4,})/g, function($0, $1, $2) {
		var inter;
		inter = $1 + $2.replace(/\d(?=(?:\d\d\d)+(?!\d))/g, "$&,");
		return inter;
	});
	return withcommas;
}
function commaStringToFloat(commaStr) {
	return parseFloat(commaStr.replace(/[,]+/g, ""));
}

var defaultDataTableConfig = {
		"sPaginationType": "bootstrap",
		"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>><'row-fluid'T>",
		"oLanguage": {
			"sLengthMenu": "_MENU_ per page"
		},
	    "oTableTools": {
	        "sSwfPath": "themes/ergo/theme/scripts/plugins/tables/DataTables/extras/TableTools/media/swf/copy_csv_xls_pdf.swf" 
	    }
	};


function bindBidPageButtons(obj) {
	
	$(obj + " a.attachments").click(function() {
		$(this).parent().parent().parent().find("ul.attachments").slideToggle();
	});
	
	$(obj + " .add-on.plus").click(function () {
		var $textbox = $(this).siblings("input");
		var textboxValue = $textbox.val();
		if (textboxValue && textboxValue.length > 0) {
			var price = commaStringToFloat(textboxValue);
			var changePrice = parseFloat($textbox.attr("data-change"));
			var newPrice = (price + changePrice).toFixed(2);
			var newPriceString = commafyNumber(newPrice);
			$textbox.val(newPriceString);
		}
	});
	$(obj + " .add-on.minus").click(function () {
		var $textbox = $(this).siblings("input");
		var textboxValue = $textbox.val();
		if (textboxValue && textboxValue.length > 0) {
			var price = commaStringToFloat(textboxValue);
			var changePrice = parseFloat($textbox.attr("data-change"));
			var newPrice = (price - changePrice).toFixed(2);
			if (newPrice >= 0) {
				var newPriceString = commafyNumber(newPrice);
				$textbox.val(newPriceString);
			} else {
				$(this).addClass("disabled");
			}
		}
	});
	
	$(obj + " button.bid").click(function() {
		$(this).addClass("disabled");
		var auctionCode = $(this).attr("data-auctionCode");
		var bidPrice = $("#bidPrice_" + auctionCode).val();
		var proxy = $("#proxy_" + auctionCode).is(":checked");
		console.log("auctionCode: " + auctionCode + " Bid price: " + bidPrice + " proxy: " + proxy);
		
		var $auctionDiv = $("#auction_" + auctionCode);
		
		$.ajax({
			type: "post",
			url: window.location.href,
			data: { 
				"auctionCode": auctionCode, 
				"bidPrice": commaStringToFloat(bidPrice), 
				"proxy": proxy 
			},
			success: function(html) {
				$auctionDiv.replaceWith(html);
				bindBidPageButtons("#auction_" + auctionCode);
				// Hack for stylish checkbox
				$("#auction_" + auctionCode + " .uniformjs").find("select, input, button, textarea").uniform();
				// Hack for stylish tooltips
				$("#auction_" + auctionCode + ' [data-toggle="tooltip"]').tooltip();
				// flash auction
				$("#auction_" + auctionCode).hide().fadeIn("slow");
			},
			error: function(data) {
				console.error("Error occured while submitting bid");
			}
		});
	});
	
}

var reRun = function() {
	// File uploader
	var docuploader = new plupload.Uploader({
		runtimes: 'html5,flash,silverlight,html4',
		browse_button: "docuploader_pickfiles",
		container: $("#docuploader_container").get(0),
		url: $("#docuploader_container").attr("data-url"),
		filters : [
			{title : "Document files", extensions : "doc,xls,txt,pdf"},
			{title : "Zip files", extensions : "zip,rar"}
		],
		init: {
			PostInit: function() {
				console.log("PostInit called.");
				 
	            document.getElementById("docuploader_uploadfiles").onclick = function() {
	                docuploader.start();
	                return false;
	            };
			},
			FilesAdded: function(up, files) {
				console.log("FilesAdded called.");
				plupload.each(files, function(file) {
					console.log("file: " + file.name);
					var $uploadItem = $("#docuploader_uploaditem").clone();
					$uploadItem.attr("id", file.id).find(".col1").html(file.name);
					$uploadItem.find(".col2").children().hide();
					$uploadItem.find(".filesize").show().html(plupload.formatSize(file.size));
					$uploadItem.show().appendTo("#docuploader_filelist");
				});
			},
			UploadProgress: function(up, file) {
				console.log("UploadProgress called.");
				$("#" + file.id + " .col2").children().hide().end()
					.find(".progress").show()
					.find(".bar").css("width", file.percent + "%").html(plupload.formatSize(file.size));
			},
			FileUploaded: function(up, file, object) {
				console.log(file.name + "uploaded.");
				var code = object.response;
				var documentUrlPrefix = $("#docuploader_container").attr("data-attachmentUrlPrefix");
				var documentLink = "<a href=\"" + documentUrlPrefix + code + "\"></a>";
				$("#" + file.id + " .col1").wrapInner(documentLink);
				$("#" + file.id + " .col2").children().hide().end()
					.find(".delete").show().end()
					.find(".addFiles, .deleteFiles").val(code);
				$("#" + file.id + " .col2 label").addClass("uniformjs");
				$("#" + file.id + " .col2 .uniformjs input").uniform();
				
			},
			Error: function(up, err) {
				console.warn("Error while uploading file : " + err.code + ": " + err.message);
				if (err.code == "-601") {
					notyfy({ text: $("#docuploader_container").attr("data-extensions"), type: "error"});
				}
			}
		}
	});

	// Image uploader
	var imageuploader = new plupload.Uploader({
		runtimes: 'html5,flash,silverlight,html4',
		browse_button: "imageuploader_pickfiles",
		container: $("#imageuploader_container").get(0),
		url: $("#imageuploader_container").attr("data-url"),
		filters : [
			{title : "Image files", extensions : "jpg,png,bmp,gif"}
		],
		init: {
			PostInit: function() {
				console.log("PostInit called.");
				 
	            document.getElementById("imageuploader_uploadfiles").onclick = function() {
	            	imageuploader.start();
	                return false;
	            };
			},
			FilesAdded: function(up, files) {
				console.log("FilesAdded called.");
				plupload.each(files, function(file) {
					console.log("Creating thumbnail image for " + file.name);
					var img = new o.Image();
					img.onload = function() {
						var div = document.createElement('div');
	                    div.id = this.uid;
	                    $uploadItem.attr("id", file.id).find(".img").get(0).appendChild(div);
	                    
						// embed the actual thumbnail
						this.embed(div.id, {
							width: 180,
							height: 135,
							crop: true
						});
					};
					img.load(file.getSource());
					var $uploadItem = $("#imageuploader_uploaditem").clone();
					$uploadItem.attr("id", file.id).find(".filename").html(file.name);
					$uploadItem.find(".controls").children().hide();
					$uploadItem.find(".filesize").show().html("(" + plupload.formatSize(file.size) + ")");
					$uploadItem.show().appendTo("#imageuploader_filelist");
				});
			},
			UploadProgress: function(up, file) {
				console.log("UploadProgress called.");
				$("#" + file.id + " .controls").children().hide().end()
					.find(".progress").show()
					.find(".bar").css("width", file.percent + "%").html(plupload.formatSize(file.size));
			},
			FileUploaded: function(up, file, object) {
				console.log(file.name + " uploaded.");
				var code = object.response;
				var documentUrlPrefix = $("#imageuploader_container").attr("data-attachmentUrlPrefix");
				var documentLink = "<a href=\"" + documentUrlPrefix + code + "\"></a>";
				$("#" + file.id + " .filename").wrapInner(documentLink);
				$("#" + file.id + " .controls").children().hide().end()
					.find(".delete").show().end()
					.find(".addFiles, .deleteFiles").val(code);
				$("#" + file.id + " .controls label").addClass("uniformjs");
				$("#" + file.id + " .controls .uniformjs input").uniform();
				
			},
			Error: function(up, err) {
				console.warn("Error while uploading file : " + err.code + ": " + err.message);
				if (err.code == "-601") {
					notyfy({ text: $("#plupload_container").attr("data-extensions"), type: "error"});
				}
			}
		}
	});
	
	docuploader.init();
	imageuploader.init();
	
	/*
	 * Boostrap Extended
	 */
	// custom select for Boostrap using dropdowns
	if ($('.selectpicker').length) $('.selectpicker').selectpicker();
	
	// bootstrap-toggle-buttons
	if ($('.toggle-button').length) $('.toggle-button').toggleButtons();
	
	/*
	 * UniformJS: Sexy form elements
	 */
	if ($('.uniformjs').length) $('.uniformjs').find("select, input, button, textarea").uniform();
	
	// colorpicker
	if ($('#colorpicker').length) $('#colorpicker').farbtastic('#colorpickerColor');
	
	// datepicker
	if ($('#datepicker').length) $("#datepicker").datepicker({ showOtherMonths:true });
	if ($('#datepicker-inline').length) $('#datepicker-inline').datepicker({ inline: true, showOtherMonths:true });
	
	// daterange
	if ($('#dateRangeFrom').length && $('#dateRangeTo').length)
	{
		$( "#dateRangeFrom" ).datepicker({
			defaultDate: "+1w",
			changeMonth: false,
			numberOfMonths: 2,
			onClose: function( selectedDate ) {
				$( "#dateRangeTo" ).datepicker( "option", "minDate", selectedDate );
			}
		}).datepicker( "option", "maxDate", $('#dateRangeTo').val() );

		$( "#dateRangeTo" ).datepicker({
			defaultDate: "+1w",
			changeMonth: false,
			numberOfMonths: 2,
			onClose: function( selectedDate ) {
				$( "#dateRangeFrom" ).datepicker( "option", "maxDate", selectedDate );
			}
		}).datepicker( "option", "minDate", $('#dateRangeFrom').val() );
	}
	
	$(".dynamicTable").dataTable(defaultDataTableConfig);
	
	$(".datetimepicker").datetimepicker({
		format: "MM d, yyyy H:ii P",
		autoclose: true
	});
	
};

var init = function() {
	console.log("Running Ergo Admin scripts...");
	
	reRun();

	// Sidebar menu collapsibles
	$('#menu .collapse').on('show', function(e)
	{
		e.stopPropagation();
		
		$(this).parents('.hasSubmenu:first').addClass('active');
		
		var id = $(this).attr("id");
		if (typeof $.cookie != 'undefined') {
			var sidebarNav = $.cookie('sidebarNav')? JSON.parse($.cookie('sidebarNav')) : {};
			sidebarNav[id] = true;
			$.cookie('sidebarNav', JSON.stringify(sidebarNav), { path: baseUrl() });
		}
	})
	.on('hidden', function(e)
	{
		e.stopPropagation();
		
		$(this).parents('.hasSubmenu:first').removeClass('active');
		
		var id = $(this).attr("id");
		if (typeof $.cookie != 'undefined') {
			var sidebarNav = $.cookie('sidebarNav')? JSON.parse($.cookie('sidebarNav')) : {};
			sidebarNav[id] = false;
			$.cookie('sidebarNav', JSON.stringify(sidebarNav), { path: baseUrl() });
		}
	});
	
	// main menu visibility toggle
	$('.navbar.main .btn-navbar').click(function()
	{
		var disabled = typeof toggleMenuButtonWhileTourOpen != 'undefined' ? toggleMenuButtonWhileTourOpen(true) : false;
		if (!disabled)
			toggleMenuHidden();
	});
	
	// topnav toggle
	$('.navbar.main .toggle-navbar').click(function()
	{
		var that = $(this);
		
		if ($('.navbar.main .wrapper').is(':hidden'))
		{
			$(this).slideUp(20, function(){
				$('.navbar.main .wrapper').show();
				$('.navbar.main').animate({ height: 34 }, 200, function(){
					$('.navbar.main').toggleClass('navbar-hidden');
					that.slideDown();
				});
			});
		}
		else
		{
			$(this).slideUp(20, function(){
				$('.navbar.main').animate({ height: 0 }, 200, function(){
					$('.navbar.main .wrapper').hide();
					$('.navbar.main').toggleClass('navbar-hidden');
					that.slideDown();
				});
			});
		}
	});
	
	// multi-level top menu
	$('.submenu').hover(function()
	{
        $(this).children('ul').removeClass('submenu-hide').addClass('submenu-show');
    }, function()
    {
    	$(this).children('ul').removeClass('.submenu-show').addClass('submenu-hide');
    })
    .find("a:first").append(" &raquo; ");
	
	// tooltips
	$('[data-toggle="tooltip"]').tooltip();
	
	// popovers
	$('[data-toggle="popover"]').popover();
	
	// print
	$('[data-toggle="print"]').click(function(e)
	{
		e.preventDefault();
		window.print();
	});
	
	// collapsible widgets
	$('.widget[data-toggle="collapse-widget"] .widget-body')
		.on('show', function(){
			$(this).parents('.widget:first').attr('data-collapse-closed', "false");
		})
		.on('shown', function(){
			setTimeout(function(){ $(window).resize(); }, 500);
		})
		.on('hidden', function(){
			$(this).parents('.widget:first').attr('data-collapse-closed', "true");
		});
	
	$('.widget[data-toggle="collapse-widget"]').each(function()
	{
		// append toggle button
		$(this).find('.widget-head').append('<span class="collapse-toggle"></span>');
		
		// make the widget body collapsible
		$(this).find('.widget-body').addClass('collapse');
		
		// verify if the widget should be opened
		if ($(this).attr('data-collapse-closed') !== "true")
			$(this).find('.widget-body').addClass('in');
		
		// bind the toggle button
		$(this).find('.collapse-toggle').on('click', function(){
			$(this).parents('.widget:first').find('.widget-body').collapse('toggle');
		});
	});
	
	// show/hide toggle buttons
	$('[data-toggle="hide"]').click(function(){
		$($(this).attr('data-target')).toggleClass('hide');
		if ($(this).is('.scrollTarget') && !$($(this).attr('data-target')).is('.hide'))
			scrollTo($(this).attr('data-target'));
	});
	
	
	// handle menu position change
	$('#toggle-menu-position').on('change', function()
	{
		$('.container-fluid:first').toggleClass('menu-right');
		
		if ($(this).prop('checked')) 
			$('.container-fluid:first').removeClass('menu-left');
		else
			$('.container-fluid:first').addClass('menu-left');
		
		if (typeof $.cookie != 'undefined')
			$.cookie('rightMenu', $(this).prop('checked') ? $(this).prop('checked') : null);
		
		if (typeof resetResizableMenu != 'undefined' && typeof lastResizableMenuPosition != 'undefined')
		{
			resetResizableMenu(true);
			lastResizableMenuPosition();
		}
		removeMenuHiddenPhone();
	});
	
	// handle persistent menu position on page load
	if (typeof $.cookie != 'undefined' && $.cookie('rightMenu') && $('#toggle-menu-position').length)
	{
		$('#toggle-menu-position').prop('checked', true);
		$('.container-fluid:first').not('.menu-right').removeClass('menu-left').addClass('menu-right');
	}
	
	// handle layout type change
	$('#toggle-layout').on('change', function()
	{
		if ($(this).prop('checked'))
		{
			$('.container-fluid:first').addClass('fixed');
		}
		else
			$('.container-fluid:first').removeClass('fixed');
		
		if (typeof $.cookie != 'undefined')
		{
			$.cookie('layoutFixed', $(this).prop('checked') ? $(this).prop('checked') : null);
			$.cookie('layoutFluid', $(this).prop('checked') ? null : $(this).prop('checked'));
		}
	});
	
	// handle persistent layout type on page load
	if (typeof $.cookie != 'undefined' && $.cookie('layoutFixed') && $('#toggle-layout').length)
	{
		$('#toggle-layout').prop('checked', true);
		$('.container-fluid:first').addClass('fixed');
	}
	else if (!$('.container-fluid:first').is('.fixed') || (typeof $.cookie != 'undefined' && $.cookie('layoutFluid')))
	{
		$('#toggle-layout').prop('checked', false);
		$('.container-fluid:first').removeClass('fixed');
	}
	
	// handle persistent menu visibility on page load
	if (typeof $.cookie != 'undefined' && $.cookie('menuHidden') && $.cookie('menuHidden') == 'true' || (!$('.container-fluid').is('.menu-hidden') && !$('#menu').is(':visible')))
		toggleMenuHidden();
	else if ($('#menu').is(':visible'))
	{
		removeMenuHiddenPhone();
		
		if (typeof lastResizableMenuPosition != 'undefined') 
			lastResizableMenuPosition();
	}
	
	// menu slim scroll max height
	setTimeout(function()
	{
		var menu_max_height = parseInt($('#menu .slim-scroll').attr('data-scroll-height'));
		var menu_real_max_height = parseInt($('#wrapper').height());
		$('#menu .slim-scroll').slimScroll({
			height: (menu_max_height < menu_real_max_height ? (menu_real_max_height - 40) : menu_max_height) + "px",
			allowPageScroll : true,
			railDraggable: ($.fn.draggable ? true : false)
	    });
		
		if (Modernizr.touch)
			return; 
		
		// fixes weird bug when page loads and mouse over the sidebar (can't scroll)
		$('#menu .slim-scroll').trigger('mouseenter').trigger('mouseleave');
	}, 200);
	
	/* Slim Scroll Widgets */
	$('.widget-scroll').each(function(){
		$(this).find('.widget-body > div').slimScroll({
			height: $(this).attr('data-scroll-height')
	    });
	});
	
	/* Other non-widget Slim Scroll areas */
	$('#content .slim-scroll').each(function(){
		$(this).slimScroll({
			height: $(this).attr('data-scroll-height'),
			allowPageScroll : false,
			railDraggable: ($.fn.draggable ? true : false)
	    });
	});

	/* wysihtml5 */
	if ($('textarea.wysihtml5').size() > 0)
		$('textarea.wysihtml5').wysihtml5();
	
	/* Table select / checkboxes utility */
	$('.checkboxs thead :checkbox').change(function(){
		if ($(this).is(':checked'))
		{
			$('.checkboxs tbody :checkbox').prop('checked', true).parent().addClass('checked');
			$('.checkboxs tbody tr.selectable').addClass('selected');
			$('.checkboxs_actions').show();
		}
		else
		{
			$('.checkboxs tbody :checkbox').prop('checked', false).parent().removeClass('checked');
			$('.checkboxs tbody tr.selectable').removeClass('selected');
			$('.checkboxs_actions').hide();
		}
	});
	
	$('.checkboxs tbody').on('click', 'tr.selectable', function(e){
		var c = $(this).find(':checkbox');
		
		if (e.srcElement.nodeName == 'INPUT')
		{
			if (c.is(':checked'))
				$(this).addClass('selected');
			else
				$(this).removeClass('selected');
		}
		else if (e.srcElement.nodeName != 'TD' && e.srcElement.nodeName != 'TR' && e.srcElement.nodeName != 'DIV')
		{
			return true;
		}
		else
		{
			if (c.is(':checked'))
			{
				c.prop('checked', false).parent().removeClass('checked');
				$(this).removeClass('selected');
			}
			else
			{
				c.prop('checked', true).parent().addClass('checked');
				$(this).addClass('selected');
			}
		}
		if ($('.checkboxs tr.selectable :checked').size() == $('.checkboxs tr.selectable :checkbox').size())
			$('.checkboxs thead :checkbox').prop('checked', true).parent().addClass('checked');
		else
			$('.checkboxs thead :checkbox').prop('checked', false).parent().removeClass('checked');

		if ($('.checkboxs tr.selectable :checked').size() >= 1)
			$('.checkboxs_actions').show();
		else
			$('.checkboxs_actions').hide();
	});
	
	if ($('.checkboxs tbody :checked').size() == $('.checkboxs tbody :checkbox').size() && $('.checkboxs tbody :checked').length)
		$('.checkboxs thead :checkbox').prop('checked', true).parent().addClass('checked');
	
	if ($('.checkboxs tbody :checked').length)
		$('.checkboxs_actions').show();
	
	$('.radioboxs tbody tr.selectable').click(function(e){
		var c = $(this).find(':radio');
		if (e.srcElement.nodeName == 'INPUT')
		{
			if (c.is(':checked'))
				$(this).addClass('selected');
			else
				$(this).removeClass('selected');
		}
		else if (e.srcElement.nodeName != 'TD' && e.srcElement.nodeName != 'TR')
		{
			return true;
		}
		else
		{
			if (c.is(':checked'))
			{
				c.attr('checked', false);
				$(this).removeClass('selected');				
			}
			else
			{
				c.attr('checked', true);
				$('.radioboxs tbody tr.selectable').removeClass('selected');
				$(this).addClass('selected');
			}
		}
	});
	
	// sortable tables
	if ($( ".js-table-sortable" ).length)
	{	
		$( ".js-table-sortable" ).sortable(
		{
			placeholder: "ui-state-highlight",
			items: "tbody tr",
			handle: ".js-sortable-handle",
			forcePlaceholderSize: true,
			helper: function(e, ui) 
			{
				ui.children().each(function() {
					$(this).width($(this).width());
				});
				return ui;
			},
			start: function(event, ui) 
			{
				if (typeof mainYScroller != 'undefined') mainYScroller.disable();
				ui.placeholder.html('<td colspan="' + $(this).find('tbody tr:first td').size() + '">&nbsp;</td>');
			},
		    stop: function() { if (typeof mainYScroller != 'undefined') mainYScroller.enable(); }
		});
	}

	
	console.log("Completed running Ergo Admin scripts.");
	
	// -------------------------------------------------------------------
	// Custom scripts
	// -------------------------------------------------------------------
	
	console.log("Running custom scripts...");
	
	// Initialize DOM elements
	
	$(".add-on:not(.disabled)").click(function() {
		$(this).addClass("clicked").delay(100).queue(function(d) {
			$(this).removeClass("clicked");
			$(this).dequeue();
		});
	});
	
	$('#news').easyTicker({
		speed: 'slow',
		interval: 5000, // 5 secs
		height: '45px',
		mousePause: 1,
		controls:{
			up: '#news_next',
			down: '#news_prev',
			toggle: ''
		}
	});
	
	// Upcoming Auction table - start
	if ($("#landing_1 #upcomingAuctionsTable").size() > 0) {
		$("#landing_1 #upcomingAuctionsTable").dataTable({
			"bPaginate": true,
			"iDisplayLength": 2,
			"sPaginationType": "two_button",
			"bSort": false,
			"bFilter": false,
			"bLengthChange": false,
			"bInfo": false,
			"fnDrawCallback": function() {
				console.log("fnDrawCallback ENTRY");
				if (this.fnPagingInfo().iTotalPages == 1) {
					$("#landing_1 #upcomingAuctionsTable" + "_paginate").parent().parent().hide();
				}
				console.log("fnDrawCallback EXIT");
			}
		});
		$("#landing_1 #upcomingAuctionsTable" + "_paginate")
			.removeClass("paging_two_button")
			.addClass("paging_bootstrap pagination");
		$("#landing_1 #upcomingAuctionsTable" + "_wrapper div:first").remove();
	} else {
		//	Differently formatted data table if not on landing page
		$("#upcomingAuctionsTable").dataTable(defaultDataTableConfig);
	}
	// Upcoming Auction table - end
	
	
	$(".tab_links a").click(function(e){
		$(".tab_links a").removeClass('active');
		$(this).addClass('active');
	});
	
	if ($("#loginForm #error").size() > 0) {
		var errorText = $("#loginForm #error").text();
		$("#loginForm #error").remove();
		notyfy({ text: errorText, type: "error"});
	}
	
	bindBidPageButtons("#auction");
	
	/**
	 * Ajax form submit for forgot password
	 */
	$("#forgotPasswordModal").on("shown.bs.modal", function() {
		var bind = function() {
			console.log("Binding Forgot Password form elements...");
			$("#forgotPassword").submit(function(e) {
				console.log("Forgot Password form submitted");
				var url = $(this).attr("action");
				$.post(url, $(this).serialize(), function(data) {
					console.log("Success handler called.");
					$("#forgotPasswordModal .modal-body").html(data);
					bind();
				});
				e.preventDefault();
			});
		};
		bind();
	});
	
	/**
	 * Ajax form submit for change password
	 */
	$("#changePasswordModal").on("shown.bs.modal", function() {
		var bind = function() {
			console.log("Binding Change Password form elements...");
			$("#changePassword").submit(function(e) {
				console.log("Change Password form submitted");
				var url = $(this).attr("action");
				$.post(url, $(this).serialize(), function(data) {
					console.log("Success handler called.");
					$("#changePasswordModal .modal-body").html(data);
					bind();
				});
				e.preventDefault();
			});
		};
		bind();
	});
	
	$("#galleryModal").on("shown.bs.modal", function() {
		console.log("gallery displayed");
		$(".carousel").carousel();
	});
	
	// Refresh widgets
	$("#auction").refreshWidget({
		refreshOnLoad: false,
		replaceSelector: "#auction-list",
		postLoad: function() {
			bindBidPageButtons("#auction");
			// Hack for stylish checkbox
			$("#auction .uniformjs").find("select, input, button, textarea").uniform();
			// Hack for stylish tooltips
			$('#auction [data-toggle="tooltip"]').tooltip();

		}
	});
	$("#dashboard-running").refreshWidget();
	
	// handle persistent sidebar menu
	if (typeof $.cookie != 'undefined' && $.cookie('sidebarNav')) {
		console.log("Reading sidebarNav states from Cookie...");
		var sidebarNav = JSON.parse($.cookie('sidebarNav'));
		$.each(sidebarNav, function(id, obj) {
			if (obj) $("#" + id).collapse("show");
		});
	}
	// Highlight dashboard when activated
	if (window.location.pathname == $("#menu .menu-0 li:first a").attr("href")) {
		$("#menu .menu-0 li:first").addClass("active");
	}
	
	console.log("Completed running custom scripts.");
	
};

/*
 * ---------------------------------------------------------------------
 * Document ready
 * ---------------------------------------------------------------------
 */
$(function() {
	init();
});