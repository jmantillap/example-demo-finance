# Proyecto de prueba técnica

Manejo de clientes, productos y transacciones, Prueba técnica

![screenshot](img/diagrama-ER.png)

## Proceso de montaje del proyecto 

1. En la carpeta /DataBase/demo-finance20240902.sql, se encuentra el Script de montaje de la base de datos en MYSQL

2. En la carpeta /postman, Se ecuentra el proyecto con todos los servicios requeridos para la funcionalidad  



## Ejecutando los servicios

* Requisito importante para ejecutar todos los servicios, se requiere de colocar la cookie como header a cada servicio

* La cookie se obtiene ejecutando el servicio:  
     localhost:8080/api/security/login

![screenshot](img/seguridad_peticiones.png)


## Pruebas unitarias de cobertura

![screenshot](img/cobertura.png)



## License

[MIT](https://choosealicense.com/licenses/mit/)
