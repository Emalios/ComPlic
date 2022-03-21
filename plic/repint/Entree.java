package repint;

public class Entree {

    private final String idf;

    public Entree(String idf) { this.idf = idf; }

    public String getIdf() {
        return idf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entree entree = (Entree) o;

        return idf != null ? idf.equals(entree.idf) : entree.idf == null;
    }

    @Override
    public int hashCode() {
        return idf != null ? idf.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.idf;
    }
}
