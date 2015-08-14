$(document).ready(function(){
	
	
	 // initialize field
	 loadFields($("body"));
	 
	 // initialize modal
	 loadModal($("body"));
	 
	 // edit button
	 editBtn($("body"));
	 
	 // cancel button
	 cancelBtn($("body"));
	 
	 // save button
	 submitBtn($("body"));
	 
	 loadCharts();
	
	 bindSubmitEvent();
});