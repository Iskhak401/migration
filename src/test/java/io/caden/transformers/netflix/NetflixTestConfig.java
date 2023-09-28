package io.caden.transformers.netflix;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Prevents loading the whole application context in the Netflix tests.
 *
 * @author Mihail Radkov
 */
@Configuration
@ComponentScan({
    "io.caden.transformers.config",
    "io.caden.transformers.netflix.*",
    "io.caden.transformers.shared.*"
})
class NetflixTestConfig {

}
