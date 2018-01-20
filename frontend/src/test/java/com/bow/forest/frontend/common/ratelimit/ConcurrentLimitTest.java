package com.bow.forest.frontend.common.ratelimit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author vv
 * @since 2018/1/20.
 */
public class ConcurrentLimitTest {

    @Test
    public void test1() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/user/addUser");
        sendRequest(request);
    }

    @Test
    public void test2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                MockHttpServletRequest request = new MockHttpServletRequest();
                request.setRequestURI("/activity/bonus");
                request.setParameter("userId", "vv1");
                request.setParameter("ip", "127.0.0.1");
                sendRequest(request);
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                MockHttpServletRequest request = new MockHttpServletRequest();
                request.setRequestURI("/activity/bonus");
                request.setParameter("userId", "vv2");
                request.setParameter("ip", "10.168.18.23");
                sendRequest(request);
            }
        });
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    private void sendRequest(HttpServletRequest request) {
        ConcurrentLimit limit = new ConcurrentLimit();
        while (true) {
            try {
                boolean result = limit.handle(request);
                String userId = request.getParameter("userId");
                String ip = request.getParameter("ip");
                System.out.println(userId + "@" + ip + "-" + (result == true ? "pass" : "reject"));
                // 每秒5次请求
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}