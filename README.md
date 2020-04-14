# payment-service
## Project Launch
**Prerequisites**
- Java 11
- Maven 3.2.5+

**Build project**

`mvn clean install`

**Launch application locally**

`mvn spring-boot:run`

## Work with application
Once application is launched open [http://localhost:8080/]() in your browser. There will be redirect to Swagger UI

Application has hardcoded users:
- User: client1, pass: 123
- User: client2, pass: 123

## Requirements
Requirements are described at [docs/requirements.docx]()

## Implemented functionality
- Payment creation
- Payment cancellation
- Payments querying (only list with details. without filtering)
- Client country logging
- Notification

### Client country logging

### Notification
Payment notifications are implemented through Spring `ApplicationListener<NotificationEvent>`. For common async processing of all payment notifications [src/java/lv/dp/education/ps/notification/listener/BaseNotificationEventListener.java](BaseNotificationEventListener) is created 