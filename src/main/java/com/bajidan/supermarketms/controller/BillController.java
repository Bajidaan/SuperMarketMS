package com.bajidan.supermarketms.controller;

import com.bajidan.supermarketms.controllerInterface.BillControllerInterface;
import com.bajidan.supermarketms.dto.bill.PostBill;
import com.bajidan.supermarketms.serviceImp.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class BillController implements BillControllerInterface {
    private final BillService billService;
    @Override
    public ResponseEntity<String> generateReport(PostBill postBill) {
        return billService.generateReport(postBill);
    }
}
