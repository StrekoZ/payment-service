# payment-service
## Project Launch
**Prerequisites**
- Java 11
- Maven 3.2.5+

**Launch application locally**

`mvn spring-boot:run`

## Work with application
Once application is launched open http://localhost:8080/ in your browser. There will be redirect to Swagger UI

Application has hardcoded users:
- User: client1, pass: 123
- User: client2, pass: 123

## Requirements
Requirements are described at [requirements.docx](doc/requirements.docx)

## Implemented functionality
- Payment creation
- Payment cancellation
- Payments querying (only list with active payments and details by UUID. Filtering is not impemented. As well you can't list cancelled payments)
- Client country logging
- Notification

### Client country logging
All HTTP requests are intercepted with default Spring `CommonsRequestLoggingFilter` (see [RequestLoggingFilterConfig](src/main/java/lv/dp/education/ps/configuration/RequestLoggingFilterConfig.java))

To avoid application delays - country resolving and print is asynchronous. Countries are also cached in-memory by requested IP address (see [CacheConfig](src/main/java/lv/dp/education/ps/configuration/CacheConfig.java) and [CountryResolver](src/main/java/lv/dp/education/ps/common/country/CountryResolver.java) )

### Notification
Payment notifications are implemented through Spring `ApplicationListener<NotificationEvent>`. 
Base of notification processing: [BaseNotificationEventListener](src/main/java/lv/dp/education/ps/notification/listener/BaseNotificationEventListener.java)

