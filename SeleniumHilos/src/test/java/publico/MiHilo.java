package publico;

import org.testng.annotations.Test;

//Ejemplo para suspender, pausar y reanudar un Hilo
class MiHilo implements Runnable{
    Thread hilo;
    boolean suspender; //Suspende un hilo cuando es true
    boolean pausar;    //Detiene un hilo cuando es true
    MiHilo (String nombre){
        hilo=new Thread(this,nombre);
        suspender=false;
        pausar=false;
    }
    public static MiHilo crearEIniciar(String nombre){
        MiHilo miHilo=new MiHilo(nombre);
        miHilo.hilo.start(); //Iniciar el hilo
        return miHilo;
    }
    
    @Test    
    public void arranca() {
    	MiHilo hola = new MiHilo("hola");
    	System.out.println(hola);
    }
    public void run() {
        System.out.println(hilo.getName()+ " iniciando.");
        try {
            for (int i=1;i<1000;i++){
                System.out.print(i+" ");
                if ((i%10)==0){
                    System.out.println();
                    Thread.sleep(250);
                }
                synchronized (this) {
                    while (suspender) {
                        wait();
                    }
                    if (pausar) break;
                }
            }
        }catch (InterruptedException exc){
            System.out.println(hilo.getName()+ "interrumpido.");
        }
        System.out.println(hilo.getName()+ " finalizado.");
    }
    //Pausar el hilo
    synchronized void pausarhilo(){
        pausar=true;
        //lo siguiente garantiza que un hilo suspendido puede detenerse.
        suspender=false;
        notify();
    }
    //Suspender un hilo
    synchronized void suspenderhilo(){
        suspender=true;
    }
    //Renaudar un hilo
    synchronized void renaudarhilo(){
        suspender=false;
        notify();
    }
}
class Suspender {
    public static void main(String[] args) {
        MiHilo mh1=MiHilo.crearEIniciar("Mi Hilo");
        try {
            Thread.sleep(1000);//dejar que el primer hilo comience a ejecutarse
            mh1.suspenderhilo();
            System.out.println("Suspendiendo Hilo.");
            Thread.sleep(1000);
            mh1.renaudarhilo();
            System.out.println("Renaudando Hilo.");
            Thread.sleep(1000);
            mh1.suspenderhilo();
            System.out.println("Suspendiendo Hilo.");
            Thread.sleep(1000);
            mh1.renaudarhilo();
            System.out.println("Renaudando Hilo.");
            Thread.sleep(1000);
            mh1.suspenderhilo();
            System.out.println("Pausando Hilo.");
            mh1.pausarhilo();
        }catch (InterruptedException e){
            System.out.println("Hilo principal interrumpido.");
        }
        //esperar a que el hilo termine
        try {
            mh1.hilo.join();
        }catch (InterruptedException e){
            System.out.println("Hilo principal interrumpido.");
        }
        System.out.println("Hilo principal finalizado.");
    }
}