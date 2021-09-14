package com.putstack.user_service_query.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginQuery {
    String email;
    String password;
}
