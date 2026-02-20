package org.example;

public class RankCliente implements Comparable<RankCliente> {
    String key;
    int seguidores;

    public RankCliente(String key, int seguidores) {
        this.key = key;
        this.seguidores = seguidores;
    }

    @Override
    public int compareTo(RankCliente o) {
        // orden principal: seguidores
        int cmp = Integer.compare(this.seguidores, o.seguidores);
        if (cmp != 0) return cmp;

        // desempate: key (para que el ABB sea estable)
        return this.key.compareTo(o.key);
    }

    @Override
    public String toString() {
        return key + " (seguidores=" + seguidores + ")";
    }
}
