/**
 * Custom JavaScripts
 */
$(function() {
	console.log("Running custom scripts...");
	
	// Selectors
	var SEL_UPCOMINGAUCTIONS = "#upcomingAuctionsTable";
	var SEL_LANDINGPAGE_UPCOMINGAUCTIONS = "#landing_1 " + SEL_UPCOMINGAUCTIONS;
	var SEL_LANDINGPAGE_TABLINKS = ".tab_links a";

	var SEL_ATTACHMENTS_DIV = "ul.attachments";
	var SEL_ATTACHMENTS_LINK = "a.attachments";
	var SEL_PLUSBUTTON = ".add-on.plus";
	var SEL_MINUSBUTTON = ".add-on.minus";
	var SEL_ADDONBUTTONS = ".add-on:not(.disabled)"; 
	var AUCTION_REFRESH_INTERVAL = 1000 * 60 * 5;	// Refresh every 5 minutes
	var DASHBOARD_AUCTION_REFRESH_INTERVAL = 1000 * 60 * 5;	// Refresh every 5 minutes
	
	// utility functions

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
	
	$(SEL_ADDONBUTTONS).click(function() {
		$(this).addClass("clicked").delay(100).queue(function(d) {
			$(this).removeClass("clicked");
			$(this).dequeue();
		});
	});
	
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
	docuploader.init();
	
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
	imageuploader.init();
	
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
	
	// Configurations
	var defaultDataTableConfig = {
		"sPaginationType": "bootstrap",
		"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
		"oLanguage": {
			"sLengthMenu": "_MENU_ records per page"
		}
	};
	
	// Upcoming Auction table - start
	if ($(SEL_LANDINGPAGE_UPCOMINGAUCTIONS).size() > 0) {
		$(SEL_LANDINGPAGE_UPCOMINGAUCTIONS).dataTable({
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
					$(SEL_LANDINGPAGE_UPCOMINGAUCTIONS + "_paginate").parent().parent().hide();
				}
				console.log("fnDrawCallback EXIT");
			}
		});
		$(SEL_LANDINGPAGE_UPCOMINGAUCTIONS + "_paginate")
			.removeClass("paging_two_button")
			.addClass("paging_bootstrap pagination");
		$(SEL_LANDINGPAGE_UPCOMINGAUCTIONS + "_wrapper div:first").remove();
	} else {
		//	Differently formatted data table if not on landing page
		$(SEL_UPCOMINGAUCTIONS).dataTable(defaultDataTableConfig);
	}
	// Upcoming Auction table - end
	
	$(".dynamicTable").dataTable(defaultDataTableConfig);
	
	$(".datetimepicker").datetimepicker({
		format: "MM d, yyyy H:ii P",
		autoclose: true
	});
	
	
	$(SEL_LANDINGPAGE_TABLINKS).click(function(e){
		$(SEL_LANDINGPAGE_TABLINKS).removeClass('active');
		$(this).addClass('active');
	});
	
	if ($("#loginForm #error").size() > 0) {
		var errorText = $("#loginForm #error").text();
		$("#loginForm #error").remove();
		notyfy({ text: errorText, type: "error"});
	}
	
	var refreshBids = function() {
		console.log("Refreshing auctions...");
		
		var $refreshBtn = $("#auction-refresh");
		var url = $refreshBtn.attr("href");
		
		$("#auction-refresh").addClass("disabled");
		$("#auction-refresh-tip").hide();
		$("#auction-refresh-loader").show();
		$("#auction-list").fadeOut();
		
		$("#auction-list").load(url + " #auction-list", function() {
			console.log("Auctions refreshed.");
			
			bindBidPageButtons("#auction");
			
			// Hack for stylish checkbox
			$("#auction .uniformjs").find("select, input, button, textarea").uniform();
			// Hack for stylish tooltips
			$('#auction [data-toggle="tooltip"]').tooltip();
			
			$refreshBtn.removeClass("disabled");
			$("#auction-list").fadeIn();
			$("#auction-refresh-tip").show();
			$("#auction-refresh-loader").hide();
		});
	};
	
	function bindBidPageButtons(obj) {
		
		$(obj + " " + SEL_ATTACHMENTS_LINK).click(function() {
			$(this).parent().parent().parent().find(SEL_ATTACHMENTS_DIV).slideToggle();
		});
		
		$(obj + " " + SEL_PLUSBUTTON).click(function () {
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
		$(obj + " " + SEL_MINUSBUTTON).click(function () {
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
		
		$(obj + " " + "button.bid").click(function() {
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
	bindBidPageButtons("#auction");
	setInterval(refreshBids, AUCTION_REFRESH_INTERVAL);
	$("#auction-refresh-tip-interval").text(Math.floor(AUCTION_REFRESH_INTERVAL / (60 * 1000)));
	$("#auction-refresh-tip").show();
	$("#auction-refresh-loader").hide();
	
	$("#auction-refresh").click(function() {
		refreshBids();
		return false;
	});
	
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
	
	var refreshRunningAuctions = function () {
		
		$("#dashboard-running-refresh").addClass("disabled");
		$("#dashboard-running-refresh-tip").hide();
		$("#dashboard-running-refresh-loader").show();
		$("#dashboard-running-list").fadeOut();
		
		$("#running .widget-body").load($("#running").attr("data-url"), function() {
			$("#dashboard-running-refresh").removeClass("disabled");
			$("#dashboard-running-list").fadeIn();
			$("#dashboard-running-refresh-tip").show();
			$("#dashboard-running-refresh-loader").hide();
		});
	};
	setInterval(refreshRunningAuctions, DASHBOARD_AUCTION_REFRESH_INTERVAL);
	refreshRunningAuctions();
	$("#dashboard-running-refresh-tip-interval").text(Math.floor(DASHBOARD_AUCTION_REFRESH_INTERVAL / (60 * 1000)));
	$("#dashboard-running-refresh-tip").show();
	$("#dashboard-running-refresh-loader").hide();
	
	$("#dashboard-running-refresh").click(function() {
		refreshRunningAuctions();
		return false;
	});
	
	console.log("Successfully completed custom scripts...");
});