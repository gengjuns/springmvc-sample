
package com.saas.identity.enrollment.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;

import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CaptchaServiceSingleton extends ListImageCaptchaEngine {
    private static Logger log = Logger.getLogger(CaptchaServiceSingleton.class);

    private static final Integer MIN_WORD_LENGTH = new Integer(5);

    private static final Integer MAX_WORD_LENGTH = new Integer(5);

    private static final Integer IMAGE_WIDTH = new Integer(137);

    private static final Integer IMAGE_HEIGHT = new Integer(35);

    private static final Integer MIN_FONT_SIZE = new Integer(20);

    private static final Integer MAX_FONT_SIZE = new Integer(20);

    private static final String NUMERIC_CHARS = "23456789";// No numeric 0 //"23456789"

    private static final String UPPER_ASCII_CHARS = "ABCDEFGHJKLMNPQRSTXYZ";// No//"ABCDEFGHJKLMNPQRSTXYZ"

    /**
     * Singleton instance of this class
     */
    private static CaptchaServiceSingleton instance = new CaptchaServiceSingleton();

    ImageCaptcha imageCaptcha = null;

    /**
     * Private constructor to prevent instantiation
     */
    private CaptchaServiceSingleton() {
    }

    public static CaptchaServiceSingleton getInstance() {
        return instance;
    }

    protected void buildInitialFactories() {
        try {
            RandomTextPaster testPaster = new RandomTextPaster(MIN_WORD_LENGTH, MAX_WORD_LENGTH, Color.black);

            GradientBackgroundGenerator backgroundGen = new GradientBackgroundGenerator(IMAGE_WIDTH, IMAGE_HEIGHT, Color.white, Color.white);

            // character too much
            RandomFontGenerator fontGen = new RandomFontGenerator(MIN_FONT_SIZE, MAX_FONT_SIZE, new Font[] { new Font("Arial", 0, 10) });// to
                                                                                                                                         // easy
                                                                                                                                         // to
                                                                                                                                         // read
            // fontGeneratorList.add(new
            // answer
            WordGenerator words = new RandomWordGenerator(NUMERIC_CHARS + UPPER_ASCII_CHARS);

            ComposedWordToImage word2image = new ComposedWordToImage(fontGen, backgroundGen, testPaster);
            ImageCaptchaFactory factory = new GimpyFactory(words, word2image);
            addFactory(factory);
        } catch (Exception ex) {
            log.error("Exception occured when generate security code." + ex);
        }
    }

    /**
     * Write the captcha image of current user to the servlet response
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @throws IOException
     */
    public void writeCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        imageCaptcha = getNextImageCaptcha();
        HttpSession session = request.getSession();
        session.setAttribute("imageCaptcha", imageCaptcha);
        BufferedImage image = (BufferedImage) imageCaptcha.getChallenge();

        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // render the captcha challenge as a JPEG image in the response
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            response.setContentType("image/jpeg");

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
            encoder.encode(image);

            outputStream.flush();
            outputStream.close();
            outputStream = null;// no close twice
        } catch (IOException ex) {
            log.error("Exception when generate image." + ex);
            throw ex;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                }
            }
            imageCaptcha.disposeChallenge();
        }
    }

    public boolean validateCaptchaResponse(String validateCode, HttpSession session, BindingResult bindingResult) {
        boolean flag = true;
        try {
            imageCaptcha = (ImageCaptcha) session.getAttribute("imageCaptcha");
            if (imageCaptcha == null) {
                // log.info("validateCaptchaResponse returned false due to
                // imageCaptcha is null");
                flag = false;
            }
            // validateCode = validateCode.toLowerCase();// use upper case for
            validateCode = validateCode.toUpperCase();// use upper case for
            // easier usage
            flag = (imageCaptcha.validateResponse(validateCode)).booleanValue();
            session.removeAttribute("imageCaptcha");
            return flag;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception occuren when check security code." + ex);
            flag = false;
        }
        if (!flag) {
            bindingResult.rejectValue("checkCode", "error_enrollment_forgotpassword_checkcodeiswrong");
        }
        return flag;
    }

}
