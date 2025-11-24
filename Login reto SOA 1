import logging
from spyne.application import Application
from spyne.decorator import rpc
from spyne.model.primitive import Unicode, Float, Boolean 
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication
from wsgiref.simple_server import make_server
import spyne

logging.basicConfig(level=logging.DEBUG)


class AuthService(spyne.Service):
    __target_namespace__ = 'urn:auth'
    
    
    @rpc(Unicode, Unicode, _returns=Boolean)
    def login(self, username, password):
      
        USUARIO_VALIDO = "admin"
        CLAVE_VALIDA = "12345"
        
        if username == USUARIO_VALIDO and password == CLAVE_VALIDA:
            print(f"Login exitoso para el usuario: {username}")
            return True
        else:
            print(f"Login fallido para el usuario: {username}")
            return False


application = Application([AuthService],
    tns='urn:auth',
    in_protocol=Soap11(),
    out_protocol=Soap11()
)

wsgi_app = WsgiApplication(application)


server = make_server('127.0.0.1', 8001, wsgi_app)

print("Servidor SOAP de Autenticación iniciado en http://127.0.0.1:8001")
print("WSDL disponible en http://127.0.0.1:8001/?wsdl")



if __name__ == '__main__':
    server.serve_forever()