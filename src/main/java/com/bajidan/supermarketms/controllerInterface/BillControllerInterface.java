package com.bajidan.supermarketms.controllerInterface;

import com.bajidan.supermarketms.dto.bill.PostBill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/bill")
public interface BillControllerInterface {
    @PostMapping("/generateReport")
    ResponseEntity<String> generateReport(@RequestBody PostBill bill);
}
