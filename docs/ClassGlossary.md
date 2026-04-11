# Glossário - The AISafe Flight Management System

Aquim ficam os principais conceitos do modelo de dominio

## 1 - Aircraft Aggregate

### 1.1 - Aircraft
- Representa a instancia de um avião que pertence a frota detida naquele aeroporto.

### 1.2 - Aircraft Model
- Representa o modelo do avião (ex: boeing xxx). Guarda as especificações do modelo.

### 1.3 - Aircraft Status
- Representa o estado atual do avião.


## 2 - Flight Aggregate

### 2.1 - Flight

- Onde estão as informações sobre um voo especifico.

### 2.2 - Flight Status

- Representa o estado atual de um voo.

## 3 - Maintenance Aggregate

### 3.1 - Maintenance Record

- Guarda a informação sobre as intervenções que as aeronaves tiveram

## 4 - Maintenance Template Aggregate

### 4.1 - Maintenance Template

- Template predefinido para preenchimento de uma ordem de manutenção.

## 5 - Route Aggregate

### 5.1 - Route

- Um rota prefinida entre um aeroporto de origem e um de destino.

## 6 - Airport Aggregate 

### 6.1 - Airport

- Um conjunto de aeroportos com dados que o identificam.

### 6.2 - Runway Info
- Informações sobre as pistas de aterragem dos aeroportos

### 6.3 - AiportStatus 
- Indicar em que estado se encontra o aeroporto

### 6.4 - ContactInfo
-Informações de contacto do aeroporto.
