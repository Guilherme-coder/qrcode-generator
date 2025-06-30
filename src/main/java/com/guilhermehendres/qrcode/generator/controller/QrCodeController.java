package com.guilhermehendres.qrcode.generator.controller;

import com.guilhermehendres.qrcode.generator.dto.QrCodeRequestDTO;
import com.guilhermehendres.qrcode.generator.dto.QrCodeResponseDTO;
import com.guilhermehendres.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    public QrCodeController(QrCodeGeneratorService qrCodeGeneratorService) {
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }
    @PostMapping("/generate")
    public ResponseEntity<QrCodeResponseDTO> generate(@RequestBody QrCodeRequestDTO request) {
        try{
            QrCodeResponseDTO response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
