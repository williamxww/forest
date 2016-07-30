package com.bow.forest.backend.serviceimpl;

import com.bow.forest.common.service.IDemoService;
import org.springframework.stereotype.Service;

/**
 * Created by vv on 2016/7/30.test git
 */
@Service("demoService")
public class DemoService implements IDemoService {
    public String getName() {
        return "run forrest Gump";
    }
}
