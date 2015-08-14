package com.saas.identity.ad.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;
import com.saas.identity.ad.entity.ADConfig;

public class LDAPService {

    public LDAPConnection lc;

    public boolean getConnection(String host, String password, String port, int ldapVersion, String loginDN) {
        boolean flag = false;
        try {
            lc = new LDAPConnection();
            lc.connect(host, Integer.parseInt(port));
            lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));
            flag = true;
        } catch (LDAPException e) {
            System.out.println("########## LDAP connect failed ##########");
        } catch (UnsupportedEncodingException e) {
            System.out.println("########## LDAP bind failed ##########");
        } catch (Exception e) {
            System.out.println("########## String conver int failed ##########");
        }

        return flag;
    }

    public void disConnection() {
        try {
            if (null != lc && lc.isConnected()) {
                lc.disconnect();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> searchAttributeNames(String searchDN, int searchScope, String searchFilter) {

        List<String> resultList = new ArrayList<String>();

        try {
            LDAPSearchResults searchResults = lc.search(searchDN, searchScope, searchFilter, null, false);
            if (searchResults.hasMore()) {
                LDAPEntry nextEntry = null;
                nextEntry = searchResults.next();
                LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();

                Iterator<LDAPAttribute> allAttributes = attributeSet.iterator();
                while (allAttributes.hasNext()) {
                    LDAPAttribute attribute = allAttributes.next();
                    String attributeName = attribute.getName();
                    if ("objectClass".equalsIgnoreCase(attributeName)) {
                        continue;
                    }
                    resultList.add(attributeName);
                }
            }
        } catch (LDAPException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static void main(String[] args) {
        ADConfig adConfig = new ADConfig();
        adConfig.setHost("192.168.80.114");
        adConfig.setPort("389");
        adConfig.setLoginDN("o=saas.com,o=asp");
        adConfig.setPassword("secret");
        adConfig.setSearchDN("uid=gengjun@outlook.com,ou=people,ou=saas-reseller,o=saas-reseller-sit.com");

    }
}
