package com.vay.bishopprototype.controller;

import com.vay.bishopprototype.service.BishopPrototypeService;
import com.vay.synthetichumancorestarter.model.Command;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BishopController {
    private final BishopPrototypeService bishopPrototypeService;

    @GetMapping("/process")
    public ResponseEntity<Boolean> isProcessorBusy() {
        return ResponseEntity.ok(bishopPrototypeService.isProcessorBusy());
    }

    @GetMapping
    public ResponseEntity<Integer> getQueueSize() {
        return ResponseEntity.ok(bishopPrototypeService.getQueueSize());
    }

    @PostMapping
    public ResponseEntity<?> submitCommand(@Valid @RequestBody Command command) {
        bishopPrototypeService.submitCommand(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{author}")
    public ResponseEntity<Integer> getAuthorCompleted(@PathVariable String author) {
        return ResponseEntity.ok(bishopPrototypeService.getAuthorCompleted(author));
    }
}
