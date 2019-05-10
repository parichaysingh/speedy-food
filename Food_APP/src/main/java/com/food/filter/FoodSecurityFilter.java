package com.food.filter;



import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import com.food.bean.ErrorResponse;
import com.food.util.UUIDUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@ComponentScan
@Component
public class FoodSecurityFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(FoodSecurityFilter.class);
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	OkHttpClient client = new OkHttpClient();
	
	@Value("${header.allow.origin}")
	private String allowOrigin;
	
	@Value("${header.allow.credentials}")
	private String allowCredentials;
	
	@Value("${header.allow.methods}")
	private String allowMethods;
	
	@Value("${header.allow.max.age}")
	private String maxAge;
	
	@Value("${header.allow.headers}")
	private String allowHeaders;
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info("VaSecurityFilter : initialized....");
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String transactionId = UUIDUtil.randomUUID();
		LOG.info("FoodSecurityFilter : FoodSecurity transaction initiated with transactionId : "+ transactionId);
		MDC.put("transactionId", transactionId);
		httpServletResponse.setHeader("cl_transaction_id", transactionId);
		httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
	    httpServletResponse.setHeader("Access-Control-Allow-Credentials", allowCredentials);
	    httpServletResponse.setHeader("Access-Control-Allow-Methods", allowMethods);
	    httpServletResponse.setHeader("Access-Control-Max-Age", maxAge);
	    httpServletResponse.setHeader("Access-Control-Allow-Headers", allowHeaders);
	    boolean isChainingDisabled = false;
	    if (!isChainingDisabled)
		        	{
				chain.doFilter(request, response);
			} 
	    
	}
	
	private void sendResponse(HttpServletResponse httpServletResponse,String response) throws IOException{
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		httpServletResponse.setContentType("application/json");
		httpServletResponse.getWriter().write(response);
	}

	private void sendErrorResponse(HttpServletResponse httpServletResponse , String errorCode , String errorMessage) throws IOException{
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(errorCode);
		errorResponse.setErrorMessage(errorMessage);
		httpServletResponse.setContentType("application/json");
		httpServletResponse.getWriter().write(errorResponse.toString());
	}
	
	private String get(String url,String transactionId) throws IOException {
	    Request request = new Request.Builder()
	        .url(url)
	        .addHeader("transactionId", transactionId)
	        .build();
	    try (Response response = client.newCall(request).execute()) {
	      return response.body().string();
	    }
	}
	
	private String post(String url, String json,String transactionId) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
	    Request request = new Request.Builder()
	        .url(url)
	        .addHeader("Content-Type", "application/json")
	        .addHeader("Accept", "application/json")
	        .addHeader("transactionId", transactionId)
	        .post(body)
	        .build();
	    try (Response response = client.newCall(request).execute()) {
	      return response.body().string();
	    }
	}
	

	private String put(String url, String json,String transactionId) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
	    Request request = new Request.Builder()
	        .url(url)
	        .addHeader("Content-Type", "application/json")
	        .addHeader("Accept", "application/json")
	        .addHeader("transactionId", transactionId)
	        .post(body)
	        .build();
	    try (Response response = client.newCall(request).execute()) {
	      return response.body().string();
	    }
	}
	
	private String delete(String url,String transactionId) throws IOException {
	    Request request = new Request.Builder()
	        .url(url)
	        .addHeader("transactionId", transactionId)
	        .delete()
	        .build();
	    try (Response response = client.newCall(request).execute()) {
	      return response.body().string();
	    }
	}
	
	
	private String getBody(HttpServletRequest servletRequest) {
		String body = "";
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = servletRequest.getReader();
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				stringBuilder.append(charBuffer, 0, bytesRead);
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (bufferedReader != null) {
				try {
				
					bufferedReader.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		body = stringBuilder.toString();
		return body;
	}
	
	@Override
	public void destroy() {
		LOG.info("vaSecurityFilter : destroying....");
	}

}
