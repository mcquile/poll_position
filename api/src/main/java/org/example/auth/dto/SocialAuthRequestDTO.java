package org.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialAuthRequestDTO {
    private String emailAddress;
    private String firstname;
    private String lastname;
    private String token;
    private String profilePic;
}
