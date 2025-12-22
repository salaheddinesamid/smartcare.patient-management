package com.healthcare.patient_management.utils;

import com.healthcare.patient_management.dto.ApiResponse;
import com.healthcare.patient_management.dto.NewUserRequestDto;
import com.healthcare.patient_management.dto.NewUserResponseDto;
import com.healthcare.patient_management.dto.UserDto;
import com.healthcare.patient_management.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserUtils {
    private static final String USER_MANAGEMENT_URI = "http://localhost:8080";

    private final RestTemplate restTemplate;

    @Autowired
    public UserUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UserDto> getUsers(List<Integer> ids){
        String uri = USER_MANAGEMENT_URI + "/api/user/get-users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Integer>> entity = new HttpEntity<>(ids,headers);

        ResponseEntity<ApiResponse<List<UserDto>>> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<>() {}
                );

        return response.getBody().getData();
    }

    public NewUserResponseDto createUser(NewUserRequestDto requestDto){
        String uri = USER_MANAGEMENT_URI + "/api/user/new";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<NewUserRequestDto> entity = new HttpEntity<>(requestDto  ,headers);
        ResponseEntity<ApiResponse<NewUserResponseDto>> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<>() {}
                );

        return response.getBody().getData();
    }


    public List<Integer> getUserIds(List<Patient> patients){
        return patients.stream()
                .map(Patient::getUserId).toList();
    }
}
