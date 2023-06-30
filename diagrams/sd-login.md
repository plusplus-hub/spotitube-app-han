# Sequence Diagram: Login

``` plantuml
@startuml
actor User
participant "LoginResource" as lRes
participant "UserService" as uSer
participant "TokenService" as tSer
participant "UserDao" as uDao
participant "TokenDao" as tDao

User -> lRes: POST /login {UserDTO}
activate lRes
lRes -> uSer: login(UserDTO)
activate uSer
uSer -> uDao: foundUserDTO = findByUsername(UserDTO.username)
deactivate uDao
uSer -> uSer: checkPassword(UserDTO.password, FoundUserDTO.password)
activate uSer

uSer --> lRes: UserDTO
deactivate uSer

lRes -> tSer: createToken(UserDTO)
activate tSer

tSer -> tSer: newToken = generateToken()
tSer -> tDao: saveToken(newToken)
deactivate tDao
tSer --> lRes: newToken
deactivate tSer
lRes --> User: newToken
deactivate lRes
@enduml
```