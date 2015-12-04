package com.jaoapps;

// Encargado de procesar los datos que se encuentran en la cola
public class Receiver {
    public void receiveMessage(String message) {
        System.out.println("Mensaje '" + message + "' recibido.");
        if (Application.DORMIR) {
            try {
                Thread.sleep(Application.TIEMPO_DORMIR);
//                throw new RuntimeException(); // Tras petar, el método vuelve a ejecutarse con el mismo mensaje.
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        System.out.println("Mensaje '" + message + "' procesado.\n");
    }
}
