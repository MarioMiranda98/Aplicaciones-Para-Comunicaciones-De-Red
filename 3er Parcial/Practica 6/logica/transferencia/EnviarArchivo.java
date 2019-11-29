package logica.transferencia;

import java.nio.channels.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import logica.TransferenciaListener;


public class EnviarArchivo {

    public EnviarArchivo(int puertoRuta, TransferenciaListener listener) throws RemoteException, AlreadyBoundException, java.rmi.AlreadyBoundException {
        final ObjRemoto objeto = new ObjRemoto(puertoRuta, listener);
        final Remote stub = UnicastRemoteObject.exportObject(objeto, 0);
        final Registry registry = LocateRegistry.createRegistry(puertoRuta + 100);
        System.out.println(puertoRuta);
        registry.bind("transferir", stub);
        System.out.println("Servidor ON");
    }
}