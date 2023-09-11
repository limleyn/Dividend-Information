package triple.dividend.scraper;

import triple.dividend.model.Company;
import triple.dividend.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
