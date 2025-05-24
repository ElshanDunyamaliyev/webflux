package dev.elshan.webflux.sec09.dto;

import java.util.UUID;

public record UploadResponse(UUID confirmationNumber, Long productsCount) {
}
