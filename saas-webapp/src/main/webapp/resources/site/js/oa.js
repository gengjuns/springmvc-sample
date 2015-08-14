/**
 * Additional validators for OfficeApps
 */

// validate if fields are s
 jQuery.validator.addMethod("notEqual", function(value, element, param) {
 return this.optional(element) || value != $(param).val();
}, "This has to be different.");


// refex validator useful for Password matching, etc.
$.validator.addMethod("regex", function(value, element, regexp) {
    return this.optional(element) || regexp.test(value);
    }, "Please check your input.");

// OfficeApps password validator
jQuery.validator.addMethod("OApassword", function(value, element) {
	return this.optional(element) || /^((?=.*\d)(?=.*[a-z])(?=.*[!@#$%^&*_+?~`]))|((?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_+?~`]))|((?=.*\d)(?=.*[a-z])(?=.*[A-Z]))|((?=.*\d)(?=.*[A-Z])(?=.*[!@#$%^&*_+?~`])).{8,}$/.test(value);
	}, "Please specify a valid password");

// Australian phone number validator
jQuery.validator.addMethod("phoneAU", function(value, element) {
	return this.optional(element) || /^(0?)\d{9}$/.test(value.replace(/(\s|-)+/g, ""));
	}, "Please specify a valid phone number");

//all country phone number validator
jQuery.validator.addMethod("phoneALL", function(value, element) {
	return this.optional(element) || /^(0?)\d{9}$/.test(value.replace(/(\s|-)+/g, ""));
	}, "Please specify a valid phone number");

// Australian mobile number validator
jQuery.validator.addMethod("mobileAU", function(value, element) {
	return this.optional(element) || /^(0?)4\d{8}$/.test(value.replace(/(\s|-)+/g, ""));
	}, "Please specify a valid mobile number");

// All other countries phone number validator
jQuery.validator.addMethod("phoneOther1", function(value, element) {
	var prefix = $(element).prevAll('select').val();
	prefix = getCountryCodeForRegion(prefix);
	prefix = '+' + prefix;
	value = prefix + value;
	var isValid = isValidPhoneNumber(value);
	//return this.optional(element) || /^(0?)\d{4,15}$/.test(value.replace(/(\s|-)+/g, ""));
	return this.optional(element) || isValid;
	}, "Please specify a valid phone number");

//All other countries phone number validator
jQuery.validator.addMethod("phoneOther", function(value, element, param) {
	var prefix = $(param).val();//get the prefix
	prefix = getCountryCodeForRegion(prefix);
	prefix = '+' + prefix;
	value = prefix + value;
	var isValid = isValidPhoneNumber(value);
	return this.optional(element) || isValid;
	}, "Please specify a valid phone number");


// OfficeApps domain validtor
jQuery.validator.addMethod("OAdomain", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9][a-zA-Z0-9-]{1,61}[a-zA-Z0-9]\.[a-zA-Z\.]{1,10}[a-zA-Z]$/.test(value);}, "Please specify a valid domain");

jQuery.validator.addMethod("filesize", function(value, element, param) { 
	// param = size (en bytes) // element = element to validate (<input>) // value = value of the element (file name) 
	return this.optional(element) || (element.files[0].size <= param) },"Your file size is too big");

function cleanDomainField(s) {
	s = s.replace(/^[^A-Za-z0-9]+/, "");
	return s.replace(/([^A-Za-z0-9/-/.])+/g, ""); 
	}

function cleanPhoneField(s) {
	s = s.replace(/^[^1-9]+/, ""); // trim leading non-digits
	return s.replace(/(\s|-)+/g, ""); // remove spaces and dashes
	}

/**
 * This method is used to valid the phone number.
 * @param number
 * @return
 */
function isValidPhoneNumber(number){
	var phoneUtil = i18n.phonenumbers.PhoneNumberUtil.getInstance();
	var phonenumber;
	try {
		phonenumber = phoneUtil.parseAndKeepRawInput(number, null);
	} catch (error) {
		return false;
	}
	return phoneUtil.isValidNumber(phonenumber);
}

function getCountryCodeForRegion(region){
	var phoneUtil = i18n.phonenumbers.PhoneNumberUtil.getInstance();
	return phoneUtil.getCountryCodeForRegion(region);
}

String.prototype.startWith = function(str) {
	var reg = new RegExp("^" + str);
	return reg.test(this);
}

String.prototype.endWith = function(str) {
	var reg = new RegExp(str + "$");
	return reg.test(this);
}