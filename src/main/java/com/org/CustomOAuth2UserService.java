package com.org;

import com.org.model.User;
import com.org.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email    = oauth2User.getAttribute("email");
        String fullName = oauth2User.getAttribute("name");

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            // नवीन user — auto register
            user = new User();
            user.setEmail(email);
            user.setFullName(fullName);
            user.setAuthProvider("GOOGLE");
            user.setPassword(null);
            userRepository.save(user);
        }
        // existing user असेल (LOCAL किंवा GOOGLE) — direct login, काही बदल नाही

        return oauth2User;
    }
}
