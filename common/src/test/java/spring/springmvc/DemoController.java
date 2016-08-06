/**  
 * @FileName: DemoController.java 
 * @Package spring.springmvc 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.springmvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
 * @ClassName: DemoController 
 * @Description: TODO(describe in one sentence) 
 * @author ViVi 
 * @date 2015年9月7日 下午10:25:31  
 */

@Controller
public class DemoController {

    @RequestMapping("/hello")
    public void getString(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        System.out.println(">> access to DemoController");
        response.getWriter().println("<h1> hello </h1>");
    }
}
