package hbx.spring.demo.services.impl;

import hbx.spring.annotation.Services;
import hbx.spring.demo.services.IDemoService;

@Services("demoService")
public class DemoServiceImpl implements IDemoService {
    @Override
    public String getName(String name) {
        return "my Name is "  + name;
    }
}
