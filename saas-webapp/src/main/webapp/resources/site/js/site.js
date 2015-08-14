function removeAttachment(attachmentId, componentId, url) {
	var dataObject = $("#" + componentId).attr("dataObject");
	var dataTableField = $("#" + componentId).attr("dataTableField");
	var param = "";
	if (typeof (dataObject) != "undefined" && dataObject != "") {
		param = "dataObjectId=" + dataObject;
	}
	if (typeof (dataTableField) != "undefined" && dataTableField != "") {
		param = param + "&dataTableFieldId=" + dataTableField;
	}

	// update upload-size
	var uploadSizeEle = "#upload_size_" + dataTableField;
	var uploadSize = $(uploadSizeEle).val();
	uploadSize = parseInt(uploadSize) - 1;
	$(uploadSizeEle).attr("value", uploadSize);
	jQuery.ajax({
		type : 'get',
		data : param,
		url : url + "/" + "remove" + "/" + attachmentId,
		success : function() {
			$("#" + attachmentId).remove();
			if ($("#" + componentId).children().length == 0) {
				$("#" + componentId).remove();
			}
		}
	});

};

function retrieveGroups(tenantId, target) {
	$("#" + target).empty();
	$("#" + target).append("<option value=''></option>");
	$("#" + target).select2("val", "");
	if (tenantId != '') {
		jQuery.ajax({
			type : 'post',
			url : SaaSApp.contextPath + '/partnership/group/list/'
					+ tenantId,
			success : function(data) {
				if (data.success) {
					$.each(data.object, function(i, item) {
						$("#" + target).append(
								"<option value='" + item.id + "'>" + item.name
										+ "</option>");
					});
				}
			}
		});
	}
}

function retrieveOutCome(ruleAction, target) {
	$("#" + target).empty();
	$("#" + target).select2("val", "");
	if (ruleAction != '') {
		jQuery.ajax({
			type : 'post',
			// 1 will not be used in backend, just hardcode as a placehold.
			url : SaaSApp.contextPath
					+ '/1/datatableruledefinition/outcome/list/' + ruleAction,
			success : function(data) {
				$.each(data, function(i, item) {
					$("#" + target).append(
							"<option value='" + item.value + "'>" + item.key
									+ "</option>");
				});
			}
		});
	}
}
function removeSelfFromOptions(value, parentId) {
	$("#" + parentId + " option").each(function() {
		if (value === $(this).val()) {
			$(this).remove();
		}
	});
}

/*
 * * function submitBtn() { $("#submit").click(function () { var fail_msg =
 * 'Record saved failed.'; var suc_msg = 'Record saved successful.'; var action =
 * $('#form').attr('action'); jQuery.ajax({ type: 'post', url: action, data:
 * $('#form').serializeArray(), headers: {'X-Requested-With': 'XMLHttpRequest'},
 * success: function (data1) { $('#content-container').replaceWith(data1); if
 * ($(data1).find(".error").length == 0) { $("<div class='alert alert-success'><button
 * type=button' class='close' data-dismiss='alert'>&times;</button>" + suc_msg + "</div>").insertAfter($("legend")[0]); } },
 * error: function () { $("<div class='alert alert-error'><button type=button'
 * class='close' data-dismiss='alert'>&times;</button>" + fail_msg + "</div>").insertAfter($("legend")[0]); }
 * 
 * }); }); }
 * 
 * function cancelBtn() { $("#cancel").click(function () { listEntity(); }); }
 * 
 * function editBtn() { $("#edit").click(function () { var id =
 * $(this).attr("entity_id"); editEntity(id); }); }
 */

function refreshGrid(gid) {
	$('#' + gid).data("kendoGrid").dataSource.read();
}

function listEntity() {
	operateEntity("", "get", "");
}

function createEntity() {
	operateEntity("create", "get", "");
}

function showEntity(id) {
	operateEntity("show", "get", id);
}

function editEntity(id) {
	operateEntity("update", "get", id);
}

function deleteEntity(id) {
	operateEntity("delete", "get", id);
}

function operateEntity(operation, type, id) {
	var url;
	if (id === '') {
		url = operation;
	} else {
		url = operation + "/" + id;
	}
	// jQuery.ajax({
	// type: type,
	// url: url,
	// headers: {'X-Requested-With': 'XMLHttpRequest'},
	// success: function (data1) {
	// $('#content-container').replaceWith(data1);
	// }
	// });

	document.location = url;
}

function saveBtn() {
	$("#Submit").click(function() {
		$("#form").submit();
	});
}

function editBtn() {
	$("#edit").click(function() {
		var id = $(this).attr("entity_id");
		var url = document.location + "";
		for ( var i = 0; i < 2; i++) {
			url = url.substring(0, url.lastIndexOf("/"));
		}
		document.location = url + "/update/" + id;
	});
}

function cancleBtn() {
	$("#cancel").click(function() {
		var url = document.location + "";
		var lastPath = url.substring(url.lastIndexOf("/") + 1, url.length);
		if (isNaN(parseInt(lastPath))) {
			url = url.substring(0, url.lastIndexOf("/"));
		} else {
			for ( var i = 0; i < 2; i++) {
				url = url.substring(0, url.lastIndexOf("/"));
			}
		}
		document.location = url + "/list";
	});
}

$(function() {
	saveBtn();
	cancleBtn();
	editBtn();
});

function loadModal($scope) {

	// cleanup modal content
	$scope.find('.modal').on('hidden', function() {
		$(this).removeData('modal');
	});

	$scope.find('.modal').on('shown', function() {
		// initialize field
		loadFields($(this));
	});

	// popoup modal content
	$scope.find(".modal-create").each(
			function() {
				var target_modal = $(this).attr('target_modal');
				var create_url = $("#" + target_modal).attr("create_url");
				$(this).attr("data-toggle", "modal").attr("data-remote",
						create_url).attr("data-target", "#" + target_modal);
			});

	$scope
			.find(".modal")
			.each(
					function() {
						var edit_url = $(this).attr('edit_url');
						var delete_url = $(this).attr('delete_url');
						var default_url = $(this).attr('default_url');
						var target_tbl = $(this).attr('target_tbl');
						var $this = $(this);
						$("#" + target_tbl + " .row-edit").click(function() {
							var row_id = $(this).attr("row_id");
							$this.modal({
								remote : edit_url + "/" + row_id
							});
						});
						$("#" + target_tbl + " .row-delete")
								.click(
										function() {
											var row_id = $(this).attr("row_id");
											jQuery
													.ajax({
														type : 'post',
														url : delete_url,
														data : 'id=' + row_id,
														success : function(data) {
															jQuery
																	.ajax({
																		type : 'post',
																		url : default_url,
																		headers : {
																			'X-Requested-With' : 'XMLHttpRequest'
																		},
																		success : function(
																				data) {
																			$(
																					'#content-container')
																					.replaceWith(
																							data);
																		}
																	});
														}
													});
										});

						$($this)
								.delegate(
										".modal_submit",
										"click",
										function(e) {
											e.preventDefault();
											var form = $this.find("form")[0];
											jQuery
													.ajax({
														type : 'post',
														url : $(form).attr(
																"action"),
														data : $(form)
																.serialize(),
														headers : {
															'X-Requested-With' : 'XMLHttpRequest'
														},
														success : function(data) {
															if ($(data).find(
																	".error").length > 0) {
																$($this)
																		.children(
																				".modal-body")
																		.html(
																				data);
																loadFields($this);
															} else {
																$($this).modal(
																		'hide');
																$(
																		'#content-container')
																		.replaceWith(
																				data);
															}
														},
														error : function() {
															$($this).modal(
																	'hide');
															$(
																	"<div class='alert alert-error'><button type=button' class='close' data-dismiss='alert'>&times;</button>Something Wrong</div>")
																	.insertAfter(
																			$("legend")[0]);
														}

													});// end jQuery.ajax

										});
					});

}

function loadFields($scope) {
	// date component initialization
	$scope.find("[data-type=date]").kendoDatePicker({
		format : "dd/MM/yyyy"
	});

	// datetime component initialization
	$scope.find("[data-type=datetime]").kendoDateTimePicker({
		format : "dd/MM/yyyy HH:mm"
	});

	// editor component initialization
	$scope.find("[data-type*=editor]").each(
			function(i, e) {
				var display = $(this).attr("data-type").contains("readonly");
				$(this).kendoEditor();
				if (display == true) {
					$(".k-content").contents().find("body").attr(
							"contenteditable", "false");
					$(".k-editor-toolbar-wrap").remove();
				}
			});
	// select component initialization
/*	$scope.find("[data-type=select]").select2({
		width : 'element',
		allowClear : true
	});*/

	// upload component initialzation
	$scope
			.find("[data-type=upload]")
			.each(
					function(i, e) {
						var name = $(e).attr("name");
						var url = SaaSApp.contextPath + "/" + "attachment";
						// $.ajaxSetup({ cache: false });

						$(e)
								.kendoUpload(
										{
											async : {
												type : "POST",
												saveUrl : url + "/" + "save"
														+ "/" + name,
												removeUrl : url + "/"
														+ "remove" + "/" + name,
												autoUpload : true
											},
											localization : {
												"select" : "Select"
											},
											upload : function(evt) {
												var files = evt.files;
												var dataFileSize = $(e).attr(
														"data_max_filesize");
												var dataFileType = $(e).attr(
														"data_file_type"); // format
												// like:
												// .jsp.xml
												var dataMaxUpload = $(e).attr(
														"data_max_upload");
												var uploadSizeEle = "#upload_size_"
														+ $(e)
																.attr(
																		"dataTableField");
												var uploadSize = $(
														uploadSizeEle).val();

												if (typeof (uploadSize) != "undefined"
														&& typeof ($(e)
																.attr("data_max_upload")) != "undefined") {
													if (0 != parseInt(dataMaxUpload)
															&& parseInt(uploadSize) >= parseInt(dataMaxUpload)) {
														evt.preventDefault();
														$(this).modal('hide');
														$(
																"<div class='alert alert-error'><button type=button' class='close' data-dismiss='alert'>&times;</button>The maximum number of attachment to upload is "
																		+ dataMaxUpload
																		+ ".</div>")
																.insertAfter(
																		$("legend")[0]);
													}
												}

												$
														.each(
																files,
																function(index,
																		value) {
																	// Check the
																	// extension
																	// of each
																	// file and
																	// abort the
																	// upload if
																	// it is not
																	// data_file_type.
																	if (typeof ($(e)
																			.attr("data_file_type")) != "undefined"
																			&& dataFileType != ''
																			&& dataFileType
																					.indexOf(value.extension) == -1) {
																		evt
																				.preventDefault();
																		$(this)
																				.modal(
																						'hide');
																		$(
																				"<div class='alert alert-error'><button type=button' class='close' data-dismiss='alert'>&times;</button>Only "
																						+ dataFileType
																						+ " files can be uploaded.</div>")
																				.insertAfter(
																						$("legend")[0]);
																	}
																	// Check the
																	// size of
																	// each file
																	// and abort
																	// the
																	// upload if
																	// it is
																	// exceed
																	// data_max_filesize
																	// bytes.
																	if (typeof ($(e)
																			.attr("data_max_filesize")) != "undefined"
																			&& dataFileSize != ''
																			&& value.size > parseInt(dataFileSize)) {
																		if (0 != parseInt(dataFileSize)) {
																			evt
																					.preventDefault();
																			$(
																					this)
																					.modal(
																							'hide');
																			$(
																					"<div class='alert alert-error'><button type=button' class='close' data-dismiss='alert'>&times;</button>"
																							+ value.name
																							+ " has exceeded the configured maximum file size("
																							+ dataFileSize
																							+ " bytes).</div>")
																					.insertAfter(
																							$("legend")[0]);
																		}
																	}
																});
											},
											success : function(evt) {
												if ("upload" == evt.operation) {
													var files = evt.files;
													var uploadSizeEle = "#upload_size_"
															+ $(e)
																	.attr(
																			"dataTableField");
													var uploadSize = $(
															uploadSizeEle)
															.val();

													for (i = 0; i < files.length; i++) {
														files[i].name = "${currentPath}";
														// update upload-size
														uploadSize = parseInt(uploadSize) + 1;
														$(uploadSizeEle).attr(
																"value",
																uploadSize);
													}
												}
											},
											remove : function(evt) {
												// update upload-size
												var uploadSizeEle = "#upload_size_"
														+ $(e)
																.attr(
																		"dataTableField");
												var uploadSize = $(
														uploadSizeEle).val();

												if (uploadSize != '0') {
													uploadSize = parseInt(uploadSize) - 1;
													$(uploadSizeEle)
															.attr("value",
																	uploadSize);
												}
											},
											error : function(evt) {
												$(this).modal('hide');
												$(
														"<div class='alert alert-error'><button type=button' class='close' data-dismiss='alert'>&times;</button>File operation("
																+ evt.operation
																+ ") failed.</div>")
														.insertAfter(
																$("legend")[0]);
											}
										});
					});

	$scope
			.find(".k-upload-files")
			.each(
					function(i, e) {
						var dataType = $(e).attr("data-type");
						if (dataType == "download") {
							var url = SaaSApp.contextPath + "/"
									+ "attachment";
							var eleId = $(e).attr("id");
							var operation = $(e).attr("operation");
							var items = $(e).attr("items"); // attachment ids
							var param = '';
							if (typeof (items) != "undefined" && items != "") {
								param = 'items=' + items;
							}
							// clean session when valid entity failure
							var validResult = $(e).attr("validResult");
							if (validResult == "false") {
								var path = $(e).attr("path");
								$.ajax({
									url : url + "/" + "clean" + "/" + path,
									type : 'get',
								});
							}
							$
									.ajax({
										url : url + "/" + "retrieve",
										type : 'post',
										data : param,
										success : function(data, status) {
											if (0 == data.length) {
												$(e).remove();
											} else {
												$
														.each(
																data,
																function() {
																	var innerHtml = "<li class='k-file' id='"
																			+ this.key
																			+ "'>"
																			+ "<a href="
																			+ url
																			+ "/"
																			+ "download/"
																			+ this.key
																			+ ">"
																			+ this.value
																			+ "</a>"
																			+ "<span>"
																			+ "<button class=\"k-button k-button-icontext k-upload-action\" type=\"button\" "
																			+ "onclick=\"removeAttachment('"
																			+ this.key
																			+ "', '"
																			+ eleId
																			+ "', '"
																			+ url
																			+ "');\""
																			+ ">Remove</button>"
																			+ "</span></li>";
																	$(e)
																			.append(
																					$(innerHtml));
																});
											}
											// update upload-size
											if (typeof (operation) != "undefined"
													&& operation == "update") {
												var uploadSizeEle = "#upload_size_"
														+ $(e)
																.attr(
																		"dataTableField");
												var uploadSize = $(
														uploadSizeEle).val();
												uploadSize = data.length;
												$(uploadSizeEle).attr("value",
														uploadSize);
											}
										}
									});
						}

					});

	$scope.find("[data-type=reference]").each(function(i, e) {
		renderReference(e)
	});

	// table component initializtion
	$scope.find("[data-type=table]").kendoGrid({
		height : 250,
		sortable : true

	});

	$scope.find(".k-grid-header-wrap").find("colgroup col:last").css({
		width : '130px'
	});
	$scope.find(".k-grid-content").find("colgroup col:last").css({
		width : '130px'
	});
}

function renderReference(e) {

	var isClient = $(e).attr("client");
	var entityName = $(e).attr("entityName");
	var url = SaaSApp.contextPath + "/" + "finder" + "/" + entityName;
	var condition = $(e).attr("condition");
	if (isClient === 'true') {
		var data = 'client=true';
		if (condition != '' && typeof (condition) != "undefined")
			data = data + '&condition=' + condition;
		if ($(e).attr("categorySupport")) {
			var category = $(e).attr("category");
			if (typeof (category) != "undefined") {
				data = data + '&category=' + category;
			} else {
				category = $('#reference_category').val();
				if (typeof (category) != "undefined") {
					if (category == 0) {
						// ignore All Categories Option.
					} else {
						data = data + '&category=' + category;
					}
				}
			}
		}
		// reference client side load
		$.ajax({
			url : url,
			type : 'post',
			data : data,
			success : function(data, status) {
				var options = [];
				$.each(data, function() {
					options.push({
						id : this.key,
						text : this.value
					});
				});

				$(e).select2({
					initSelection : function(element, callback) {
						var id = $(element).val();
						if (id !== "") {
							$.ajax(url + "/" + id).done(function(data) {
								callback({
									id : data.key,
									text : data.value
								});
							});
						}
					},
					allowClear : true,
					data : options,
					width : 'element'
				});
			}

		});
	} else {
		// reference server side loader
		$(e)
				.select2(
						{
							allowClear : true,
							initSelection : function(element, callback) {
								var id = $(element).val();
								if (id !== "") {
									$.ajax(url + "/" + id).done(function(data) {
										callback({
											id : data.key,
											text : data.value
										});
									});
								}
							},
							minimumInputLength : 3,
							ajax : {
								type : 'post',
								url : url,
								quietMillis : 200,
								data : function(term, page) {
									var result = "term=" + term;
									var category = $(e).attr("category");
									if (condition != '')
										result = result + '&condition='
												+ condition;
									if (category != ''
											&& typeof ($(e).attr("category")) != "undefined")
										result = result + '&category='
												+ category;
									return result;
								},
								results : function(data, page) {
									var options = [];
									$.each(data, function() {
										options.push({
											id : this.key,
											text : this.value
										});
									});
									return {
										results : options
									};
								}
							},
							width : 'element'
						});

	}

}
function initializeDataSource() {

	return new kendo.data.DataSource({

		transport : {

			read : {
				url : "read1",
				dataType : "json",
				contentType : "application/json",
				type : "POST"
			},
			destroy : {
				url : "delete",
				type : "POST"
			},

			parameterMap : function(options, type) {
				if (type == "destroy")
					return "id=" + options.id;
				if (options.filter) {
					for ( var i = 0; i < options.filter.filters.length; i++) {
						if (options.filter.filters[i].value instanceof Date) {
							options.filter.filters[i].value = kendo.toString(
									options.filter.filters[i].value,
									"dd/MM/yyyy HH:mm");
						}
					}
				}
				return JSON.stringify(options);

			}
		},
		pageSize : 25,
		serverPaging : true,
		serverSorting : true,
		serverFiltering : true,
		// autoSync: true,
		schema : {
			model : {
				id : "id",
				fields : {
					id : {
						field : "id"
					},
					updatedDate : {
						type : "date",
						parse : function(data) {
							return kendo.parseDate(data, "dd/MM/yyyy HH:mm")
						}
					},
					effectiveDate : {
						type : "date",
						parse : function(data) {
							return kendo.parseDate(data, "dd/MM/yyyy HH:mm")
						}
					},
					expiryDate : {
						type : "date",
						parse : function(data) {
							return kendo.parseDate(data, "dd/MM/yyyy HH:mm")
						}
					}
				}
			},
			data : "aaData",
			total : "total"
		}
	});
}

function categoryOnChange(e) {
	var categoryId = e.value;
	if ($("#type").length > 0) {
		if (categoryId == 0) {
			categoryId = "";
		}
		$("#type").select2("val", "");
		$("#type").attr("category", categoryId);
		$("#type").select2("destroy");
		$("#type").removeClass("select2-offscreen");

		renderReference($("#type"));
	}
}

function changeDataObjectType() {
	changeDataObjectStatus();
}
function changeDataObjectStatus() {
	jQuery
			.ajax({
				type : 'post',
				url : $('#form').attr("action"),
				data : $('#form').serializeArray(),
				headers : {
					'X-Requested-With' : 'XMLHttpRequest',
					'Submit_Request' : 'false'
				},
				success : function(data1) {
					$('#content-container').replaceWith(data1);
				},
				error : function() {
					$(
							"<div class='alert alert-error'><button type=button' class='close' data-dismiss='alert'>&times;</button>"
									+ fail_msg + "</div>").insertAfter(
							$("legend")[0]);
				}
			});
}

/**
 * init 2 modals on page: a.session reminder b.session timeout
 */
function sessionReminder() {
	$('#_session_modal').modal('hide');
	$('#_session_modal_timeout').modal('hide');
	if (typeof (timeoutReminder) !== 'undefined') {
		clearTimeout(timeoutReminder);
	}
	if (typeof (timeoutInformation) !== 'undefined') {
		clearTimeout(timeoutInformation);
	}
	if (!SaaSApp.isLogin)
		return;
	var _var1 = SaaSApp.timeoutInterval;
	var _var2 = SaaSApp.timeoutLogout;
	var _seconds = eval(_var1 - _var2 * 60);
	timeoutReminder = setTimeout(function() {
		$('#_session_modal').modal('show');
	}, _seconds * 1000);
	timeoutInformation = setTimeout(function() {
		clearTimeout(timeoutReminder);
		$('#_session_modal').modal('hide');
		$('#_session_modal_timeout').modal('show');
	}, _var1 * 1000);
}

/**
 * extend session expired time
 */
function refreshSession() {
	jQuery.ajax({
		type : 'get',
		url : SaaSApp.contextPath + "/ping"
	});
}

/**
 * 
 * this method is used to initalize chart defined on the page.
 * 
 */
function loadCharts() {
	$(".chart").each(
			function(i, e) {
				var id = $(e).attr("id");
				var url = $(e).attr("data-url");
				jQuery.ajax({
					type : 'post',
					url : url,
					success : function(data) {
						columnInit(id, data.title, data.subtitle, data.ytitle,
								data.categories, data.unit, data.series);
					},
					error : function() {
					}
				});

			});
}

/**
 * 
 * remove load... after request complete
 * 
 */
$(document).ajaxComplete(function() {
	$('.k-loading-mask').remove();
});

/**
 * 
 * to add loading... click on submit button
 * 
 */
function bindSubmitEvent() {
	$(".btn-primary")
			.on(
					"click",
					function() {
						$("#content-container")
								.append(
										'<div class="k-loading-mask" style="width: 100%; height: 100%; top: 0px; left: 0px;"><span class="k-loading-text">Loading...</span><div class="k-loading-image"></div><div class="k-loading-color"></div></div>');
					});
}

function retrieveRecallQuestion(btnEle, sourceEle, questionDiv, answerDiv,
		questionEle, usernameEmptyMsg, usernameNotExistMsg, noQuestionMsg) {
	var username = $(sourceEle).val();
	var spanHelpEle = $(sourceEle).next();
	username = username.replace(/^\s+|\s+$/g, '');
	if (username.length == 0) {
		if ($(spanHelpEle).children().length > 1) {
			$(btnEle).prev().remove();
		}
		$(sourceEle).focus();
		$(spanHelpEle).prepend(
				"<label style='color:red'>" + usernameEmptyMsg + "</label>");
		return;
	}

	$.ajax({
		type : 'post',
		url : SaaSApp.contextPath
				+ "/forgetpassword/retrieveRecallQuestion",
		data : "username=" + username,
		success : function(data) {
			if ($(spanHelpEle).children().length > 1) {
				$(btnEle).prev().remove();
			}
			if ("0" == data) {
				$(sourceEle).focus();
				$(spanHelpEle).prepend(
						"<label style='color:red'>" + usernameNotExistMsg
								+ "</label>");
				return;
			} else if ("1" == data) {
				$(spanHelpEle).prepend(
						"<label style='color:red'>" + noQuestionMsg
								+ "</label>");
				return;
			} else if (data) {
				$(questionDiv).attr("style", "display:");
				$(answerDiv).attr("style", "display:");
				$(questionEle).text(data);
				$(spanHelpEle).remove();
			}
		}
	});
}

function checkRecallAnswer(btnEle, usernameEle, sourceEle, passwordDiv,
		passwordConfDiv, answerEmptyMsg, answerIncorrectMsg) {
	var username = $(usernameEle).val();
	var recallanswer = $(sourceEle).val();
	var spanHelpEle = $(sourceEle).next();
	username = username.replace(/^\s+|\s+$/g, '');
	recallanswer = recallanswer.replace(/^\s+|\s+$/g, '');
	if (recallanswer.length == 0) {
		if ($(spanHelpEle).children().length > 1) {
			$(btnEle).prev().remove();
		}
		$(sourceEle).focus();
		$(spanHelpEle).prepend(
				"<label style='color:red'>" + answerEmptyMsg + "</label>");
		return;
	}

	$.ajax({
		type : 'post',
		url : SaaSApp.contextPath + "/forgetpassword/checkRecallAnswer",
		data : "username=" + username + "&recallanswer=" + recallanswer,
		success : function(data) {
			if ($(spanHelpEle).children().length > 1) {
				$(btnEle).prev().remove();
			}
			if ("true" == data) {
				$(passwordDiv).attr("style", "display:");
				$(passwordConfDiv).attr("style", "display:");
				$(spanHelpEle).remove();
			} else {
				$(sourceEle).focus();
				$(spanHelpEle).prepend(
						"<label style='color:red'>" + answerIncorrectMsg
								+ "</label>");
			}
		}
	});
}

function resetPassword(btnEle, usernameEle, passwordEle, passConfEle,
		passEmptyMsg, passNotMatchMsg) {
	var username = $(usernameEle).val();
	var password = $(passwordEle).val();
	var passConf = $(passConfEle).val();
	var spanHelpEle = $(passConfEle).next()

	username = username.replace(/^\s+|\s+$/g, '');
	password = password.replace(/^\s+|\s+$/g, '');
	passConf = passConf.replace(/^\s+|\s+$/g, '');

	if ($(spanHelpEle).children().length > 1) {
		$(btnEle).prev().remove();
	}
	if (password.length == 0) {
		$(passwordEle).focus();
		$(passwordEle).next().empty();
		$(passwordEle).next().append(
				"<label style='color:red'>" + passEmptyMsg + "</label>");
		return;
	} else if (password == passConf) {
		$.ajax({
			type : 'post',
			url : SaaSApp.contextPath + '/forgetpassword/resetpassword',
			data : "username=" + username + "&newpassword=" + password,
			success : function(data) {
				location.href = "${pageContext.request.contextPath}/login";
			}
		});
	} else {
		$(passwordEle).focus();
		$(passwordEle).next().empty();
		$(passwordEle).next().append(
				"<label style='color:red'>" + passNotMatchMsg + "</label>");
		$(spanHelpEle).prepend(
				"<label style='color:red'>" + passNotMatchMsg + "</label>");
	}
	return false;
}

// Report Tempalte Start
function populateOptis() {
	var viewId = $("#view").val();
	if (viewId == '') {
		viewId = 'undefined';
	}
	jQuery.ajax({
		type : 'get',
		url : SaaSApp.contextPath + '/reporttemplate/criteria/list/'
				+ viewId,
		success : function(data) {
			$.each(data, function(index, list) {
				if (index == 0) {
					$.each(list, function(index1, field) {
						fieldOptions = fieldOptions + '<option value='
								+ field.value + '>' + field.key + '</option>';
					});
				} else if (index == 1) {
					$.each(list, function(index1, field) {
						operatorOptions = operatorOptions + '<option value='
								+ field.value + '>' + field.key + '</option>';
					});
				} else if (index == 2) {
					$.each(list, function(index1, field) {
						logicOptions = logicOptions + '<option value='
								+ field.value + '>' + field.key + '</option>';
					});
				} else if (index == 3) {
					fieldTypes = list;
				} else if (index == 5) {
					$.each(list, function(index1, field) {
						sortFieldOptions = sortFieldOptions + '<option value='
								+ field.value + '>' + field.key + '</option>';
					});
				}
			});
		}
	});
}

function formatRowStr(i) {
	var fieldName = 'criteriaSearchRequest.filter.filters[' + i
			+ '].filters[0].field';
	var operatorName = 'criteriaSearchRequest.filter.filters[' + i
			+ '].filters[0].operator';
	var valueName = 'criteriaSearchRequest.filter.filters[' + i
			+ '].filters[0].value';
	var logicName = 'criteriaSearchRequest.filter.filters[' + i + '].logic';
	var fieldName1 = 'criteriaSearchRequest.filter.filters[' + i
			+ '].filters[1].field';
	var operatorName1 = 'criteriaSearchRequest.filter.filters[' + i
			+ '].filters[1].operator';
	var valueName1 = 'criteriaSearchRequest.filter.filters[' + i
			+ '].filters[1].value';

	var str = "";
	str += "<div class='controls-row' id='filter" + i
			+ "' style='margin-bottom:5px'>";
	str += "<div style='float:left;'>"
			+ "<select data-type='select' id='field"
			+ i
			+ "0'"
			+ " class='span2' name='"
			+ fieldName
			+ "' onchange='javascript:changeField(this.options[this.options.selectedIndex].value, \"value"
			+ i + "0\",\"field" + i + "1\", \"value" + i + "1\")' >"
			+ fieldOptions + "</select>" + "</div>";
	str += "<div style='float:left;'>"
			+ "<select data-type='select' id='operator" + i + "0'"
			+ " class='span1' name='" + operatorName + "'>" + operatorOptions
			+ "</select>" + "</div>";
	str += "<div style='float:left;'>" + "<input id='value" + i + "0'"
			+ "class='span2' name='" + valueName + "' type='text'/>" + "</div>";
	str += "<div style='float:left;'>" + "<select data-type='select' id='logic"
			+ i + "'" + " class='span1' name='" + logicName + "'>"
			+ logicOptions + "</select>" + "</div>";
	str += "<div style='float:left; display:none'>"
			+ "<input type='text' id='field" + i + "1'" + " name='"
			+ fieldName1 + "' />" + "</div>";
	str += "<div style='float:left;'>"
			+ "<select data-type='select' id='operator" + i + "1'"
			+ " class='span1' name='" + operatorName1 + "'>" + operatorOptions
			+ "</select>" + "</div>";
	str += "<div style='float:left;'>" + "<input id='value" + i + "1'"
			+ " class='span2' name='" + valueName1 + "' type='text'/>"
			+ "</div>";
	str += "<div style='float:left;' class='span1'>"
			+ "<button type='button' id='btnRemove' class='btn' value=''><span class='icon-minus'></span></button>"
			+ "</div>";
	str += "</div>";
	return str;
}

function addRow(containerId) {
	var rowStr = formatRowStr(size);
	$(containerId).append(rowStr);// last .row-fluid div is btnAdd'div
	loadFields($(containerId + ' .controls-row:last'));
	size++;

}

function removeRow($clickRow) {
	$clickRow.closest(".controls-row").remove();// delete btnRemove's cloest
	// .row-fluid div
}

function changeReportTemplateView(criteriaDiv, sortFieldId, conditionDiv,
		viewId, action) {
	$(criteriaDiv).empty();
	$(sortFieldId).empty();
	fieldOptions = '';
	sortFieldOptions = '';
	if (viewId != '') {
		jQuery.ajax({
			type : 'get',
			url : SaaSApp.contextPath + '/reporttemplate/criteria/list/'
					+ viewId,
			success : function(data) {
				$(conditionDiv).show();

				$.each(data, function(index, list) {
					if (index == 0) {
						$.each(list, function(index1, field) {
							fieldOptions = fieldOptions + '<option value='
									+ field.value + '>' + field.key
									+ '</option>';
						});
					} else if (index == 3) {
						fieldTypes = list;
					} else if (index == 5) {
						$.each(list, function(index1, field) {
							sortFieldOptions = sortFieldOptions
									+ '<option value=' + field.value + '>'
									+ field.key + '</option>';
						});
					}
				});
				addRow(criteriaDiv);
				$(sortFieldId).append(sortFieldOptions);
			}
		});
	} else {
		$(conditionDiv).hide();
	}

}

function changeField(field, valueId, tagetField, targetValueId) {

	$
			.each(
					fieldTypes,
					function(index1, fieldType) {
						if (field == fieldType.key) {
							// $("#"+valueId).closest().html();
							var dataTimeObject = $("#" + valueId).data(
									"kendoDateTimePicker");
							var dataObject = $("#" + valueId).data(
									"kendoDatePicker");
							// prevent memory leak
							if (typeof (dataTimeObject) != "undefined") {
								dataTimeObject.destroy();
							}
							if (typeof (dataObject) != "undefined") {
								dataObject.destroy();
							}
							var dataTimeObject4Target = $("#" + targetValueId)
									.data("kendoDateTimePicker");
							var dataObject4Target = $("#" + targetValueId)
									.data("kendoDatePicker");
							// prevent memory leak
							if (typeof (dataTimeObject4Target) != "undefined") {
								dataTimeObject4Target.destroy();
							}
							if (typeof (dataObject4Target) != "undefined") {
								dataObject4Target.destroy();
							}
							if (fieldType.value == 'Date') {
								$("#" + valueId)
										.closest("div")
										.html(
												"<input id='"
														+ valueId
														+ "'"
														+ "class='span2' style='margin-left:0' name='"
														+ $("#" + valueId)
																.attr("name")
														+ "' type='text' data-type='date'/>"
														+ "</div>");
								$("#" + valueId).kendoDatePicker({
									format : "dd/MM/yyyy"
								});

								$("#" + targetValueId)
										.closest("div")
										.html(
												"<input id='"
														+ targetValueId
														+ "'"
														+ "class='span2' style='margin-left:0' name='"
														+ $("#" + targetValueId)
																.attr("name")
														+ "' type='text' data-type='date'/>"
														+ "</div>");
								$("#" + targetValueId).kendoDatePicker({
									format : "dd/MM/yyyy"
								});
							} else if (fieldType.value == 'DateTime') {
								$("#" + valueId)
										.closest("div")
										.html(
												"<input id='"
														+ valueId
														+ "'"
														+ "class='span2' style='margin-left:0' name='"
														+ $("#" + valueId)
																.attr("name")
														+ "' type='text' data-type='datetime'/>"
														+ "</div>");
								$("#" + valueId).kendoDateTimePicker({
									format : "dd/MM/yyyy HH:mm"
								});

								$("#" + targetValueId)
										.closest("div")
										.html(
												"<input id='"
														+ valueId
														+ "'"
														+ "class='span2' style='margin-left:0' name='"
														+ $("#" + targetValueId)
																.attr("name")
														+ "' type='text' data-type='datetime'/>"
														+ "</div>");
								$("#" + targetValueId).kendoDateTimePicker({
									format : "dd/MM/yyyy HH:mm"
								});
							} else if (fieldType.value == 'Text') {
								$("#" + valueId)
										.closest("div")
										.html(
												"<input id='"
														+ valueId
														+ "'"
														+ "class='span2' style='margin-left:0' name='"
														+ $("#" + valueId)
																.attr("name")
														+ "' type='text'/>"
														+ "</div>");

								$("#" + targetValueId)
										.closest("div")
										.html(
												"<input id='"
														+ valueId
														+ "'"
														+ "class='span2' style='margin-left:0' name='"
														+ $("#" + targetValueId)
																.attr("name")
														+ "' type='text'/>"
														+ "</div>");
							}
						}
					});

	$("#" + tagetField).attr("value", field);

}
// Report Tempalte End
