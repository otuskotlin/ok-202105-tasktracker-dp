package com.polyakovworkbox.tasktracker.springapp

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@Configuration
@ConfigurationProperties(prefix = "mykeycloak")
class TokenRetriever {

    lateinit var resourceUrl: String

    fun getToken() : String {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val tokenRequest: HttpEntity<String> = HttpEntity("client_id=my_client&grant_type=password&scope=openid&username=user&password=userpass", headers)

        val tokenResponse : LinkedHashMap<*, *>? = restTemplate.postForObject(resourceUrl, tokenRequest, LinkedHashMap::class.java)

        return tokenResponse?.get("access_token") as String
    }
}