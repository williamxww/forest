/**  
 * @FileName: SpringMvcTest.java 
 * @Package beanfactory 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @ClassName: SpringMvcTest
 * @Description:
 * @author ViVi
 * @date 2015年8月19日 下午9:58:42
 */

public class SpringMvcTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringMvcTest.class);
    HandlerMapping handlerMapping = null;

    HandlerAdapter handlerAdapter = null;

    @Before
    public void initSpringMvcContainer() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "spring/springmvc/applicationContext-springmvc.xml" });

        MockServletContext sc = new MockServletContext();
        sc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);

        handlerMapping = context.getBean(RequestMappingHandlerMapping.class);
        handlerAdapter = context.getBean(RequestMappingHandlerAdapter.class);
        // handlerAdapter = (HandlerAdapter) context.getBean(context
        // .getBeanNamesForType(RequestMappingHandlerAdapter.class)[0]);

    }


    public ModelAndView excuteAction(HttpServletRequest request, HttpServletResponse response) {

        // 这里需要声明request的实际类型，否则会报错
        request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
        HandlerExecutionChain chain = null;
        ModelAndView model = null;
        try {
            chain = handlerMapping.getHandler(request);
            model = handlerAdapter.handle(request, response, chain.getHandler());
        } catch (Exception e) {
            logger.error("handlerAdapter执行出错了", e);
        }
        return model;
    }

    @Test
    public void test() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/getString.do");
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setParameter("param", "aaa");
        this.excuteAction(request, response);
    }

}
