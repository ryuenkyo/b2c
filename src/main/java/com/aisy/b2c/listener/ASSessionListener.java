package com.aisy.b2c.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.model.CartModel;
import com.aisy.b2c.model.ClientModel;
import com.aisy.b2c.service.IShoppingCartService;
import com.aisy.b2c.util.ApplicationContextUtil;
import com.aisy.b2c.util.SYConst;

/**
 * Application Lifecycle Listener implementation class ASSessionListener
 *
 */
@WebListener
public class ASSessionListener implements HttpSessionListener {
	
	ApplicationContext ctx;

    /**
     * Default constructor. 
     */
    public ASSessionListener() {
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(se.getSession().getServletContext());
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  {
    	HttpSession session = se.getSession();
    	
    	Object flgObj = session.getAttribute(SYConst.SESSION.OP_CLOSE_FLG);
    	
    	boolean opCloseFlg = false;
    	
    	if (null != flgObj) {
    		opCloseFlg = (boolean) flgObj;
	    	if (!opCloseFlg) {
	    		ApplicationContextUtil.getInstance().init(ctx);
	    		IShoppingCartService cartService = 
	    				ApplicationContextUtil.getInstance().getBean(IShoppingCartService.class);
	    		
	    		CommonCache COMMON_CACHE = ApplicationContextUtil.getInstance().getBean(CommonCache.class);
	    		
	    		ClientModel cm = (ClientModel) session.getAttribute(SYConst.SESSION.FRONT_LOGIN_USER);
	    		List<CartModel> cartList = (List<CartModel>) session.getAttribute(SYConst.SESSION.CART);
	    		Map<String, Object> context = new HashMap<String, Object>();
	    		context.put(SYConst.CLIENT_PARAM_NAME.CLIENT_ID, cm.getClient().getClientId());
	    		context.put(SYConst.CART.CART_LIST, cartList);
	    		cartService.updateBack(context);
	    		COMMON_CACHE.SESSION_CONTAINER.remove(String.valueOf(cm.getClient().getClientId()));
	    	}
    	}
    	
    	
    	
    
    }
	
}
