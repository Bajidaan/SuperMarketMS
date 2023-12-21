package com.bajidan.supermarketms.serviceInterface;

import com.bajidan.supermarketms.dto.bill.PostBill;
import org.springframework.http.ResponseEntity;

public interface BillServiceInterface {
    ResponseEntity<String> generateReport(PostBill bill);

}
