package com.polyakovworkbox.tasktracker.springapp

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class TokenRetriever {

    fun getToken() : String {
        val restTemplate = RestTemplate()
        val fooResourceUrl = "http://localhost:8484/auth/realms/my_realm/protocol/openid-connect/token"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val tokenRequest: HttpEntity<String> = HttpEntity("client_id=my_client&grant_type=password&scope=openid&username=user&password=userpass", headers)

        val tokenResponse : LinkedHashMap<*, *>? = restTemplate.postForObject(fooResourceUrl, tokenRequest, LinkedHashMap::class.java)

        return tokenResponse?.get("access_token") as String
    }
}