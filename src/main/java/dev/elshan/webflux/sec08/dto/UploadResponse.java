package dev.elshan.webflux.sec08.dto;

import java.util.UUID;

public record UploadResponse(UUID confirmationNumber, Long productsCount) {
}
