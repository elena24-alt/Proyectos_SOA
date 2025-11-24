from spyne.application import Application
from spyne.decorator import srpc
# Usamos Unicode para ambos parámetros de entrada para simplificar la conversión
from spyne.model.primitive import Unicode, Integer
from spyne.server.wsgi import WsgiApplication
from spyne.service import ServiceBase
from spyne.protocol.soap import Soap11 
from spyne.protocol.http import HttpRpc
from wsgiref.simple_server import make_server

class DispositivosService(ServiceBase):
    
   
    @srpc(Unicode, Unicode, _returns=Unicode) 
    def registrar_dispositivo(nombre_dispositivo, id_usuario):
        
        
        try:
            id_usuario_int = int(id_usuario)
        except ValueError:
            return f"Error: El ID de usuario '{id_usuario}' debe ser un número entero."

        print(f"Petición SOAP recibida: Dispositivo '{nombre_dispositivo}' para Usuario ID: {id_usuario_int}")
        
        return f"Dispositivo '{nombre_dispositivo}' registrado con éxito en el servidor."
    
   
    @srpc(Unicode, Unicode, _returns=Unicode) # Cambié a Unicode para id_usuario aquí también
    def obtener_resumen_diario(id_usuario, fecha):
       
        
        try:
            id_usuario_int = int(id_usuario)
        except ValueError:
            return f"Error: El ID de usuario '{id_usuario}' debe ser un número entero."
            
        print(f"Petición SOAP recibida: Resumen de actividad para Usuario ID: {id_usuario_int} en la fecha: {fecha}")
      
        resumen = f"Resumen para Usuario {id_usuario_int} ({fecha}): 12,500 pasos, 700 calorías quemadas, 8 horas de sueño."
        
        return resumen

 
    @srpc(Unicode, Integer, _returns=Unicode)
    def procesar_frecuencia_cardiaca(id_usuario, frecuencia):
       
        try:
            id_usuario_int = int(id_usuario)
        except ValueError:
            return f"Error: El ID de usuario '{id_usuario}' debe ser un número entero."

        nivel = "Normal"
        if frecuencia > 150:
            nivel = "Alta (Alerta)"
        
        print(f"Petición SOAP recibida: Frecuencia {frecuencia} para Usuario {id_usuario_int}. Nivel: {nivel}")
        
        return f"Registro de frecuencia cardíaca ({frecuencia} LPM) procesado. Nivel: {nivel}"
    


if __name__ == '__main__':
    
    application = Application([DispositivosService],
                              tns='spyne.examples.dispositivos', 
                              in_protocol=Soap11(), # <--- Usar SOAP11 para entrada
                              out_protocol=Soap11()) # <--- Usar SOAP11 para salida

    wsgi_app = WsgiApplication(application)
    host = '0.0.0.0'
    port = 8000 
    server = make_server(host, port, wsgi_app)

    print("-" * 50)
    print(f"Servidor SOAP de Dispositivos Corriendo en http://127.0.0.1:{port}/")
    print("Para ver el WSDL: http://127.0.0.1:8000/?wsdl")
    print("-" * 50)
    server.serve_forever()