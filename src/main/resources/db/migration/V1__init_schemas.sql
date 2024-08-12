CREATE TABLE "personal_data" (
    "id" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "vorname" VARCHAR(255) NOT NULL,
    "strasse" VARCHAR(255),
    "nummer" VARCHAR(255),
    "zusatz" VARCHAR(255),
    "PLZ" VARCHAR(255),
    "ort" VARCHAR(255),
    "geburtsdatum" DATE NOT NULL,
    "AHVN13" VARCHAR(255) NOT NULL,
    "zivilstand" VARCHAR(255) NOT NULL,
    "konfession" VARCHAR(255) NOT NULL,
    "beruf" VARCHAR(255),
    "telefon" VARCHAR(10),
    "email" VARCHAR(255),
    "PID" VARCHAR(255) NOT NULL,
    "pensionskasse" BOOLEAN NOT NULL,
    "gemeinde" VARCHAR(255) NOT NULL,
    "gemeinde2" VARCHAR(255)
);

CREATE TABLE "wage_statement" (
    "id" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "personalDataId" BIGINT NOT NULL,
    "von" VARCHAR(255) NOT NULL,
    "bis" VARCHAR(255),
    "arbeitGeber" VARCHAR(255),
    "nettolohn" VARCHAR(255)
);

