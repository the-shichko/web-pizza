package com.bsuir.webpizza.controllers;

import com.bsuir.webpizza.db.repo.MyOrderPizzaRepository;
import com.bsuir.webpizza.db.repo.MyOrderRepository;
import com.bsuir.webpizza.db.repo.PizzaRepository;
import com.bsuir.webpizza.helper.HelperService;
import com.bsuir.webpizza.viewmodels.FullOrder;
import com.itextpdf.text.*;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
public class OrderController {
    private final HelperService helperService;
    private final MyOrderRepository orderRepository;

    public OrderController(PizzaRepository pizzaRepository, MyOrderRepository orderRepository, MyOrderPizzaRepository myOrderPizzaRepository) {
        helperService = new HelperService(pizzaRepository, myOrderPizzaRepository, orderRepository);
        this.orderRepository = orderRepository;
    }

    @PostMapping("/order")
    public ResponseEntity<Boolean> saveOrder(@RequestBody String backet) {
        return new ResponseEntity<>(helperService.saveOrder(backet), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public String getOrders(Model model) throws IOException {
        var result = orderRepository.getFullList();
        model.addAttribute("orders", result);
        return "orders";
    }

    @PostMapping("/orders/close")
    public ResponseEntity<Boolean> closeOrder(@RequestBody Long orderId) {
        var order = orderRepository.findById(orderId);
        order.get().Status = "Completed";
        orderRepository.save(order.get());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/orders/pdf")
    public ResponseEntity<byte[]> getPdfFile() throws IOException, DocumentException {
        var orders = orderRepository.getFullList();
        var fileName = System.getProperty("user.dir") + "\\orders.pdf";
        var document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        var table = new PdfPTable(3);

        PdfPCell c1 = new PdfPCell(new Phrase("OrderId"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Status"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        for (FullOrder item : orders) {
            var cell1 = new PdfPCell(new Phrase(item.Number));
            var cell2 = new PdfPCell(new Phrase(String.format("%.2f", item.Price) + " BYN"));
            var cell3 = new PdfPCell(new Phrase(item.Status));
            if (Objects.equals(item.Status, "Completed")){
                var green = new BaseColor(25, 135, 84);
                cell1.setBackgroundColor(green);
                cell2.setBackgroundColor(green);
                cell3.setBackgroundColor(green);
            }
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
        }

        document.add(table);
        document.close();

        var fileBytes = Files.readAllBytes(Paths.get(fileName));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); // (3) Content-Type: application/octet-stream
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(fileName).build().toString()); // (4) Content-Disposition: attachment; filename="demo-file.txt"
        return ResponseEntity.ok().headers(httpHeaders).body(fileBytes); // (5) Return Response
    }
}
