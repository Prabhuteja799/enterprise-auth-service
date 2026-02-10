package com.telusko.security.request;

import com.telusko.security.model.RoleName;

import java.util.Set;

public record PatchUserRolesRequest(
        Set<RoleName> add ,
        Set <RoleName> remove
    ){ }
