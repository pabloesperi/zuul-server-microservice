package com.proyects.microservices.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter{
	
	private Logger logger = Logger.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext context = RequestContext.getCurrentContext();
		
		HttpServletRequest req = context.getRequest();
		
		logger.info("Entrando a post");
		
		Long tiempoInicio = (Long) req.getAttribute("TiempoInicio");
		
		Long tiempoFin = System.currentTimeMillis();
		
		Long tiempoTardado = tiempoInicio - tiempoFin;
		
		req.setAttribute("Tiempo fin", tiempoFin);
		
		logger.info("Tiempo transcurrido en segundos " + tiempoTardado.doubleValue()/1000.00);
		logger.info(String.format("Tiempo transcurrido en segundos %s seg.", tiempoTardado.doubleValue()/1000.00));
		logger.info("Tiempo transcurrido en milisegundos " + tiempoTardado);

		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}
//	post y pre son palabras clave

	@Override
	public int filterOrder() {
		return 1;
	}

}
