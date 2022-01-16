package tse.poc.timemgr.tse.service;

import org.springframework.http.ResponseEntity;
import tse.poc.timemgr.tse.domain.Time;
import tse.poc.timemgr.tse.payload.request.TimeRequest;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface TimeService {

    List<Time> findAllTimes();

    ResponseEntity<?> deleteTime(Long id);

    Optional<List<Time>> getUserTimes(Long id);

    ResponseEntity<?> addTime(TimeRequest timeRequest) throws ParseException;



}
