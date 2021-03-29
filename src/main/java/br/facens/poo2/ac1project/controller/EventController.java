package br.facens.poo2.ac1project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.facens.poo2.ac1project.dto.request.EventInsertRequest;
import br.facens.poo2.ac1project.dto.response.EventPageableResponse;
import br.facens.poo2.ac1project.dto.response.MessageResponse;
import br.facens.poo2.ac1project.exception.EventAlreadyRegisteredException;
import br.facens.poo2.ac1project.exception.IllegalDateScheduleException;
import br.facens.poo2.ac1project.exception.IllegalDateTimeFormat;
import br.facens.poo2.ac1project.service.EventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@Api(value = "Event Scheduler", description = "API for managing Events") 
@RequestMapping("/api/v1/events")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {

  private EventService eventService;
  
  @ApiOperation(value = "Event schedule operation")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Event successfully scheduled."),
      @ApiResponse(code = 400, message = "Missing required fields or wrong field range value."),
      @ApiResponse(code = 409, message = "Event already scheduled for the specified date-time.")
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MessageResponse save(@RequestBody @Valid EventInsertRequest eventInsertRequest) throws IllegalDateScheduleException, IllegalDateTimeFormat, EventAlreadyRegisteredException {
    return eventService.save(eventInsertRequest);
  }


  @ApiOperation(value = "List all scheduled events")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully listed all scheduled events")
  })
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<EventPageableResponse> findAll(
      @RequestParam(value =          "page", defaultValue =   "0")  Integer page,
      @RequestParam(value =  "linesPerPage", defaultValue =   "8")  Integer linesPerPage,
      @RequestParam(value =     "direction", defaultValue = "ASC")   String direction,
      @RequestParam(value =       "orderBy", defaultValue =  "id")   String orderBy,
      @RequestParam(value =          "name", defaultValue =    "")   String name,
      @RequestParam(value =   "description", defaultValue =    "")   String description,
      @RequestParam(value =         "place", defaultValue =    "")   String place,
      @RequestParam(value =     "startDate", defaultValue =    "")   String startDate
  ) throws IllegalDateTimeFormat {
    PageRequest pageRequest = PageRequest.of(0, 8, Direction.valueOf(direction), orderBy);
    return eventService.findAll(pageRequest, name, description, place, startDate);
  }
}
