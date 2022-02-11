package com.fsnteam.fsnweb.service;

import com.fsnteam.fsnweb.util.Result;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IUsersService {
    public Result getAllUsers(Map params);

    public Result getAllUsersNormalInfo(Map params);
}
