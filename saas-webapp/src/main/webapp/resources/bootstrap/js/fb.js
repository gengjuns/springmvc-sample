$(document).ready(function(){
	
	$("#target").delegate(".add-tab", "click", function(e){
  	   		e.preventDefault();
			var $active_tab = $(this).parent().parent();
			var tabsIndex = $("#target .tab-pane").length;
			$("<li class=><a href='#tab"+(tabsIndex+1)+"' data-toggle='tab'>Tab</a></li>").insertBefore($(this));
			$active_tab.find(".tab-content").append("<div class='tab-pane' id='tab"+(tabsIndex+1)+"'></div>");
				
  	   });
	 
	 
	
	$("form").delegate(".section-title", "mousedown", function(md){
	    $(".popover").remove();
	    md.preventDefault();
	    var tops = [];
	    var mouseX = md.pageX;
	    var mouseY = md.pageY;
	    var $temp;
	    var timeout;
	    var $this = $(this);
	    var delays = {
	      main: 0,
	      form: 120
	    }
	    var type;

	    if($this.parent().parent().parent().parent().parent().attr("id") === "components"){
	      type = "main";
	    } else {
	      type = "form";
	    }

	    var $div = $($this.parent());
	    var delayed = setTimeout(function(){
	      if(type === "main"){
	        $temp = $("<form class='form-horizontal span6' id='temp'></form>").append($div.clone());
	      } else {
	          $temp = $("<form class='form-horizontal span6' id='temp'></form>").append($div);
	      }

	      $("body").append($temp);

	      $temp.css({"position" : "absolute",
	                 "top"      : mouseY - ($temp.height()/2) + "px",
	                 "left"     : mouseX - ($temp.width()/2) + "px",
	                 "opacity"  : "0.9"}).show()

	      var half_box_height = ($temp.height()/2);
	      var half_box_width = ($temp.width()/2);
	      var $target = $("#target");
	      var tar_pos = $target.position();
	      var $target_component = $("#target .section");

	      $(document).delegate("body", "mousemove", function(mm){

	        var mm_mouseX = mm.pageX;
	        var mm_mouseY = mm.pageY;

	        $temp.css({"top"      : mm_mouseY - half_box_height + "px",
	          "left"      : mm_mouseX - half_box_width  + "px"});

	        if ( mm_mouseX > tar_pos.left &&
	          mm_mouseX < tar_pos.left + $target.width() + $temp.width()/2 &&
	          mm_mouseY > tar_pos.top &&
	          mm_mouseY < tar_pos.top + $target.height() + $temp.height()/2
	          ){
	            $("#target").css("background-color", "#fafdff");
	            $target_component.css({"border-top" : "1px solid white", "border-bottom" : "none"});
	            tops = $.grep($target_component, function(e){
	              return ($(e).position().top -  mm_mouseY + half_box_height > 0);
	            });
	            if (tops.length > 0){
	              $(tops[0]).css("border-top", "1px solid #22aaff");
	            } else{
	              if($target_component.length > 0){
	                $($target_component[$target_component.length - 1]).css("border-bottom", "1px solid #22aaff");
	              }
	            }
	          } else{
	            $("#target").css("background-color", "#fff");
	            $target_component.css({"border-top" : "1px solid white", "border-bottom" : "none"});
	            $target.css("background-color", "#fff");
	          }
	      });

	      $("body").delegate("#temp", "mouseup", function(mu){
	        mu.preventDefault();
	        
	        var mu_mouseX = mu.pageX;
	        var mu_mouseY = mu.pageY;
	        var tar_pos = $target.position();

	        //$("#target .component").css({"border-top" : "1px solid white", "border-bottom" : "none"});

	        // acting only if mouse is in right place
	        if (mu_mouseX + half_box_width > tar_pos.left &&
	          mu_mouseX - half_box_width < tar_pos.left + $target.width() &&
	          mu_mouseY + half_box_height > tar_pos.top &&
	          mu_mouseY - half_box_height < tar_pos.top + $target.height()
	          ){
	            $temp.attr("style", null);
	            // where to add
	            if(tops.length > 0){
	              $($temp.html()).insertBefore(tops[0]);
	            } else {
	            		$("#target fieldset").append($temp.append("\n\n\ \ \ \ ").html());
	            }
	          } else {
	            // no add
	            $("#target .component").css({"border-top" : "1px solid white", "border-bottom" : "none"});
	            tops = [];
	          }

	        //clean up & add popover
	        $target.css("background-color", "#fff");
	        $(document).undelegate("body", "mousemove");
	        $("body").undelegate("#temp","mouseup");
	        $("#target div[rel='popover']").popover({trigger: "manual"});
	        $temp.remove();
	        $("#target .section").css({"border": "1px solid #ccc"});
	        genSource();
	      });
	    }, delays[type]);

	    $(document).mouseup(function () {
	      clearInterval(delayed);
	      return false;
	    });
	    $(this).mouseout(function () {
	      clearInterval(delayed);
	      return false;
	    });
	  });
	
	
	
	
  $("form").delegate(".component", "mousedown", function(md){
    $(".popover").remove();
    md.preventDefault();
    var tops = [];
    var mouseX = md.pageX;
    var mouseY = md.pageY;
    var $temp;
    var timeout;
    var $this = $(this);
    var delays = {
      main: 0,
      form: 120
    }
    var type;

    if($this.parent().parent().parent().parent().attr("id") === "components"){
      type = "main";
    } else {
      type = "form";
    }

    var delayed = setTimeout(function(){
      if(type === "main"){
        $temp = $("<form class='form-horizontal span6' id='temp'></form>").append($this.clone());
      } else {
        if($this.attr("id") !== "legend"){
          $temp = $("<form class='form-horizontal span6' id='temp'></form>").append($this);
        }
      }

      $("body").append($temp);

      $temp.css({"position" : "absolute",
                 "top"      : mouseY - ($temp.height()/2) + "px",
                 "left"     : mouseX - ($temp.width()/2) + "px",
                 "opacity"  : "0.9"}).show()

      var half_box_height = ($temp.height()/2);
      var half_box_width = ($temp.width()/2);
      var $target = $("#target");
      var tar_pos = $target.position();
      var $target_component = $("#target .component");

      $(document).delegate("body", "mousemove", function(mm){

        var mm_mouseX = mm.pageX;
        var mm_mouseY = mm.pageY;

        $temp.css({"top"      : mm_mouseY - half_box_height + "px",
          "left"      : mm_mouseX - half_box_width  + "px"});

        if ( mm_mouseX > tar_pos.left &&
          mm_mouseX < tar_pos.left + $target.width() + $temp.width()/2 &&
          mm_mouseY > tar_pos.top &&
          mm_mouseY < tar_pos.top + $target.height() + $temp.height()/2
          ){
            $("#target").css("background-color", "#fafdff");
            $target_component.css({"border-top" : "1px solid white", "border-bottom" : "none"});
            tops = $.grep($target_component, function(e){
              return ($(e).position().top -  mm_mouseY + half_box_height > 0 && $($(e).parent()).position().top < mm_mouseY && $($(e).parent()).height() + $($(e).parent()).position().top > mm_mouseY);
            });
            if (tops.length > 0){
              $(tops[0]).css("border-top", "1px solid #22aaff");
            } else{
              if($target_component.length > 0){
                //$($target_component[$target_component.length - 1]).css("border-bottom", "1px solid #22aaff");
              }
            }
          } else{
            $("#target").css("background-color", "#fff");
            $target_component.css({"border-top" : "1px solid white", "border-bottom" : "none"});
            $target.css("background-color", "#fff");
          }
      });

      $("body").delegate("#temp", "mouseup", function(mu){
        mu.preventDefault();
        
        var mu_mouseX = mu.pageX;
        var mu_mouseY = mu.pageY;
        var tar_pos = $target.position();

        //$("#target .component").css({"border-top" : "1px solid white", "border-bottom" : "none"});
        
        

        // acting only if mouse is in right place
        if (mu_mouseX + half_box_width > tar_pos.left &&
          mu_mouseX - half_box_width < tar_pos.left + $target.width() &&
          mu_mouseY + half_box_height > tar_pos.top &&
          mu_mouseY - half_box_height < tar_pos.top + $target.height()
          ){
            $temp.attr("style", null);
            
            // where to add
            if(tops.length > 0){
              $($temp.html()).insertBefore(tops[0]);
            } else {
            	
            	 $.each($("#target .section"), function(e){
                 	if($(this).position().top<mu_mouseY && $(this).position().top+$(this).height() > mu_mouseY){
                 		$(this).find(".section-content").append($temp.append("\n\n\ \ \ \ ").html());
                 	}
                 });
            	
            }
          } else {
            // no add
            $("#target .component").css({"border-top" : "1px solid white", "border-bottom" : "none"});
            tops = [];
          }

        //clean up & add popover
        $target.css("background-color", "#fff");
        $(document).undelegate("body", "mousemove");
        $("body").undelegate("#temp","mouseup");
        $("#target div[rel='popover']").popover({trigger: "manual"});
        $temp.remove();
        $("#target .component").css({"border-top" : "1px solid white", "border-bottom" : "none"});
        $("#target .section").css({"border": "1px solid #ccc"});
        genSource();
      });
    }, delays[type]);

    $(document).mouseup(function () {
      clearInterval(delayed);
      return false;
    });
    $(this).mouseout(function () {
      clearInterval(delayed);
      return false;
    });
  });
  
  var isFieldUsed = function(fieldName){
	  var result = false;
	  $.each($("#target .component [data-valtype*=name]"),function(i,e){
		  if($(e).attr("name").toLowerCase() == fieldName.toLowerCase())
		  {
			  result = true;
		  		return;
		  }
	  });
	  return result;
  }

  var genSource = function(){
    var $temptxt = $("<div>").html($("#build").html());
    //scrubbbbbbb
    $($temptxt).find(".component, .section-title, .section").attr({"title": null,
      "data-original-title":null,
      "data-type": null,
      "data-content": null,
      "rel": null,
      "trigger":null,
      "style": null});
    $($temptxt).find(".valtype").attr("data-valtype", null).removeClass("valtype");
    //$($temptxt).find(".component").removeClass("component");
    $($temptxt).find("form").attr({"id":  null, "style": null});
    $($temptxt).find("[data-html]").removeAttr("data-html");
    $("#source").val($temptxt.html().replace(/\n\ \ \ \ \ \ \ \ \ \ \ \ /g,"\n"));
  }

  //activate legend popover
  $("#target div[rel='popover']").popover({trigger: "manual"});
  //popover on click event
  $("#target").delegate("div[rel='popover']", "click", function(e){
    e.preventDefault();
    $(".popover").hide();
    var $active_component = $(this);
    $active_component.popover("show");
    if(!$active_component.attr("class").contains("section-title")){
    	$(".popover #name").append("<option value=''></option>");
    	
    	
    	
    	var fieldType = $active_component.attr("field-type");
    	var name = $($active_component.find("[name]")[0]).attr("name");
        for(var i=0;i<fields.length;i++){
        	if(name.toLowerCase() == fields[i].name.toLowerCase() || (fields[i].fieldType.toLowerCase() == fieldType.toLowerCase() && !isFieldUsed(fields[i].name.toLowerCase())))
        	{	
        		$(".popover #name").append("<option value='"+fields[i].name+"'>"+fields[i].name+"</option>");
        	}
        }
        $(".popover #label").attr("disabled",true);
        
        $(".popover #name").change(function(e){
    		for(var i=0;i<fields.length;i++){
    			if(fields[i].name===$(this).val()){
    				$(".popover #label").val(fields[i].label);
    			}
    		}
    	});
    }
    
    var valtypes = $active_component.find(".valtype");
    $.each(valtypes, function(i,e){
    	
	var arr = $(e).attr("data-valtype").split(" ");
    for(var index=0;index<arr.length;index++){
    	var valID = "#" + arr[index];
      var val;
      if(valID === "#column"){
    	  val = $(e).attr("column");
    	  $.each($(".popover").find("input[name='column']"),function(e){
    		  if(val === $(this).val()){
    			  $(this).attr("checked","checked");
    		  }
    	  });
      }else if(valID ==="#name"){
          val = $(e).attr("name");
          $(".popover " + valID).val(val);
        }
      else if(valID ==="#placeholder"){
        val = $(e).attr("placeholder");
        $(".popover " + valID).val(val);
      } else if(valID==="#checkbox"){
        val = $(e).attr("checked");
        $(".popover " + valID).attr("checked",val);
      } else if(valID==="#option"){
        val = $.map($(e).find("option"), function(e,i){return $(e).text()});
        val = val.join("\n")
      $(".popover "+valID).text(val);
      } else if(valID==="#checkboxes"){
        val = $.map($(e).find("label"), function(e,i){return $(e).text().trim()});
        val = val.join("\n");
        $(".popover #name").val($(e).find("input").attr("name"));
      $(".popover "+valID).text(val);
      } else if(valID==="#radios"){
        val = $.map($(e).find("label"), function(e,i){return $(e).text().trim()});
        val = val.join("\n");
        $(".popover "+valID).text(val);
        $(".popover #name").val($(e).find("input").attr("name"));
      } else if(valID==="#inline-checkboxes"){
        val = $.map($(e).find("label"), function(e,i){return $(e).text().trim()});
        val = val.join("\n")
          $(".popover "+valID).text(val);
      } else if(valID==="#inline-radios"){
        val = $.map($(e).find("label"), function(e,i){return $(e).text().trim()});
        val = val.join("\n")
          $(".popover "+valID).text(val);
        $(".popover #name").val($(e).find("input").attr("name"));
      } else if(valID==="#button") {
        val = $(e).text();
        var type = $(e).find("button").attr("class").split(" ").filter(function(e){return e.match(/btn-.*/)});
        $(".popover #color option").attr("selected", null);
        if(type.length === 0){
          $(".popover #color #default").attr("selected", "selected");
        } else {
          $(".popover #color #"+type[0]).attr("selected", "selected");
        }
        val = $(e).find(".btn").text();
        $(".popover #button").val(val);
      } else {
        val = $(e).text();
        $(".popover " + valID).val(val);
      }
    }
    });

    $(".popover").delegate(".btn-danger", "click", function(e){
      e.preventDefault();
      $active_component.popover("hide");
    });

    $(".popover").delegate(".btn-info", "click", function(e){
      e.preventDefault();
      var inputs = $(".popover input ");
      inputs.push($(".popover textarea")[0]);
      inputs.push($(".popover select")[0]);
      $.each(inputs, function(i,e){
      var vartype = $(e).attr("id");
      var value = $active_component.find('[data-valtype*="'+vartype+'"]')
      if(vartype==="column"){
    	  if($(e).attr("checked") === "checked"){
    		  $(value).attr("column",$(e).val());
    	  }
      }else if(vartype==="name"){
        $(value).attr("name", $(e).val());
      }
      else if(vartype==="placeholder"){
        $(value).attr("placeholder", $(e).val());
      } else if (vartype==="checkbox"){
        if($(e).is(":checked")){
          $(value).attr("checked", true);
        }
        else{
          $(value).attr("checked", false);
        }
      } else if (vartype==="option"){
        var options = $(e).val().split("\n");
        $(value).html("");
        $.each(options, function(i,e){
          $(value).append("\n      ");
          $(value).append($("<option>").text(e));
        });
      } else if (vartype==="checkboxes"){
        var checkboxes = $(e).val().split("\n");
        var group_name = $(".popover #name").val();
        $(value).html("\n      <!-- Multiple Checkboxes -->");
        $.each(checkboxes, function(i,e){
          if(e.length > 0){
            //$(value).append('\n      <label class="checkbox">\n        <input type="checkbox" value="'+e+'">\n        '+e+'\n      </label>');
        	  $(value).append('\n      <label class="checkbox">\n        <input type="checkbox" value="'+e+'" name="'+group_name+'">\n        '+e+'\n      </label>');
          }
        });
        $(value).append("\n  ")
      } else if (vartype==="radios"){
        var group_name = $(".popover #name").val();
        var radios = $(e).val().split("\n");
        $(value).html("\n      <!-- Multiple Radios -->");
        $.each(radios, function(i,e){
          if(e.length > 0){
            $(value).append('\n      <label class="radio">\n        <input type="radio" value="'+e+'" name="'+group_name+'">\n        '+e+'\n      </label>');
          }
        });
        $(value).append("\n  ")
          $($(value).find("input")[0]).attr("checked", true)
      } else if (vartype==="inline-checkboxes"){
        var checkboxes = $(e).val().split("\n");
        $(value).html("\n      <!-- Inline Checkboxes -->");
        $.each(checkboxes, function(i,e){
          if(e.length > 0){
            $(value).append('\n      <label class="checkbox inline">\n        <input type="checkbox" value="'+e+'">\n        '+e+'\n      </label>');
          }
        });
        $(value).append("\n  ")
      } else if (vartype==="inline-radios"){
        var radios = $(e).val().split("\n");
        var group_name = $(".popover #name").val();
        $(value).html("\n      <!-- Inline Radios -->");
        $.each(radios, function(i,e){
          if(e.length > 0){
            $(value).append('\n      <label class="radio inline">\n        <input type="radio" value="'+e+'" name="'+group_name+'">\n        '+e+'\n      </label>');
          }
        });
        $(value).append("\n  ")
          $($(value).find("input")[0]).attr("checked", true)
      } else if (vartype === "button"){
        var type =  $(".popover #color option:selected").attr("id");
        $(value).find("button").text($(e).val()).attr("class", "btn "+type);
      } else {
        $(value).text($(e).val());
      }
      
      
      
    $active_component.popover("hide");
    
    genSource();
    });
      
    });
  });
  
  
  $("#navtab").delegate("#sourcetab", "click", function(e){
    genSource();
  });
});
