# SPARK-API
Javada spark kullanilarak mysql detsekli bir sablon gelistirilmeye musait .


## Kullanım:

 #### Login:
```Java
POST /login?username={username}&password={password}
```

 #### Register
```Java
POST /register?username={username}&password={password}&email={email}
```

 #### Logout:
```Java
POST /logout
```


## İçerik:

- **DatabaseManager:** Veritabanı işlemlerini yönetir.
- **Router:** Spark framework'ü kullanarak API endpoint'lerini yönetir.
- **JwtManager:** JSON Web Token (JWT) oluşturma, doğrulama ve işlemleri yönetir.
- **TableManager:** Veritabanı tablolarını oluşturmayı yönetir.
- **Main:** Uygulamanın başlangıç noktasını oluşturur ve Spark framework'ünü başlatır.

## Proje Geliştirme

Bu projeyi geliştirmeye katkıda bulunmak isterseniz:

