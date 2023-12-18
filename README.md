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

### İçerik:
 - DatabaseManager
 - Router
 - JwtManager
 - TableManager
 - Main
