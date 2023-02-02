		function customPromptsBtnEdit(data){  
			return "<button " +
			"type='button' " +
			"class='btn btn-outline-primary btn-abrir-modal' " +
			"data-modal-dir = 'editPrompt' " +
			"data-modal-url = " + urlEditarPrpt + "/" + data.nome + " " +
			"data-modal-nome-prompt = " + data.nome + " " +
			"data-modal-app = " + data.aplicacao + " " +
			"title='" + editarPrompt + "'>" +
			"<i class='fa fa-pencil-square-o' aria-hidden='true'></i>" +
			"</button>";
		}
		function customPromptsBtnRemover(data){
			return "<button type='button' " +
			"class='btn btn-outline-danger btn-abrir-modal'" +
			"data-modal-dir = 'deletePrompt'" +
			"data-modal-url = " + urlDeletePrpt + "/" + data.nome + " " +
			"data-modal-nome-prompt = " + data.nome + " " +
			"data-modal-app = " + data.aplicacao + " " +
			"title='" + removerPrompt + "'>" +
			"<i class='fa fa-eraser' aria-hidden='true'></i>" +
			"</button>";
		}
		
		function customBtnsDtPrompts(data){
			return "<div class='btn-group'>" + customPromptsBtnEdit(data) + customPromptsBtnRemover(data) + "</div>";
		}

		var table = $('#prompts-table').DataTable({
	        
	        "ajax": {
	            "url": "/prompts/"+app,
	            "type": "PATCH",
	            "dataType": "json",
	            "contentType": "application/json",
	            "data": function (d) {
	                return JSON.stringify(d);
	           	 }
	        },
	        "columns": [
	            {"data": "nome", "width": "20%", "name":"promptNomeAppID.nome"},
	            {"data": "conteudo", "width": "40%", "name":"conteudo"},
	            {"data": "categoria", "width": "20%", "name": "categoria.nome"},
	            {"data": "dataAtt", "width": "20%", "name": "dataAtt"},
	            {
	                orderable: false,
	                data: null,
	                render:function(data, type, row)
	                {
	                	return customBtnsDtPrompts(data);
	                	//return '<div class="btn-group">' + customPromptsBtnEdit + customPromptsBtnRemover +'</div>';
	                }
	                
	            },
	        ]
	    });
