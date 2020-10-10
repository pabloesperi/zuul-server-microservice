package com.proyects.microservices.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreTiempoTranscurridoFilter extends ZuulFilter{
	
	private Logger logger = Logger.getLogger(PreTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext context = RequestContext.getCurrentContext();
		
		HttpServletRequest req = context.getRequest();
		
		logger.info(String.format("%s request enrutado a %s", req.getMethod(), req.getRequestURL().toString()));
		
		Long tiempoInicio = System.currentTimeMillis();
		
		req.setAttribute("TiempoInicio", tiempoInicio);
		
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
