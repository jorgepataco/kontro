		function customBtnEdit(data){  
			return "<button " +
			"type='button' " +
			"class='btn btn-outline-primary btn-abrir-modal' " +
			"data-modal-dir = 'editPrompt' " +
			"data-modal-url = " + urlEditar + "/" + data.name + " " +
			"title='" + titleBtnEditarParametro + "'>" +
			"<i class='fa fa-pencil-square-o' aria-hidden='true'></i>" +
			"</button>";
		}
		function customBtnRemover(data){
			return "<button type='button' " +
			"class='btn btn-outline-danger btn-abrir-modal'" +
			"data-modal-dir = 'deleteParametro'" +
			"data-modal-url = " + urlDelete + "/" + data.name + " " +
			"title='" + titleBtnRemoverParametro + "'>" +
			"<i class='fa fa-eraser' aria-hidden='true'></i>" +
			"</button>";
		}
		
		function customBtnsDt(data){
			return "<div class='btn-group'>" + customBtnEdit(data) + customBtnRemover(data) + "</div>";
		}

		var table = $('#parametros-table').DataTable({
	        
	        "ajax": {
	            "url": "/parametros/"+server+"/"+app,
	            "type": "PATCH",
	            "dataType": "json",
	            "contentType": "application/json",
	            "data": function (d) {
	                return JSON.stringify(d);
	           	 }
	        },
	        "columns": [
	            {"data": "name", "width": "20%", "name":"name"},
	            {"data": "value", "width": "80%", "name":"name"},
	            {
	                orderable: false,
	                data: null,
	                render:function(data, type, row)
	                {
	                	return customBtnsDt(data);
	                	//return '<div class="btn-group">' + customPromptsBtnEdit + customPromptsBtnRemover +'</div>';
	                }
	                
	            },
	        ]
	    });
