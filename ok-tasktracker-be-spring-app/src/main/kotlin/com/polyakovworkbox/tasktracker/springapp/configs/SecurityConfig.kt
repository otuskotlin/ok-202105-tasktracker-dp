package com.polyakovworkbox.tasktracker.springapp.configs

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("taskUser").password(passwordEncoder().encode("taskUserPass"))
            .authorities("ROLE_USER")
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests().anyRequest().authenticated()
            .and().httpBasic()
            .and().sessionManagement().disable()
            .oauth2ResourceServer().jwt()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
