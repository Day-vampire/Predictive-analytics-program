package vla.sai.spring.reportservice.entity;

public enum FileStatus {
    VALID,      // файл проверен и он нормальнный
    NOT_VALID,  // файл проверен и он ненормальнный
    CORRUPTED   // файл проверен и он поврежден
}
