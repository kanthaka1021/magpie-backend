package com.magpie.config;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(RequestFilter.class);

	public RequestFilter() {
		log.info("SimpleCORSFilter init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;

	    response.setHeader("Access-Control-Allow-Origin", "*");
	    
	    this.traceRequestInfo((HttpServletRequest)request);
	    
	    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) { // 현재 지원하는 메소드 정보 
	        response.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT");
	        response.setHeader("Access-Control-Max-Age", "3600");
	        response.setHeader("Access-Control-Allow-Headers", "content-type,access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
	        response.setStatus(HttpServletResponse.SC_OK);
	    } else { // POST,GET,DELETE,PUT인 경우
			chain.doFilter(req, res);
	    }
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}
	
	private void traceRequestInfo(HttpServletRequest httpRequest) throws IOException{
		log.debug("REQ : request uri => " + httpRequest.getRequestURI());
		log.debug("REQ : request url => " + httpRequest.getRequestURL());
		log.debug("REQ : query string => " + Strings.nullToEmpty(httpRequest.getQueryString()));
		log.debug("REQ : remote address => " + httpRequest.getRemoteAddr());
		log.debug("REQ : remote port => " + httpRequest.getRemotePort());
		log.debug("REQ : method => " + httpRequest.getMethod());
	}
	
}
