package com.bajidan.supermarketms.serviceImp;

import com.bajidan.supermarketms.constant.Constant;
import com.bajidan.supermarketms.dto.bill.PostBill;
import com.bajidan.supermarketms.dto.bill.ProductDetailsDTO;
import com.bajidan.supermarketms.dto.bill.TotalPrice;
import com.bajidan.supermarketms.jwt.JwtFilter;
import com.bajidan.supermarketms.model.Bill;
import com.bajidan.supermarketms.model.ProductDetails;
import com.bajidan.supermarketms.repository.BillRepository;
import com.bajidan.supermarketms.serviceInterface.BillServiceInterface;
import com.bajidan.supermarketms.util.BillUtil;
import com.bajidan.supermarketms.util.HttpMessageUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class BillService implements BillServiceInterface {
    private final BillRepository billRepository;
    private final JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> generateReport(PostBill bill) {
        try {
            String fileName =  BillUtil.getUUID();;

            String data = header(bill);

            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(Constant.STORE_LOCATION + "\\" + fileName + ".pdf"));
            document.open();

            setRectangleInPdf(document);

            Paragraph chunk = new Paragraph("Super Market Management System", getFont("Header"));
            chunk.setAlignment(Element.ALIGN_CENTER);
            document.add(chunk);

            Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
            document.add(paragraph);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            addTableHeader(table);

            addRow(table, bill.getProductDetailsDTOList());

            document.add(table);

            Paragraph footer = new Paragraph("Total: " + getTotalPrice(bill)
                    + "\nThank you for shopping with us", getFont("Data"));
            document.add(footer);

            document.close();

            insertBill(bill);

            return new ResponseEntity<>("{\n\"uuid\":\"" + fileName + "\"\n}", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpMessageUtil.internalServerError();

    }

    private void addRow1(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRow");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));

    }

    private void addRow(PdfPTable table, List<ProductDetailsDTO> productDetails) {
        log.info("Inside addRow");
        productDetails.forEach(productDetailsDTO -> {
            table.addCell(productDetailsDTO.getName());
            table.addCell(productDetailsDTO.getCategory());
            table.addCell(String.valueOf(productDetailsDTO.getQuantity()));
            table.addCell(String.valueOf(productDetailsDTO.getPrice()));
            table.addCell(String.valueOf(productDetailsDTO.getTotal()));
        });

    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Category", "Quantity", "Price", "Sub Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private Font getFont(String type) {
        log.info("inside header");
        switch (type) {
            case "Header" -> {
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            }
            case "Data" -> {
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            }
            default -> {
                return new Font();
            }
        }

    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rectangle =new Rectangle(577, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);

        document.add(rectangle);
    }

    private void insertBill(PostBill postBill) {

        Bill bill = new Bill();

        bill.setUuid(BillUtil.getUUID());
        bill.setName(postBill.getName());
        bill.setEmail(postBill.getEmail());
        bill.setContactNumber(postBill.getContactNumber());
        bill.setPaymentMethod(postBill.getPaymentMethod());
        bill.setTotal(getTotalPrice(postBill));
        bill.setCreatedBy(jwtFilter.getCurrentUsername());
        bill.setProductDetails(getProductDetails(postBill.getProductDetailsDTOList()));

        billRepository.save(bill);
    }

    public double getTotalPrice(PostBill postBill) {
        List<TotalPrice> totalPricesList = postBill.getProductDetailsDTOList()
                .stream()
                .map(productDetails ->
                    new TotalPrice(productDetails.getTotal())
        ).toList();

        double total = 0;

        for (TotalPrice price : totalPricesList) {
            total += price.total();
        }
        return total;
    }

    private String header(PostBill postBill) {
        return "Name: " + postBill.getName() + "\nContact Number: " + postBill.getContactNumber() +
                "\nEmail: " + postBill.getEmail() +
                "\nPayment Method: " + postBill.getPaymentMethod();
    }

    public List<ProductDetails> getProductDetails(List<ProductDetailsDTO> productDetailsDTOList) {
        log.info("Inside ListGetDetails");

        return productDetailsDTOList
                .stream().map(productDetailsDTO -> new ProductDetails(
                    productDetailsDTO.getId(), productDetailsDTO.getName(), productDetailsDTO.getCategory(),
                    productDetailsDTO.getQuantity(), productDetailsDTO.getPrice(),
                    productDetailsDTO.getTotal()
                        )).toList();
    }

}




