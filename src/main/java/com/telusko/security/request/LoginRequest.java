package com.telusko.security.request;

import javax.swing.*;

public record LoginRequest(
        String username,
        String password
) {
}
