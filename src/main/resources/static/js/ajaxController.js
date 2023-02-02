$(document).on("submit", ".form-kontro", (function(e) {
	var _datas = getDatas($(this));
	var _fields = JSON.stringify(getFormFields($(this)));
	desabilitarForm($(this));
	$(this).find('.alert').hide();
	$(this).find('.loader').show();
	e.preventDefault();
	setTimeout(enviarForm, 750, $(this), _datas, _fields);
}));

function desabilitarForm(form){
	form.find('input, textarea, button, select').attr('disabled','disabled');
}

function enviarForm(form, _datas, _fields) {
	//saveForm(form, _datas, _fields);
	window[_datas["ajaxSaveForm"]](form, _datas, _fields);
}

function onAjaxSavePrompt(form, _datas, _fields) {
	$.ajax({
		type : _datas["type"],
		contentType : "application/json",
		data : _fields,
		url : _datas["url"],
		cache : false,
		timeout : 600000,
		success : function(response) {
			if ($(response).find('.has-error').length) {
				form.replaceWith(response);
			}else{
				form.replaceWith(response);
				table.ajax.reload();
				setTimeout(closeModal, 750);
			}
		},
		error : function(jqXHR, exception) {
			// console.log("ERROR : ", jqXHR);
			// console.log("ERROR : ", exception);
			// console.log("ERROR : ", jqXHR.responseText);

		}
	});
}

function onAjaxSaveParametro(form, _datas, _fields) {
	$.ajax({
		type : _datas["type"],
		contentType : "application/json",
		data : _fields,
		url : _datas["url"],
		cache : false,
		timeout : 600000,
		success : function(response) {
			if ($(response).find('.has-error').length) {
				form.replaceWith(response);
			}else{
				form.replaceWith(response);
				table.ajax.reload();
				setTimeout(closeModal, 750);
			}
		},
		error : function(jqXHR, exception) {
			// console.log("ERROR : ", jqXHR);
			// console.log("ERROR : ", exception);
			// console.log("ERROR : ", jqXHR.responseText);

		}
	});
}


function getDatas(t) {
	var parametro = {};
	var data = t.data();
	for ( var i in data) {
		parametro[i] = data[i];
	}
	return parametro;
}

function getFormFields($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});

	return indexed_array;
}
