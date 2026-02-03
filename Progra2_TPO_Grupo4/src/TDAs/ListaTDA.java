package TDAs;

public interface ListaTDA<T> {
    void insertar(int pos, T elem);
    void agregar(T elem);
    T obtener(int pos);
    void imprimirLista();
    int largo();

}
