package hexlet.code.configs.rollbar;

//@Configuration()
//@EnableWebMvc
//@ComponentScan({"hexlet.code"})
public class RollbarConfiguration {

    /*@Value("${ROLLBAR_TOKEN}")
    private String rollbarToken;

    @Value("${APP_ENV}")
    private String activeProfile;

    private static final String PROD = "prod";

    private static final String DEVELOPMENT = "development";

    @Bean
    public Rollbar rollbar() {
        return new Rollbar(getRollbarConfigs(rollbarToken));
    }

    private Config getRollbarConfigs(String accessToken) {
        return RollbarSpringConfigBuilder.withAccessToken(accessToken)
                .environment(DEVELOPMENT)
                .enabled(activeProfile.equals(PROD))
                .build();
    }*/
}
