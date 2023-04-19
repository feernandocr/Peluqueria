import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BusquedaClientes  implements Busqueda{
    @Override
    public void executeBusqueda() {
        long tInicial    = 0;
        long tFinal      = 0;
        long lTranscurre = 0;
        boolean bandera  = true;

        tInicial = System.currentTimeMillis();
        while (bandera) {
            System.out.println("Barberia Abierta ..." );
            procesaExecutor();
            tFinal = System.currentTimeMillis();
            lTranscurre = ((tFinal - tInicial) / 1000);

//			if (lTranscurre >= 570) {
//				bandera = false;
//			}
        }
    }

    @Override
    public void procesaExecutor() {

        Integer                  threadCounter = 0;
        BlockingQueue<Runnable> blockingQueue;
        CustomThreadPoolExecutor executor;

        Integer maxSillas = 30;
        Integer numBarbero = 1;
        Integer maxNumBarberos=1;
        /*Buscando clientes*/
        List<Cliente> clientes= new ArrayList<>();
        clientes = monitorBusqueda();

        if (clientes.isEmpty()){
            System.err.println("El barbero se sienta en la silla de peluquero y se duerme.");
        }else{
            long tInicial = 0;
            long tFinal   = 0;

            tInicial       = System.currentTimeMillis();
            blockingQueue  = new ArrayBlockingQueue<Runnable>(maxSillas);
            executor       = new CustomThreadPoolExecutor(numBarbero,
                                                            maxNumBarberos,
                                                        10000,
                                                        TimeUnit.MILLISECONDS, blockingQueue);

            executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                    System.out.println(this.getClass() + " procesaExecutor " + "Waiting for a second !!" + " INFO");

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(this.getClass() + " procesaExecutor Lets add another time : " + " INFO");

                    executor.execute(r);
                }
            });
            // Let start all core threads initially
            System.out.println("Numero de hilos--->" + executor.prestartAllCoreThreads());

            for (Cliente informacion : clientes) {
                threadCounter++;
                executor.execute(new ThreadSilla(informacion));
                System.out.println("Adding DemoTask : " + threadCounter);

            }

            executor.shutdown();
            while (!executor.isTerminated()) {
            }

            tFinal = System.currentTimeMillis();
           System.out.println(this.getClass() + " procesaExecutor Tiempo procesar general-> " + ((tFinal - tInicial) / 1000) + " INFO");
        }


    }

    private List<Cliente> monitorBusqueda() {
        /*1er caso Si no hay clientes el barbero se sienta en la silla de peluquero
         y se duerme. Cuando llega un
         cliente debe despertar al barbero.*/
//        List<Cliente> list = new ArrayList<>();


        /*2do caso*/
        List<Cliente> list = new ArrayList<>();

        Cliente cliente1 = new Cliente();
        cliente1.setNombre("Alfonso");
        cliente1.setCorte("Moicano");

        Cliente cliente2 = new Cliente();
        cliente2.setNombre("Daniel");
        cliente2.setCorte("Pelon");

        Cliente cliente3 = new Cliente();
        cliente3.setNombre("Ale");
        cliente3.setCorte("Pelon");

        Cliente cliente4 = new Cliente();
        cliente4.setNombre("Ellie");
        cliente4.setCorte("Militar");

        Cliente cliente5 = new Cliente();
        cliente5.setNombre("Aria");
        cliente5.setCorte("Honguito");

        list.add(cliente1);
        list.add(cliente2);
        list.add(cliente3);
        list.add(cliente4);
        list.add(cliente5);

        return list;
    }
}
