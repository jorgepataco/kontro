
var tabelaView = "";
var idModalKontro = "#modal-kontro";

function sleep(delay) {
    var start = new Date().getTime();
    while (new Date().getTime() < start + delay);
}

function getParametrosByPrefix(t, prefix) {
	var parametro = {};
	var data = t.data();

	for ( var i in data) {
		if (i.includes(prefix)) {
			parametro[i] = data[i];
		}
	}

	return parametro;
}

function toLowerCaseFirstLetter(string) {
	return string.charAt(0).toLowerCase() + string.slice(1);
}

$(document).on("click", ".btn-abrir-modal", (function(e) {
	var parametros = getParametrosByPrefix($(this), "modal");
	console.log('Parametros: ' + parametros['modalUrl']);
	renderModal(parametros);
	renderBodyModal(parametros);

}));

$(document).on("click" ,".modal-kontro-btn-close", (function(e) {
	closeModal();
}));

function openModal(){
	$(idModalKontro).modal("toggle");
}

function closeModal(){
	$(idModalKontro).modal('hide');
	$(this).html('');
}

function renderBodyModal(parametros) {
	$.ajax({
		type : 'GET',
		async: false,
		url : parametros["modalUrl"],
		success : function(data) {
			$('#body-modal-kontro').html(data);
			openModal();
		}
	});
}

function renderModal(parametros) {
	$.ajax({
		type : 'GET',
		async: false,
		url : URL_OPEN_MODAL + "/" + parametros["modalDir"],
		success : function(data) {
			$('#hidden-modal').html(data);
		}
	});
}

var tabelas = {

	ids : function() {
		var tabelas_id = [];
		$("#bodykontro table").each(function() {
			if (this.id != '') {
				console.log(this.id);
				tabelas_id.push(this.id);
			}
		});

		return tabelas_id;
	}
}

$(document)
		.ready(
				function() {

					function abrirModal(nomeModal, url) {
						$("#hidden-modal").load(url,
								function(response, status, xhr) {
									if (status == "success") {
										$(idModalKontro).modal("toggle");
									}

								});
					}

					function getTabelasId() {
						var tabelas_id = [];
						$("#bodykontro table").each(function() {
							if (this.id != '') {
								console.log(this.id);
								tabelas_id.push(this.id);
							}
						});

						return tabelas_id;
					}

					var tabelas_id = [];
					var sheets = [];
					// var reqNewRow = {};
					// var rowsOriginais = {};
					$("#bodykontro table").each(function() {
						if (this.id != '') {
							tabelas_id.push(this.id);
						}
					});

					$("#bodykontro .tab-pane").each(function() {
						if (this.id != '') {
							sheets.push(this.id + "");
						}
					});

					if (sheets.length == 0) {
						sheets.push("aba01");
					}

					var nomeValor = "";
					var valorParametro = "";
					var nomeColuna = "";

					$("#filterbox").keyup(function() {
						console.log(table);
						var tableFilter = table;
						tableFilter.search(this.value).draw();
					});

					$("#ExportReporttoExcel").on(
							"click",
							function() {
								tablesToExcel(tabelas.ids(), sheets,
										'Export-kontro', 'Excel');
							});

					function attParametro() {
						var $e = $('#ParametrosTable tbody tr.selected td.asset_value');
						var value = $e.html();

						console.log(value);
						console.log($e);

						$e
								.html('<input type="text" class="col-7 form-control form-control-sm" value="'
										+ value + '" />');

						var $newE = $e.find('input');
						$newE.focus();

						$newE.on('blur', function() {
							valorParametro = $(this).val();
							$(this).parent().html(valorParametro);
							console.log(valorParametro);
							updateParametro();
						});

					}

					$('#att-par')
							.on(
									'click',
									function() {
										var $e = $('#ParametrosTable tbody tr.selected td.asset_value');
										var value = $e.html();

										console.log(value);
										console.log($e);

										$e
												.html('<input type="text" class="col-7 form-control form-control-sm" value="'
														+ value + '" />');

										var $newE = $e.find('input');
										$newE.focus();

										$newE.on('blur', function() {
											valorParametro = $(this).val();
											$(this).parent().html(
													valorParametro);
											console.log(valorParametro);
											updateParametro();
										});
									});
					$("#foo").on("custom", function(event, param1, param2) {
						alert(param1 + "\n" + param2);
					});

					$('td').on('focusin', function() {
						var val = $(this).find("input").val();
						$(this).data('val', val);
					});

					$('td').on('change', function() {
						var prev = $(this).data('val');
						var current = $(this).find("input").val();

						if (prev != current) {
							$(this).addClass("changed");
							$(this).data('current', current);
							console.log("Prev value " + prev);
							console.log("New value " + current);
						}

					});

					$('.btn-ok')
							.on(
									'click',
									function() {
										console.log("btd-cancel");
										var idTr = "#"
												+ $(this).attr('data-id-tr');
										var $row = $(idTr);
										var $tds = $row.find("td");

										$row.toggleClass("selected");
										$row.toggleClass("unselected");
										var reqNewRow = {};
										var nomeTabela = $row
												.attr('data-nomeTabela');
										var rownum = $row.attr('data-rownum');
										var colunas = [];
										var tdsModificados = [];
										var idxCols = 0;
										var idxMods = 0;
										$
												.each(
														$tds,
														function() {

															if ($(this)
																	.hasClass(
																			'table-active')) {
																var cell = table
																		.cell($(this));
																$(this)
																		.find(
																				"form")
																		.toggleClass(
																				"d-none");
																cell
																		.draw(false);
															} else {
																var html = $(
																		this)
																		.data(
																				'prev');
																var nomeColuna = $(
																		this)
																		.attr(
																				'data-nomeColuna');

																if (!$(this)
																		.hasClass(
																				'td-null')) {
																	colunas[idxCols] = nomeColuna;
																	idxCols = idxCols + 1;
																}

																if ($(this)
																		.hasClass(
																				'changed')) {
																	var tdModificado = {};
																	var newValue = [];
																	html = $(
																			this)
																			.data(
																					'current');
																	$(this)
																			.removeClass(
																					"changed");
																	tdModificado["nomeColuna"] = nomeColuna;
																	newValue[0] = html;
																	tdModificado["valores"] = newValue;
																	tdsModificados[idxMods] = tdModificado;

																	idxMods = idxMods + 1;
																}

																$(this).html(
																		html);
															}
														});

										reqNewRow["nomeTabela"] = nomeTabela;
										reqNewRow["rownum"] = rownum;
										reqNewRow["colunas"] = colunas;
										reqNewRow["tdsModificado"] = tdsModificados;
										console.log(tdsModificados);
										updateRecvoz(reqNewRow);
									});

					$('.btn-cancel').on('click', function() {
						console.log("btd-cancel");
						var idTr = "#" + $(this).attr('data-id-tr');
						var $row = $(idTr);
						var $tds = $row.find("td");

						$row.toggleClass("selected");
						$row.toggleClass("unselected");

						$.each($tds, function() {
							if ($(this).hasClass('table-active')) {
								var cell = table.cell($(this));
								$(this).find("form").toggleClass("d-none");
								cell.draw(false);
							} else {
								var html = $(this).data('prev');
								$(this).removeClass("changed");
								// $(this).data('prev', html);
								// var input = $('<input class="col-7
								// form-control form-control-sm" type="text"
								// data-nomeColuna="'+nomeColuna+'"
								// value="'+html+'" />');
								// input.val(html);
								$(this).html(html);
							}
						});

					});
					$('.btn-edit')
							.on(
									'click',
									function(e) {
										console.log("btd-edit");
										// e.preventDefault();
										var idTr = "#"
												+ $(this).attr('data-id-tr');
										var $row = $(idTr);
										var $tds = $row.find("td");

										$row.toggleClass("selected");
										$row.toggleClass("unselected");

										$
												.each(
														$tds,
														function() {
															if ($(this)
																	.hasClass(
																			'table-active')) {
																var cell = table
																		.cell($(this));
																$(this)
																		.find(
																				"form")
																		.toggleClass(
																				"d-none");
																// e.preventDefault();
																cell
																		.draw(false);
															} else {
																var html = $(
																		this)
																		.html();
																$(this).data(
																		'prev',
																		html);
																var input = $('<input class="col-7 form-control form-control-sm" type="text" data-nomeColuna="'
																		+ nomeColuna
																		+ '" value="'
																		+ html
																		+ '" />');
																input.val(html);
																// e.preventDefault();
																$(this).html(
																		input);
															}
														});

									});
					$('.tab-content tbody')
							.on(
									'removerSelecaoTR',
									function() {
										if (!$(this).hasClass('selected')) {
											$(this).addClass('selected');
											var $e = $('tr.selected');

											var nomeTabela = $e
													.attr('data-nomeTabela');
											var rownum = $e.attr('data-rownum');
											var colunas = [];
											var idx = 0;

											$e
													.find('td.asset_value')
													.each(
															function() {
																var nomeColuna = $(
																		this)
																		.attr(
																				'data-nomeColuna');
																colunas[idx] = nomeColuna;
																idx = idx + 1;
																// console.log("Saving
																// value " +
																// $(this).html());
																// $(this).data('val',
																// $(this).html());
																var html = $(
																		this)
																		.html();
																var input = $('<input class="col-7 form-control form-control-sm" type="text" data-nomeColuna="'
																		+ nomeColuna
																		+ '" value="'
																		+ html
																		+ '" />');
																input.val(html);
																$(this).html(
																		input);

															});

											reqNewRow["nomeTabela"] = nomeTabela;
											reqNewRow["rownum"] = rownum;
											reqNewRow["colunas"] = colunas;

											var $newE = $e.find('input');

											$('input').on(
													'focusin',
													function() {
														$(this).data('val',
																$(this).val());
													});

											$('input').on(
													'change',
													function() {
														var prev = $(this)
																.data('val');
														var current = $(this)
																.val();

														if (prev != current) {
															$(this).addClass(
																	"changed");
														}
													});

										}

									});

					$('li').on('click', 'a', function() {
						tabelaView = $(this).text();
					});

					function updateRecvoz(reqNewRow) {

						console.log("entrou aqui");
						event.preventDefault();
						$
								.ajax({
									type : "POST",
									contentType : "application/json",
									data : JSON.stringify(reqNewRow),
									url : URL_UPDATE_RECVOZ,
									cache : false,
									timeout : 600000,
									success : function(data) {
										console.log("SUCCESS : ");
									},
									error : function(jqXHR, exception) {
										console.log("ERROR : ", jqXHR);
										console.log("ERROR : ", exception);
										console.log("ERROR : ",
												jqXHR.responseText);

										$(".title-kontro-error")
												.html(
														"Erro - Serviço: Recvoz - Reportar log abaixo para equipe Everis");
										$(".message-kontro-error").html(
												jqXHR.responseText);
										$('#modal-kontro-view-error').modal(
												'show');
									}
								});

						$("#att-par").addClass('disabled');
						$("#del-par").addClass('disabled');
					}

					function buildHtmlTable(selector) {
						console.log("entrou aqui");
						var columns = addAllColumnHeaders(myList, selector);

						for (var i = 0; i < myList.length; i++) {
							var row$ = $('<tr/>');
							for (var colIndex = 0; colIndex < columns.length; colIndex++) {
								var cellValue = myList[i][columns[colIndex]];
								if (cellValue == null)
									cellValue = "";
								row$.append($('<td/>').html(cellValue));
							}
							$(selector).append(row$);
						}
					}

					function addAllColumnHeaders(myList, selector) {
						var columnSet = [];
						var headerTr$ = $('<tr/>');

						for (var i = 0; i < myList.length; i++) {
							var rowHash = myList[i];
							for ( var key in rowHash) {
								if ($.inArray(key, columnSet) == -1) {
									columnSet.push(key);
									headerTr$.append($('<th/>').html(key));
								}
							}
						}
						$(selector).append(headerTr$);

						return columnSet;
					}
				});

var tablesToExcel = (function() {
	var uri = 'data:application/vnd.ms-excel;base64,', tmplWorkbookXML = '<?xml version="1.0"?><?mso-application progid="Excel.Sheet"?><Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet" xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet">'
			+ '<DocumentProperties xmlns="urn:schemas-microsoft-com:office:office"><Author>Axel Richter</Author><Created>{created}</Created></DocumentProperties>'
			+ '<Styles>'
			+ '<Style ss:ID="Currency"><NumberFormat ss:Format="Currency"></NumberFormat></Style>'
			+ '<Style ss:ID="Date"><NumberFormat ss:Format="Medium Date"></NumberFormat></Style>'
			+ '</Styles>' + '{worksheets}</Workbook>', tmplWorksheetXML = '<Worksheet ss:Name="{nameWS}"><Table>{rows}</Table></Worksheet>', tmplCellXML = '<Cell{attributeStyleID}{attributeFormula}><Data ss:Type="{nameType}">{data}</Data></Cell>', base64 = function(
			s) {
		return window.btoa(unescape(encodeURIComponent(s)))
	}, format = function(s, c) {
		return s.replace(/{(\w+)}/g, function(m, p) {
			return c[p];
		})
	}
	return function(tables, wsnames, wbname, appname) {

		var ctx = "";
		var workbookXML = "";
		var worksheetsXML = "";
		var rowsXML = "";
		tabelaView = tabelaView != "" ? tabelaView : wsnames[0];
		for (var i = 0; i < tables.length; i++) {

			if (tabelaView == wsnames[i]) {
				var idElement = "#" + tables[i];
				var tableAux = $(idElement).DataTable();
				var iColumns = $(idElement + ' thead th').length;
				var iRows = tableAux.rows().data().length;
				if (!tables[i].nodeType)
					tables[i] = document.getElementById(tables[i]);
				for (var j = -1; j < iRows; j++) {
					rowsXML += '<Row>'
					for (var k = 0; k < iColumns; k++) {
						var title = tableAux.column(k).header();
						var dataType = null;
						var dataStyle = null;
						// var dataValue =
						// tables[i].rows[j].cells[k].getAttribute("data-value");
						var dataValue = j == -1 ? $(title).html() : tableAux
								.rows().data()[j][k];
						console.log(dataValue);
						// dataValue =
						// (dataValue)?dataValue:tables[i].rows[j].cells[k].innerHTML;
						var dataFormula = null;
						dataFormula = (dataFormula) ? dataFormula
								: (appname == 'Calc' && dataType == 'DateTime') ? dataValue
										: null;
						ctx = {
							attributeStyleID : (dataStyle == 'Currency' || dataStyle == 'Date') ? ' ss:StyleID="'
									+ dataStyle + '"'
									: '',
							nameType : (dataType == 'Number'
									|| dataType == 'DateTime'
									|| dataType == 'Boolean' || dataType == 'Error') ? dataType
									: 'String',
							data : (dataFormula) ? '' : dataValue,
							attributeFormula : (dataFormula) ? ' ss:Formula="'
									+ dataFormula + '"' : ''
						};
						rowsXML += format(tmplCellXML, ctx);

					}
					rowsXML += '</Row>'
				}
				ctx = {
					rows : rowsXML,
					nameWS : wsnames[i] || 'Sheet' + i
				};
				worksheetsXML += format(tmplWorksheetXML, ctx);
				rowsXML = "";
			}

		}

		ctx = {
			created : (new Date()).getTime(),
			worksheets : worksheetsXML
		};
		workbookXML = format(tmplWorkbookXML, ctx);

		var link = document.createElement("A");
		link.href = uri + base64(workbookXML);
		link.download = wbname || 'Workbook.xls';
		link.target = '_blank';
		document.body.appendChild(link);
		link.click();
		document.body.removeChild(link);
	}
})();

$('.ctx-menu-apto').on('contextmenu', function(e) {
	var top = e.pageY - 10;
	var left = e.pageX - 90;
	console.log(this);

	$("#context-menu").find('a').addClass("d-none");

	if ($(this).hasClass("cen-acao")) {
		$("#context-menu").find('.context-op-acao').removeClass("d-none");
	} else {
		$("#context-menu").find('.context-op-prompt').removeClass("d-none");
	}

	$("#context-menu").css({
		display : "block",
		top : top,
		left : left
	}).addClass("show");

	$("body").addClass("contextmenu-active");

	return false; // blocks default Webbrowser right click menu
});

$("body").on("click", function() {
	$("#context-menu").find('a').addClass("d-none");
	$("#context-menu").removeClass("show").hide();
});

$("#context-menu a").on("click", function() {
	console.log(this);
	$(this).parent().removeClass("show").hide();
});

$('#addRow').on('click', function() {
	$("#modal-add-parametro :input").attr("disabled", true);
	addNovoParametro($("#parametro-name").val(), $("#parametro-valor").val());

});

$('#removeRow').click(function() {
	$("#modal-remove-parametro :input").attr("disabled", true);
	var nomeParametro = $("#msg-confirma-delete").html();
	console.log(nomeParametro);
	deletarParametro(nomeParametro);
});

function deletarParametro(nomeParametro) {
	event.preventDefault();
	$
			.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/" + appName + "/parametros/deletar/" + "hml" + "/"
						+ app + "/" + nomeParametro,
				// url: "/parametros/deletar/" + server + "/" + app + "/" +
				// nomeParametro,
				cache : false,
				timeout : 600000,
				success : function() {
					console.log("SUCCESS : ");
					table.row('.selected').remove().draw(false);

					console.log($("#row-par-" + nomeParametro));
					table.row($("#row-par-" + nomeParametro)).remove().draw(
							false);

					$('#modal-remove-parametro').modal('hide');

				},
				error : function(jqXHR, exception) {
					console.log("ERROR : ", jqXHR);
					console.log("ERROR : ", exception);
					console.log("ERROR : ", jqXHR.responseText);
					$('#modal-remove-parametro').modal('hide');
					$(".title-kontro-error")
							.html(
									"Erro - Serviço: Parâmetros (deletar) - Reportar log abaixo para equipe Everis");
					$(".message-kontro-error").html(jqXHR.responseText);
					$('#modal-kontro-view-error').modal('show');

				},
				complete : function(data) {
					$("#modal-remove-parametro :input").attr("disabled", false);
				}
			});
}

function addNovoParametro(nomeValor, valorParametro) {

	var parametro = {};
	parametro["name"] = nomeValor;
	parametro["value"] = valorParametro;
	event.preventDefault();
	$
			.ajax({
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify(parametro),
				url : "/" + appName + "/parametros/add/" + "hml" + "/" + app,
				// url: "/parametros/add/" + server + "/" + app,
				cache : false,
				timeout : 600000,
				success : function(data) {
					console.log("SUCCESS : ");
					$('#modal-add-parametro').modal('hide');
					var data = [ "", nomeValor, valorParametro ];
					var newForm = $('<form>', {
						"class" : "navbar-form form-inline form-edit"
					});

					var newHref = $('<a>', {
						"href" : "#",
						"data-pk" : nomeValor,
						"onclick" : "return false;",
						"class" : "btn btn-danger btn-sm btn-kontro-deletar"
					});

					var newIBtn = $('<i>', {
						"aria-hidden" : "true",
						"class" : "fa fa-fw fa-times"
					});

					newIBtn.appendTo(newHref);
					newHref.appendTo(newForm);

					var rowNode = table.row.add(data).draw(false).node();

					var newHrefEditable = $('<a>', {
						"style" : "border: none;",
						"class" : "asset_value",
						"data-pk" : nomeValor,
						"href" : "#"
					});
					newHrefEditable.html(valorParametro);
					$(rowNode).find('td').eq(2).html(newHrefEditable);
					$(rowNode).find('td').eq(0).addClass("table-active");
					newForm.appendTo($(rowNode).find('td').eq(0));
					$(rowNode).attr("id", "row-par-" + nomeValor);

					$($(newHrefEditable)).editable({
						escape : false,
						tpl : "<input type='text' style='width: 550px'>",
						inputclass : 'form-control-sm',
						disabled : (server == 'hml' ? false : true),
						type : "text",
						pk : "1",
						mode : "inline"

					// console.log(this);
					});

					$($(newHrefEditable)).on('save', function(e, params) {
						var nomeParametro = $(this).attr("data-pk");
						var valorParametro = params.newValue;
						console.log("nomeParametro: " + nomeParametro);
						console.log("valorParametro: " + valorParametro);
						updateParametro(nomeParametro, valorParametro);
					});

					$(newHref).on("click", function() {
						nomeValor = $(this).attr('data-pk');
						$("#msg-confirma-delete").html(nomeValor);
						$("#modal-remove-parametro").modal()

					});
					// loadJquerysEvents();
				},
				error : function(jqXHR, exception) {
					console.log("ERROR : ", jqXHR);
					console.log("ERROR : ", exception);
					console.log("ERROR : ", jqXHR.responseText);

					$('#modal-add-parametro').modal('hide');
					$(".title-kontro-error")
							.html(
									"Erro - Serviço: Parâmetros (Novo) - Reportar log abaixo para equipe Everis");
					$(".message-kontro-error").html(jqXHR.responseText);
					$('#modal-kontro-view-error').modal('show');

				},
				complete : function(data) {
					$("#modal-add-parametro :input").attr("disabled", false);
					document.getElementById("form-novo-parametro").reset();
				}
			});
}

function updateParametro(nomeValor, valorParametro) {
	var parametro = {};
	parametro["name"] = nomeValor;
	parametro["value"] = valorParametro;
	event.preventDefault();
	$
			.ajax({
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify(parametro),
				url : "/" + appName + "/parametros/att/" + "hml" + "/" + app,
				cache : false,
				timeout : 600000,
				success : function(data) {
					console.log("SUCCESS : ");
				},
				error : function(jqXHR, exception) {
					console.log("ERROR : ", jqXHR);
					console.log("ERROR : ", exception);
					console.log("ERROR : ", jqXHR.responseText);
					$(".title-kontro-error")
							.html(
									"Erro - Serviço: Parâmetros (atualizar) - Reportar log abaixo para equipe Everis");
					$(".message-kontro-error").html(jqXHR.responseText);
					$('#modal-kontro-view-error').modal('show');
				}
			});

	$("#att-par").addClass('disabled');
	$("#del-par").addClass('disabled');
}
