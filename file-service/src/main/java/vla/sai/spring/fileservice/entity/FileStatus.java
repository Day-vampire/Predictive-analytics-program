package vla.sai.spring.fileservice.entity;

public enum FileStatus {
    NEW,        // файл только прибыл в микросервис и не проверен
    VALID,      // файл проверен и он нормальнный
    NOT_VALID,  // файл проверен и он ненормальнный
    CORRUPTED   // файл проверен и он поврежден
}
