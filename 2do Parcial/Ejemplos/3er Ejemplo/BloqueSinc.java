
/**
 *
 * @author axel
 */
public class BloqueSinc implements Runnable{
    StringBuffer sb;
    int contador;
    public BloqueSinc(){
        sb = new StringBuffer();
        contador=1;
    }//constructor
    
    public void run(){
        synchronized(sb){
            System.out.println("Comienza un bloque sincronizado...");
            int tmp = contador ++;
            tmp = contador ++;
            String mensaje = "La cuenta es: "+tmp+System.getProperty("line.separator");
            try{
                Thread.sleep(100);
            }catch(InterruptedException ie){ }//catch
            sb.append(mensaje);
            System.out.println("Termina el bloque sincronizado..");
        }//synchronized
    }//run
    
    public static void main(String[] args)throws Exception{
        BloqueSinc bs = new BloqueSinc();
        Thread t1 = new Thread(bs);
        Thread t2 = new Thread(bs);
        Thread t3 = new Thread(bs);
        Thread t4 = new Thread(bs);
        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join(); t2.join(); t3.join(); t4.join();
        System.out.println(bs.sb);
    }//main
}