package com.microservice.orderservice.constants;

public interface ValidationMessages {
    String ORDER_CONFIRMATION_MESSAGE = "Order successfully confirmed";
    String FAILURE_MESSAGE = "Failure";
    String FAILURE_STATUS = "0";
    String MSG_INTERNAL_SERVER_ERROR = "Internal server error";
    String SUCCESS_MESSAGE = "Success";
    String SUCCESS_MESSAGE_EMAIL_ERROR = "Email not sent ";

    String SUCCESS_STATUS = "1";
    String MSG_UNAUTHORISED = "Access denied for requested URL";

    String MSG_INVALID_JWT = "Invalid JWT token in request header !!";
    String MSG_SESSION_TIMEOUT = "Session timeout !!";
}
