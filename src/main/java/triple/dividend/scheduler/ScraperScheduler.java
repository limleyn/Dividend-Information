package triple.dividend.scheduler;

import triple.dividend.constants.CacheKey;
import triple.dividend.model.Company;
import triple.dividend.model.ScrapedResult;
import triple.dividend.persist.CompanyRepository;
import triple.dividend.persist.DividendRepository;
import triple.dividend.persist.entity.CompanyEntity;
import triple.dividend.persist.entity.DividendEntity;
import triple.dividend.scraper.Scraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableCaching
@AllArgsConstructor
public class ScraperScheduler {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    private final Scraper yahooFinanceScraper;

    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    // "${scheduler.scrap.yahoo}" 알수없는 오류로 인한 직접 입력
    @Scheduled(cron = "0 0 0 * * *")
    public void yahooFinanceScheduling() {
        log.info("scraping scheduler is started");

        List<CompanyEntity> companies = this.companyRepository.findAll();


        for (var company : companies) {
            log.info("scraping scheduler is started -> " + company.getName());
            ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(new Company(company.getTicker(), company.getName()));

            scrapedResult.getDividends().stream()

                    .map(e -> new DividendEntity(company.getId(), e))

                    .forEach(e -> {
                        boolean exists = this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());
                        if (!exists) {
                            this.dividendRepository.save(e);
                            log.info("insert new dividend -> " + e.toString());
                        }
                    });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
