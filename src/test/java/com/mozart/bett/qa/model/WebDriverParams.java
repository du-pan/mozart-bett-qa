package com.mozart.bett.qa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebDriverParams {
    private ChromeOptions chromeOpts;
    private EdgeOptions edgeOpts;
    private FirefoxOptions firefoxOpts;
    private SafariOptions safariOpts;
}
