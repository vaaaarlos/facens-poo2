package br.facens.poo2.ac1project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventPageableResponse {
  
  private Long id;
  private String name;
  private String place;
  private String startDate;
  private String startTime;

}