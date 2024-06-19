# login-spring 
## Dependências  
###### - Spring Web
###### - Spring Data JPA
###### - Spring Security
###### - DevTools
###### - Lombok


## 1º - Criando Token
### Criar algoritmo de criptografia
```
JWT.create()
Type Algorithm
Hash HMAC256(<Passar uma secret key>)
```

## 2º - Validando Token
### Validar algoritmo de criptografia
```
JWT.require
```

## 3º Criando o filtro de segurançar - SecurityFilter