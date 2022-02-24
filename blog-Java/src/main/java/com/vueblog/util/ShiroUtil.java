package com.vueblog.util;

import com.vueblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
    public static AccountProfile getSubject() {
        return (AccountProfile) SecurityUtils.getSubject();
    }

}
