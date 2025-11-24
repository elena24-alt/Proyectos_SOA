import logging
import spyne
from spyne.application import Application
from spyne.decorator import rpc
from spyne.model.primitive import Unicode, Integer, Float
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication
from wsgiref.simple_server import make_server


logging.basicConfig(level=logging.DEBUG)


class TemperatureConversionService(spyne.Service):
    __target_namespace__ = 'urn:temperaturas'

   
    @rpc(Float, _returns=Float)
    def c_to_f(self, celsius):
        
        fahrenheit = (celsius * 9/5) + 32
        print(f"Conversión C->F: {celsius}°C es {fahrenheit}°F")
        return fahrenheit

  
    @rpc(Float, _returns=Float)
    def f_to_c(self, fahrenheit):
       
        celsius = (fahrenheit - 32) * 5/9
        print(f"Conversión F->C: {fahrenheit}°F es {celsius}°C")
        return celsius

    
    @rpc(Float, _returns=Float)
    def f_to_k(self, fahrenheit):
        """Convierte una temperatura de Fahrenheit a Kelvin."""
       
        celsius = (fahrenheit - 32) * 5/9
        kelvin = celsius + 273.15
        print(f"Conversión F->K: {fahrenheit}°F es {kelvin}K")
        return kelvin



application = Application([TemperatureConversionService],
    tns='urn:temperaturas',
    in_protocol=Soap11(validator='lxml'),
    out_protocol=Soap11()
)


wsgi_app = WsgiApplication(application)


server = make_server('127.0.0.1', 8000, wsgi_app)

print("Servidor SOAP iniciado en http://127.0.0.1:8000")
print("WSDL disponible en http://127.0.0.1:8000/?wsdl")

if __name__ == '__main__':
   
    server.serve_forever()