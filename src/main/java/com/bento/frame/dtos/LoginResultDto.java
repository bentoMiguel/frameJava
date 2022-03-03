package com.bento.frame.dtos;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginResultDto {
   
   @NonNull
   private String jwt;
}
