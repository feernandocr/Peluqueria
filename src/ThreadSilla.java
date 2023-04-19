public class ThreadSilla extends Thread  {

    Cliente clienteAsignado;

    public ThreadSilla (Cliente clienteAsignado){
        this.clienteAsignado = clienteAsignado;
    }
    @Override
    public void run() {
        System.out.println("Bienvenido : "+ clienteAsignado.getNombre());

        System.out.println("Haremos el siguiente corte : "+ clienteAsignado.getCorte());
        try {
            /*Tiempo del corte aproximado 5 min*/
            sleep(50000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Se termino el corte");
    }
}
