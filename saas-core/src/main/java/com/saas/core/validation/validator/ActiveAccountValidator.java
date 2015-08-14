package com.saas.core.validation.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.saas.identity.user.entitiy.User;
import com.saas.otp.entity.Otp;
import com.saas.otp.repository.OtpRepository;

public class ActiveAccountValidator implements Validator{
	
	private String otp;
	private OtpRepository otpRepository;
	
	public ActiveAccountValidator(String otp,OtpRepository otpRepository) {
		this.otp = otp;
		this.otpRepository = otpRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		//1 otp delete_indicator is N
		Otp otpTo = otpRepository.findByOtp(otp);
		if(otpTo == null || otpTo.getDeleteIndicator().equalsIgnoreCase("Y")){
			errors.reject("msg_active_otp_valid");
			return;
		}
		
		//2 password/confirmPassword/question answer is not null
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "msg_active_password_empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "msg_active_passwordConfirm_empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "question1", "msg_active_question1_empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "question2", "msg_active_question2_empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "answer1", "msg_active_answer1_empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "answer2", "msg_active_answer2_empty");
		
		//3 password equals confirmPassword
		if(!user.getPasswordConfirm().trim().equals(user.getPassword().trim())){
			errors.rejectValue("passwordConfirm", "msg_active_password_confirmed_valid");
		}
		
		//4 check whether newpassword long than 8 and contains A-Z a-z 0-9 #$%^&*(, at least three of them
		if(user.getPassword().trim().length() < 8){
			errors.rejectValue("password", "msg_active_password_password_length");
		}
		
        Pattern p1 = Pattern.compile("\\S*[A-Z]\\S*");
        Pattern p2 = Pattern.compile("\\S*[a-z]\\S*");
        Pattern p3 = Pattern.compile("\\S*\\d\\S*");
        Pattern p4 = Pattern.compile("\\S*[^A-Z a-z 0-9]\\S*");

        int number = 0;

        if (p1.matcher(user.getPassword().trim()).matches()) {
            number++;
        }

        if (p2.matcher(user.getPassword().trim()).matches()) {
            number++;
        }

        if (p3.matcher(user.getPassword().trim()).matches()) {
            number++;
        }

        if (p4.matcher(user.getPassword().trim()).matches()) {
            number++;
        }

        if (number < 3) {
        	errors.rejectValue("password", "msg_active_password_password_regex");
        }
		
		//5 question1/answer1 not equals question2/answer2
		if(user.getQuestion1().trim().equals(user.getQuestion2().trim())){
			errors.rejectValue("question2", "msg_active_question_same_valid");
		}
		if(user.getAnswer1().trim().equals(user.getAnswer2().trim())){
			errors.rejectValue("answer2", "msg_active_answer_same_valid");
		}
	}

}
