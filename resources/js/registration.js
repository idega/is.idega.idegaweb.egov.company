window.addEvent('domready', function() {

	var inputs = new Array();

	$$('input.personalID').each(
	function(element, index){
		inputs.push(element);
		element.addEvent('keyup', function() {
		
			var lastIndex = element.value.length;
			var lastChar = element.value.charAt(lastIndex-1);
			
			if (lastChar == '-') {
				element.value = element.value.substring(0, lastIndex-1);
			}
		
			if (element.value.length == 10) {
				FSKDWRUtil.getUserName(element.value, element.fillSpan);
			} else {
				element.inputer.value = '';
			}
		});
		
		element.fillSpan = function(data) {
			element.inputer.value = data;
		}

	});

	$$('input.userResults').each(
	function(element, index){
		inputs[index].inputer = element;
	});
	
});
