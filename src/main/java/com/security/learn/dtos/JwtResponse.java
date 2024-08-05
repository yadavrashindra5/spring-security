package com.security.learn.dtos;

import com.security.learn.entities.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private String token;
    User user;
    private String refreshToken;
}
