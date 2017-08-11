package com.gome.wa.util;

import com.gome.wa.domain.User;
import com.gome.wa.domain.UserBranchInfo;
import com.gome.wa.domain.UserPosition;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


public class ClientUtil {

    public static String CLIENT_INFO = "CLIENT_INFO";

    public static ThreadLocal<Map<String, Object>> data = new ThreadLocal<Map<String, Object>>();

    /**
     * 获取客户端信息
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        if (data.get() != null) {
            return data.get().get(key);
        }
        return null;
    }

    /**
     * 设置客户端信息
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value, HttpSession session) {
        if (data.get() == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(key, value);
            data.set(map);
        } else {
            data.get().put(key, value);
        }
        session.setAttribute(CLIENT_INFO, data.get());
    }

    /**
     * 在过滤器或者拦截器中使用
     * 示例：ClientUtil.set(request.getSession());
     *
     * @param
     */
    @SuppressWarnings("unchecked")
    public static void set(HttpSession session) {
        data.set((Map<String, Object>) session.getAttribute(CLIENT_INFO));
    }

    public static void setBranchNo(String branch_no, HttpSession session) {
        set(USER_BRANCHNO, branch_no, session);
    }

    /**
     * 获取登录用户的分部编码
     *
     * @return
     */
    public static String getBranchNo() {
        return (String) get(USER_BRANCHNO);
    }

    /**
     * 获取用户id
     *
     * @param
     * @return
     */
    public static String getEmployeeId() {
        User user = getUser();
        if (user != null) {
            return user.getEmployeeId();
        }
        return null;
    }

    public static final String USER = "USER";
    public static final String USER_ROLE = "USER_ROLE";
    public static final String USER_POSITION = "USER_POSITION";
    public static final String USER_BRANCHNO = "USER_BRANCHNO";
    public static final String USER_BRANCHINFO = "USER_BRANCHINFO";

    public static void setRole(String role, HttpSession session) {
        set(USER_ROLE, role, session);
    }

    public static String getRole() {
        return (String) get(USER_ROLE);
    }

    public static void setPosition(UserPosition position, HttpSession session) {
        set(USER_POSITION, position, session);
    }

    public static UserPosition getPosition() {
        return (UserPosition) get(USER_POSITION);
    }

    public static void setUser(User user, HttpSession session) {
        set(USER, user, session);
    }

    public static User getUser() {
        return (User) get(USER);
    }

    public static String getSid() {
        return (String) get(Constant.SHOP_USER_ID);
    }


    public static void setUserBranchInfo(UserBranchInfo userBranchInfo,
                                         HttpSession session) {
        set(USER_BRANCHINFO, userBranchInfo, session);
    }

    public static UserBranchInfo getUserBranchInfo() {
        return (UserBranchInfo) get(USER_BRANCHINFO);
    }

    public static final String LV_1 = "1";
    public static final String LV_2 = "2";
    public static final String LV_3 = "3";
    public static final String LV_4 = "4";

    public static String getLevel() {
        return (String) get(Constant.LEADER_LEVEL);

    }

    public static String getLevelCode() {
        return (String) get(Constant.LEADER_CODE);
    }

    public static String changeLevel(String level) {
        if ("0".equals(level) || "1".equals(level)) {
            return LV_1;
        } else if ("2".equals(level)) {
            return LV_2;
        } else if ("3".equals(level)) {
            return LV_3;
        } else if ("6".equals(level)) {
            return LV_4;
        }
        return LV_1;
//		if(level.equals("0") || level.equals("1")){
//			return LV_1;
//		}else if(level.equals("2")){
//			return LV_2;
//		}else if(level.equals("3")){
//			return LV_3;
//		}else if(level.equals("6")){
//			return LV_4;
//		}
//		return null;
    }


}
