package com.MKaaN.OtelBackend.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private String id;
    private String userId;
    private String roomId;
    private int rating;
    private String comment;
    private LocalDateTime reviewDate;
    private boolean isVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
