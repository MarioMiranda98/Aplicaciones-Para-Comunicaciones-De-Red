/**
 *
 * @author axel
 */
public class Hilo2 implements Runnable{
    String mensaje;
    public Hilo2(String msj){
        this.mensaje=msj;
    }//constructor
    
    void m1(){
        System.out.println("Dentro del metodo 1..");
    }//m1
    void m2(){
        System.out.println("Dentro del metodo 2..");
    }//m2
    public void run(){
        System.out.println("El hilo: "+this.mensaje+" esta en ejecucion.. obteniendo sus propiedades");
        System.out.println("Nombre:"+Thread.currentThread().getName()+" Id:"+Thread.currentThread().getId()+ " Prioridad:"+Thread.currentThread().getPriority()+" Grupo:"+Thread.currentThread().getThreadGroup().getName()+" Estado:"+Thread.currentThread().getState());
        m1();
        m2();
        System.out.println(""+Thread.currentThread().toString());
    }//run
    
    public static void main(String[] args){
     Hilo2 h1 = new Hilo2("hilo 1");    
     Hilo2 h2 = new Hilo2("hilo 2");
     Thread t1 = new Thread(h1);
     Thread t2 = new Thread(h2);
     t1.start();
     t2.start();
    }//main
}