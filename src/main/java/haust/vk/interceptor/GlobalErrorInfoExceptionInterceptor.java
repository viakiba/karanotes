package haust.vk.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.interfaces.MesscodeInterface;
import haust.vk.result.ResultBody;

/**
 * 异常处理响应类
 * @author viakiba
 */
@ControllerAdvice
public class GlobalErrorInfoExceptionInterceptor extends HandlerInterceptorAdapter{
	private static Logger logger = Logger.getLogger(GlobalErrorInfoExceptionInterceptor.class);
	/**
	 * 异常拦截方法
	 * @Author : viakiba
	 * @param req
	 * @param exceptioninfo
	 * @return
	 * 2017-04-15
	 */
	@ExceptionHandler(value=GlobalErrorInfoException.class)
	public @ResponseBody ResultBody errorInfoHandlerOverJson(HttpServletRequest req,GlobalErrorInfoException exceptioninfo){
		MesscodeInterface messcodeInterface = exceptioninfo.getMesscodeInterface();
		ResultBody result = new ResultBody(messcodeInterface);
		logger.info("异常处理", exceptioninfo);
        return result;
	}
}
