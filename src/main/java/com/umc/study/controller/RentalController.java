package com.umc.study.controller;

import com.umc.study.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createRental(
            @RequestBody Map<String, Object> body
    ) {
        rentalService.createRental(body);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "도서 대여가 완료되었습니다.",
                        "userId", body.get("userId"),
                        "bookId", body.get("bookId")
                ));
    }
    @PatchMapping("/{rentalId}/return")
    public ResponseEntity<Map<String, Object>> returnRental(
            @PathVariable Long rentalId
    ) {
        rentalService.returnRental(rentalId);

        return ResponseEntity.ok(
                Map.of(
                        "message", "도서 반납이 완료되었습니다.",
                        "rentalId", rentalId
                )
        );
    }
}