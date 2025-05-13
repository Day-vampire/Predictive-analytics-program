package vla.sai.spring.notificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    DATA_REPORT_NOTIFICATION("Notification about data report"),
    SMOOTHING_GRAPH_REPORT_NOTIFICATION("Notification about smoothing report"),
    HOLT_WINTERS_REPORT_NOTIFICATION("Notification about holt winters report"),
    ACF_PACF_REPORT_NOTIFICATION("Notification about acf/pacf report"),
    ARIMA_REPORT_NOTIFICATION("Notification about arima report"),
    SARIMA_REPORT_NOTIFICATION("Notification about sarima report"),
    PREDICTION_NOTIFICATION("Notification about prediction"),;
    private final String value;
}
