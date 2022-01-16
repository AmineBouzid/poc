package tse.poc.timemgr.tse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tse.poc.timemgr.tse.dao.ProjectRepository;
import tse.poc.timemgr.tse.dao.TimeRepository;
import tse.poc.timemgr.tse.dao.UserRepository;
import tse.poc.timemgr.tse.domain.Project;
import tse.poc.timemgr.tse.domain.Time;
import tse.poc.timemgr.tse.domain.User;
import tse.poc.timemgr.tse.payload.request.SingleUserRequest;
import tse.poc.timemgr.tse.payload.request.TimeRequest;
import tse.poc.timemgr.tse.payload.request.UpdateRequest;
import tse.poc.timemgr.tse.payload.response.MessageResponse;
import tse.poc.timemgr.tse.service.TimeService;


import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/time")
public class TimeController {

    @Autowired
    private TimeService timeService;



    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping(path = "/all", produces ="application/json")
    public List<Time> findAllTimes() {

        return this.timeService.findAllTimes();
    }

    //@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @DeleteMapping(path ="/delete/{id}")
    public ResponseEntity<?> deleteTime(@PathVariable Long id){
        return this.timeService.deleteTime(id);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(path=   "/usertime/{id}", produces ="application/json")
    public Optional<List<Time>>  getUserTimes(@PathVariable Long id) {
        return this.timeService.getUserTimes(id);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(path ="/add")
    public ResponseEntity<?> addTime(@Valid @RequestBody TimeRequest timeRequest) throws ParseException {
        return  this.timeService.addTime(timeRequest);
    }
}
