package com.MKaaN.OtelBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private String role;
    private String firstName;
    private String lastName;

    public JwtResponse(String token, String id, String username, String email, String role, String firstName, String lastName) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public static JwtResponseBuilder builder() {
        return new JwtResponseBuilder();
    }

    public static class JwtResponseBuilder {
        private String token;
        private String type = "Bearer";
        private String id;
        private String username;
        private String email;
        private String role;
        private String firstName;
        private String lastName;

        public JwtResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public JwtResponseBuilder type(String type) {
            this.type = type;
            return this;
        }

        public JwtResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public JwtResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public JwtResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public JwtResponseBuilder role(String role) {
            this.role = role;
            return this;
        }

        public JwtResponseBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public JwtResponseBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(token, id, username, email, role, firstName, lastName);
        }
    }
}