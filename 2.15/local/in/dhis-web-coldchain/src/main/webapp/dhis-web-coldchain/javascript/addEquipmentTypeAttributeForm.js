jQuery(document).ready(	function(){
		validation( 'addEquipmentTypeAttributeForm', function(form){
			if( isSubmit && ATTRIBUTE_OPTION.checkOnSubmit() ) {
				form.submit(i18n_field_is_required);
			}
		}, function(){
			isSubmit = true;
			
			var fields = jQuery("#addEquipmentTypeAttributeForm").serializeArray();
			jQuery.each(fields, function(i, field) {
				if(  field.name.match("^attrOption")=='attrOption' && field.value == ""){
					setInnerHTML("attrMessage", i18n_field_is_required);
					isSubmit = false;
				}
			});
		}); 
		
		jQuery("#attributeComboRow").hide();
			
		checkValueIsExist( "name", "validateEquipmentTypeAttribute.action");
	});	