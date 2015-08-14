package com.saas.core.web.interceptor;

import com.saas.Constants;
import com.saas.core.annotation.WebComponent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 
 * @since 25/02/2013 5:49 PM
 */
@WebComponent
public class WebConfigInterceptor extends HandlerInterceptorAdapter {

    @Value("${ui.responsive.enabled}")
    protected boolean uiResponsiveEnabled = true;

    @Value("${ui.theme.cookie.name:SaaSApp_THEME_COOKIE}")
    protected String themeCookieName;

    @Value("${ui.theme.default.name:default}")
    protected String defaultThemeName;

    @Value("${ui.theme.cookie.duration:3600}")
    protected int themeCookieDuration;

    @Value("${ui.secure.cookie:false}")
    protected boolean useSecureCookie;

    @Value("${ui.version.no}")
    protected String versionNo;

    @Value("${ui.session.logout.timeout}")
    protected String sessionLogoutTimeout;


    public boolean isUiResponsiveEnabled() {
        return uiResponsiveEnabled;
    }


    public String getVersionNo() {
        return versionNo;
    }


    public String getSessionLogoutTimeout() {
        return sessionLogoutTimeout;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        checkThemeCookie(request, response);
        return super.preHandle(request, response, handler);
    }


    protected void checkThemeCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie themeCookie = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (themeCookieName.equalsIgnoreCase(cookie.getName())) {
                    themeCookie = cookie;
                    break;
                }
            }
        }

        //extends cookie timing
        if (themeCookie != null) {
            themeCookie.setSecure(useSecureCookie || request.isSecure());
            themeCookie.setMaxAge(themeCookieDuration);
            themeCookie.setPath(request.getContextPath() + "/");

            response.addCookie(themeCookie);
        }

        String bootstrapThemeName = themeCookie != null ? themeCookie.getValue() : defaultThemeName;
        String kendoThemeName = "bootstrap";
        if (bootstrapThemeName.equals("cerulean")) {
            kendoThemeName = "blueopal";
        } else if (bootstrapThemeName.equals("cosmo")) {
            kendoThemeName = "metro";
        } else if (bootstrapThemeName.equals("slate")) {
            kendoThemeName = "moonlight";
        }

        request.setAttribute(Constants.BOOTSTRAP_THEME_NAME, bootstrapThemeName);
        request.setAttribute(Constants.KENDO_THEME_NAME, kendoThemeName);
    }


}
