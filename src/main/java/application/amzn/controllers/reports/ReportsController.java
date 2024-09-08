package application.amzn.controllers.reports;

import application.amzn.entities.Report;
import application.amzn.exceptions.primitives.NotFoundException;
import application.amzn.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("reports")
public class ReportsController {
    private final ReportsService service;

    @Autowired
    public ReportsController(ReportsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Report> getReport(@RequestParam(required = false) Instant date) {
        if (date != null) {
            var sales = service.findByDate(date);
            if (sales.isEmpty()) {
                throw new NotFoundException("Não há vendas nessa data.");
            }

            var report = service.generateReport(sales);
            return ResponseEntity.ok(report);
        }

        var sales = service.findAll();
        if (sales.isEmpty()) {
            throw new NotFoundException("Não há vendas registradas no sistema.");
        }
        var report = service.generateReport(sales);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/month")
    public ResponseEntity<Report> getMonthlyReport(@RequestParam() Instant date) {
        var sales = service.findByMonth(date);
        if (sales.isEmpty()) {
            throw new NotFoundException("Não há vendas nesse mês.");
        }

        var report = service.generateReport(sales);
        return ResponseEntity.ok(report);
    }
}
