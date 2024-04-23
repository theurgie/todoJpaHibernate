package com.springPractice.rest.webservices.restfulwebservices.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class JwtAuthenticationController {
    
    private final JwtTokenService tokenService;
    
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationController(JwtTokenService tokenService, 
            AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtTokenResponse> generateToken(
            @RequestBody JwtTokenRequest jwtTokenRequest) {
        
        var authenticationToken = 
                new UsernamePasswordAuthenticationToken(
                        jwtTokenRequest.username(), 
                        jwtTokenRequest.password());
        
        var authentication = 
                authenticationManager.authenticate(authenticationToken);
        
        var token = tokenService.generateToken(authentication);
        
        return ResponseEntity.ok(new JwtTokenResponse(token));
    }
}

//{
//"token": "eyJraWQiOiIzMTU2ZjdjOS03YWZjLTRkZTItOWI3OS04ZjE1ZTkxZjZjYzMiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoidXNlciIsImV4cCI6MTcxMzU1NzM2OCwiaWF0IjoxNzEzNTUxOTY4LCJzY29wZSI6IlJPTEVfVVNFUiJ9.E4K3GOScLZc-f6J98mnKyfqBVP0Wx54ibLln-ssQpITYnSrN6f-I6nJuJWgxHvEkSd7xiA0gQS70uOsT1GWzFmo1jKlgL13bKpFiWVWaKmUaRFOMQs8PjBY--DoKV4cn7rhtZpHGcXNLakDU3JpMVyePpr_Qjz1dQSdEXcbtziQyhWUsGfZuorlRmY0s5zEpKeT-6G_E0gtsAKP7F0p6ZvUOjk0FZvE6Ng4ZqO1u1QaTbLbzucSGZL5F7XDmqdGkcwJDM8pAooCiqtMkYLoULbxB2uXOn70ZIQt3rtvdOml2VeLpSMpOr7IBkHLj7hUvIGXKroJ8VKZ7V_9C6NOjdA"
//}


