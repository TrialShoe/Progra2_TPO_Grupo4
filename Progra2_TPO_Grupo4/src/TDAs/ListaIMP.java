package TDAs;

public class ListaIMP<T> implements ListaTDA<T> {
    private T[] elementos;
    private int contador = 0;

    @SuppressWarnings("unchecked")
    public ListaIMP(int capacidad) {
        elementos = (T[]) new Object[capacidad];
    }

    public void insertar(int pos, T elemento) {
        for (int i = contador; i > pos; i--) { // 1
            elementos[i] = elementos[i - 1];
        }
        elementos[pos] = elemento; // 1
        contador++; // 1
    }

    public void agregar(T elemento) {
        elementos[contador++] = elemento;
    }

    public T obtener(int indice) {
        return elementos[indice];
    }

    public void imprimirLista() {
        for (int i = 0; i < contador; i++) {
            System.out.println(elementos[i]);
        }
    }

    @Override
    public int largo() {
        return contador;
    }

}